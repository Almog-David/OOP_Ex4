import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    Location point = new Location(); // default - empty
    Location g = new Location(7, 1, 18);

    @BeforeEach
    void setUp() {
    }

    @Test
    void getX() {
        assertEquals(0, point.getX()); // empty constructor

        point.setX(5.5);
        assertEquals(5.5, point.getX());
        assertEquals(7, g.getX());
    }

    @Test
    void setX() {
        point.setX(11);
        assertEquals(11, point.getX());

        point.setX(2);
        assertNotEquals(11, point.getX()); //the x variable we changed
        assertEquals(2, point.getX());

        g.setX(3);
        assertNotEquals(7, g.getX());//the x variable we changed
        assertEquals(3, g.getX());
    }

    @Test
    void getY() {
        assertEquals(0, point.getY()); // empty constructor

        point.setY(0.3);
        assertEquals(0.3, point.getY());
        assertEquals(1, g.getY());
    }

    @Test
    void setY() {
        point.setY(6);
        assertEquals(6, point.getY());

        point.setY(0.1);
        assertNotEquals(6, point.getY());//the y variable we changed
        assertEquals(0.1, point.getY());

        g.setY(4.4);
        assertNotEquals(1, g.getY());//the y variable we changed
        assertEquals(4.4, g.getY());
    }

    @Test
    void getZ() {
        assertEquals(0, point.getZ()); // empty constructor

        point.setZ(10);
        assertEquals(10, point.getZ());
        assertEquals(18, g.getZ());
    }

    @Test
    void setZ() {
        point.setZ(3);
        assertEquals(3, point.getZ());

        point.setZ(12);
        assertNotEquals(3, point.getZ());//the z variable we changed
        assertEquals(12, point.getZ());

        g.setZ(8);
        assertNotEquals(18, g.getZ());//the z variable we changed
        assertEquals(8, g.getZ());
    }
}


