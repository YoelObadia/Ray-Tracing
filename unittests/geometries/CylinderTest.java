package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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

    /**
     * Test method for {@link geometries.Cylinder#findIntsersections(Ray)}.
     */
    @Test
    void testFindIntsersections() {

        Cylinder cylinder = new Cylinder(new Ray(new Point(2,0,0), new Vector(0,0,1)), 1d, 2d);

        // ============ Equivalence Partitions Tests ==============

        //TC01 ray is outside and parallel to the cylinder's ray

        List<Point> result = cylinder.findIntsersections(new Ray(new Point(5,0,0), new Vector(0,0,1)));
        assertNull(
                result,
                "Wrong number of points"
        );


        //TC02 ray starts inside and parallel to the cylinder's ray

        result = cylinder.findIntsersections(new Ray(new Point(2.5,0,1), new Vector(0,0,1)));
        assertEquals(
                1,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(2.5,0,2)),
                result,
                "Bad intersection point"
        );

        //TC03 ray starts outside and parallel to the cylinder's ray and crosses the cylinder

        result = cylinder.findIntsersections(new Ray(new Point(2.5,0,-1), new Vector(0,0,1)));
        assertEquals(
                2,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(2.5, 0, 0), new Point(2.5,0,2)),
                result,
                "Bad intersection point"
        );

        //TC04 ray starts from outside and crosses the cylinder

        result = cylinder.findIntsersections(new Ray(new Point(-2,0,0.5), new Vector(1,0,0)));
        assertEquals(
                2,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(1,0,0.5), new Point(3,0,0.5)),
                result,
                "Bad intersection points"
        );

        //TC05 ray starts from inside and crosses the cylinder

        result = cylinder.findIntsersections(new Ray(new Point(1.5,0,0.5), new Vector(1,0,0)));
        assertEquals(
                1,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(3,0,0.5)),
                result,
                "Bad intersection points"
        );

        //TC06 ray starts from outside the cylinder and doesn't cross the cylinder

        result = cylinder.findIntsersections(new Ray(new Point(5,0,0), new Vector(1,0,0)));
        assertNull(
                result,
                "Wrong number of points"
        );

        //TC07 ray starts from outside and crosses base and surface

        result = cylinder.findIntsersections(new Ray(new Point(1,0,-1), new Vector(1,0,1)));
        assertEquals(
                2,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(2,0,0), new Point(3,0,1)),
                result,
                "Bad intersection points"
        );

        //TC08 ray starts from outside and crosses surface and base

        result = cylinder.findIntsersections(new Ray(new Point(4,0,2), new Vector(-1,0,-1)));
        assertEquals(
                2,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(2,0,0),new Point(3,0,1)),
                result,
                "Bad intersection points"
        );


        // =============== Boundary Values Tests ==================

        //TC09 ray is on the surface of the cylinder (not bases)

        result = cylinder.findIntsersections(new Ray(new Point(3,0,0), new Vector(0,0,1)));
        assertNull(
                result,
                "Wrong number of points"
        );

        //TC10 ray is on the base of the cylinder and crosses 2 times

        result = cylinder.findIntsersections(new Ray(new Point(-1,0,0), new Vector(1,0,0)));
        assertNull(
                result,
                "Wrong number of points"
        );

        //TC11 ray is in center of the cylinder

        result = cylinder.findIntsersections(new Ray(new Point(2,0,0), new Vector(0,0,1)));
        assertEquals(
                1,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(2,0,2)),
                result,
                "Bad intersection points"
        );

        //TC12 ray is perpendicular to cylinder's ray and starts from outside the tube

        result = cylinder.findIntsersections(new Ray(new Point(-2,0,0.5), new Vector(1,0,0)));
        assertEquals(
                2,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(1,0,0.5), new Point(3,0,0.5)),
                result,
                "Bad intersection points"
        );

        //TC13 ray is perpendicular to cylinder's ray and starts from inside cylinder (not center)

        result = cylinder.findIntsersections(new Ray(new Point(1.5,0,0.5), new Vector(1,0,0)));
        assertEquals(
                1,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(3,0,0.5)),
                result,
                "Bad intersection points"
        );

        //TC14 ray is perpendicular to cylinder's ray and starts from the center of cylinder

        result = cylinder.findIntsersections(new Ray(new Point(2,0,0.5), new Vector(1,0,0)));
        assertEquals(
                1,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(3,0,0.5)),
                result,
                "Bad intersection points"
        );

        //TC15 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to inside

        result = cylinder.findIntsersections(new Ray(new Point(1,0,0.5), new Vector(1,0,0)));
        assertEquals(
                1,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(3,0,0.5)),
                result,
                "Bad intersection points"
        );

        //TC16 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to outside

        result = cylinder.findIntsersections(new Ray(new Point(3,0,0), new Vector(1,0,0)));
        assertNull(
                result,
                "Wrong number of points"
        );

        //TC17 ray starts from the surface to outside

        result = cylinder.findIntsersections(new Ray(new Point(3,0,0), new Vector(1,1,1)));
        assertNull(
                result,
                "Wrong number of points"
        );

        //TC18 ray starts from the surface to inside

        result = cylinder.findIntsersections(new Ray(new Point(3,0,0.5), new Vector(-1,0,0)));
        assertEquals(
                1,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(1,0,0.5)),
                result,
                "Bad intersection point"
        );

        //TC19 ray starts from the center

        result = cylinder.findIntsersections(new Ray(new Point(2,0,0), new Vector(1,0,1)));
        assertEquals(
                1,
                result.size(),
                "Wrong number of points"
        );
        assertEquals(
                List.of(new Point(3,0,1)),
                result,
                "Bad intersection point"
        );

        //TC20 prolongation of ray crosses cylinder

        result = cylinder.findIntsersections(new Ray(new Point(3,0,0), new Vector(1,0,0)));
        assertNull(
                result,
                "Wrong number of points"
        );

        //TC21 ray is on the surface starts before cylinder

        result = cylinder.findIntsersections(new Ray(new Point(3,0,-1), new Vector(0,0,1)));
        assertNull(
                result,
                "Wrong number of points"
        );

        //TC22 ray is on the surface starts at bottom's base

        result = cylinder.findIntsersections(new Ray(new Point(3,0,0), new Vector(0,0,1)));
        assertNull(
                result,
                "Wrong number of points"
        );

        //TC23 ray is on the surface starts on the surface

        result = cylinder.findIntsersections(new Ray(new Point(3,0,1), new Vector(0,0,1)));
        assertNull(
                result,
                "Wrong number of points"
        );

        //TC24 ray is on the surface starts at top's base

        result = cylinder.findIntsersections(new Ray(new Point(3,0,2), new Vector(0,0,1)));
        assertNull(
                result,
                "Wrong number of points"
        );
    }
}