package geometries;

/**
 * Abstract class that sphere and tube inherits for the field radius
 * It allows to not create every time the field radius
 * The class inherit from Geometry and allow to Sphere and Tube
 * (and Cylinder) to use the functions of Geometry abstract class
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * Field for the classes sphere and tube
     */
    protected double radius;
}
