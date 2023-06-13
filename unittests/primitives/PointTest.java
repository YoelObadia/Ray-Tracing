package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testing Point
 */
class PointTest {

    /**
     * Test method for {@link primitives.Point#subtract(Point)} (primitives.Point)}.
     */
    @Test
    void testSubtract() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Point(1, 1, 1).subtract(new Point(1, 1, 1)),
                "ERROR: Bad vector obtained"
        );
    }

    /**
     * Test method for {@link primitives.Point#add(Vector)} (primitives.Point)}.
     */
    @Test
    void testAdd() {
        Point p1 = new Point(1, 2, 3);
        assertEquals(
                new Point(0, 0, 0),
                p1.add(new Vector(-1, -2, -3)),
                "ERROR: Point + Vector does not work correctly"
        );
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)} (primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        Point p = new Point(2, 4, 6);
        assertEquals(
                14,
                p.distanceSquared(new Point(1, 2, 3)),
                "ERROR: Bad square distance"
        );
    }

    /**
     * Test method for {@link primitives.Point#distance(Point)} (primitives.Point)}.
     */
    @Test
    void testDistance() {
        Point p = new Point(2, 4, 6);
        assertEquals(
                Math.sqrt(14),
                p.distance(new Point(1, 2, 3)),
                "ERROR: Bad distance"
        );
    }
}