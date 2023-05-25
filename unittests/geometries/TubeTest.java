package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here

        Tube tube = new Tube(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1)), 2);
        assertEquals(
                new Vector(-0.8164965809277264, 0.40824829046386263, 0.40824829046386263),
                tube.getNormal(new Point(1, 2, 2)),
                "Bad normal to tube!"
        );
    }
}