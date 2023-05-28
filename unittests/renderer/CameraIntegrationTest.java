package renderer;

import geometries.*;
import primitives.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Camera class for sphere, plane and triangle
 */
public class CameraIntegrationTest {

    /**
     * Help function to avoid redundancies
     * @param camera for create ray
     * @param geometry the form for the test
     * @return number of intersections
     */
    private int countIntersectionsCameraGeometry(Camera camera, Intersectable geometry){

        // This function return the number of point of intersection
        // between the geometries and a ray from our camera
        // In details,we get the position of the pixel by nX & nY
        // and launch a ray to this pixel ,if there's no intersection
        // so the count will be null but if there's intersection point.
        // So we add them up to our variable "count" and return it

        int count = 0;
        List<Point> intersections;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                intersections = geometry.findIntsersections(camera.constructRay(3, 3, j, i));
                count += intersections == null ? 0 : intersections.size();
            }
        }

        return count;
    }


    @Test
    public void CameraRaySphereIntegration() {

        Camera cam1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPDistance(1d)
                .setVPSize(3d, 3d);

        Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPDistance(1d)
                .setVPSize(3d, 3d);

        //TC01:2 points
        assertEquals(
                2,
                countIntersectionsCameraGeometry(
                        cam1, new Sphere(new Point(0,0,-3), 1d)),
                "Bad number of intersections"
        );

        // TC02:  18 points
        assertEquals(
                18,
                countIntersectionsCameraGeometry(
                        cam2, new Sphere(new Point(0,0,-2.5), 2.5)),
                "Bad number of intersections"
        );

        //TC03: 10 points
        assertEquals(
                10,
                countIntersectionsCameraGeometry(
                        cam2, new Sphere(new Point(0,0,-2), 2d)),
                "Bad number of intersections"
        );

        // TC04: 9 points
        assertEquals(
                9,
                countIntersectionsCameraGeometry(
                        cam2, new Sphere(new Point(0, 0, 1), 4d)),
                "Bad number of intersections"
        );

        // TC05: 0 points
        assertEquals(
                0,
                countIntersectionsCameraGeometry(
                        cam1, new Sphere(new Point(0, 0, 1), 0.5)),
                "Bad number of intersections"
        );
    }


    @Test
    public void CameraRayTriangleIntegration() {
        Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPDistance(1d)
                .setVPSize(3d, 3d);

        // TC01:  1 point
        assertEquals(
                1,
                countIntersectionsCameraGeometry(
                        cam,
                        new Triangle(
                                new Point(1, -1, -2),
                                new Point(-1, -1, -2),
                                new Point(0, 1, -2))
                ),
                "Bad number of intersections");

        // TC02:  2 points
        assertEquals(
                2,
                countIntersectionsCameraGeometry(
                        cam,
                        new Triangle(
                                new Point(1, 1, -2),
                                new Point(-1, 1, -2),
                                new Point(0, -20, -2))
                ),
                "Bad number of intersections"
        );
    }

    @Test
    public void CameraRayPlaneIntegration() {
        Camera cam = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVPDistance(1d)
                .setVPSize(3d, 3d);

        // TC01: Plane against camera 9 points
        assertEquals(
                9,
                countIntersectionsCameraGeometry(
                        cam,
                        new Plane(
                                new Point(0, 0, 5),
                                new Vector(0, 0, 1)
                        )
                ),
                "Bad number of intersections");

        // TC02: Plane with small angle 9 points
        assertEquals(
                9,
                countIntersectionsCameraGeometry(
                        cam,
                        new Plane(
                                new Point(0, 0, 5),
                                new Vector(0, -1, 2))
                ),
                "Bad number of intersections"
        );

        // TC03:  6 points
        assertEquals(
                6,
                countIntersectionsCameraGeometry(
                        cam,
                        new Plane(
                                new Point(0,0,2),
                                new Vector(1,1,1))
                ),
                "Bad number of intersections"
        );

        // TC04:  0 points
        assertEquals(
                0,
                countIntersectionsCameraGeometry(
                        cam,
                        new Plane(
                                new Point(0, 0, -4),
                                new Vector(0,0,1))
                ),
                "Bad number of intersections"
        );
    }

}
