package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Tube: geometry of the project
 */
public class Tube implements Geometry{

    /**
     * Field axisRay that is the ray of the tube
     */
    protected final Ray axisRay;

    /**
     * Field radius that is the length from the center
     */
    protected final double radius;

    /**
     * Constructor of Tube with 2 parameters
     * @param axisRay ray of the tube
     * @param radius radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * Getter for the field axisRay
     * @return axisRay of the tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Getter for the field radius
     * @return radius of the tube
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Use of the function getNormal of the interface Geometry
     * @param point that is the point on the geometry
     * @return vector normal
     */
    @Override
    public Vector getNormal(Point point) {
        // n = normalize(P - O)
        // t = v ∙ (𝑃 − 𝑃0)
        // 𝑂 = 𝑃0 + 𝑡 ∙ v

        Point P0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        Vector P0_P = point.subtract(P0);

        double t = alignZero(v.dotProduct(P0_P));

        if (isZero(t)) {
            return P0_P.normalize();
        }

        Point o = P0.add(v.scale(t));

        if (point.equals(o)) {
            throw new IllegalArgumentException("Point cannot be on the tube axis");
        }

        return point.subtract(o).normalize();
    }
}
