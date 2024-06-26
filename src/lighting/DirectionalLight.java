package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class DirectionalLight used in the case of directional light
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * Field direction vector
     */
    private Vector direction;

    /**
     * Constructor of Light with 2 parameter
     *
     * @param intensity Color for light source
     * @param direction Direction Vector
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    /**
     * Getter for the intensity of the DirectionalLight
     *
     * @param p point
     * @return Color
     */
    @Override
    public Color getIntensity(Point p) {
        return getIntensity();
    }

    /**
     * Getter for the direction vector of the DirectionalLight
     *
     * @param p point
     * @return Vector
     */
    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }

    /**
     * Implementation of the function getDistance
     *
     * @param point Point
     * @return Double.POSITIVE_INFINITY
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
