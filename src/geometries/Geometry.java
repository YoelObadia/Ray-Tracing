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

    /** Getter of emission
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
     * Definition of the function getNormal
     * @param point that is the point on the geometry
     * @return the same vector with length = 1
     */
    public abstract Vector getNormal(Point point);
}
