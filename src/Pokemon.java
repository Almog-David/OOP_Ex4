import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


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

    public static Queue<Pokemon> load(String file) {
        LinkedList<Pokemon> pokemons;
        try {
            pokemons = new LinkedList<Pokemon>();
            Object o = new JSONParser().parse(new FileReader(file));
            JSONObject jo = (JSONObject) o; // typecasting obj to JSONObject
            JSONArray Agent = (JSONArray) jo.get("Pokemons"); // reading the Agents from json
            Iterator a = Agent.iterator();
            while (a.hasNext()) {
                HashMap<String, Object> map = (HashMap<String, Object>) a.next();
                double v = (double) map.get("value");
                int t = (int) map.get("type");
                String p = (String) map.get("pos");
                String[] position = p.split(",");
                Location l = new Location(Double.parseDouble(position[0])
                        , Double.parseDouble(position[1])
                        , Double.parseDouble(position[2]));
                pokemons.add(new Pokemon(v,t,l));
            }
        } catch (IOException e1) {
            return null;
        } catch (ParseException e2) {
            return null;
        }
        return pokemons;
    }

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