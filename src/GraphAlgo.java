import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GraphAlgo {
    private Graph graph;

    public GraphAlgo() {this.graph = new Graph();}
    public GraphAlgo(Graph graph) {this.graph = graph;}

    public Graph getGraph() {return graph;}

    public void init(Graph graph) {this.graph = graph;}

    public boolean save(String file) { // need to fix save
        JSONObject ans=new JSONObject(); //creating a json object
        JSONArray edges=new JSONArray(); // creating an array in order to save the Edges information
        JSONArray nodes=new JSONArray(); // creating an array in order to save the Nodes information
        Iterator<Map.Entry<Integer, HashMap<Integer, Double>>> source = this.graph.getOut_edges().entrySet().iterator();
        while (source.hasNext()) {
            Map.Entry<Integer, HashMap<Integer, Double>> first = source.next();
            int src = first.getKey();
            Iterator<Map.Entry<Integer, Double>> destination = this.graph.getOut_edges().get(src).entrySet().iterator();
            while (destination.hasNext()) {
                Map.Entry<Integer, Double> second = destination.next();
                int dest = second.getKey();
                JSONObject edge = new JSONObject();
                edge.put("src", src);
                edge.put("w", this.graph.getEdge(src,dest));
                edge.put("dest", dest);
                edges.add(edge);
            }
        }
            Iterator<Map.Entry<Integer, Node>> iterNodes = this.graph.getNodes().entrySet().iterator();
            while (iterNodes.hasNext()) {
                Map.Entry<Integer, Node> v = iterNodes.next();
                JSONObject vertex=new JSONObject();
                vertex.put("pos",v.getValue().getLocation().toString());
                vertex.put("id",v.getKey());
            }
            ans.put("Edges",edges);
            ans.put("Nodes",nodes);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(ans.toJSONString());
                writer.flush();
                return true;
            }
            catch (IOException e) { return false;}
        }

    public boolean load(String file) {
        try {
            Graph g = new Graph();
            Object obj = new JSONParser().parse(file); // parsing the file
            JSONObject jo = (JSONObject) obj; // typecasting obj to JSONObject
            JSONArray nodes = (JSONArray) jo.get("Nodes"); // reading the Nodes from json
            Iterator i = nodes.iterator();
            while (i.hasNext()) {
                HashMap<String, Object> map = (HashMap<String, Object>) i.next();
                String pos = (String) map.get("pos");
                int id = (int) ((long) map.get("id"));
                String[] position = pos.split(",");
                Location l = new Location(Double.parseDouble(position[0])
                        , Double.parseDouble(position[1])
                        , Double.parseDouble(position[2]));
                g.addNode(id, l);
            }
            JSONArray edges = (JSONArray) jo.get("Edges"); //reading the edges
            i = edges.iterator();
            while (i.hasNext()) {
                HashMap<String, Object> map = (HashMap<String, Object>) i.next();
                int src = (int) (long) map.get("src");
                int dest = (int) (long) map.get("dest");
                double w = (double) map.get("w");
                g.addEdge(src, dest, w);
            }
            this.init(g);
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }

    public List<Integer> shortestPath(int src, int dest){
        HashMap<Integer, Node> D = Dijkstra(src);
        List<Integer> ans = new LinkedList<>();
        Node location = graph.getNode(dest);
        while (location!=null) {
            int curr = location.getId();
            ans.add(0,location.getId());
            location = D.get(curr);
        }
        return ans;
    }

    public double calculateLength(List<Integer> l) {
        double ans = 0;
        for (int i = 0; i < l.size() - 1; i++) {
            ans += this.graph.getEdge(l.get(i), l.get(i + 1));
        }
        return ans;
    }

    public int center(){
        double max, temp;
        List list;
        HashMap<Integer, Double> ans = new HashMap<>();
        for (int first : graph.getNodes().keySet()) {
            max = 0;
            for (int second : graph.getNodes().keySet()) {
                if (first!= second) {
                    list = shortestPath(first, second);
                    temp = calculateLength(list);
                    max = Math.max(max,temp);
                }
            }
            ans.put(first, max);
        }
        return Collections.min(ans.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private HashMap<Integer,Node> Dijkstra(int src) {
        reset(); // reset the nodes information
        HashMap<Integer, Node> previous = new HashMap<>(); // save the node previous pointer
        Queue<Node> neighbours = new PriorityQueue<>((v1, v2) -> (int) (v1.getWeight() - v2.getWeight()));
        Node first = this.graph.getNode(src);
        first.setWeight(0);
        first.setTag(1);
        neighbours.add(first);
        while (!neighbours.isEmpty()) {
            Node curr = neighbours.poll(); // we need to take the node with the minimum weight.
            Iterator<Map.Entry<Integer, Double>> itr = this.graph.getOut_edges().get(curr.getId()).entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<Integer, Double> entry = itr.next();
                if (relax(curr.getId(), entry.getKey())) {
                    Node temp = this.graph.getNode(entry.getKey());
                    if (!previous.containsKey(temp.getId()))
                        previous.put(temp.getId(), curr);
                    else {
                        previous.replace(temp.getId(), curr);
                    }
                    if (this.getGraph().getNode(temp.getId()).getTag() == 0) {
                        this.graph.getNode(temp.getId()).setTag(1);
                        neighbours.add(temp);
                    }
                }
            }
        }
        return previous;
    }

    private boolean relax(int src, int dest){
        Node s = this.graph.getNode(src);
        Node d = this.graph.getNode(dest);
        double e = this.graph.getEdge(src, dest);
        if (d.getWeight() <= s.getWeight() + e)
            return false;

        d.setWeight(s.getWeight() + e);
        return true;
    }

    private void reset(){
        for (HashMap.Entry<Integer, Node> set : this.graph.getNodes().entrySet()){
            set.getValue().setTag(0);
            set.getValue().setWeight(Double.MAX_VALUE);
        }
    }

    public List<Integer> tsp(List<Integer> cities) {
        if(cities==null || cities.size()==0){
            return null;
        }
        HashMap<Integer,Integer> route = new HashMap<>();
        int start = cities.get(0);
        while (cities.size()-1>0){
            HashMap<Integer,Node> map = Dijkstra(cities.remove(0));
            if(!route.containsKey(cities.get(0))){
                Node location = graph.getNode(cities.get(0));
                while (location != null) {
                    int curr = location.getId();
                    if(map.get(curr)!=null) {
                        route.put(map.get(curr).getId(), curr);
                    }
                    location = map.get(curr);
                }
            }
        }
        List<Integer> ans = new LinkedList<>();
        while (route.get(start)!=null){
            int key=route.get(start);
            ans.add(key);
            start=key;
        }
        ans.add(start);
        return  ans;
    }

}

