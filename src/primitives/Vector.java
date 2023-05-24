package primitives;

/**
 * This class will serve to use vector for all the project that is
 * a fundamental object in geometry that has direction and size
 */
public class Vector extends Point{

    /**
     * Constructor of Vector with 3 double in parameter
     * @param d1 for X axis
     * @param d2 for Y axis
     * @param d3 for Z axis
     */
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if(d1 == 0 && d2 == 0 && d3 == 0)
            throw new IllegalArgumentException("Vector null is not permitted!");
    }

    /**
     * Constructor of Vector with a Double3 in parameter
     * @param xyz
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector null is not permitted!");
        }
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }

    /**
     * Addition between 2 vectors
     * @param vector that is the vector of the operation: vector1 + vector
     * @return new vector
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Scalar multiplication between 2 vectors
     * @param a coefficient of the multiplication
     * @return new vector
     */
    public Vector scale(double a) {
        return new Vector(xyz.scale(a));
    }

    /**
     * Cross product between 2 vectors
     * @param vector that is the second vector of the operation
     * @return new vector
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(
                (xyz.d2 * vector.xyz.d3) - (xyz.d3 * vector.xyz.d2),
                (xyz.d3 * vector.xyz.d1) - (xyz.d1 * vector.xyz.d3),
                (xyz.d1 * vector.xyz.d2) - (xyz.d2 * vector.xyz.d1)
        );
    }

    /**
     * Dot product between 2 vectors
     * @param vector that is the second vector of the operation
     * @return new vector
     */
    public double dotProduct(Vector vector) {
        return (
                (xyz.d1 * vector.xyz.d1) +
                (xyz.d2 * vector.xyz.d2) +
                (xyz.d3 * vector.xyz.d3)
        );
    }

    /**
     * Calculate the squared length between 2 vectors
     * @return (x^2 + y^2 + z^2)
     */
    public double lengthSquared() {
        return (
                (xyz.d1 * xyz.d1) +
                (xyz.d2 * xyz.d2) +
                (xyz.d3 * xyz.d3)
        );
    }

    /**
     * Calculate the length between 2 vectors
     * @return sqrt(x^2 + y^2 + z^2)
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Transform the vector to be unit
     * @return same vector with length = 1
     */
    public Vector normalize() {
        double len = length();
        return new Vector(xyz.reduce(len));
    }
}