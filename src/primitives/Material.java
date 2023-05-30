package primitives;

/**
 * Class Material without constructor.
 * In order to save the parameters of the Fong light reflection model.
 */
public class Material {

    /**
     * Field coefficient kD
     */
    public Double3 kD = Double3.ZERO;

    /**
     * Field coefficient kS
     */
    public Double3 kS = Double3.ZERO;

    /**
     * Field coefficient nShininess
     */
    public int nShininess = 0;

    /**
     * Setter for coefficient kD
     * @param kD Double3
     * @return this
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for coefficient kD
     * @param kD double
     * @return this
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter for coefficient kS
     * @param kS Double3
     * @return this
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for coefficient kS
     * @param kS double
     * @return this
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for coefficient nShininess
     * @param nShininess int
     * @return this
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
