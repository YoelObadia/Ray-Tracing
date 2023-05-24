package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Class Tube: geometry of the project
 */
public class Tube implements Geometry{

    /**
     * Field axisRay that is the ray of the tube
     */
    protected Ray axisRay;

    /**
     * Use of the function getNormal of the interface Geometry
     * @param point that is the point on the geometry
     * @return vector normal
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    /**
     * Getter for the field axisRay
     * @return axisRay of the tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }
}
