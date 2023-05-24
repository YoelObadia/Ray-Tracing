package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder extends Tube implements Geometry{

    /**
     * Field height
     */
    private double height;

    /**
     * Constructor of Cylinder with 1 parameter
     * @param height that is the length of the cylinder
     */
    public Cylinder(double height) {
        this.height = height;
    }

    /**
     * Getter for the field height
     * @return height of the cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     * Use of the function getNormal of the interface Geometry
     * @param point that is the point on the geometry
     * @return vector normal
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
