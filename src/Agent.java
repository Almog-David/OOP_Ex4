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
    private boolean tag;

    public Agent(int id, double value, int source, int dest, double speed, Location pos) {
        this.id = id;
        this.value = value;
        this.source = source;
        this.dest = dest;
        this.speed = speed;
        this.pos = pos;
        this.tag = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Location getPos() {
        return pos;
    }

    public void setPos(Location pos) {
        this.pos = pos;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }


    public static HashMap<Integer,Agent> load(String file) {
        HashMap<Integer,Agent> agents = new HashMap<>();
        org.json.JSONObject o = new org.json.JSONObject(file);
        org.json.JSONArray pk = o.getJSONArray("Agents");
        for (int i = 0; i < pk.length(); i++) {
            org.json.JSONObject agent = pk.getJSONObject(i).getJSONObject("Agent");
            int id = agent.getInt("id");
            double value = agent.getDouble("value");
            int src = agent.getInt("src");
            int dest = agent.getInt("dest");
            double speed = agent.getDouble("speed");
            String pos = agent.getString("pos");
            String[] position = pos.split(",");
            Location l = new Location(Double.parseDouble(position[0])
                    , Double.parseDouble(position[1])
                    , Double.parseDouble(position[2]));
            agents.put(id,new Agent(id, value, src, dest, speed, l));
        }
        return agents;
    }
}

