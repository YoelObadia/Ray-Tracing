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
     *
     * @param height that is the length of the cylinder
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /**
     * Getter for the field height
     *
     * @return height of the cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     * Use of the function getNormal of the interface Geometry
     *
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
     *
     * @param ray that allow us to know if there are intersections
     * @return list of intersections points
     */
    @Override
    public List<GeoPoint> findGeoIntsersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = super.findGeoIntsersectionsHelper(ray, maxDistance);
        Point p0 = axisRay.getP0();
        Vector dir = axisRay.getDir();
        if (intersections != null) {
            List<GeoPoint> temp = new LinkedList<>();
            for (GeoPoint g : intersections) {
                double pointHeight = alignZero(g.point.subtract(p0).dotProduct(dir));
                if (pointHeight > 0 && pointHeight < height) {
                    temp.add(g);
                }
            }
            if (temp.isEmpty())
                intersections = null;
            else
                intersections = temp;
        }

        List<GeoPoint> planeIntersection = new Plane(p0, dir).findGeoIntsersections(ray, maxDistance);
        if (planeIntersection != null) {
            GeoPoint point = planeIntersection.get(0);
            if (point.point.equals(p0) ||
                    alignZero(point.point.subtract(p0).lengthSquared() - radius * radius) < 0) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                point.geometry = this;
                intersections.add(point);
            }
        }

        Point p1 = p0.add(dir.scale(height));

        planeIntersection = new Plane(p1, dir).findGeoIntsersections(ray, maxDistance);
        if (planeIntersection != null) {
            GeoPoint point = planeIntersection.get(0);
            if (point.point.equals(p1) ||
                    alignZero(point.point.subtract(p1).lengthSquared() - radius * radius) < 0) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                point.geometry = this;
                intersections.add(point);
            }
        }

        if (intersections != null) {
            for (GeoPoint g : intersections) {
                g.geometry = this;
            }
        }

        return intersections;
    }
}
