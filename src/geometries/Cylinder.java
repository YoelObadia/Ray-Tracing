package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Cylinder extends Tube {

    /**
     * Field height
     */
    private final double height;

    /**
     * Constructor of Cylinder with 1 parameter and super from Tube
     * @param height that is the length of the cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * Getter for the field height
     * @return height of the cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     * Use of the function getNormal of the interface Geometry
     * @param point that is the point on the geometry
     * @return vector normal
     */
    @Override
    public Vector getNormal(Point point) {
        Point o = axisRay.getP0();
        Vector v = axisRay.getDir();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(point.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) {
            return v;
        }

        // If the point is at a base
        // If it is close to 0, we'll get ZERO vector exception
        if (t == 0 || isZero(height - t))
            return v;

        o = o.add(v.scale(t));
        return point.subtract(o).normalize();
    }

    /**
     * Use of the function findIntsersections from the interface intersectable
     * @param ray that allow us to know if there are intersections
     * @return list of intersections points
     */
    @Override
    public List<GeoPoint> findGeoIntsersectionsHelper(Ray ray) {

        List<GeoPoint> result = new LinkedList<>();
        Vector va = this.axisRay.getDir();
        Point p1 = this.axisRay.getP0();
        Point p2 = p1.add(this.axisRay.getDir().scale(this.height));

        // Get plane of bottom base
        Plane plane1 = new Plane(p1, this.axisRay.getDir());
        // Intersections with bottom's plane
        List<GeoPoint> result2 = plane1.findGeoIntsersections(ray);

        if (result2 != null) {
            // Add all intersections of bottom's plane that are in the base's bounders
            for (GeoPoint geoPoint : result2) {
                // To avoid vector ZERO
                if (geoPoint.point.equals(p1)) {
                    result.add(new GeoPoint(this, p1));
                }
                // Formula that checks that point is inside the base
                else if ((
                        geoPoint.point.subtract(p1).dotProduct(geoPoint.point.subtract(p1)) <
                                this.radius * this.radius
                )) {
                    result.add(geoPoint);
                }
            }
        }

        // Get intersections for tube
        List<GeoPoint> result1 = super.findGeoIntsersectionsHelper(ray);

        if (result1 != null) {
            // Add all intersections of tube that are in the cylinder's bounders
            for (GeoPoint geoPoint : result1) {
                if (va.dotProduct(geoPoint.point.subtract(p1)) > 0
                        && va.dotProduct(geoPoint.point.subtract(p2)) < 0) {
                    result.add(geoPoint);
                }
            }
        }

        // Get plane of top base
        Plane plane2 = new Plane(p2, this.axisRay.getDir());
        // Intersections with top's plane
        List<GeoPoint> result3 = plane2.findGeoIntsersections(ray);

        if (result3 != null) {
            for (GeoPoint geoPoint : result3) {
                // To avoid vector ZERO
                if (geoPoint.point.equals(p2)) {
                    result.add(new GeoPoint(this, p2));
                }
                // Formula that checks that point is inside the base
                else if ((
                        geoPoint.point.subtract(p2).dotProduct(geoPoint.point.subtract(p2)) <
                                this.radius * this.radius
                )) {
                    result.add(geoPoint);
                }
            }
        }

        if (result.size() > 0)
            return result;

        return null;
    }
}
