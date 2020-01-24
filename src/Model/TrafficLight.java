package Model;

import Abstract.TrafficLightAbstract;

import java.awt.*;
import java.util.Random;

public class TrafficLight extends TrafficLightAbstract {
    private boolean isGreen,isOrange,isRed,onRoadEnd,probabilityOfChange,isEastWest, isNorthSouth;
    private int onRoad;

    int getNextRoad() {
        return nextRoad;
    }

    public void setNextRoad(int nextRoad) {
        this.nextRoad = nextRoad;
    }

    private int nextRoad;
    private int trafficLightID;

    int getDistance() {
        return distance;
    }

    private int distance;
    private final Random random = new Random();
    public TrafficLight(int trafficLightID, int x, int y, Boolean isGreen) {
        super(x, y, isGreen);
        this.trafficLightID = trafficLightID;
        width = 20;
        height = width*2;
    }

    public boolean isGreen() {
        return isGreen;
    }

    public void setGreen(boolean green) {
        isGreen = green;
        isRed = !green;
        isOrange = !green;
    }


    public boolean isRed() {
        return isRed;
    }

    private void setRed(boolean red) {
        isRed = red;
        isGreen = !red;
        isOrange = !red;

    }

    public boolean isOnRoadEnd() {
        return onRoadEnd;
    }

    public void setOnRoadEnd(boolean onRoadEnd) {
        this.onRoadEnd = onRoadEnd;
    }

    public boolean isProbabilityOfChange() {
        return probabilityOfChange;
    }

    public void setProbabilityOfChange(boolean probabilityOfChange) {
        this.probabilityOfChange = probabilityOfChange;
    }

    public int getOnRoad() {
        return onRoad;
    }

    public void setOnRoad(int onRoad) {
        this.onRoad = onRoad;
    }

    public int getTrafficLightID() {
        return trafficLightID;
    }

    public void setTrafficLightID(int trafficLightID) {
        this.trafficLightID = trafficLightID;
    }

    public boolean isEastWest() {
        return isEastWest;
    }

    public void setEastWest(boolean eastWest) {
        isEastWest = eastWest;
        this.distance = x;
    }

    public boolean isNorthSouth() {
        return isNorthSouth;

    }

    public void setNorthSouth(boolean northSouth) {
        isNorthSouth = northSouth;
        this.distance =y;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x-width/2,y-height/2,width,height);
        if(isGreen) {
            g.setColor(Color.GREEN);
            g.fillRect(x - width / 4, y - height / 4, width / 2, height / 2);
        } else if (isRed){
            g.setColor(Color.RED);
            g.fillRect(x - width / 4, y - height / 4, width / 2, height / 2);
        }

    }

    public void goToRed() {
        setGreen(true);
        restInterval();
        restInterval();
        setRed(true);
    }

    private void restInterval() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    //Randomly generate a number. If the value is greater than 50 then the light goes Red. If not, the light goes Green.
    public void randomStop() {
        int probability = random.nextInt(101);
        if (probability > 50) {
            setRed(true);
        } else {
            setGreen(true);
        }
    }
}

