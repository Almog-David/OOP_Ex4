import java.util.LinkedList;
import java.util.Queue;

public class Agent {
    private int id;
    private double value;
    private int source;
    private int dest;
    private double speed;
    private Location pos;
    private Queue<Integer> path; // in path we will save the path of the agent

    public Agent(int id, double value, int source, int dest, double speed, Location pos) {
        this.id = id;
        this.value = value;
        this.source = source;
        this.dest = dest;
        this.speed = speed;
        this.pos = pos;
        this.path = new LinkedList<Integer>();
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public double getValue() {return value;}

    public void setValue(double value) {this.value = value;}

    public int getSource() {return source;}

    public void setSource(int source) {this.source = source;}

    public int getDest() {return dest;}

    public void setDest(int dest) {this.dest = dest;}

    public double getSpeed() {return speed;}

    public void setSpeed(double speed) {this.speed = speed;}

    public Location getPos() {return pos;}

    public void setPos(Location pos) {this.pos = pos;}

    public Queue<Integer> getPath() {return path;}

    public void setPath(Queue<Integer> path) {this.path = path;}

    public void allocateAgents (GraphAlgo g){ // at the beginning of the game we put all the agents in the center
        int center = g.center();
        this.setSource(center);
    }


}

