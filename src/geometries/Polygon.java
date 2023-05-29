package geometries;

import primitives.*;
import static primitives.Util.*;
import java.util.List;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    /**
     * Use of the function getNormal of the interface Geometry
     *
     * @param point that is the point on the geometry
     * @return vector normal
     */
    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    /**
     * Use of the function findIntersections from the interface intersectable
     * @param ray that allow us to know if there are intersections
     * @return list of intersections points
     */
    @Override
    public List<GeoPoint> findGeoIntsersectionsHelper(Ray ray) {

        // First ,we check if the plane of our polygon intersects with the ray,
        // if there's no intersection with the
        // plane so there's no intersection with the polygon.

        // N=_normal of the plane
        // P0=_q0 of the plane
        // R0=_p0 of the ray
        // d is the vector offset from the origin
        // V is _dir of the ray


        // If there's intersection with the plane,
        // so we have to substitute the ray equation into the plane equation
        // (replacing P) to get: (P0 + tV) . N + d = 0 and find the value of t:
        // t = -(P0 . N + d) / (V . N)
        // Then you substitute that value of t back into your ray equation to get the value of P:
        // R0 + tV = P.
        // Finally, you want to go around each adjacent pair of points
        // in the polygon checking that P is inside.
        // The polygon, which is done by checking that P is
        // to the same side of each line made by the points.

        List<GeoPoint> intersections = plane.findGeoIntsersections(ray);

        if (intersections == null)
            return null;

        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Vector v1 = vertices.get(1).subtract(p0);
        Vector v2 = vertices.get(0).subtract(p0);

        double sign = v.dotProduct(v1.crossProduct(v2));

        if (isZero(sign))
            return null;

        boolean positive = sign > 0;

        for (int i = vertices.size() - 1; i > 0; --i) {
            v1 = v2;
            v2 = vertices.get(i).subtract(p0);
            sign = alignZero(v.dotProduct(v1.crossProduct(v2)));

            if (isZero(sign))
                return null;

            if (positive != (sign > 0))
                return null;
        }

        return intersections;
    }
}
