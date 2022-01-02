import java.util.HashMap;


public class Pokemon {
    private double value;
    private int type; // -1 if the src is bigger than dest, 1 if the dest is bigger than the src
    private Location pos;
    private boolean captured; // if we allocate an agent to the pokemon it will change to true

    public Pokemon(double value, int type, Location pos) {
        this.value = value;
        this.type = type;
        this.pos = pos;
        this.captured = false;
    }

    public double getValue() {return value;}

    public void setValue(double value) {this.value = value;}

    public int getType() {return type;}

    public void setType(int type) {this.type = type;}

    public Location getPos() {return pos;}

    public void setPos(Location pos) {this.pos = pos;}

    public boolean isCaptured() {return captured;}

    public void setCaptured(boolean captured) {this.captured = captured;}

    public int [] findEdge(Graph g) {
        Location C = this.pos; // The pokemon Location
        int[] ans = new int[2];
        for (HashMap.Entry<Integer, Node> s : g.getNodes().entrySet()) {
            Node first = s.getValue();
            for (HashMap.Entry<Integer, Double> d : g.getOut_edges().get(first.getId()).entrySet()) {
                Node second = g.getNode(d.getKey());
                Location A = first.getLocation();
                Location B = second.getLocation();
                if (distance(A, C) + distance(B, C) == distance(A, B)) {
                    if (this.type < 0 && first.getId() - second.getId() > 0) // if the source is bigger than the dest - the pokemon type is -1
                        ans = new int[]{first.getId(), second.getId()};
                    if (this.type > 0 && first.getId() - second.getId() < 0) // if the dest is bigger than the source - the pokemon type is 1
                        ans = new int[]{second.getId(), first.getId()};
                }
            }
        }
        return ans;
    }

    public double distance(Location f,Location s){
        return Math.sqrt(Math.abs(f.getX()-s.getX())+ Math.sqrt(f.getY()-s.getY()));
    }
}