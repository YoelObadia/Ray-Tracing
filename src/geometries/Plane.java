package geometries;

import primitives.*;
import java.util.List;
import static primitives.Util.*;

/**
 * Class Plane that contains 2 fields: point and vector
 * These are the 2 things that make a plane
 */
public class Plane extends Geometry {

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
     * @param q0     point on the plane
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

        // v1 = p2 - p1
        Vector v1 = p1.subtract(p2);
        // v2 = p3 - p1
        Vector v2 = p1.subtract(p3);

        // n = normalize( v1 x v2 )
        this.normal = (v1.crossProduct(v2)).normalize();
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
        return normal;
    }

    /**
     * Use of the function getNormal of the interface Geometry
     * @param point that is the point on the geometry
     * @return vector normal
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Use of the function findIntersections from the interface intersectable
     * @param ray that allow us to know if there are intersections
     * @return list of intersections points
     */
    @Override
    public List<GeoPoint> findGeoIntsersectionsHelper(Ray ray) {

        Vector p0Q;

        // q is a point from the plane and getP0 returns the origin of the ray
        try {
            p0Q = q0.subtract(ray.getP0());
        }
        // No intersection
        catch (IllegalArgumentException e) {
            return null;
        }

         /*
            The value of our variable t that we search for is the result of this formula:(p0−l0)⋅n/l⋅n
            p0 is the point of our plane, n is the normal of our plane,
            l0 is the origin point of the ray and l is the vector director of the ray.
            So this value helps us to determine the intersection or not.
         */
        double check = normal.dotProduct(ray.getDir());

        // Ray is parallel to the plane
        if (isZero(check)) {
            return null;
        }

        // It gives us the t to determine the coordinate of the intersection,
        // so the x,y,z according to the value of t
        double t = alignZero(normal.dotProduct(p0Q) / check);

        if (t <= 0) {
            return null;
        } else {
            // We use the function getPoint of Ray class
            return List.of((new GeoPoint(this,ray.getPoint(t))));
        }
    }
}
