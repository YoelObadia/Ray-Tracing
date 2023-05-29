package renderer;

import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

/**
 * Class RayTracerBasic inherit from the abstract class RayTracerBse
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * Constructor of RayTracerBasic that use the constructor
     * of the father class RayTracerBase
     * @param scene parameter
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Search the geometries intersections in the scene.
     * If a geometry is founded, so the closest point is searched.
     * After this, the point is colored.
     * If there aren't intersections, the color is background (BLACK)
     * @param ray through the scene
     * @return Color
     */
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findGeoIntsersections(ray);
        if (intersections.size() == 0)
            return scene.background;
        GeoPoint closestPoint = ray.findGeoClosestPoint(intersections);
        return calcColor(closestPoint);
    }


    /**
     * Helper function
     * @param gp parameter
     * @return Color
     */
    private Color calcColor(GeoPoint gp) {
        return scene.ambientLight.getIntensity()
                .add(gp.geometry.getEmission());
    }
}
