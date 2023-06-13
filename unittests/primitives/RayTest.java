package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RayTest {

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void testFindClosestPoint() {
        Ray r = new Ray(new Point(1, 0, 0), new Vector(3, 3, 3));
        List<Point> myList = new LinkedList<>();

        // ============ Equivalence Partitions Tests ==============
        // TC01: Middle element in list is the closest
        myList.add(new Point(6, 5, 4));
        myList.add(new Point(2, 0, 0));
        myList.add(new Point(3, 4, 4));
        assertEquals(
                myList.get(myList.size() / 2),
                r.findClosestPoint(myList),
                "Error ");


        // =============== Boundary Values Tests ==================
        //TC02: Null list
        myList = null;
        assertNull(
                r.findClosestPoint(myList),
                "Error !");

        //TC03:First element in list is the closest
        myList = new LinkedList<>();
        myList.add(new Point(2, 0, 0));
        myList.add(new Point(6, 5, 4));
        myList.add(new Point(3, 4, 4));
        assertEquals(
                myList.get(0),
                r.findClosestPoint(myList),
                "Error ");


        //TC04: Last element in list is the closest
        myList = null;
        myList = new LinkedList<>();
        myList.add(new Point(6, 5, 4));
        myList.add(new Point(3, 4, 4));
        myList.add(new Point(2, 0, 0));
        assertEquals(
                myList.get(myList.size() - 1),
                r.findClosestPoint(myList),
                "Error ");
    }
}