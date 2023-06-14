package primitives;

/**
 * Class Material without constructor.
 * In order to save the parameters of the Fong light reflection model.
 */
public class Material {
    /**
     * Field coefficient Kt for transparency
     */
    public Double3 kT=Double3.ZERO;
    /**
     * Field coefficient Kr for reflection
     */
    public Double3 kR=Double3.ZERO;
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
     *
     * @param kD Double3
     * @return this
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }


    /**
     * Setter for coefficient kD
     *
     * @param kD double
     * @return this
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter for coefficient kS
     *
     * @param kS Double3
     * @return this
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for coefficient kS
     *
     * @param kS double
     * @return this
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for coefficient nShininess
     *
     * @param nShininess int
     * @return this
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * setter for coefficient Kt
     * @param kT
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }
    /**
     * setter for coefficient KR
     * @param kR
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }/**
     * setter for coefficient Kt
     * @param kT
     */
    public Material setKt(double kT) {
        this.kT=new Double3(kT);
        return this;
    }
    /**
     * setter for coefficient KR
     * @param kR
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
}
