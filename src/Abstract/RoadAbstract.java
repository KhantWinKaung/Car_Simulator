package Abstract;

import java.awt.*;

public abstract class RoadAbstract {
    public final int x;
    public final int y;
    private final Color color;

    public RoadAbstract(int x, int y, Color colorRoad){
        this.x = x;
        this.y = y;
        color = colorRoad;
    }

    public abstract void update(int boundaryWidth, int boundaryHeight);
    public abstract void draw(Graphics g);
}
