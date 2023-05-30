package lighting;

import primitives.*;

/**
 * Interface LightSource created for external light sources.
 * Since not all light sources need to implement the light propagation model
 * but only the external light sources,
 * we cannot add the required operations to the Light abstract class.
 */
public interface LightSource {

    /**
     * Getter of intensity color
     * @param p point
     * @return Color
     */
    public Color getIntensity(Point p);

    /**
     * Getter of vector l
     * @param p point
     * @return Vector
     */
    public Vector getL(Point p);

}
