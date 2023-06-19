package renderer;

import geometries.Intersectable.*;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class RayTracerBasic extends RayTracerBase {

    /**
     * the const value of the maximum amount of recursion in calculating refracted and reflected rays
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * the const value for the stopping condition in the recursion
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * the stating value of the parameter K in the function calcColor
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Constructor of RayTracerBasic
     * @param scene the scene for our ray tracer
     */
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


    /**
     * @param geopoint the point which we want the color of
     * @param ray the ray for to obtain the color
     * @return the color at the point
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * @param geoPoint the point at which we want to calc the color
     * @param ray the ray hitting the geometry
     * @param level the level of recursion
     * @param k the parameter helping us calculate how much color each ray is giving to the final pixel
     * @return the color at the gp
     */
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

        return 1 == level ? color : color.add(
                calcGlobalEffects(geoPoint, material, n, v, level, k)
        );
    }

    /**
     * Add here the lights effects
     * At beginning, we used unshaded and now we use transparency
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
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Double3 ktr = transparency(lightSource, l, n, gp);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
//                if (unshaded(gp, lightSource, l, n)) {
                    Color iL = lightSource.getIntensity(point).scale(ktr);
                    color = color.add(
                            calcDiffusive(material.getKd(), nl, iL),
                            calcSpecular(material.getKs(), n, l, nl, v, material.getNShininess(), iL));
                }
            }
        }
        return color;
    }

    /**
     * Helper function used for the Phong light model
     * @param kS          coefficient of the class Material
     * @param n           vector normal of the geometry
     * @param l           vector director of the lightSource
     * @param nl          dot product between vectors n and l
     * @param v           ray direction
     * @param shininess   coefficient from the class Material for Phong light model
     * @param intensity   the intensity of the lightSource
     * @return the specular part of the light in the Phong light model
     */
    private Color calcSpecular(
            Double3 kS, Vector n, Vector l, double nl, Vector v, int shininess, Color intensity) {

        Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(r.dotProduct(v));

        if (minusVR <= 0)
            return Color.BLACK; // view from direction opposite to r vector

        Double3 amount = kS.scale(Math.pow(minusVR, shininess));

        return intensity.scale(amount);
    }

    /**
     * Helper function used for the Phong light model
     * @param kD         coefficient from the class Material
     * @param nl         dot product between vectors n and l
     * @param intensity  the intensity of the lightSource
     * @return the diffusive part of the light in the Phong light model
     */
    private Color calcDiffusive(Double3 kD, double nl, Color intensity) {

        double abs_nl = Math.abs(nl);
        Double3 amount = kD.scale(abs_nl);

        return intensity.scale(amount);
    }

    /**
     *
     * @param gp        the point at which we calculate the color
     * @param material  instance to get the coefficients
     * @param n         vector normal of the geometry
     * @param v         the ray hitting the geometry
     * @param level     the level of recursion
     * @param k         the parameter helping us calculate how much color each ray is giving to the final pixel
     * @return the color at the gp
     */
    private Color calcGlobalEffects(
            GeoPoint gp, Material material, Vector n, Vector v, int level, Double3 k) {

        Color color = Color.BLACK;
        Double3 kkr = material.getKr().product(k);

        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.getKr(), kkr));
        Double3 kkt = material.kT.product(k);

        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }


    /**
     * @param ray    the ray hitting the geometry
     * @param level  the level of recursion if level == 1 we stop the recursion
     * @param kx     the parameter helping us calculate how much color each ray is giving to the final pixel
     * @param kkx    a parameter helping us stop the recursion is the effect of the recursion is too small to notice
     * @return the color at the intersection with ray
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {

        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    /**
     * @param pointGeo the Point at the surface of the geometry
     * @param v     the dir of the original ray
     * @param n     the normal to the surface of the geometry at the point of gp.point
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Point pointGeo, Vector v, Vector n) {
        return new Ray(pointGeo, v, n);
    }

    /**
     * @param pointGeo the GeoPoint at the surface of the geometry
     * @param v        the dir of the original ray
     * @param n        the normal to the surface of the geometry at the point of gp.point
     * @return the reflected ray
     */
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
     * The method checks whether there is any object shading the light source from a point
     *
     * @param gp          the point with its geometry
     * @param lightSource light source
     * @param l           direction from light to the point
     * @param n           normal vector to the surface of gp
     * @return accumulated transparency attenuation factor
     */

    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {

        // from point to light source
        Vector lightDirection = l.scale(-1);
        Point point = gp.point;
        Ray lightRay = new Ray(point, lightDirection, n);

        double maxDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntsersections(lightRay, maxDistance);

        if (intersections == null) {
            return true;
        }

        for (var item : intersections) {
            if (item.geometry.getMaterial().getKt().lowerThan(MIN_CALC_COLOR_K)) {
                return false;
            }
        }

        return true;
    }

    /**
     * The method checks whether there is any object shading the light source from a point
     *
     * @param gp          the point with its geometry
     * @param lightSource light source
     * @param l           direction from light to the point
     * @param n           normal vector from the surface towards the geometry
     * @return accumulated transparency attenuation factor
     */

    private Double3 transparency(LightSource lightSource, Vector l, Vector n, GeoPoint gp) {

        // from point to light source
        Vector lightDirection = l.scale(-1);
        Point point = gp.point;
        Ray lightRay = new Ray(point, lightDirection, n);

        double maxDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntsersections(lightRay, maxDistance);

        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;

        // loop over intersections and for each intersection which is closer to the
        // point than the light source multiply ktr by 𝒌𝑻 of its geometry.
        // Performance:
        // if you get close to 0 –it’s time to get out (return 0)

        for (var geo : intersections) {

            ktr = ktr.product(geo.geometry.getMaterial().getKt());

            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return ktr;
    }

    /**
     * @param ray the ray from which we get the intersection
     * @return the closest intersection as GeoPoint or null if there is no intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {

        List<GeoPoint> intersections = scene.geometries.findGeoIntsersections(ray);

        // If there are no intersections
        if (intersections == null) {
            return null;
        }

        return ray.findGeoClosestPoint(intersections);
    }

}

