package Model;

import java.awt.*;

public class CrossRoadIntersection extends Road {
    private int southRoadId, northRoadId, eastRoadId, westRoadId;
    public CrossRoadIntersection(int roadId, int southRoadId, int northRoadId, int eastRoadId, int westRoadId , int x, int y, int width, int height, Color color) {
        super(roadId, x, y, width, height, color);
        this.southRoadId = southRoadId;
        this.northRoadId = northRoadId;
        this.eastRoadId = eastRoadId;
        this.westRoadId = westRoadId;
    }
}