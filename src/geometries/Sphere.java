package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class sphere
 */
public class Sphere extends RadialGeometry implements Geometry{

    /**
     * Point that is the center of the sphere
     */
    private final Point center;

    /**
     * Radius of the sphere from the center
     */
    private final double radius;

    /**
     * Constructor of the Sphere with 2 parameters
     * @param center that is the Point
     * @param radius that is the length from the center point
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Getter for the field center
     * @return center of the sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Getter for the field radius
     * @return radius of the sphere
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Use of the function getNormal from the interface Geometry
     * @param point that is the point on the geometry
     * @return normalized vector
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
