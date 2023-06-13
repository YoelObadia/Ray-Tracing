package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class Geometries using Intersectable interface for to make scene
 * composed of several geometries and so this class make collection of geometries.
 * We use for this the design patterns Composite and Iterator.
 */
public class Geometries extends Intersectable {

    /**
     * Private LinkedList of Intersectable type for the Composite design pattern
     */
    private final List<Intersectable> geometries;

    /**
     * Default constructor that initialize the list intersectable to a LinkedList<>()
     */
    public Geometries() {
        geometries = new LinkedList<>();
    }

    /**
     * Constructor with 1 parameter
     *
     * @param geometries list of Intersectables
     */
    public Geometries(Intersectable... geometries) {
        this();
        Collections.addAll(this.geometries, geometries);
    }

    /**
     * Add the geometries to list (for design pattern Composite)
     *
     * @param geometries all geometries for the scene
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * Use of the function findGeoIntsersectionsHelper from the interface Intersectable
     * We will iterate the List that contains several geometries and
     * for each geometry, we will call his findGeoIntsersections function.
     *
     * @param ray that allow us to know if there are intersections
     * @return list of intersections GeoPoints
     */
    @Override
    public List<GeoPoint> findGeoIntsersectionsHelper(Ray ray) {

        List<GeoPoint> intersections = new LinkedList<>();

        for (Intersectable geometry : geometries) {
            var temp = geometry.findGeoIntsersections(ray);
            if (temp != null)
                intersections.addAll(temp);
        }

        return intersections;
    }
}
