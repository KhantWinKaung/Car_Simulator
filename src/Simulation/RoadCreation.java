package Simulation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.Arrays;
import javax.swing.*;

public class RoadCreation extends JFrame implements ActionListener {

    private JPanel btnPanel;
    private JPanel panel;
    private JButton[][]buttons;
    private boolean [][] isRoad;
    private final int SIZE = 8;
    private GridLayout experimentLayout;
    private int counter = 0;
    private JButton btnSave = new JButton("Save");
    private JButton btnRemove = new JButton( "Remove" );
    //private boolean isRoad = false;
    public RoadCreation()
    {
        super("City Creation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,800);
        setResizable(false);
        setLocationRelativeTo(null);
        experimentLayout =  new GridLayout(SIZE,SIZE);
        panel = new JPanel();
        btnPanel = new JPanel();
        panel.setLayout(experimentLayout);
        buttons = new JButton[SIZE][SIZE];
        isRoad = new boolean[SIZE][SIZE];
        addButtons();

        add(panel);
        add(btnPanel, BorderLayout.SOUTH);
        btnPanel.add(btnSave);
        btnPanel.add(btnRemove);
        btnSave.addActionListener( this );
        btnRemove.addActionListener( this );
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addButtons() {
        try{
            for(int k = 0; k < SIZE; k++) {
                for (int j = 0; j < SIZE; j++) {
                    buttons[k][j] = new JButton(k + 1 + ", " + (j + 1));
                    isRoad[k][j]=false;
                    buttons[k][j].addActionListener(this);
                    panel.add(buttons[k][j]);
                }
            }
        }catch(IndexOutOfBoundsException ex ){
            System.out.println("This is your problem: " +  ex.getMessage()
                    + "\nHere is where it happened:\n");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new RoadCreation();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Object source = e.getSource();
            ++counter;

            int k, j;
            for (k = 0; k < SIZE; k++) {
                for (j = 0; j < SIZE; j++) {
                    if (source == buttons[k][j]) {
                        if (counter == 5) {
                            counter = 0;
                        }
                        if (counter == 1) {
                            buttons[k][j].setText( "R" );
                            buttons[k][j + 1].setText( "R" );
                            buttons[k][j + 2].setText( "R" );

                            buttons[k][j].setForeground( Color.GREEN );
                            buttons[k][j + 1].setForeground( Color.GREEN );
                            buttons[k][j + 2].setForeground( Color.GREEN );

                            isRoad[k][j] = true;
                            isRoad[k][j + 1] = true;
                            isRoad[k][j + 2] = true;

                            System.out.println( isRoad[k][j] );
                            System.out.println( isRoad[k][j + 1] );
                            System.out.println( isRoad[k][j + 2] );
                            System.out.println( "Horizontal Road" );
                        } else if (counter == 2) {
                            buttons[k][j].setText( "R" );
                            buttons[k + 1][j].setText( "R" );
                            buttons[k + 2][j].setText( "R" );

                            buttons[k][j].setForeground( Color.GREEN );
                            buttons[k + 1][j].setForeground( Color.GREEN );
                            buttons[k + 2][j].setForeground( Color.GREEN );

                            isRoad[k][j] = true;
                            isRoad[k + 1][j] = true;
                            isRoad[k + 2][j] = true;
                            System.out.println( isRoad[k][j] );
                            System.out.println( isRoad[k + 1][j] );
                            System.out.println( isRoad[k + 2][j] );
                            System.out.println( "Vertical Road" );
                        } else if (counter == 3) {
                            buttons[k][j].setForeground( Color.GREEN );
                            buttons[k][j + 1].setForeground( Color.GREEN );
                            buttons[k - 1][j + 1].setForeground( Color.GREEN );
                            buttons[k + 1][j + 1].setForeground( Color.GREEN );

                            buttons[k][j].setText( "R" );
                            buttons[k][j + 1].setText( "R" );
                            buttons[k - 1][j + 1].setText( "R" );
                            buttons[k + 1][j + 1].setText( "R" );

                            isRoad[k][j] = true;
                            isRoad[k][j + 1] = true;
                            isRoad[k - 1][j + 1] = true;
                            isRoad[k + 1][j + 1] = true;
                            System.out.println( isRoad[k][j] );
                            System.out.println( isRoad[k][j + 1] );
                            System.out.println( isRoad[k - 1][j + 1] );
                            System.out.println( isRoad[k + 1][j + 1] );
                            System.out.println( "T Road" );
                        } else if (counter == 4) {
                            buttons[k][j].setForeground( Color.GREEN );
                            buttons[k][j + 1].setForeground( Color.GREEN );
                            buttons[k][j + 2].setForeground( Color.GREEN );
                            buttons[k - 1][j + 1].setForeground( Color.GREEN );
                            buttons[k + 1][j + 1].setForeground( Color.GREEN );

                            buttons[k][j].setText( "R" );
                            buttons[k][j + 1].setText( "R" );
                            buttons[k][j + 2].setText( "R" );
                            buttons[k - 1][j + 1].setText( "R" );
                            buttons[k + 1][j + 1].setText( "R" );

                            isRoad[k][j] = true;
                            isRoad[k][j + 1] = true;
                            isRoad[k][j + 2] = true;
                            isRoad[k - 1][j + 1] = true;
                            isRoad[k + 1][j + 1] = true;
                            System.out.println( isRoad[k][j] );
                            System.out.println( isRoad[k][j + 1] );
                            System.out.println( isRoad[k][j + 2] );
                            System.out.println( isRoad[k - 1][j + 1] );
                            System.out.println( isRoad[k + 1][j + 1] );
                            System.out.println( "Cross Road" );
                        }
                    }
                }
            }
            if (source == btnSave) {
                String row = "";
                FileWriter csvWriter = new FileWriter("Road.csv");
                for (boolean[] rowRoad: isRoad) {
                    row = Arrays.toString(rowRoad);
                    csvWriter.append(row.substring(1, row.length()-1));

                    csvWriter.append("\n");

                }
                csvWriter.append('\b');
                csvWriter.flush();
                csvWriter.close();
            }
            if(source == btnRemove){
                for (boolean[] rowRoad: isRoad) {
                    for (boolean[] rowColumn: isRoad) {
                        //rowColumn=false;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
