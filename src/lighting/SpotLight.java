package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * Class SpotLight used in the case of SpotLight
 */
public class SpotLight extends PointLight {

    /**
     * Field direction vector
     */
    private Vector direction;

    /**
     * Protected constructor of Light with 3 parameters
     *
     * @param intensity Color for light source
     * @param position  Point from the vector
     * @param direction Direction vector
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Getter for the intensity in the case of SpotLight
     *
     * @param p point
     * @return Color
     */
    @Override
    public Color getIntensity(Point p) {

        double cosinus = getL(p).dotProduct(direction);

        if (isZero(cosinus)) {
            return Color.BLACK;
        }

        return super.getIntensity(p).scale(Math.max(0, cosinus));
    }

    /**
     * Getter for the direction vector in the case of SpotLight
     *
     * @param p point
     * @return Vector
     */
    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
