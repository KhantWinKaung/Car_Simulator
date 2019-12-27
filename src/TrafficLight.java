public class TrafficLight {
    String color;
    double rateOfChange;

    public TrafficLight() {
    }

    // Traffic Light constructor
    public TrafficLight(String color, double rateOfChange){
        this.color = color;
        this.rateOfChange = rateOfChange;
    }

    // operate method for traffic light
    public void operate(){
        if(rateOfChange < (Math.random() * ((1.0 - 10.0) + 1)) + 10.0)
            this.color = "Red";
        else
            this.color = "Green";
    }
}
