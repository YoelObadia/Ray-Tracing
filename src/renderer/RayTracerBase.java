package renderer;

import primitives.*;
import scene.Scene;

/**
 * Abstract Class RayTracerBase created because there are
 * few classes that will use the method traceRay()
 */
public abstract class RayTracerBase {

    /**
     * Protected field scene
     */
    protected Scene scene;

    /**
     * Constructor of RayTracerBase with 1 parameter
     * @param scene parameter
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * State the method of scanning rays through the scene
     * @param ray through the scene
     * @return Color
     */
    public abstract Color traceRay(Ray ray);
}
