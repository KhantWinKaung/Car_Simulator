package Abstract;

import java.awt.*;

public abstract class VehicleAbstract {
    public int x, y;
    protected final Color color;
    protected int xDir;
    protected int yDir;
    public float xSpeed, ySpeed;

    protected VehicleAbstract(int x, int y, Color color, int xv, int yv) {
        this.x = x;
        this.y = y;
        this.color = color;
        xDir = +1;
        yDir = +1;
        xSpeed = xv;
        ySpeed = yv;
    }

    protected void move() {
        x += (int) xSpeed * xDir;
        y += (int) ySpeed * yDir;
    }
}