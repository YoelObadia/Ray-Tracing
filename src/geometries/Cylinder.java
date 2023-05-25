package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Cylinder extends Tube implements Geometry{

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

        // if the point is at a base
        // if it's close to 0, we'll get ZERO vector exception
        if (t == 0 || isZero(height - t))
            return v;

        o = o.add(v.scale(t));
        return point.subtract(o).normalize();
    }
}
