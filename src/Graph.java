import java.util.HashMap;

public class Graph {
    private HashMap<Integer,Node> nodes;
    private HashMap<Integer,HashMap<Integer,Double>> out_edges;
    private HashMap<Integer, HashMap<Integer, Double>> in_edges;
    private int counter;

    public Graph(){
        this.nodes = new HashMap<>();  
        this.out_edges = new HashMap<>();
        this.in_edges = new HashMap<>();;
        this.counter = 0;
    }
    
    public Graph(HashMap<Integer, Node> nodes, HashMap<Integer, HashMap<Integer, Double>> out_edges, HashMap<Integer, HashMap<Integer, Double>> in_edges, int counter) {
        this.nodes = nodes;
        this.out_edges = out_edges;
        this.in_edges = in_edges;
        this.counter = counter;
    }

    public Graph(Graph g) {
        this.nodes = g.nodes;
        this.out_edges = g.out_edges;
        this.in_edges = g.in_edges;
        this.counter = g.counter;
    }

    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public void setNodes(HashMap<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    public HashMap<Integer, HashMap<Integer, Double>> getOut_edges() {
        return out_edges;
    }

    public void setOut_edges(HashMap<Integer, HashMap<Integer, Double>> out_edges) {
        this.out_edges = out_edges;
    }

    public HashMap<Integer, HashMap<Integer, Double>> getIn_edges() {
        return in_edges;
    }

    public void setIn_edges(HashMap<Integer, HashMap<Integer, Double>> in_edges) {
        this.in_edges = in_edges;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    // Get the Node object that has that id
    public Node getNode(int key){
        if(!nodes.containsKey(key))
            return null;
        return this.nodes.get(key);
    }

    // Get the value of the Edge that has the source and dest
    public Object getEdge (int src, int dest){
        //if the values are equal or there isn't a source node - it's not a valid Edge
        if(src==dest || !nodes.containsKey(src))
            return null;
        //if there isn't a connection between the source and the destination - there isn't an Edge exist in the graph
        else if(!out_edges.get(src).containsKey(dest))
            return null;
        return out_edges.get(src).get(dest);
    }
    public void addNode(int id, Location location){
        if(!nodes.containsKey(id)) { // if the Node doesn't exist - we will add him. if he exists - continue
            nodes.put(id, new Node(id, location));
            in_edges.put(id, new HashMap<>());
            out_edges.put(id, new HashMap<>());
            counter++;
        }
    }
    public void addEdge(int src, int dest, double w){
        if(src!=dest && nodes.containsKey(src) && nodes.containsKey(dest)){ // if the Edge doesn't exist - we will add him. if he exists - continue
            out_edges.get(src).put(dest,w);
            in_edges.get(dest).put(src,w);
            counter++;
        }
    }

    public void removeNode(int id) {
        if (nodes.containsKey(id)) {
            out_edges.get(id).forEach((k, v) -> removeInEdge(k,id));
            in_edges.get(id).forEach((k,v) -> removeOutEdge(k,id));
            out_edges.remove(id);
            in_edges.remove(id);
            nodes.remove(id);
            counter++;
        }
    }

     public void removeOutEdge(int src, int dest){
        if(src!=dest && nodes.containsKey(src) && nodes.containsKey(dest) && out_edges.get(src).containsKey(dest)){
            out_edges.get(src).remove(dest);
            counter++;
        }
     }
    public void removeInEdge(int src, int dest) {
        if (src != dest && nodes.containsKey(src) && nodes.containsKey(dest) && in_edges.get(src).containsKey(dest)) {
            in_edges.get(src).remove(dest);
            counter++;
        }
    }
}


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    