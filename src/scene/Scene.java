package scene;

import geometries.*;
import lighting.AmbientLight;
import primitives.*;

/**
 * Class scene
 */
public class Scene {

    /**
     * Name of the scene
     */
    public String name;

    /**
     * Background of the scene, black by default
     */
    public Color background = Color.BLACK;

    /**
     * Ambient light of the scene with default value
     */
    public AmbientLight ambientLight = AmbientLight.NONE;

    /**
     * 3D model
     */

    public Geometries geometries = new Geometries();

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
}
