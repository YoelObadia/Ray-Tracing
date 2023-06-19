package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class sphere
 */
public class Sphere extends RadialGeometry {

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
     *
     * @param center that is the Point
     * @param radius that is the length from the center point
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Getter for the field center
     *
     * @return center of the sphere
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Getter for the field radius
     *
     * @return radius of the sphere
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Use of the function getNormal from the interface Geometry
     *
     * @param point that is the point on the geometry
     * @return normalized vector
     */
    @Override
    public Vector getNormal(Point point) {
        // n = normalize(P - 0)
        return (point.subtract(getCenter())).normalize();
    }

    /**
     * Use of the function findIntersections from the interface intersectable
     *
     * @param ray that allow us to know if there are intersections
     * @return list of intersections points
     */
    @Override
    public List<GeoPoint> findGeoIntsersectionsHelper(Ray ray, double maxDistance) {


        Vector u = center.subtract(ray.getP0());
        double tm = alignZero(u.dotProduct(ray.getDir()));
        double d = alignZero(Math.sqrt(u.lengthSquared() - (tm * tm)));

        // No intersections point
        if (d >= radius)
            return null;

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = tm - th;
        double t2 = tm + th;

        // We have to check the order of the intersections because
        // there is the possibility of 2 intersections points

        // No intersections point
        if (t1 <= 0 && t2 <= 0)
            return null;

        // 2 intersections points
        if (t1 > 0 && t2 > 0 &&
                alignZero(t1 - maxDistance) <= 0 &&
                alignZero(t2 - maxDistance) <= 0) {

            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);

            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }

        // 1 intersection point
        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            Point p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, p1));
        }

        // 1 intersection point
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p2));
        }

        return null;
    }
}
