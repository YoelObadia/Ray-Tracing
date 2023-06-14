package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class RayTracerBasic extends RayTracerBase {

    private static final double DELTA = 0.1;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double3 INITIAL_K = Double3.ONE;

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null) {
            return scene.background;
        }
        return calcColor(closestPoint, ray);

    }

    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

//    private Color calcColor(GeoPoint gp, Ray ray) {
//        Color color = scene.ambientLight.getIntensity();
//        color = color.add(calcLocalEffects(gp, ray));
//
//        return color;
//    }
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = geoPoint.geometry.getEmission();

        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        // check that ray is not parallel to geometry
        double nv = alignZero(n.dotProduct(v));

        if (isZero(nv)) {
            return color;
        }
        Material material = geoPoint.geometry.getMaterial();
        color = color.add(calcLocalEffects(geoPoint, material, n, v, nv, k));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, material, n, v, level, k));
    }

    /**
     * add here the lights effects
     *
     * @param gp geopoint of the intersection
     * @param v  ray direction
     * @return resulting color with diffuse and specular
     */
    private Color calcLocalEffects(
            GeoPoint gp, Material material, Vector n, Vector v, double nv, Double3 k) {

        Color color = Color.BLACK;

        Point point = gp.point;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(point);
            double nl = alignZero(n.dotProduct(l));
            // sign(nl) == sign(nv)
            if (nl * nv > 0) {
                Double3 ktr = transparency(lightSource, l, n, gp);
                 if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                     //if (unshaded(gp, lightSource, l, n, nv)) {
                         Color iL = lightSource.getIntensity(point).scale(ktr);
                         color = color.add(
                                 calcDiffusive(material.kD, nl, iL),
                                 calcSpecular(material.kS, n, l, nl, v, material.nShininess, iL));
                 }
            }
        }

        return color;
    }


    private Color calcSpecular(
            Double3 kS, Vector n, Vector l, double nl, Vector v, int shininess, Color intensity) {
        // nl must not be zero!
        Vector r = l.add(n.scale(-2 * nl));
        double minusVR = -alignZero(r.dotProduct(v));

        // view from direction opposite to r vector
        if (minusVR <= 0)
            return Color.BLACK;

        Double3 amount = kS.scale(Math.pow(minusVR, shininess));

        return intensity.scale(amount);
    }

    private Color calcDiffusive(Double3 kD, double nl, Color intensity) {
        double abs_nl = Math.abs(nl);
        Double3 amount = kD.scale(abs_nl);
        return intensity.scale(amount);
    }

    private Color calcGlobalEffects(
            GeoPoint gp, Material material, Vector n, Vector v, int level, Double3 k) {

        Color color = Color.BLACK;
        Double3 kkr = material.kR.product(k);

        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(calcGlobalEffect(
                    constructReflectedRay(gp.point, v, n), level, material.kR, kkr)
            );

        Double3 kkt = material.kT.product(k);

        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(
                            constructRefractedRay(gp.point, v, n), level, material.kT, kkt)
            );

        return color;
    }


    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    private Ray constructReflectedRay(Point pointGeo, Vector v, Vector n) {

        // r = v - n*(2*(v.n))
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));

        return new Ray(pointGeo, r, n);
    }

    /**
     * The method checks whether there is any object shading the light source from a
     * point
     *
     * @param gp          the point with its geometry
     * @param lightSource light source
     * @param l           direction from light to the point
     * @param n           normal vector to the surface of gp
     * @param nv          dotProduct between n and ray direction
     * @return accumulated transparency attenuation factor
     */

    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n, double nv) {

        Vector lightDirection = l.scale(-1); // from point to light source
        double nl = n.dotProduct(lightDirection);

        Vector delta = n.scale(nl > 0 ? DELTA : -DELTA);
        Point pointRay = gp.point.add(delta);
        Ray lightRay = new Ray(pointRay, lightDirection);

        double maxDistance = lightSource.getDistance(gp.point);

        List<GeoPoint> intersections = scene.geometries.findGeoIntsersections(lightRay);

        if (intersections == null||intersections.size()==0) {
            return true;
        }

        for (var item : intersections) {
            if (item.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K) &&
                    item.point.distance(gp.point) < maxDistance) {
                return false;
            }
        }

        return true;
    }

    private Double3 transparency(LightSource lightSource, Vector l, Vector n, GeoPoint gp) {

        // from point to light source
        Vector lightDirection = l.scale(-1);
        double nl = n.dotProduct(lightDirection);

        Vector delta = n.scale(nl > 0 ? DELTA : -DELTA);
        Point pointRay = gp.point.add(delta);
        Ray lightRay = new Ray(pointRay, lightDirection);

        double maxDistance = lightSource.getDistance(gp.point);

        List<GeoPoint> intersections = scene.geometries.findGeoIntsersections(lightRay);

        if (intersections == null || intersections.size()==0) {
            return Double3.ONE;
        }

        Double3 ktr = Double3.ONE;

        for (var item : intersections) {
            ktr = ktr.product(item.geometry.getMaterial().kT);
            if (item.geometry.getMaterial().kT.lowerThan(MIN_CALC_COLOR_K) &&
                   item.point.distance(gp.point) < maxDistance &&
                    ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }

        return ktr;
    }


    private GeoPoint findClosestIntersection(Ray ray) {

        List<GeoPoint> intersections = scene.geometries.findGeoIntsersections(ray);

        if (intersections == null) {
            return null;
        }

        return ray.findGeoClosestPoint(intersections);
    }

}


