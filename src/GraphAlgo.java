import com.google.gson.*;
import java.util.*;

public class GraphAlgo {
    private Graph graph;

    private GraphAlgo() {this.graph = new Graph();}
    public GraphAlgo(Graph graph) {this.graph = graph;}

    public Graph getGraph() {return graph;}

    public void setGraph(Graph graph) {this.graph = graph;}

    public boolean save(String file) { // need to fix save

        try {
            GsonBuilder gson =new GsonBuilder();
            gson.registerTypeAdapter(Graph.class, new Graph());
            Gson g= gson.create();
            PrintWriter gFile=new PrintWriter(new File(file));
            gFile.write(g.toJson(graph));
            gFile.close();
            return true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("can't write the graph to a file ");
            e.printStackTrace();
        }
        return false;
    }

    public boolean load(String file) { // need to fix load

        try {
            GsonBuilder builder=new GsonBuilder();
            builder.registerTypeAdapter(Graph.class, new Graph().DWGraph_DSJson());
            Gson gson=builder.create();
            BufferedReader gFile=new BufferedReader(new FileReader(file));
            directed_weighted_graph g=gson.fromJson(gFile,DWGraph_DS.class);
            init(g);
            return true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("can't read the graph from the file");
            e.printStackTrace();
        }
        return false;
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

    public HashMap<Integer,Node> Dijkstra(int src) {
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

    public boolean relax(int src, int dest){
        Node s = this.graph.getNode(src);
        Node d = this.graph.getNode(dest);
        double e = this.graph.getEdge(src, dest);
        if (d.getWeight() <= s.getWeight() + e)
            return false;

        d.setWeight(s.getWeight() + e);
        return true;
    }

    public void reset(){
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

