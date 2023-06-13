package primitives;

import java.util.Objects;

/**
 * This class will serve to use point that is a fundamental object in geometry
 */
public class Point {

    /**
     * final field Point (0, 0, 0)
     */
    public static final Point ZERO = new Point(Double3.ZERO);
    /**
     * final field Double3
     */
    final Double3 xyz;

    /**
     * Constructor of Point with 3 doubles in parameter
     *
     * @param d1 for X axis
     * @param d2 for Y axis
     * @param d3 for Z axis
     */
    public Point(double d1, double d2, double d3) {
        xyz = new Double3(d1, d2, d3);
    }

    /**
     * Constructor of Point with a Double3 in parameter
     *
     * @param xyz Double3 value containing the 3 coordinates x, y, z
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }

    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }

    public double getZ() {
        return xyz.d3;
    }

    /**
     * Subtraction between vector and point
     *
     * @param point that is the point of the operation: vector - point
     * @return new vector
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    /**
     * Addition between 2 points and return a
     *
     * @param vector that is the vector of the operation: point + vector
     * @return new point
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Calculate the squared distance between 2 points
     *
     * @param point that is the second point
     * @return (( xb - xa)^2 + (yb-ya)^2 +(zb-za)^2)
     */
    public double distanceSquared(Point point) {
        return (
                (xyz.d1 - point.xyz.d1) * (xyz.d1 - point.xyz.d1) +
                        (xyz.d2 - point.xyz.d2) * (xyz.d2 - point.xyz.d2) +
                        (xyz.d3 - point.xyz.d3) * (xyz.d3 - point.xyz.d3)
        );
    }

    /**
     * Calculate the square root of the distance between 2 points
     *
     * @param point that is the second point
     * @return sqrt(( xb - xa)^2 + (yb-ya)^2 +(zb-za)^2)
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }
}
