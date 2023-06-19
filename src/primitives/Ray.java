package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

/**
 * This class will serve to use rays
 */
public class Ray {

    /**
     * Field fixed for ray head offset size for shading rays
     */
    private static final double DELTA = 0.1;

    /**
     * Head of the ray
     */
    final Point p0;

    /**
     * Vector director of the ray
     */
    final Vector dir;

    /**
     * Constructor of Ray with 2 parameters
     *
     * @param p0  as first parameter
     * @param dir as second parameter
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Constructor for ray deflected by DELTA
     *
     * @param point origin
     * @param n     normal vector
     * @param dir   direction
     */
    public Ray(Point point, Vector dir, Vector n) {
        this.dir = dir.normalize();
        double nv = n.dotProduct(this.dir);
        Vector delta = n.scale(DELTA);
        if (nv < 0)
            delta = delta.scale(-1);
        this.p0 = point.add(delta);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /**
     * Getter for the head of the ray
     *
     * @return point p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter for the vector director of the ray
     *
     * @return vector dir
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Refactoring for the calculation code of a point on a ray
     *
     * @param t distance from ray head
     * @return the point
     */
    public Point getPoint(double t) {
        if (isZero(t))
            return p0;
        return p0.add(dir.scale(t));
    }

    /**
     * This function call the function findGeoClosestPoint()
     *
     * @param intersections list
     * @return the closest point of the head of the ray
     */
    public Point findClosestPoint(List<Point> intersections) {
        return intersections == null ? null
                : findGeoClosestPoint(intersections.stream()
                .map(p -> new GeoPoint(null, p))
                .toList()).point;
    }


    /**
     * Function that calculate the minimal distance between
     * head of a ray and a list of intersection points
     *
     * @param intersections list
     * @return the closest GeoPoint of the head of the ray
     */
    public GeoPoint findGeoClosestPoint(List<GeoPoint> intersections) {

        // There are no points
        if (intersections == null)
            return null;

        // We'll every time compare if the distance with the point is lower than the precedent distance.
        // For this, we need to begin by the maximal value.
        double minDistance = Double.MAX_VALUE;
        double distance;

        // Initialisation of the GeoPoint that we'll return
        GeoPoint closestPoint = null;

        for (GeoPoint geoPoint : intersections) {
            distance= geoPoint.point.distance(p0);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = geoPoint;
            }
        }

        return closestPoint;
    }
}
