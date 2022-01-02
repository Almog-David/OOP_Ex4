import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    public void setPath(LinkedList<Integer> path) {this.path = path;}

    public static LinkedList<Agent> load(String file) {
        LinkedList<Agent> agents;
        try {
            agents = new LinkedList<Agent>();
            Object obj = new JSONParser().parse(new FileReader(file)); // parsing the file
            JSONObject jo = (JSONObject) obj; // typecasting obj to JSONObject
            JSONArray Agent = (JSONArray) jo.get("Agents"); // reading the Agents from json
            Iterator a = Agent.iterator();
            while (a.hasNext()) {
                HashMap<String, Object> map = (HashMap<String, Object>) a.next();
                int id = (int) map.get("id");
                double value = (double) map.get("value");
                int src = (int) map.get("src");
                int dest = (int) map.get("dest");
                double speed = (double) map.get("speed");
                String pos = (String) map.get("pos");
                String[] position = pos.split(",");
                Location l = new Location(Double.parseDouble(position[0])
                        , Double.parseDouble(position[1])
                        , Double.parseDouble(position[2]));
                agents.add(new Agent(id, value, src, dest, speed, l));
            }
        } catch (IOException e1) {
            return null;
        } catch (ParseException e2) {
            return null;
        }
        return agents;
    }

    public void allocateAgents (GraphAlgo g){ // at the beginning of the game we put all the agents in the center
        int center = g.center();
        this.setSource(center);
    }


}

