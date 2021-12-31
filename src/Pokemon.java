public class Pokemon {
    private double value;
    private int type;
    private Location pos;

    public Pokemon(double value, int type, Location pos) {
        this.value = value;
        this.type = type;
        this.pos = pos;
    }

    public double getValue() {return value;}

    public void setValue(double value) {this.value = value;}

    public int getType() {return type;}

    public void setType(int type) {this.type = type;}

    public Location getPos() {return pos;}

    public void setPos(Location pos) {this.pos = pos;}
}