package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

public class Camera {

    /**
     * Position of the camera
     */
    private Point p0;

    /**
     * Vector direction of the camera vTo
     */
    private Vector vTo;

    /**
     * Vector direction of the camera vUp
     */
    private Vector vUp;

    /**
     * Vector direction of the camera vRight
     */
    private Vector vRight;

    /**
     * Width of the View Plane
     */
    private double width;

    /**
     * Height of the View Plane
     */
    private double height;

    /**
     * Distance from the View PLane
     */
    private double distance;

    /**
     * Field of ImageWriter type
     */
    private ImageWriter imageWriter;

    /**
     * Field of RayTracerBase type
     */
    private RayTracerBase rayTracer;

    /**
     * Constructor of Camera with 3 parameters: position point and 2 vectors.
     * The 2 vectors are used to calculate the third.
     * If the 2 vectors are not orthogonal, there is an exception.
     *
     * @param p0  position of the camera
     * @param vTo vector direction
     * @param vUp vector direction
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("The two unit vectors vTo and vup are not orthogonal");
        }

        this.p0 = p0;

        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        this.vRight = this.vTo.crossProduct(this.vUp);
    }

    public Point getP0() {
        return p0;
    }

    public Vector getVTo() {
        return vTo;
    }

    public Vector getVUp() {
        return vUp;
    }

    public Vector getVRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }

    /**
     * Setter for the dimension of the View Plane
     *
     * @param width  of the View plane
     * @param height of the View Plane
     * @return this
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Setter for the distance from the View Plane
     *
     * @param distance between Camera and View Plane
     * @return this
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Setter for renderTest
     *
     * @param imageWriter field
     * @return imageWriter
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Setter for renderTest
     *
     * @param rayTracer field
     * @return Camera
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Function for to create ray through pixel
     *
     * @param nX Represents amount of columns
     * @param nY Represents amount of rows
     * @param j  Represents column of pixel
     * @param i  Represent row of pixel
     * @return Ray through pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        // Image center
        Point pc = p0.add(vTo.scale(distance));

        // Ratio (pixel width & height)
        double Ry = height / nY;
        double Rx = width / nX;

        // Pixel[i,j] center
        double yi = -(i - (nY - 1) / 2d) * Ry;
        double xj = (j - (nX - 1) / 2d) * Rx;

        Point Pij = pc;

        if (!isZero(xj)) {
            Pij = Pij.add(vRight.scale(xj));
        }
        if (!isZero(yi)) {
            Pij = Pij.add(vUp.scale(yi));
        }

        // vector from camera's eye in the direction of point(i,j) in the viewplane
        Vector Vij = Pij.subtract(p0);

        return new Ray(p0, Vij);
    }

    /**
     * This function create a ray, scan him with the traceRay and return the color
     *
     * @param nX resolution
     * @param nY resolution
     * @param i  index
     * @param j  index
     * @return the color of the ray
     */
    private void castRay(int nX, int nY, int i, int j) {
        Ray ray = constructRay(nX, nY, j, i);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, pixelColor);
    }

    /**
     * Color the image pixel by pixel
     */
    public Camera renderImage() {
        if (p0 == null
                || vUp == null
                || vTo == null
                || vRight == null
                || width == 0
                || height == 0
                || distance == 0
                || imageWriter == null
                || rayTracer == null) {
            throw new MissingResourceException(
                    "Missing resource",
                    getClass().getName(),
                    ""
            );
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                castRay(nX, nY, i, j);
            }
        }
        return this;
    }

    /**
     * Print the grid on the image
     *
     * @param interval fix field
     * @param color    color of the grid
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null) {
            throw new MissingResourceException("imageWriter is null", getClass().getName(), "");
        }

        for (int row = 0; row < 10; row++) {
            for (int j = 0; j < 1000; j++) {
                imageWriter.writePixel(row * 100, j, color);
            }
        }

        for (int col = 0; col < 10; col++) {
            for (int j = 0; j < 1000; j++) {
                imageWriter.writePixel(j, col * 100, color);
            }
        }

        imageWriter.writeToImage();
    }

    /**
     * Generate the image in the folder
     */
    public Camera writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException(
                    "imageWriter is null",
                    getClass().getName(),
                    ""
            );
        }

        imageWriter.writeToImage();
        return  this;
    }


}
