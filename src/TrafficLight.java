public class TrafficLight {
    String color;
    double rateOfChange;

    public TrafficLight() {
    }

    // TrafficLight constructor
    public TrafficLight(String color, double rateOfChange){
        this.color = color;
        this.rateOfChange = rateOfChange;
    }

    // operate method
    public void operate(){
        if(rateOfChange < (Math.random() * ((1.0 - 10.0) + 1)) + 10.0)
            this.color = "Red";
        else
            this.color = "Green";
    }
}
