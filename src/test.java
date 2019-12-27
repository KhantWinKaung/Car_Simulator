public class test {
    public static void main(String[] args) throws InterruptedException {
        //Testing traffic light
        TrafficLight trafficLight=new TrafficLight("Red",5.0);
        trafficLight.operate();
        System.out.println("Traffic light: "+trafficLight.color);

        //Testing vehicle
        Vehicle vehicle=new Vehicle("Car",0,null);
        vehicle.move();
        System.out.println("Vehicle position: "+vehicle.position);

        //Testing road
        Road road=new Road("r3",6,vehicle,null,trafficLight);
        road.update(vehicle);
        for(int i=0;i<=road.object.length-1;i++){
            System.out.println(road.object[i]);
        }
    }
}
