package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Plane
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormalPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here

        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(
                new Vector(sqrt3, sqrt3, sqrt3),
                pl.getNormal(new Point(0, 0, 1)),
                "Bad normal to plane"
        );
    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal2() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here

        Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(
                new Vector(sqrt3, sqrt3, sqrt3),
                pl.getNormal(new Point(0, 0, 1)),
                "Bad normal to triangle"
        );
    }
}