package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Vector
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
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(
                v1.length() * v2.length(), vr.length(),
                0.00001,
                "crossProduct() wrong result length"
        );

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(
                isZero(vr.dotProduct(v1)),
                "crossProduct() result is not orthogonal to 1st operand"
        );
        assertTrue(
                isZero(vr.dotProduct(v2)),
                "crossProduct() result is not orthogonal to 2nd operand"
        );

        // =============== Boundary Values Tests ==================

        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception"
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