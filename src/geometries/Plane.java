package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Class Plane that contains 2 fields: point and vector
 * These are the 2 things that make a plane
 */
public class Plane implements Geometry{

    /**
     * Field point
     */
    private final Point q0;

    /**
     * Field vector
     */
    private final Vector normal;

    /**
     * Constructor of the plane
     * @param q0 point on the plane
     * @param normal vector normal of the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * Constructor of Plane with 3 Points in parameters for to calculate
     * the normal with the same formula that triangle
     * @param p1 first point of the plane
     * @param p2 second point of the plane
     * @param p3 third point of the plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q0 = p1;
        this.normal = null;
    }

    /**
     * Getter for the field q0
     * @return point q0 of the plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * Function getNormal without parameter
     * @return vector normal
     */
    public Vector getNormal() {
        return null;
    }

    /**
     * Use of the function getNormal of the interface Geometry
     * @param point that is the point on the geometry
     * @return vector normal
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
