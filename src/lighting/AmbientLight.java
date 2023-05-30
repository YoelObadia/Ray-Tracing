package lighting;

import primitives.*;

/**
 * Class to implement ambient light of the scene and inherit of the abstract class Light
 */
public class AmbientLight extends Light {

    /**
     * Ambient light default value (black)
     */
    public static final AmbientLight NONE=new AmbientLight(Color.BLACK,Double3.ZERO);

    /**
     * Constructor of AmbientLight with 2 parameters
     * Use the constructor of Light to initialise intensity
     * @param iA Light intensity according to RGB components
     * @param kA Fill light attenuation coefficient
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }


}
