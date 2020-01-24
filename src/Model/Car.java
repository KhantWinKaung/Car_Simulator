package Model;

import Abstract.VehicleAbstract;

import java.awt.*;

public class Car extends VehicleAbstract {
    //Local Variables.
    private final int carId;
    private int carAhead;
    private final int width;
    private final int height;
    private int onRoad;
    private boolean goingSouth, goingNorth, goingEast, goingWest, isStopped, isSlowing, atEnd;
    private int length;

    //Car Object Initialisers.
    public Car(int carId, int x, int y, int width, int height, Color color, int xspeed, int yspeed) {
        super(x, y, color, xspeed, yspeed);
        this.carId = carId;
        this.width = width;
        this.height = height;
    }

    public Car(int carId, int carAhead, int x, int y, int width, int height, Color color, int xspeed, int yspeed) {
        super(x, y, color, xspeed, yspeed);
        this.carId = carId;
        this.carAhead = carAhead;
        this.width = width;
        this.height = height;
    }

    private void update(int boundaryWidth, int boundaryHeight) {
        //If Car has reached boundaries of the display, set at End.
        if ((x - width / 2 < 0 && xDir < 0) || (x + width / 2 > boundaryWidth && xDir > 0)) {
            this.setAtEnd();
        }
        if ((y - height / 2 < 0 && yDir < 0) || (y + height / 2 > boundaryHeight && yDir > 0)) {
            this.setAtEnd();
        }
    }

    public void draw(Graphics g) {
        //Draw Car specifically depending on direction of Car.
        if (goingEast) {
            g.setColor(color);
            g.fillRect(x - width / 2, y - height / 2, width, height);
        } else if (goingWest) {
            g.setColor(color);
            g.fillRect(x - width / 2, y - height / 2, width, height);
        }
        if (goingSouth) {
            g.setColor(color);
            g.fillRect(x - width / 2, y - height / 2, width, height);
        }
        if (goingNorth) {
            g.setColor(color);
            g.fillRect(x - width / 2, y - height / 2, width, height);
        }
        if (atEnd) {
            g.clearRect(x - width / 2, y - height / 2, width, height);
            g.setColor(Color.BLACK);
            g.fillRect(x - width / 2, y - height / 2, width, height);
            g.setColor(Color.BLACK);
            g.fillRect(x + width / 10, y - height / 2, width / 10, height);
        }
    }

    private void speedUp(float new_speed) {
        //Add to specific speed new_speed
        if (goingWest || goingEast) {
            //If going EastWest, increase xSpeed
            if (this.xSpeed <= 6) {
                this.xSpeed += new_speed;
            }
            this.isStopped = false;
            this.isSlowing = false;
        } else if (goingSouth || goingNorth) {
            //If going NorthSouth, increase ySpeed
            if (this.ySpeed <= 6) {
                this.ySpeed += new_speed;
            }
            this.isStopped = false;
            this.isSlowing = false;
        }
    }

    private void slowDown(float new_speed) {
        //Subtract from specific speed  the new_speed
        if (goingEast || goingWest) {
            //If going EastWest, decrease xSpeed
            if (xSpeed >= 0) {
                this.setSlowing();
                this.isStopped = false;
                xSpeed -= new_speed;
                if (xSpeed <= 0) {
                    this.stop();
                }
            }
        } else if (goingNorth || goingSouth) {
            //If going NorthSouth, decrease ySpeed
            if (ySpeed >= 0) {
                this.setSlowing();
                this.isStopped = false;
                ySpeed -= new_speed;
                if (ySpeed <= 0) {
                    this.stop();
                }
            }
        }
    }

    private void stop() {
        //Set Speed to 0 depending on direction.
        if (goingEast || goingWest) {
            xSpeed = 0;
            this.setStopped();
            this.isSlowing = false;
        } else if (goingNorth || goingSouth) {
            ySpeed = 0;
            this.setStopped();
            this.isSlowing = false;
        }
    }

    public void lookAhead(TrafficLight trafficLight, Car car_next, float speed, int boundaryWidth, int boundaryHeight, boolean isIntersection) {
        float roadShift = 0;
        if (isIntersection) {
            roadShift = this.width * 4;
        }
        if ((trafficLight.getOnRoad() == this.getOnRoad()) || (trafficLight.getNextRoad() == this.getOnRoad())) {
            //Going West code
            if (goingWest) {
                if (trafficLight.isGreen()) {
                    //If Traffic Light is Green, Speed Up Car.
                    this.move();
                    this.speedUp(speed / 10);
                    this.update(boundaryWidth, boundaryHeight);
                    if (this.isStopped()) {
                        this.speedUp(speed);
                    }
                }
                if (trafficLight.isRed() && (this.x > trafficLight.getDistance() + roadShift)) {
                    this.slowDown(speed / 20);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                if (trafficLight.isRed() && (this.x < trafficLight.getDistance() + roadShift)) {
                    // If Car is further left than Traffic Light and light is Red, speed up.
                    this.speedUp(speed / 4);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }

                if (trafficLight.isRed() && (this.x < roadShift + trafficLight.getDistance() * 1.02) && (trafficLight.getDistance() + roadShift < this.x)) {
                    this.stop();
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                try {
                    //If Next Car in queue is close to current, slow down.
                    if ((car_next.x + 2 * car_next.getWidth()) > this.x) {
                        this.slowDown(speed);
                    }
                    if (car_next.x + car_next.getWidth() > this.x) {
                        //If Cars overlap, shift current by 1 car width.
                        this.stop();
                        this.x += car_next.getWidth();
                    }
                    if ((car_next.x > this.x - this.getWidth() / 4) && trafficLight.isRed()) {
                        //If Other Car is close and Light is Red, stop.
                        this.stop();
                    }
                    if (trafficLight.isRed() && (car_next.isStopped() || car_next.isSlowing()) && (car_next.x < this.x - 2 * this.width)) {
                        //If next car in queue is too far, speed up.
                        this.speedUp(speed / 2);
                    }
                } catch (NullPointerException e) {
                    //

                }
                //Going East Code
            }
            if (this.isGoingEast()) {
                if (trafficLight.isGreen()) {
                    this.move();
                    this.speedUp(speed / 10);
                    this.update(boundaryWidth, boundaryHeight);
                    if (this.isStopped()) {
                        this.speedUp(speed);
                    }

                }
                if (trafficLight.isRed() && (this.x < trafficLight.getDistance())) {
                    this.slowDown(speed / 20);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                if (trafficLight.isRed() && (this.x > trafficLight.getDistance())) {
                    this.speedUp(speed / 4);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                if (trafficLight.isRed() && (trafficLight.getDistance() * 0.98 <= this.x) && (this.x < trafficLight.getDistance())) {
                    this.stop();
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                try {
                    if (this.x > car_next.x - 2 * car_next.getWidth()) {
                        this.slowDown(speed);
                    }
                    if (this.x + this.width > car_next.x) {
                        this.stop();
                        this.x -= car_next.getWidth();
                    } else if ((car_next.x < this.x + this.getWidth() / 4) && trafficLight.isRed()) {
                        this.stop();
                    }
                    if (trafficLight.isRed() && (car_next.isStopped() || car_next.isSlowing()) && (car_next.x > this.x + 2 * this.width)) {
                        this.speedUp(speed / 2);
                    }

                } catch (NullPointerException e) {
                    //
                }
                //Going South Code
            } else if (this.goingSouth) {
                if (trafficLight.isGreen()) {
                    this.speedUp(speed / 10);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                    if (this.isStopped()) {
                        this.speedUp(speed);
                    }
                }
                if (trafficLight.isRed() && (this.y < trafficLight.getDistance())) {
                    this.slowDown(speed / 20);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                if (trafficLight.isRed() && (this.y > trafficLight.getDistance() - 2 * roadShift)) {
                    this.speedUp(speed / 4);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                } else if (trafficLight.isRed() && ((trafficLight.getDistance() * 0.95) - (2 * roadShift) < this.y) && (this.y < trafficLight.getDistance())) {
                    this.stop();
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }


                try {
                    if (this.y > car_next.y - 2 * car_next.length) {
                        this.slowDown(speed);
                    }
                    if (this.y + this.length > car_next.y) {
                        this.stop();
                        this.y -= car_next.length;
                    }
                    if (trafficLight.isRed() && (car_next.isStopped() || car_next.isSlowing()) && (car_next.y > this.y + 2 * this.length)) {
                        this.speedUp(speed / 2);
                    }
                } catch (NullPointerException e) {
                    //
                }
//Going North Code
            } else if (this.goingNorth) {
                if (trafficLight.isGreen()) {
                    this.speedUp(speed / 10);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                    if (this.isStopped()) {
                        this.speedUp(speed);
                    }
                }
                if (trafficLight.isRed() && (this.y > trafficLight.getDistance())) {
                    this.slowDown(speed / 20);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                } else if (trafficLight.isRed() && ((trafficLight.getDistance() * 1.01) < this.y) && (this.y > trafficLight.getDistance())) {
                    this.stop();
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }
                if (trafficLight.isRed() && (this.y < trafficLight.getDistance())) {
                    this.speedUp(speed / 4);
                    this.move();
                    this.update(boundaryWidth, boundaryHeight);
                }

                try {
                    if (this.y < car_next.y + 2 * car_next.length) {
                        this.slowDown(speed);
                    }
                    if (this.y - this.length < car_next.y) {
                        this.stop();
                        this.y += car_next.length;
                    }
                    if (trafficLight.isRed() && (car_next.isStopped() || car_next.isSlowing()) && (car_next.y < this.y - 2 * this.length)) {
                        this.speedUp(speed / 4);
                    }
                } catch (NullPointerException e) {
                    //
                }
            }
        }
    }


    public void setGoingSouth(boolean goingSouth) {
        this.goingSouth = goingSouth;
        this.length = this.height;
        xDir = 0;
        yDir = 1;
    }


    public void setGoingNorth(boolean goingNorth) {
        this.goingNorth = goingNorth;
        this.length = this.height;
        xDir = 0;
        yDir = -1;

    }

    private boolean isGoingEast() {
        return goingEast;
    }

    public void setGoingEast(boolean goingEast) {
        this.goingEast = goingEast;
        this.length = this.width;
        xDir = 1;
        yDir = 0;
    }


    public void setGoingWest(boolean goingWest) {
        this.goingWest = goingWest;
        this.length = this.width;
        xDir = -1;
        yDir = 0;
    }


    private boolean isStopped() {
        return isStopped;
    }

    private void setStopped() {
        isStopped = true;
    }

    private boolean isSlowing() {
        return isSlowing;
    }

    private void setSlowing() {
        isSlowing = true;
    }


    private void setAtEnd() {
        this.atEnd = true;
    }

    public int getCarId() {
        return carId;
    }

    public int getCarAhead() {
        return carAhead;
    }


    private int getWidth() {
        return width;
    }

    private int getOnRoad() {
        return onRoad;
    }

    public void setOnRoad(int onRoad) {
        this.onRoad = onRoad;
    }
}
