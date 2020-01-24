package Simulation;

import java.io.*;
import java.util.Arrays;

public class ReadMap {
    public ReadMap() {

    }

    public String[][] ReadMap() throws IOException {
        final int SIZE = 8;
        String FILENAME = "RoadList.csv";
        String[] row;
        String[][] isRoad = new String[SIZE][SIZE];
        InputStream is = new FileInputStream( FILENAME );
        BufferedReader buf = new BufferedReader( new InputStreamReader( is ) );

        String line = "";
        StringBuilder sb = new StringBuilder();
        String tempLine = "";

        for (int i = 0; i < 8; i++) {
            tempLine = sb.append( line ).toString();
            line = buf.readLine();
            row = line.split( "," );
            isRoad[i] = row;
        }
        System.out.println( Arrays.deepToString( isRoad ) );
        return isRoad;
    }

}
