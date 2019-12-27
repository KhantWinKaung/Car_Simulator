public class Road {
    // Declare road variable
    Object[]object;
    String name;
    int length;
    Vehicle vehicle = new Vehicle();
    TrafficLight trafficLight = new TrafficLight();
    Road connectedRoad;
    boolean reached = false;    //Set the boolean value

    public Road(String name,int length,Vehicle vehicle, Road connectedRoad,TrafficLight trafficLight) {
        this.object = new Object[length];
        this.name = name;
        this.length = length;
        this.vehicle = vehicle;
        this.trafficLight = trafficLight;
        this.connectedRoad = connectedRoad;
        object[0] = vehicle;
        object[this.length-1] = trafficLight;
    }
    //update method
    public void update(Vehicle vehicle)
    {
        if(vehicle.position < this.length) {        //Check the vehicle position is < length
            object[vehicle.position - 1] = null;
            this.object[vehicle.position] = this.vehicle;
        }
        else if(vehicle.position == this.length){
            if(trafficLight.color.equalsIgnoreCase("Green")) {
                vehicle.position = 0;
                vehicle.currentRoad = connectedRoad;
                reached = true;
            }
            else{
                vehicle.position -= 1;
            }
        }
    }
}
