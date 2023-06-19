package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Tube: geometry of the project
 */
public class Tube extends RadialGeometry {

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
     *
     * @param axisRay ray of the tube
     * @param radius  radius of the tube
     */
    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * Getter for the field axisRay
     *
     * @return axisRay of the tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * Getter for the field radius
     *
     * @return radius of the tube
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Use of the function getNormal of the interface Geometry
     *
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

    /**
     * Use of the function findGeoIntsersectionsHelper
     * @param ray         the ray crossing the geometric object
     * @param maxDistance max distance for finding intersections
     * @return List of intersection points
     * @author Dan
     * {@see <a href="https://mrl.cs.nyu.edu/~dzorin/rendering/lectures/lecture3/lecture3.pdf"></a>}
     */
    @Override
    protected List<GeoPoint> findGeoIntsersectionsHelper(Ray ray, double maxDistance) {

        Vector vAxis = axisRay.getDir();
        Vector v = ray.getDir();
        Point p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;

        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;

        else {
            vVaVa = vAxis.scale(vVa);

            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }

        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;

        try {
            deltaP = p0.subtract(axisRay.getP0());
        }
            // the ray begins at axis P0
            catch (IllegalArgumentException e1) {

            // the ray is orthogonal to Axis
            if (vVa == 0 && alignZero(radius - maxDistance) <= 0) {
                return List.of(new GeoPoint(this, ray.getPoint(radius)));
            }

            double t = alignZero(Math.sqrt(radius * radius / vMinusVVaVa.lengthSquared()));

            return alignZero(t - maxDistance) >= 0 ?
                    null :
                    List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;

        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;

        else {
            dPVaVa = vAxis.scale(dPVAxis);

            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {

                double t = alignZero(Math.sqrt(radius * radius / a));

                return alignZero(t - maxDistance) >= 0 ?
                        null :
                        List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - radius * radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);

        // the ray is outside or tangent to the tube
        if (discr <= 0) return null;

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;

        // the ray is tangent to the tube
        if (isZero(th))
            return null;

        double t1 = alignZero(tm + th);

        // t1 is behind the head
        if (t1 <= 0)
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0 && alignZero(t2 - maxDistance) < 0)

            return List.of(
                    new GeoPoint(
                            this, ray.getPoint(t1)),
                    new GeoPoint(
                            this, ray.getPoint(t2))
            );

            // t2 is behind the head
        else if (alignZero(t1 - maxDistance) < 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));

        return null;
    }
}
