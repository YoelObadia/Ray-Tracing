package primitives;

import geometries.Intersectable.GeoPoint;
import java.util.*;

/**
 * This class will serve to use
 */
public class Ray {

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
     * @param p0  as first parameter
     * @param dir as second parameter
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
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
     * @return point p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter for the vector director of the ray
     * @return vector dir
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Refactoring for the calculation code of a point on a ray
     * @param t distance from ray head
     * @return the point
     */
    public Point getPoint(double t) {
        if (t == 0)
            return p0;
        else {
            return (p0).add(dir.scale(t));
        }
    }

    /**
     * This function call the function findGeoClosestPoint()
     * @param intersections list
     * @return the closest point of the head of the ray
     */
    public Point findClosestPoint(List<Point> intersections) {
        return intersections == null ? null
                : findGeoClosestPoint(intersections.stream()
                .map(p -> new GeoPoint(null, p)).toList()).point;
    }


    /**
     * Function that calculate the minimal distance between
     * head of a ray and a list of intersection points
     * @param intersections list
     * @return the closest GeoPoint of the head of the ray
     */
    public GeoPoint findGeoClosestPoint(List<GeoPoint> intersections) {

        // There are no points
        if(intersections == null)
            return null;

        // We'll every time compare if the distance with the point is lower than the precedent distance.
        // For this, we need to begin by the maximal value.
        double maxDistance = Double.MAX_VALUE;
        double minDistance;

        // Initialisation of the GeoPoint that we'll return
        GeoPoint closestPoint = null;

        for (GeoPoint geoPoint: intersections) {
            if(geoPoint.point.distance(p0) < maxDistance) {
                minDistance = geoPoint.point.distance(p0);
                maxDistance = minDistance;
                closestPoint = geoPoint;
            }
        }

        return closestPoint;
    }
}
