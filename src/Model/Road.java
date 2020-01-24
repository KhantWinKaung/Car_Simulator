package Model;

import Abstract.RoadAbstract;

import java.awt.*;


public class Road extends RoadAbstract {
    private int roadId,nextRoadId;
    private int length,width,height;
    private boolean LightStart,LightEnd;
    private Color color;

    public Road(int roadId, int nextRoadId, int x, int y, int width, int height, Color color){
        super(x,y,color);
        this.nextRoadId = nextRoadId;
        this.roadId = roadId;
        this.width = width;
        this.height = height;
        this.length = width;
    }

    Road(int roadId, int x, int y, int width, int height, Color color){
        super(x,y,color);
        this.roadId = roadId;
        this.width = width;
        this.height = height;
        this.length = width;

    }
    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isLightStart() {
        return LightStart;
    }

    public void setLightStart(boolean lightStart) {
        LightStart = lightStart;
    }

    public boolean isLightEnd() {
        return LightEnd;
    }

    public void setLightEnd(boolean lightEnd) {
        LightEnd = lightEnd;
    }

    @Override
    public void update(int boundaryWidth, int boundaryHeight) {

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getNextRoadId() {
        return nextRoadId;
    }

    public void setNextRoadId(int nextRoadId) {
        this.nextRoadId = nextRoadId;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x-width/2,y-height/2,width,height);
    }
}
