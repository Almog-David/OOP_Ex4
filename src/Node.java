public class Node {
    private int id;
    private double weight;
    private int tag;
    private Location location;

    public Node(int id, Location location) {
        this.id = id;
        this.weight = 0.0;
        this.tag = 0;
        this.location = location;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public double getWeight() {return weight;}

    public void setWeight(double weight) {this.weight = weight;}

    public int getTag() {return tag;}

    public void setTag(int tag) {this.tag = tag;}

    public Location getLocation() {return location;}

    public void setLocation(Location location) {this.location = location;}
}
