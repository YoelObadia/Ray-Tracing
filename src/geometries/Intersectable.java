package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * Abstract class Intersectable for the definition of the function findIntersections()
 * used by all the other geometries of the project
 */
public abstract class Intersectable {

    /**
     * Definition of the function findIntersections()
     * @param ray the ray that will search intersections
     * The function return a list of GeoPoints that are
     * the intersections between the ray and the geometries
     */
    public final List<Point> findIntsersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntsersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .toList();
    }

    /**
     * New definition added for the step 7 about shadows and transparency
     * @param ray the ray that will search intersections
     * @return new definition of the function findGeoIntsersections with a new parameter
     */
    public final List<GeoPoint> findGeoIntsersections(Ray ray) {
        return findGeoIntsersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Function findGeoIntsersections that use the function findGeoIntsersectionsHelper()
     *
     * @param ray through the geometry
     * @param maxDistance the maximum distance we want the point to be from the starting point
     * @return list of intersections GeoPoints
     */
    public final List<GeoPoint> findGeoIntsersections(Ray ray, double maxDistance) {
        return findGeoIntsersectionsHelper(ray, maxDistance);
    }

    ;

    /**
     * Function findGeoIntsersectionsHelper() used by each geometry
     *
     * @param ray through the geometry
     * @return list of intersections GeoPoints
     */
    protected abstract List<GeoPoint> findGeoIntsersectionsHelper(Ray ray, double maxDistance);

    /**
     * Helper internal class (PDS)
     */
    public static class GeoPoint {

        /**
         * Field geometry
         */
        public Geometry geometry;

        /**
         * Field point
         */
        public Point point;

        /**
         * Constructor of GeoPoint with 2 parameters
         *
         * @param geometry form
         * @param point    intersection
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
