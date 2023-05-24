package geometries;

import primitives.Point;

/**
 * Class Triangle that inherit from Polygon
 */
public class Triangle extends Polygon implements Geometry{

    /**
     * Constructor of Triangle and execute the constructor of Polygon with super()
     * @param vertices
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }
}
