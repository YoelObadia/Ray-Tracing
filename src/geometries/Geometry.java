package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface Geometry that all the geometries of the project will implement
 */
public interface Geometry extends Intersectable{

    /**
     * Definition of the function getNormal
     * @param point that is the point on the geometry
     * @return the same vector with length = 1
     */
    public Vector getNormal(Point point);
}
