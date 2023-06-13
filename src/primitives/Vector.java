package primitives;

import java.util.Objects;

/**
 * This class will serve to use vector for all the project that is
 * a fundamental object in geometry that has direction and size
 */
public class Vector extends Point {

    /**
     * Constructor of Vector with 3 double in parameter
     *
     * @param d1 for X axis
     * @param d2 for Y axis
     * @param d3 for Z axis
     */
    public Vector(double d1, double d2, double d3) {
        this(new Double3(d1, d2, d3));
    }

    /**
     * Constructor of Vector with a Double3 in parameter
     *
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(xyz, vector.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz +
                '}';
    }

    /**
     * Addition between 2 vectors where each coordinate of the first vector
     * is added with the same coordinate of the second vector
     *
     * @param vector that is the vector of the operation: vector1 + vector
     * @return new vector
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Scalar multiplication between 2 vectors
     *
     * @param a coefficient of the multiplication
     * @return new vector
     */
    public Vector scale(double a) {
        return new Vector(xyz.scale(a));
    }

    /**
     * Cross product between 2 vectors
     *
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
     *
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
     *
     * @return (x ^ 2 + y ^ 2 + z ^ 2)
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
     *
     * @return sqrt(x ^ 2 + y ^ 2 + z ^ 2)
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Transform the vector to be unit
     *
     * @return same vector with length = 1
     */
    public Vector normalize() {
        double len = length();
        return new Vector(xyz.reduce(len));
    }
}
