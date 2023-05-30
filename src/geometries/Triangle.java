package geometries;

import primitives.*;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Class Triangle that inherit from Polygon
 */
public class Triangle extends Polygon {

    /**
     * Constructor of Triangle and execute the constructor of Polygon with super()
     * @param vertices
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }

    /**
     * Use of the function findIntersections from the interface intersectable
     * @param ray that allow us to know if there are intersections
     * @return list of intersections
     */
    @Override
    public List<GeoPoint> findGeoIntsersectionsHelper(Ray ray) {

        Point p = ray.getP0();
        Vector v = ray.getDir();

        // v1 = p1 - p0
        // v2 = p2 - p0
        // v3 = p3 - p0

        Vector v1 = vertices.get(0).subtract(p);
        Vector v2 = vertices.get(1).subtract(p);
        Vector v3 = vertices.get(2).subtract(p);

        // n1 = normalize(v1 × v2)
        // n2 = normalize(v2 × v3)
        // n3 = normalize(v3 × v1)

        // We calculate and check the 3 signs
        double s1 = v.dotProduct(v1.crossProduct(v2));
        double s2 = v.dotProduct(v2.crossProduct(v3));
        double s3 = v.dotProduct(v3.crossProduct(v1));

        // The point is inside if and only if all v∙ni have the same sign (+/-)
        if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) {
            // I used stream because if I return just plane.findGeoIntersections() it doesn't work
            return (plane.findIntsersections(ray)).stream()
                    .map(gp->new GeoPoint(this,gp))
                    .toList();
        }

        return null;
    }
}
