import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Simulation.MapCreator;
import Simulation.Simulator;

public class Main implements ActionListener{
    private boolean isRunning = false;
    private int counter =0;
    private final int DISPLAY_WIDTH = 1200;
    private final int DISPLAY_HEIGHT = 600;
    private JFrame frame = new JFrame("Car Traffic Simulator");
    private JPanel panel = new JPanel (new GridBagLayout ());
    private Simulator simulator = new Simulator(DISPLAY_WIDTH,DISPLAY_HEIGHT);
    private MapCreator mapCreator = new MapCreator(DISPLAY_WIDTH,DISPLAY_HEIGHT);
    private final int PERIOD_INTERVAL = 300;
    //Container for the buttons
    private Container containerSouth = new Container();
    private JLabel lblTitle = new JLabel("Car Traffic Simulator");
    private JButton btnRun = new JButton("Simulate");
    private JButton btnBuild = new JButton("Build");
    private Timer timer;

    JMenuBar menuBar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenu edit = new JMenu("Edit");
    JMenu help = new JMenu("Help");
    JMenuItem exit = new JMenuItem("Exit");
    JMenuItem city_map = new JMenuItem("Create a New City Map");
    JMenuItem about = new JMenuItem("About");
    public Main(){
        frame.setSize(500,300);
        frame.setLayout(new BorderLayout());
        //Center Container
        panel.add(lblTitle);
        //South Container
        containerSouth.setLayout(new GridLayout(1,2));
        containerSouth.add(btnRun);
        btnRun.addActionListener(this);
        containerSouth.add(btnBuild);
        btnBuild.addActionListener(this);

        exit.addActionListener(e -> System.exit(0));
        about.addActionListener(actionEvent -> JOptionPane.showMessageDialog(frame,"Car Traffic Simulation!"));
        file.add(exit);
        edit.add(city_map);
        help.add(about);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);
        frame.setJMenuBar(menuBar);

        MapCreator mapCreator = new MapCreator(DISPLAY_WIDTH,DISPLAY_HEIGHT);
        city_map.addActionListener(actionEvent -> {
            frame.remove(simulator);
            panel.setVisible( false );
            frame.add(mapCreator, BorderLayout.CENTER);
            frame.pack();
            frame.invalidate();
            frame.setVisible(true);
            frame.validate();
            btnRun.setVisible(false);
            btnBuild.setVisible(false);
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(containerSouth, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnRun) {
            if (counter == 0){
                btnRun.setText("Stop");
                counter++;
            }
            else{
                btnRun.setText("Restart");
                counter = 0;
            }

            simulator.initialise();
            frame.invalidate();
            simulator.animate(isRunning);
            frame.add(simulator, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);

            btnBuild.setVisible(false);

            isRunning = !isRunning;
        }
        if (source == btnBuild) {
            frame.remove(simulator);
            panel.setVisible( false );
            frame.add(mapCreator, BorderLayout.CENTER);
            frame.pack();
            frame.invalidate();
            frame.setVisible(true);
            frame.validate();
            btnRun.setVisible(false);
            btnBuild.setVisible(false);
        }
    }

    public static void main(String[]args){
        new Main();
    }
}
