package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Sphere
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here

        Sphere sph = new Sphere(new Point(0, 0, 1), 1.0);
        assertEquals(
                new Vector(0, 0, 1),
                sph.getNormal(new Point(0, 0, 2)),
                "Bad normal to sphere"
        );
    }

    /**
     * Test method for {@link geometries.Sphere#findIntsersections(Ray)}.
     */
    @Test
    void testFindIntsersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntsersections(new Ray(
                        new Point(-1, 0, 0),
                        new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);

        List<Point> result = sphere.findIntsersections(
                new Ray(
                        new Point(-1, 0, 0),
                        new Vector(3, 1, 0)
                )
        );
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)

        result = sphere.findIntsersections(new Ray(new Point(0.8, 0.2, 0.2),
                new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0.8, 0.2, 0.9591663046625438)), result, "Bad intersection point");

        // TC04: Ray starts after the sphere (0 points)

        result = sphere.findIntsersections(new Ray(new Point(3, 3, 3), new Vector(1, 1, 1)));
        assertNull(result, "Wrong number of points");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)

        result = sphere.findIntsersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 0)), result, "Bad intersection point");

        // TC12: Ray starts at sphere and goes outside (0 points)

        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)

        result = sphere.findIntsersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0)));
        assertEquals(List.of(new Point(0, 0, 0), new Point(2, 0, 0)), result, "Bad intersection points");

        // TC14: Ray starts at sphere and goes inside (1 points)

        result = sphere.findIntsersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 0)), result, "Bad intersection point");

        // TC15: Ray starts inside (1 points)

        result = sphere.findIntsersections(new Ray(new Point(0.2, 0, 0), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 0)), result, "Bad intersection point");

        // TC16: Ray starts at the center (1 points)

        try {
            result = sphere.findIntsersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0)));
            fail("Error when ray starts at center of sphere");
        } catch (IllegalArgumentException exception) {
        }


        // TC17: Ray starts at sphere and goes outside (0 points)

        result = sphere.findIntsersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        // TC18: Ray starts after sphere (0 points)

        result = sphere.findIntsersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point

        result = sphere.findIntsersections(new Ray(new Point(0, 1, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        // TC20: Ray starts at the tangent point

        result = sphere.findIntsersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        // TC21: Ray starts after the tangent point

        result = sphere.findIntsersections(new Ray(new Point(2, 1, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line

        result = sphere.findIntsersections(new Ray(new Point(3, 0, 0), new Vector(0, 1, 0)));
        assertNull(result, "Wrong number of points");
    }
}