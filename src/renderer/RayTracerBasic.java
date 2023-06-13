package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class RayTracerBasic inherit from the abstract class RayTracerBse
 */
public class RayTracerBasic extends RayTracerBase {


    /**
     * Constructor of RayTracerBasic that use the constructor
     * of the father class RayTracerBase
     *
     * @param scene parameter
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Search the geometries intersections in the scene.
     * If a geometry is founded, so the closest GeoPoint is searched.
     * After this, the GeoPoint is colored.
     * If there aren't intersections, the color is background (BLACK) by default
     *
     * @param ray through the scene
     * @return Color
     */
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntsersections(ray);
        if (intersections.size() == 0)
            return scene.background;
        GeoPoint closestPoint = ray.findGeoClosestPoint(intersections);
        return calcColor(closestPoint, ray);
    }


    /**
     * Helper function for the color.
     * Call the helper function CalcLocalEffects()
     * to give all the effects of the different lights sources
     *
     * @param gp  parameter
     * @param ray parameter
     * @return Color
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(gp.geometry.getEmission())
                .add(calcLocalEffects(gp, ray));
    }

    /**
     * Helper function for the color from the theory presentation
     *
     * @param geoPoint GeoPoint
     * @param ray      Ray
     * @return Color
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {

        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));

        if (nv == 0)
            return Color.BLACK;

        double nShininess = geoPoint.geometry.getMaterial().nShininess;
        Double3 kd = geoPoint.geometry.getMaterial().kD;
        Double3 ks = geoPoint.geometry.getMaterial().kS;
        Color color = Color.BLACK;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));

            // sign(nl) == sing(nv)
            if (nl * nv > 0) {
                if (unshaded(geoPoint, lightSource, l, n, nl)) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }

        return color;
    }

    /**
     * Helper function that return the diffusion from the theory presentation
     *
     * @param kd             coefficient
     * @param l              vector
     * @param n              vector
     * @param lightIntensity color
     * @return Color
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {

        double lN;

        try {
            lN = l.normalize().dotProduct(n.normalize());
        } catch (Exception exception) {
            return lightIntensity.scale(0);
        }

        return lightIntensity.scale(kd.scale(Math.abs(lN)));
    }

    /**
     * Helper function to return specular from the theory presentation
     *
     * @param ks             coefficient
     * @param l              vector
     * @param n              vector
     * @param v              vector
     * @param nShininess     double
     * @param lightIntensity color
     * @return Color
     */
    private Color calcSpecular
    (Double3 ks, Vector l, Vector n, Vector v, double nShininess, Color lightIntensity) {

        Vector r = l.add(n.scale(n.dotProduct(l) * -2));
        double vR;

        try {
            vR = v.scale(-1).normalize().dotProduct(r.normalize());
        } catch (Exception exception) {
            return lightIntensity.scale(1);
        }

        return lightIntensity.scale(ks.scale(Math.pow(Math.max(0, vR), nShininess)));
    }

    /**
     * A method of checking non-shading between a point and the light source
     *
     * @param gp    GeoPoint
     * @param light LightSource
     * @param l     Vector
     * @param n     Vector
     * @param nl    double
     * @return Boolean value
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {

        // from point to light source
        Vector lightDirection = l.scale(-1);
        Ray lightRay = new Ray(gp.point, lightDirection, n);


        // This list contains the shadow ray intersections
        List<GeoPoint> intersections = scene.geometries.findGeoIntsersections(lightRay);

        if (intersections == null || intersections.size() == 0)
            return true;

        // if there are points in the intersections list
        // that are closer to the point than light source
        double maxDistance = light.getDistance(gp.point);

        for (var elem :intersections) {
            if(elem.point.distance(gp.point) < maxDistance)
                return  false;
        }

        return true;
    }
}
