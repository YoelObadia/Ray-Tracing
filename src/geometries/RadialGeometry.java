package geometries;

/**
 * Abstract class that sphere and tube inherits for the field radius
 * It allows to not create every time the field radius
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * Field for the classes sphere and tube
     */
    protected double radius;
}
