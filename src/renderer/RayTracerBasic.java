package renderer;

import primitives.*;
import scene.Scene;

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
     * Use of the function traceRay
     * @param ray through the scene
     * @return Color
     */
    @Override
    public Color traceRay(Ray ray) {
        var intersections = scene.geometries.findIntsersections(ray);
        if (intersections.size() == 0)
            return scene.background;
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Helper function
     * @param point parameter
     * @return Color
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
