package Simulation;

import Model.Car;
import Model.Road;
import Model.TrafficLight;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Simulator extends JPanel{
    private Timer timer;
    private Car[] cars;
    private Road[] roads;
    private TrafficLight[] trafficLights;
    private int counter =0;
    private int counter1 =0;
    private static final Random random = new Random();
    public Simulator(int width, int height){
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(width,height));
    }

    public void initialise(){
        final int DISPLAY_WIDTH = 1200;
        final int DISPLAY_HEIGHT = 600;
        final int CAR_COUNT = 160;
        final int CAR_LENGTH = 50;
        final int ROAD_COUNT = 4;
        final int TRAFFIC_LIGHT_COUNT =2;
        Car[] cars = new Car[CAR_COUNT];
        Road[] roads = new Road[ROAD_COUNT];
        TrafficLight[] trafficLights = new TrafficLight[TRAFFIC_LIGHT_COUNT];
        //West
        int x = 0;
        int xshift = 0;
        int y;
        int carId =1;
        for(int i =0; i <cars.length/4;i++){
            y=(DISPLAY_HEIGHT/2)-(CAR_LENGTH/2);
            int width = CAR_LENGTH;
            int height = width/2;
            Color color = randomColor();
            if(carId<=1){
                cars[i]= new Car(carId,x,y,width,height,color,2,0);}
            else {
                int next = carId-1;
                if(next <CAR_COUNT/4) {
                    cars[i] = new Car(carId, next, x, y, width, height, color, 2, 0);
                } else {
                    cars[i] = new Car(carId, x, y, width, height, color, 2, 0);
                }
            }
            carId++;
            if(x<=0){
                x-=width*1.5;
            } else if (x>= DISPLAY_WIDTH){
                x-=width*1.5;
            }
            Car car = cars[i];
            car.setGoingWest(true);
            car.setOnRoad(2);
            car.x=DISPLAY_WIDTH+xshift;
            xshift += width*2;
            car.y += height*2;

        }
        // East
        x = 0;
        xshift = 0;
        y=0;
        carId =cars.length/4+1;

        for(int ii = cars.length/4; ii <cars.length/2; ii++){
            y=(DISPLAY_HEIGHT/2)-(CAR_LENGTH/2);
            int width = CAR_LENGTH;
            int height = width/2;
            Color color = randomColor();
            if(carId<=4){
                cars[ii]= new Car(carId,x,y,width,height,color,2,0);}
            else {
                int next = carId-1;
                if((next > CAR_COUNT/4) && (next < CAR_COUNT/2)) {
                    cars[ii] = new Car(carId, next, x, y, width, height, color, 2, 0);
                } else {
                    cars[ii] = new Car(carId, x, y, width, height, color, 2, 0);
                }
            }
            carId++;
            if(x<=0){
                x-=width*1.5;
            } else if (x>= DISPLAY_WIDTH){
                x-=width*1.5;
            }
            Car car = cars[ii];
            car.setGoingEast(true);
            car.setOnRoad(1);
            car.x= xshift;
            xshift -= width*2;
            car.y += 0;
        }

        // South
        x = DISPLAY_WIDTH/2+((CAR_LENGTH/2)*5);
        int yshift = 0;
        y=0;
        carId =cars.length/2+1;
        for(int ii = cars.length/2; ii <((cars.length*3)/4); ii++){
//            y=(DISPLAY_HEIGHT/2)+(CAR_LENGTH/2);
            int height = CAR_LENGTH;
            int width = height/2;

            Color color = randomColor();
            if(carId<=4){
                cars[ii]= new Car(carId,x,y,width,height,color,2,0);}
            else {
                int next = carId-1;
                if((next> CAR_COUNT/2)&& (next <(CAR_COUNT*3)/4)) {
                    cars[ii] = new Car(carId, next, x, y, width, height, color, 2, 0);
                } else {
                    cars[ii] = new Car(carId, x, y, width, height, color, 2, 0);
                }
            }
            carId++;
            Car car = cars[ii];
            car.setGoingSouth(true);
            car.setOnRoad(3);
            car.y= yshift;
            yshift -= width*2;
        }
        // North
        x = DISPLAY_WIDTH/2+((CAR_LENGTH/2)*3);
        yshift = 0;
        y=0;
        carId =((3*cars.length)/4)+1;
        for(int ji = ((3*cars.length)/4); ji <cars.length; ji++){
            int height = CAR_LENGTH;
            int width = height/2;
            Color color = randomColor();
            if(carId<=4){
                cars[ji]= new Car(carId,x,y,width,height,color,2,0);}
            else {
                int next = carId-1;
                if((next <CAR_COUNT) && (next > (CAR_COUNT*3)/4)) {
                    cars[ji] = new Car(carId, next, x, y, width, height, color, 2, 0);
                } else {
                    cars[ji] = new Car(carId, x, y, width, height, color, 2, 0);
                }
            }
            carId++;
            Car car = cars[ji];
            car.setGoingNorth(true);
            car.setOnRoad(4);
            car.y=DISPLAY_HEIGHT+yshift;
            yshift += width*2;
            car.y += 0;
        }


        int xloc =0;
        for(int j =0; j <roads.length/2; j++){
            int length = 2*DISPLAY_WIDTH/(ROAD_COUNT);
            int roadId = j+1;
            roads[j] = new Road(roadId,0,xloc+(length/2),DISPLAY_HEIGHT/2, length, 2*CAR_LENGTH, Color.BLACK);
            xloc += length;
        }
        int yloc =0;
        xloc = 2*DISPLAY_WIDTH/ROAD_COUNT+(2*CAR_LENGTH);
        int length = 2*DISPLAY_HEIGHT/(ROAD_COUNT);
        for(int jj = roads.length/2; jj < roads.length; jj++){
            int roadId = jj+1;
            roads[jj] = new Road(roadId,0,xloc,yloc+(length/2), 2*CAR_LENGTH, length, Color.BLACK);
            yloc += length;
        }

        int shift=0;

        for(int k = 0; k< trafficLights.length;k++){
            int light_X = roads[k].getLength();
            int light_Y = DISPLAY_HEIGHT/2-(2*CAR_LENGTH);
            trafficLights[k]= new TrafficLight(k+1,shift+light_X,light_Y,true);
            trafficLights[k].setOnRoadEnd(true);
            if(k==0) {
                trafficLights[k].setEastWest(true);
                trafficLights[k].setOnRoad(k + 1);
                trafficLights[k].setNextRoad(k + 2);
            } if(k==1){
                TrafficLight trafficLight = trafficLights[k];
                trafficLight.setOnRoad(k + 2);
                trafficLight.setNextRoad(k + 3);
                trafficLight.x = light_X +(roads[trafficLight.getOnRoad()-1].getWidth()*2);
                light_Y = (roads[trafficLight.getOnRoad()-1].getLength())+(roads[trafficLight.getOnRoad()-1].getWidth()*3);
                trafficLight.y =  light_Y;
                trafficLight.setNorthSouth(true);

            }

        }

        this.setRoads(roads);
        this.setCars(cars);
        this.setTrafficLights(trafficLights);
    }

    public void animate(boolean isRunning){

        if(isRunning){
            timer.stop();
        }
        else{
            for(TrafficLight trafficLight: trafficLights){
                trafficLight.setGreen(true);
            }
            timer = new Timer(1000/60, e -> {
                if (cars == null) return;
                float speed = 1;
                for (Car car : cars) {
                    for(TrafficLight trafficLight :trafficLights) {
                        TrafficLight next_trafficLight;
                        if(trafficLight.getTrafficLightID() == 1){
                            next_trafficLight = trafficLights[1];
                        } else {
                            next_trafficLight = trafficLights[0];
                        }
                        if(trafficLight.isEastWest()) {
                            if ((trafficLight.isGreen())   && (counter >= 60)) {
                                trafficLight.randomStop();
                                counter = 0;
                            }
                            if (trafficLight.isRed() && (!next_trafficLight.isGreen()) && (counter >= 60)) {
                                trafficLight.setGreen(true);
                                counter = 0;
                            }
                            int id = car.getCarId();
                            int cars_length = cars.length;
                            if ((id != 1) && (id != cars_length / 4 + 1) && (id != cars_length / 2 + 1) && (id != (3 * cars_length) / 4 + 1)) {
                                Car car_next = cars[car.getCarAhead() - 1];
                                car.lookAhead(trafficLight, car_next, speed, getWidth(), getHeight(), true);
                            } else {
                                car.lookAhead(trafficLight, null, speed, getWidth(), getHeight(), true);
                            }
                        } else if (trafficLight.isNorthSouth()){
                            if ((trafficLight.isGreen()) && (counter1 >= 60)) {
                                trafficLight.randomStop();
                                counter1 = 0;
                            }
                            if (trafficLight.isRed()  && (!next_trafficLight.isGreen()) && (counter1 >= 60)) {
                                trafficLight.setGreen(true);
                                counter1 = 0;
                            }
                            int id = car.getCarId();
                            int cars_length = cars.length;
                            if ((id != 1) && (id != cars_length / 4 + 1) && (id != cars_length / 2 + 1) && (id != (3 * cars_length) / 4 + 1)) {
                                Car car_next = cars[car.getCarAhead() - 1];
                                car.lookAhead(trafficLight, car_next, speed, getWidth(), getHeight(), true);
                            } else {
                                car.lookAhead(trafficLight, null, speed, getWidth(), getHeight(), true);
                            }

                        }
                    }
                }
                speed++;
                counter ++;
                counter1++;
                repaint();
            });
            timer.start();
        }

    }

    private void setCars(Car[] Cars) {
        this.cars = Cars;
    }

    private void setRoads(Road[] roads) {
        this.roads = roads;
    }

    private void setTrafficLights(TrafficLight[] trafficLights) {
        this.trafficLights = trafficLights;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Road road: roads){
            g.setColor(Color.BLACK);
            road.draw(g);
        }
        for (Car car : cars) {
            g.setColor(Color.WHITE);
            car.draw(g);
        }
        for(TrafficLight trafficLight : trafficLights){
            g.setColor(Color.BLACK);
            trafficLight.draw(g);
        }
    }

    private static int randomSize(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private static Color randomColor() {
        int red = randomSize(0, 255);
        int green = randomSize(0, 255);
        int blue = randomSize(0, 255);
        int alpha = randomSize(100, 255);

        return new Color(red, green, blue, alpha);
    }

}
