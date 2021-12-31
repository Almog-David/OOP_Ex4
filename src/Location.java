public class Location {
    public double x;
    public double y;
    public double z;

    public Location() {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(Location l) {
        this.x = l.x;
        this.y = l.y;
        this.z = l.z;
    }

    public double getX() {return x;}

    public void setX(double x) {this.x = x;}

    public double getY() {return y;}

    public void setY(double y) {this.y = y;}

    public double getZ() {return z;}

    public void setZ(double z) {this.z = z;}
}
