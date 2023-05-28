package lighting;

import primitives.*;

/**
 * Class to implement ambient light of the scene
 */
public class AmbientLight {

    /**
     * Field intensity of ambient light
     */
    private Color intensity;

    /**
     * Ambient light default value (black)
     */
    private static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructor of AmbientLight with 2 parameters
     * @param iA Light intensity according to RGB components
     * @param kA Fill light attenuation coefficient
     */
    public AmbientLight(Color iA, Double3 kA) {
        this.intensity = iA.scale(kA);
    }

    /**
     * Getter of intensity
     * @return Color intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
