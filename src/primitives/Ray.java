package primitives;

import java.util.Objects;

/**
 * This class will serve to use
 */
public class Ray {

    /**
     * Head of the ray
     */
    final Point p0;

    /**
     * Vector director of the ray
     */
    final Vector dir;

    /**
     * Constructor of Ray with 2 parameters
     * @param p0  as first parameter
     * @param dir as second parameter
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /**
     * Getter for the head of the ray
     * @return point p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter for the vector director of the ray
     * @return vector dir
     */
    public Vector getDir() {
        return dir;
    }
}
