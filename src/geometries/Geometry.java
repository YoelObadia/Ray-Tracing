package geometries;

import primitives.*;

/**
 * Abstract class Geometry that all the geometries of the project will inherit
 */
public abstract class Geometry extends Intersectable{

    /**
     * Field emission
     */
    protected Color emission = Color.BLACK;

    /**
     * Object instance of Material type
     */
    private Material material = new Material();

    /**
     * Getter of emission
     * @return Color emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Setter of emission
     * @param emission color
     * @return this
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Getter for the field material
     * @return Material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Setter for material
     * @param material Material
     * @return this
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Definition of the function getNormal
     * @param point that is the point on the geometry
     * @return the same vector with length = 1
     */
    public abstract Vector getNormal(Point point);
}
