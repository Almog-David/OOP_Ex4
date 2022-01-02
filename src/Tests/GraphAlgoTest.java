import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphAlgoTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        GraphAlgo g = new GraphAlgo();
        String file = "C:/Java Projects/Ex2_OOP/src/data/G1.json";
        g.load(file);
        assertTrue(g.save(file +'s'));
    }

    @Test
    void load() {
        GraphAlgo g = new GraphAlgo();
        assertTrue(g.load("C:/Java Projects/Ex2_OOP/src/data/G3.json"));

        assertTrue(g.load("C:/Java Projects/Ex2_OOP/src/data/G1.json"));
    }

    @Test
    void shortestPath() {
        GraphAlgo g = new GraphAlgo();
        Graph graph = g.getGraph();
        Location g1 = new Location(22,34,0);
        Node a = new Node(1,g1);
        Location g2 = new Location(14,24,0);
        Node b = new Node(2,g2);
        Location g3 = new Location(17,56,0);
        Node c = new Node(3,g3);
        Location g4 = new Location(17,56,0);
        Node d = new Node(4,g4);
        graph.addNode(a.getId(),g1);
        graph.addNode(b.getId(),g2);
        graph.addNode(c.getId(),g3);
        graph.addNode(d.getId(),g4);
        graph.addEdge(a.getId(),b.getId(),2);
        graph.addEdge(b.getId(),c.getId(),7);
        graph.addEdge(b.getId(),d.getId(),1);
        graph.addEdge(c.getId(),a.getId(),3);
        graph.addEdge(c.getId(), b.getId(),1.5);
        graph.addEdge(d.getId(), a.getId(),4);
        graph.addEdge(d.getId(), c.getId(),2);
        List <Integer> ans = g.shortestPath(a.getId(),c.getId());
        assertEquals(4,ans.size());
        assertEquals(1,ans.get(0));
        assertEquals(2,ans.get(1));
        assertEquals(4,ans.get(2));
        assertEquals(3,ans.get(3));
        double weight = g.shortestPath(a.getId(),c.getId()).size();
        assertEquals(4, weight);

        g.load("C:/Java Projects/Ex2_OOP/src/data/G2.json");
        List <Integer> ans9 = g.shortestPath(1,14);
        assertNotEquals(7,ans9.size());

        g.load("C:/Java Projects/Ex2_OOP/src/data/G3.json");
        List <Integer> ans10 = g.shortestPath(1,14);
        assertNotEquals(2,ans10.size());


    }

    @Test
    void center() {
        GraphAlgo g = new GraphAlgo();
        Graph graph = g.getGraph();
        Location g1 = new Location(22,34,0);
        Node a = new Node(1,g1);
        Location g2 = new Location(14,24,0);
        Node b = new Node(2,g2);
        Location g3 = new Location(17,56,0);
        Node c = new Node(3,g3);
        graph.addNode(1,g1);
        graph.addNode(2,g2);
        graph.addNode(3,g3);
        graph.addEdge(a.getId(),b.getId(),5);
        graph.addEdge(a.getId(),c.getId(),2);
        graph.addEdge(b.getId(),c.getId(),7);
        graph.addEdge(b.getId(),a.getId(),9);
        graph.addEdge(c.getId(),a.getId(),3);
        graph.addEdge(c.getId(), b.getId(),1.5);
        int ans = g.center();
        assertEquals(3,ans);

        g.load("C:\\Java Projects\\Ex2_OOP\\src\\data\\G1.json");
        int n = g.center();
        assertEquals(8,n);
        g.getGraph().removeNode(8);
        int n2 = g.center();
        assertNotEquals(n,n2);
    }

    @Test
    void tsp() {
        GraphAlgo g = new GraphAlgo();
        g.load("C://Java Projects//Ex2_OOP//src//data//100000.json");
        List<Integer> temp = new LinkedList<>();
        temp.add(1);
        temp.add(2);
        temp.add(13);
        temp.add(17);
        temp.add(25);
        temp.add(17);
        temp.add(35);
        temp.add(147);
        temp.add(323);
        temp.add(324);

        List <Integer> ans = g.tsp(temp);
        assertNotEquals(2,ans.size());
    }

}


