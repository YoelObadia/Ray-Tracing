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
     * Use of the function findIntersections from the interface intersectable
     *
     * @param ray that allow us to know if there are intersections
     * @return list of intersections points
     */
    @Override
    public List<GeoPoint> findGeoIntsersectionsHelper(Ray ray) {

        /*
            The equation for a tube of radius r oriented along a line pa + vat:
            (q - pa - (va,q - pa)va)2 - r2 = 0
            get intersections using formula : (p - pa + vt - (va,p - pa + vt)va)^2 - r^2 = 0
            reduces to at^2 + bt + c = 0
            with a = (v - (v,va)va)^2
                 b = 2 * (v - (v,va)va,∆p - (∆p,va)va)
                 c = (∆p - (∆p,va)va)^2 - r^2
            where  ∆p = p - pa
        */

        Vector v = ray.getDir();
        Vector va = this.getAxisRay().getDir();

        // If vectors are parallel then there is no intersections possible
        if (v.normalize().equals(va.normalize()))
            return null;

        // Use of calculated variables to avoid vector ZERO
        double vva;
        double pva;
        double a;
        double b;
        double c;

        // Check every variable to avoid ZERO vector
        if (ray.getP0().equals(this.axisRay.getP0())) {
            vva = v.dotProduct(va);

            if (vva == 0) {
                a = v.dotProduct(v);
            } else {
                a = (v.subtract(va.scale(vva))).dotProduct(v.subtract(va.scale(vva)));
            }

            b = 0;
            c = -radius * radius;
        } else {

            Vector deltaP = ray.getP0().subtract(this.axisRay.getP0());
            vva = v.dotProduct(va);
            pva = deltaP.dotProduct(va);

            if (vva == 0 && pva == 0) {
                a = v.dotProduct(v);
                b = 2 * v.dotProduct(deltaP);
                c = deltaP.dotProduct(deltaP) - radius * radius;
            } else if (vva == 0) {
                a = v.dotProduct(v);
                if (deltaP.equals(va.scale(deltaP.dotProduct(va)))) {
                    b = 0;
                    c = -radius * radius;
                } else {
                    b = 2 * v.dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))));
                    c = (deltaP.subtract(va.scale(deltaP.dotProduct(va)))
                            .dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))))) -
                            this.radius * this.radius;
                }
            } else if (pva == 0) {
                a = (v.subtract(va.scale(vva))).dotProduct(v.subtract(va.scale(vva)));
                b = 2 * v.subtract(va.scale(vva)).dotProduct(deltaP);
                c = (deltaP.dotProduct(deltaP)) - this.radius * this.radius;
            } else {
                a = (v.subtract(va.scale(vva))).dotProduct(v.subtract(va.scale(vva)));
                if (deltaP.equals(va.scale(deltaP.dotProduct(va)))) {
                    b = 0;
                    c = -radius * radius;
                } else {
                    b = 2 * v.subtract(va.scale(vva)).
                            dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))));
                    c = (deltaP.subtract(va.scale(deltaP.dotProduct(va)))
                            .dotProduct(deltaP.subtract(va.scale(deltaP.dotProduct(va))))) -
                            this.radius * this.radius;
                }
            }
        }

        // Calculate delta for result of equation
        double delta = b * b - 4 * a * c;

        // No intersections point
        if (delta <= 0) {
            return null;
        } else {
            // Calculate points taking only those with t > 0
            double t1 = alignZero((-b - Math.sqrt(delta)) / (2 * a));
            double t2 = alignZero((-b + Math.sqrt(delta)) / (2 * a));
            if (t1 > 0 && t2 > 0) {
                Point p1 = ray.getPoint(t1);
                Point p2 = ray.getPoint(t2);
                return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
            } else if (t1 > 0) {
                Point p1 = ray.getPoint(t1);
                return List.of(new GeoPoint(this, p1));
            } else if (t2 > 0) {
                Point p2 = ray.getPoint(t2);
                return List.of(new GeoPoint(this, p2));
            }
        }
        return null;
    }
}
