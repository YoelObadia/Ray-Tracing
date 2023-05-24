package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class VectorTest: we will check all the calculation functions of the class Vector
 */
class VectorTest {

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        Vector v = new Vector(2, 3, 4);
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(
                v,
                v1.add(new Vector(1, 1, 1)),
                "ERROR: Bad addition between 2 vectors!"
        );
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        Vector v = new Vector(2, 4, 6);
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(
                v,
                v1.scale(2),
                "ERROR: Bad scalar operation!"
        );
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v = new Vector(1, -2, 1);
        Vector v1 = new Vector(3, 5, 7);
        assertEquals(
                v,
                v1.crossProduct(new Vector(1, 2, 3)),
                "ERROR: Bad cross product"
        );
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)} (primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        Vector v = new Vector(1, 2, 3);
        assertEquals(
                20,
                v.dotProduct(new Vector(2, 3, 4)),
                "ERROR: Bad dot product!"
        );
    }

    /**
     * Test method for {@link Vector#lengthSquared()} (primitives.Vector)}.
     */
    @Test
    void testLengthSquared() {
        Vector v = new Vector(1, 2, 3);
        assertEquals(
                14,
                v.lengthSquared(),
                "ERROR: Bad squared length!"
        );
    }

    /**
     * Test method for {@link Vector#length()} (primitives.Vector)}.
     */
    @Test
    void testLength() {
        Vector v = new Vector(1, 2, 3);
        assertEquals(
                Math.sqrt(14),
                v.length(),
                "ERROR: Bad length!"
        );
    }

    /**
     * Test method for {@link Vector#normalize()} (primitives.Vector)}.
     */
    @Test
    void testNormalize() {
        Vector u = new Vector(0.2672612419124244, 0.5345224838248488, 0.8017837257372732);
        Vector v = new Vector(1, 2, 3);
        assertEquals(u, v.normalize(), "ERROR: Bad normalisation of the vector!");
    }
}