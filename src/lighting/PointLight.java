package lighting;

import primitives.*;

/**
 * Class PointLight used in the case of PointLight
 */
public class PointLight extends Light implements LightSource{

    /**
     * Field position
     */
    private Point position;

    /**
     * Field kC
     */
    private double kC = 1d;
    /**
     * Field kL
     */
    private double kL = 0d;

    /**
     * Field kQ
     */
    private double kQ = 0d;

    /**
     * Constructor of Light with 2 parameters
     * @param intensity Color for light source
     * @param position Point from the vector
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Getter of the intensity in case of PointLight
     * @param p point
     * @return Color
     */
    @Override
    public Color getIntensity(Point p) {

        double factor = kC;
        double distance;

        try {
            distance = position.distance(p);
            factor += kL * distance + kQ * distance * distance;
        } catch (Exception e) {
            return null;
        }

        return getIntensity().scale(1 / factor);
    }

    /**
     * Getter of the direction vector in case of PointLight
     * @param p point
     * @return Vector
     */
    @Override
    public Vector getL(Point p) {

        try {
            return p.subtract(this.position).normalize();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Setter for coefficient kC
     * @param kC double
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for coefficient kL
     * @param kL double
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for coefficient kQ
     * @param kQ double
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
