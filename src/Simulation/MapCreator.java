package Simulation;

import Model.TRoadIntersection;
import Model.Road;
import Model.CrossRoadIntersection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapCreator extends JPanel {
    private int roadCount=0;
    private java.util.List<Road> roads = new ArrayList<>();
    private List<String> mapData = new ArrayList<>();
    private JButton roadButton = new JButton("Click to add new Road to Design");
    private JButton maxLengthButton = new JButton("Set to Maximum Length Road");
    private JButton minLengthButton = new JButton("Set to Minimum Length Road");
    private JButton clearButton = new JButton("Click to Clear Map");
    private JButton rotateButton = new JButton("Click to Rotate Road");
    private JButton fourWayButton = new JButton("Click to Add 4 way Intersection");
    private JButton threeWayButton = new JButton("Click to Add 3 way Intersection");
    private JButton saveButton = new JButton("Click here to Save current map ");
    private JPanel designPanel = new JPanel();
    private boolean inProcess,fourWay,threeWay = false;
    private int length =300;
    private int road_width = length;
    private int road_height =100;
    private int roadX=0;
    private int roadY=0;


    public MapCreator(int width, int height){
        roadButton.addActionListener(actionEvent -> addStraightRoad());
        clearButton.addActionListener(actionEvent -> {
            if(inProcess){
                clearButton.setEnabled(false);
            } else {
                clearButton.setEnabled(true);
                Graphics roadGraphics = designPanel.getGraphics();
                designPanel.paint(roadGraphics);
                roads.removeAll(roads);
                File old_file = new File("NEW_MAP");
                if(old_file.delete()){
                    System.out.println(old_file.getName() + " is deleted!");
                }else{
                    System.out.println("Delete operation is failed.");
                }
            }
        });
        maxLengthButton.addActionListener(actionEvent -> {
            if(road_width == length) {
                this.length = 750;
                road_width = length;
            } else if (road_height == length){
                this.length = 750;
                road_height = length;
            }
        });
        minLengthButton.addActionListener(actionEvent -> {
            if(road_width == length) {
                length = 300;
                road_width = length;
            } else if (road_height == length){
                length = 300;
                road_height = length;
            }
        });
        rotateButton.addActionListener(actionEvent -> {
            if(road_width==length){
                road_width = 100;
                road_height = length;
            } else if (road_height == length){
                road_height=100;
                road_width=length;

            }
        });
        fourWayButton.addActionListener(actionEvent -> add4WayIntersection());
        threeWayButton.addActionListener(actionEvent -> add3WayIntersection());
        saveButton.addActionListener(actionEvent -> {
             for(Road road: roads) {
                 String information = String.format("info",road.getRoadId(),road.getNextRoadId(),road.x,road.y,road.getWidth(),road.getHeight());
                 mapData.add(information);
                 try {
                     createCSVOutput();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
        });
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(roadButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(rotateButton);
        buttonPanel.add(maxLengthButton);
        buttonPanel.add(minLengthButton);
        buttonPanel.add(fourWayButton);
        buttonPanel.add(threeWayButton);
        buttonPanel.add(saveButton);
        designPanel.setBackground( Color.CYAN);
        buttonPanel.setBackground( Color.DARK_GRAY);
        designPanel.setPreferredSize(new Dimension((width*3)/4,height));
        buttonPanel.setPreferredSize(new Dimension(width/4,height));
        gridBagConstraints.gridx =0;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridwidth =2;
        gridBagConstraints.gridheight=3;
        gridBagConstraints.weightx =1;
        gridBagConstraints.weighty=1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.addLayoutComponent(designPanel,gridBagConstraints);
        gridBagConstraints.gridx =3;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridwidth =1;
        gridBagConstraints.gridheight=3;
        gridBagConstraints.weightx =0.05;
        gridBagConstraints.weighty=0.2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagLayout.addLayoutComponent(buttonPanel,gridBagConstraints);
        setLayout(gridBagLayout);
        setBackground( Color.BLACK);
        initialiseKeys();
        setPreferredSize(new Dimension(width,height));
        this.add(designPanel);
        this.add(buttonPanel);
    }


    private void initialiseKeys(){
        Action upPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadY-=100;
            }
        };
        Action downPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadY+=100;
            }
        };
        Action leftPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadX-=100;
            }
        };
        Action rightPressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                roadX+=100;
            }
        };
        Action spacePressed = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                inProcess = false;
                timer.stop();
                enableAllButtons();
                if(!fourWay && !threeWay) {
                    roads.add(new Road(roadCount + 1, roadCount + 2, roadX, roadY, road_width, road_height, Color.BLACK));
                } else if (fourWay){
                    roads.add(new CrossRoadIntersection(roadCount + 1, 0,0,0,0, roadX, roadY, road_width, road_height, Color.BLACK));
                    fourWay = false;
                } else if(threeWay){
                    roads.add(new TRoadIntersection(roadCount + 1, 0, roadX, roadY, road_width, road_height, Color.BLACK));
                    threeWay = false;
                }
                for (Road road : roads) {
                    Graphics roadGraphics = designPanel.getGraphics();
                    roadGraphics.setColor( Color.BLACK);
                    roadGraphics.fillRect(road.x, road.y, road.getWidth(), road.getHeight());
                    designPanel.paintComponents(roadGraphics);
                }
                resetRoad();
                roadCount++;
            }
        };
        bindKeyStrokes("up.pressed", KeyStroke.getKeyStroke( KeyEvent.VK_UP,0),upPressed);
        bindKeyStrokes("down.pressed", KeyStroke.getKeyStroke( KeyEvent.VK_DOWN,0),downPressed);
        bindKeyStrokes("left.pressed", KeyStroke.getKeyStroke( KeyEvent.VK_LEFT,0),leftPressed);
        bindKeyStrokes("right.pressed", KeyStroke.getKeyStroke( KeyEvent.VK_RIGHT,0),rightPressed);
        bindKeyStrokes("space.pressed", KeyStroke.getKeyStroke( KeyEvent.VK_SPACE,0),spacePressed);

    }

    private Timer timer = new Timer(1000 / 60, e -> updateMap());

    private void addStraightRoad(){
        disableAllButtons();
        inProcess=true;
        timer.start();
    }

    private void add4WayIntersection(){
        disableAllButtons();
        inProcess =true;
        fourWay =true;
        road_width=100;
        road_height = 100;
        timer.start();
    }

    private void add3WayIntersection(){
        disableAllButtons();
        inProcess =true;
        threeWay = true;
        road_width=100;
        road_height = 100;
        timer.start();

    }
    private void updateMap(){
        if(roadCount==0) {
            Graphics roadGraphics = designPanel.getGraphics();
            designPanel.paint(roadGraphics);
            roadGraphics.setColor( Color.BLACK);
            roadGraphics.fillRect(roadX, roadY, road_width, road_height);
            designPanel.paintComponents(roadGraphics);
        } else {
            Graphics roadGraphics = designPanel.getGraphics();
            designPanel.paint(roadGraphics);
            roadGraphics.setColor( Color.BLACK);
            roadGraphics.fillRect(roadX, roadY, road_width, road_height);
            designPanel.paintComponents(roadGraphics);
            for (Road road : roads) {
                roadGraphics.setColor( Color.BLACK);
                roadGraphics.fillRect(road.x, road.y, road.getWidth(), road.getHeight());
                designPanel.paintComponents(roadGraphics);
            }
        }
    }
    private void bindKeyStrokes(String name, KeyStroke keyStroke, Action action){
        InputMap inputMap = getInputMap( JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        inputMap.put(keyStroke,name);
        actionMap.put(name,action);
    }

    private void disableAllButtons(){
        roadButton.setEnabled(false);
        clearButton.setEnabled(false);
        maxLengthButton.setEnabled(false);
        minLengthButton.setEnabled(false);
        rotateButton.setEnabled(false);
        fourWayButton.setEnabled(false);
        threeWayButton.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private void enableAllButtons(){
        roadButton.setEnabled(true);
        clearButton.setEnabled(true);
        maxLengthButton.setEnabled(true);
        minLengthButton.setEnabled(true);
        rotateButton.setEnabled(true);
        fourWayButton.setEnabled(true);
        threeWayButton.setEnabled(true);
        saveButton.setEnabled(true);
    }

    private void resetRoad(){
        road_height = 100;
        road_width = 300;
    }

    private String convertToCSV(String data){
        return Stream.of(data)
                .map(this::escapeSpecialCharacters).collect( Collectors.joining(","));

    }

    private void createCSVOutput() throws IOException {
        File csvOutput = new File("NEW_MAP");
        try(PrintWriter printWriter = new PrintWriter(csvOutput)){
            mapData.stream().map(this::convertToCSV).forEach(printWriter::println);
        } assert(csvOutput.exists());
    }

    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
