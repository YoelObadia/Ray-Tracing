package geometries;

import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * Abstract class Intersectable for the definition of the function findIntersections()
 * used by all the other geometries of the project
 */
public abstract class Intersectable {

    /**
     * Definition of the function findIntersections()
     * Take the parameter Ray that will search intersections
     * The function return a list of GeoPoints that are
     * the intersections between the ray and the geometries
     */
    public final List<Point> findIntsersections(Ray ray) {
        List<GeoPoint> geoList = findGeoIntsersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Function findGeoIntsersections that use the function findGeoIntsersectionsHelper()
     * @param ray through the geometry
     * @return list of intersections GeoPoints
     */
    public final List<GeoPoint> findGeoIntsersections(Ray ray) {
        return findGeoIntsersectionsHelper(ray);
    };

    /**
     * Function findGeoIntsersectionsHelper() used by each geometry
     * @param ray through the geometry
     * @return list of intersections GeoPoints
     */
    protected abstract List<GeoPoint> findGeoIntsersectionsHelper(Ray ray);

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
         * @param geometry form
         * @param point intersection
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
