package Abstract;

import java.awt.*;

public abstract class TrafficLightAbstract {
    public int x;
    public int y;
    protected int width;
    protected int height;
    private final boolean isGreen;

    protected TrafficLightAbstract(int x, int y, Boolean isGreen){
        this.x = x;
        this.y = y;
        this.isGreen = isGreen;
    }
    public abstract void draw(Graphics g);
}

