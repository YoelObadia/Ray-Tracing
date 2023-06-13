package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

/**
 * Class PointLight used in the case of PointLight
 */
public class PointLight extends Light implements LightSource {

    /**
     * Field position
     */
    private Point position;

    /**
     * Field kC
     */
    private Double3 kC = Double3.ONE;
    /**
     * Field kL
     */
    private Double3 kL = Double3.ZERO;

    /**
     * Field kQ
     */
    private Double3 kQ = Double3.ZERO;

    /**
     * Constructor of Light with 2 parameters
     *
     * @param intensity Color for light source
     * @param position  Point from the vector
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Getter of the intensity in case of PointLight
     *
     * @param p point
     * @return Color
     */
    @Override
    public Color getIntensity(Point p) {

        Double3 factor;
        double distance;

        try {
            distance = position.distance(p);
            factor = kC
                    .add(kL.scale(distance))
                    .add(kQ.scale(distance * distance));
        } catch (Exception e) {
            return Color.BLACK;
        }
        Color baseIntensity = getIntensity();

        return baseIntensity.reduce(factor);
    }

    /**
     * Getter of the direction vector in case of PointLight
     *
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
     * Implementation of the function getDistance
     *
     * @param point Point
     * @return the distance between pointLight and point
     */
    @Override
    public double getDistance(Point point) {
        return this.position.distance(point);
    }

    /**
     * Setter for coefficient kC
     *
     * @param kC double
     */
    public PointLight setkC(double kC) {
        this.kC = new Double3(kC);
        return this;
    }
    public PointLight setkC(Double3 kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for coefficient kL
     *
     * @param kL double
     */
    public PointLight setKl(double kL) {
        this.kL = new Double3(kL);
        return this;
    }

    public PointLight setKl(Double3 kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for coefficient kQ
     *
     * @param kQ double
     */
    public PointLight setKq(double kQ) {
        this.kQ = new Double3(kQ);
        return this;
    }

    /**
     * Setter for coefficient kQ
     *
     * @param kQ double
     */
    public PointLight setKq(Double3 kQ) {
        this.kQ = kQ;
        return this;
    }
}
