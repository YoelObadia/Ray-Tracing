package scene;

import geometries.*;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Class scene
 */
public class Scene {

    /**
     * Name of the scene
     */
    public String name;

    /**
     * Background of the scene, BLACK by default
     */
    public Color background = Color.BLACK;

    /**
     * Ambient light of the scene with default value
     */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /**
     * 3D model geometry
     */

    public Geometries geometries = new Geometries();

    /**
     * List of LightSource
     */
    public List<LightSource> lights = new LinkedList<>();

    /**
     * Constructor of the scene with 1 parameter
     * @param name of the scene
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     *  Setter of background
     * @param background Color
     * @return this
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Setter of ambientLight
     * @param ambientLight AmbientLight
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Setter of 3D model
     * @param geometries 3D model
     * @return this
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Setter of list of LightSource
     * @param lights list
     * @return this
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
