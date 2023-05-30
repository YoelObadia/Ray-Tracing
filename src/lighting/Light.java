package lighting;

import primitives.*;

/**
 * Abstract class Light without access permission
 */
abstract class Light {

    /**
     * Field intensity for intensity of light source
     */
    private Color intensity;

    /**
     * Protected constructor of Light with 1 parameter
     * @param intensity Color for light source
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Getter for intensity
     * @return Color
     */
    public Color getIntensity() {
        return intensity;
    }
}
