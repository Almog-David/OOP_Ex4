import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private HashMap<Integer, HashMap<Integer, Double>> out_edges;
    private HashMap<Integer,HashMap<Integer,Double>> in_edges;
    private HashMap<Integer, Node> nodes;
    Location g = new Location();
    Graph graph = new Graph();

    @BeforeEach
    void setUp() {
    }

    @Test
    void getNodes() {
        graph = new Graph();
        nodes = graph.getNodes();
        Node node = new Node(0, new Location());
        Node node1 = new Node(1, g);
        g.setY(4); g.setX(2);
        Node node2 = new Node(2, g);
        g.setX(12); g.setY(9);
        Node node3 = new Node(3, g);
        nodes.put(0, node); nodes.put(1, node1); nodes.put(2, node2); nodes.put(3, node3);
        assertEquals(node1, nodes.get(1));
        assertEquals(node3, nodes.get(3));
        assertNotEquals(node2, nodes.get(0));
    }

    @Test
    void getNode() {
        graph = new Graph();
        nodes = graph.getNodes();
        Node node = new Node(0, new Location());
        Node node1 = new Node(1, g);
        g.setY(4); g.setX(2);
        Node node2 = new Node(2, g);
        g.setX(12); g.setY(9);
        Node node3 = new Node(3, g);
        nodes.put(0, node); nodes.put(1, node1); nodes.put(2, node2); nodes.put(3, node3);
        Node ans = graph.getNode(1);
        assertEquals(node1.getWeight(), ans.getWeight());
        assertEquals(node1.getId(), ans.getId());
        assertEquals(node1.getLocation().getY(), ans.getLocation().getY());
        Node ans2 = graph.getNode(5);
        assertEquals(null,ans2);
    }

    @Test
    void getEdge() {
        Graph g = new Graph();
        Location g1 = new Location(22,34,0);
        Node a = new Node(1,g1);
        Location g2 = new Location(14,24,0);
        Node b = new Node(2,g2);
        g.addNode(a.getId(),g1);
        g.addNode(b.getId(),g2);
        g.addEdge(a.getId(),b.getId(),22);
        Location g3 = new Location(12,17,0);
        Node c = new Node(3,g3);
        g.addNode(c.getId(),g3);
        g.addEdge(c.getId(),a.getId(),4);
        g.addEdge(b.getId(),c.getId(),7);
        g.addEdge(a.getId(),c.getId(),2);
        assertEquals(4,g.getEdge(c.getId(),a.getId()));
        assertEquals(22, g.getEdge(a.getId(),b.getId()));
        assertNotEquals(3, g.getEdge(a.getId(),a.getId()));

    }

    @Test
    void addNode() {
        nodes = new HashMap<Integer, Node>();
        assertTrue(nodes.isEmpty());

        graph = new Graph();

        Node node = new Node(0, new Location());
        Node node1 = new Node(1, g);
        g.setY(4); g.setX(2);
        Node node2 = new Node(2, g);
        g.setX(12); g.setY(9);
        Node node3 = new Node(3, g);
        graph.addNode(node.getId(),node.getLocation());
        graph.addNode(node1.getId(),node1.getLocation());
        graph.addNode(node2.getId(),node2.getLocation());
        graph.addNode(node3.getId(),node3.getLocation());
        assertTrue(graph.getNodes().size() == 4);

        graph.addNode(node.getId(),node.getLocation()); // adding a node that already exist
        assertTrue(graph.getNodes().size() == 4);
    }

    @Test
    void addEdge() {
        Graph g = new Graph();
        Location g1 = new Location(22,34,0);
        Node a = new Node(1,g1);
        Location g2 = new Location(14,24,0);
        Node b = new Node(2,g2);
        g.addNode(a.getId(),g1);
        g.addNode(b.getId(),g2);
        g.addEdge(a.getId(),b.getId(),22);
        assertTrue(g.getOut_edges().get(a.getId()).containsKey(b.getId()));
        Location g3 = new Location(12,17,0);
        Node c = new Node(2,g3);
        g.addNode(c.getId(),g3);
        g.addEdge(c.getId(),a.getId(),4);
        assertTrue(g.getOut_edges().get(c.getId()).containsKey(a.getId()));

    }

    @Test
    void removeNode() {
        graph = new Graph();
        nodes = graph.getNodes();
        assertTrue(graph.getNodes().size()==0);

        Node node = new Node(4,new Location());
        Node node1 = new Node(1, g);
        g.setY(4); g.setX(2);
        Node node2 = new Node(2, g);
        g.setX(12); g.setY(9);
        Node node3 = new Node(3, g);
        nodes.put(4, node); nodes.put(1, node1); nodes.put(2, node2); nodes.put(3, node3);
        assertTrue(graph.getNodes().size()==4);

        graph.removeNode(node.getId());
        assertTrue(graph.getNodes().size()==3);
        assertFalse(graph.getNodes().containsKey(node.getId()));

        graph.removeNode(node3.getId());
        assertTrue(graph.getNodes().size()==2);
        assertFalse(graph.getNodes().containsKey(node3.getId()));
    }

    @Test
    void removeEdge() {
        graph = new Graph();
        nodes = graph.getNodes();
        assertTrue(graph.getOut_edges().size()== 0);

        Node node = new Node(0, new Location());
        Node node1 = new Node(1, g);
        g.setY(4); g.setX(2);
        Node node2 = new Node(2, g);
        g.setX(12); g.setY(9);
        Node node3 = new Node(3, g);
        graph.addNode(0,node.getLocation()); graph.addNode(1,node1.getLocation());
        graph.addNode(2,node2.getLocation()); graph.addNode(3,node3.getLocation());
        graph.addEdge(1,3,4.22);
        graph.addEdge(0,2,9);
        assertTrue(graph.getOut_edges().get(1).size()== 1);
        assertTrue(graph.getOut_edges().get(0).size()== 1);

        graph.removeEdge(1, 3);
        assertFalse(graph.getOut_edges().get(1).containsValue(3));
    }
}