package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Cylinder
 */
class CylinderTest {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here

        Cylinder cylinder = new Cylinder(
                new Ray(new Point(1, 1, 1), new Vector(1, 1, 1)),
                2,
                10
        );
        assertEquals(
                new Vector(-0.8164965809277264, 0.40824829046386263, 0.40824829046386263),
                cylinder.getNormal(new Point(1, 2, 2)),
                "Bad normal to cylinder!"
        );
    }
}