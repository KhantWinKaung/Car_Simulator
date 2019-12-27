public class Vehicle {
    //Declare Vehicle variable
    String name;
    int position;
    Road currentRoad;
    public Vehicle() {
    }

    //Vehicle constructor
    public Vehicle(String name, int position, Road currentRoad) {
        this.name = name;
        this.position = position;
        this.currentRoad = currentRoad;
    }

    public void move(){
        this.position += 1;
    }
}
