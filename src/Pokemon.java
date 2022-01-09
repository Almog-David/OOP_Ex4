//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import  com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


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

    public static LinkedList<Pokemon> load(String file) {
        LinkedList<Pokemon> pokemons = new LinkedList<>();
        org.json.JSONObject o = new org.json.JSONObject(file);
        org.json.JSONArray pk = o.getJSONArray("Pokemons");
        for (int i = 0; i < pk.length(); i++) {
            org.json.JSONObject pokemon = pk.getJSONObject(i).getJSONObject("Pokemon");
            double v = pokemon.getDouble("value");
            int t = pokemon.getInt("type");
            String p = pokemon.getString("pos");
            String[] position = p.split(",");
            Location l = new Location(Double.parseDouble(position[0])
                    , Double.parseDouble(position[1])
                    , Double.parseDouble(position[2]));
            pokemons.add(new Pokemon(v, t, l));
        }
        return pokemons;
    }



    public int [] findEdge(Graph g) {
        double epsilon = 0.000000009;
        Location C = this.pos; // The pokemon Location
        int[] ans = new int[2];
        for (HashMap.Entry<Integer, Node> s : g.getNodes().entrySet()) {
            Node first = s.getValue();
            for (HashMap.Entry<Integer, Double> d : g.getOut_edges().get(first.getId()).entrySet()) {
                Node second = g.getNode(d.getKey());
                Location A = first.getLocation();
                Location B = second.getLocation();
                double ax = A.getX();
                double ay = A.getY();
                double bx = B.getX();
                double by = B.getY();
                double m = (by-ay)/(bx-ax);
                double n = by - m*bx;
                double ab=distance(A, B);
                double ap=distance(A, C);
                double pb=distance(C,B);
                double pokedistance=ap+pb;
                if (Math.abs(pokedistance-ab)<=epsilon || C.y - m*C.x - n < epsilon  && -1* epsilon < C.y - m*C.x - n) {
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
        return Math.sqrt(Math.pow(f.getX()-s.getX(),2)+ Math.pow(f.getY()-s.getY(),2));

    }
}

