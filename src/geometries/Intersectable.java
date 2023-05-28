package geometries;

import primitives.*;
import java.util.List;

/**
 * Interface Intersectable for the definition of the function findIntersections()
 * used by all the other geometries of the project
 */
public interface Intersectable {

    /**
     * Definition of the function findIntersections()
     * Take the parameter Ray that will search intersections
     * The function return a list of Points that are
     * the intersections between the ray and the geometries
     */
    List<Point> findIntsersections(Ray ray);
}
