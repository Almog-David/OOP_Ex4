import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Location g = new Location();
    Node n = new Node(0,g);

    @BeforeEach
    void setUp() {
    }

    @Test
    void getId() {
        assertEquals(0, n.getId());
        n = new Node(1, g);
        assertNotEquals(0, n.getId());
    }

    @Test
    void getWeight() {
        assertEquals(0,n.getWeight());

        Node d = new Node(1,g);
        assertEquals(0,d.getWeight());
        assertNotEquals(25,d.getWeight());
    }

    @Test
    void setWeight() {
        n.setWeight(55);
        assertEquals(55,n.getWeight());
        assertNotEquals(0,n.getWeight());

        n.setWeight(21);
        n.setWeight(22);
        assertEquals(22,n.getWeight());
        assertNotEquals(21,n.getWeight());
        assertNotEquals(55,n.getWeight());
    }

    @Test
    void getTag() {
        assertEquals(0,n.getTag());

        Node d = new Node(1,g);
        assertEquals(0, d.getTag());
        assertNotEquals(1, d.getTag());
    }

    @Test
    void setTag() {
        n.setTag(3);
        assertNotEquals(1, n.getTag());

        n.setTag(2);
        n.setTag(5);
        assertNotEquals(3, n.getTag());
        assertNotEquals(2, n.getTag());
        assertEquals(5, n.getTag());
    }

    @Test
    void getLocation() {
        assertEquals(g.getX(), n.getLocation().getX());
        assertEquals(g.getY(), n.getLocation().getY());
        assertEquals(g.getZ(), n.getLocation().getZ());

        Location d = new Location(12.0, 4.0,0.0);
        n.setLocation(d);
        assertNotEquals(g, n.getLocation());
        assertNotEquals(0.0, n.getLocation().getX()); // previous p.x()
        assertNotEquals(0.0, n.getLocation().getY()); // previous p.y()
        assertEquals(d.getX(), n.getLocation().getX());
        assertEquals(d.getY(), n.getLocation().getY());
        assertEquals(d.getZ(), n.getLocation().getZ()); // previous p.x() is equals to p.z()
    }

    @Test
    void setLocation() {
        n = new Node(1,g);
        Location x = new Location(2.0,3.0,1.0);
        n.setLocation(x);
        assertEquals(x.getX(),n.getLocation().getX());
        assertEquals(x.getY(),n.getLocation().getY());
        assertEquals(x.getZ(),n.getLocation().getZ());

        Location y = new Location (3.0,1.5,4.7);
        n.setLocation(y);
        assertNotEquals(x.getX(),n.getLocation().getX());
        assertNotEquals(x.getY(),n.getLocation().getY());
        assertNotEquals(x.getZ(),n.getLocation().getZ());
    }
}