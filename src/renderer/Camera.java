package renderer;

import primitives.*;

import java.util.MissingResourceException;

public class Camera {

    /**
     * Position of the camera
     */
    private Point p0;

    /**
     * Vector direction of the camera vTo
     */
    private Vector vto;

    /**
     * Vector direction of the camera vUp
     */
    private Vector vup;

    /**
     * Vector direction of the camera vRight
     */
    private Vector vright;

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

    public Point getP0() {
        return p0;
    }

    public Vector getVto() {
        return vto;
    }

    public Vector getVup() {
        return vup;
    }

    public Vector getVright() {
        return vright;
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
     * Constructor of Camera with 3 parameters: position point and 2 vectors.
     * The 2 vectors are used to calculate the third.
     * If the 2 vectors are not orthogonal, there is an exception.
     * @param p0 position of the camera
     * @param vto vector direction
     * @param vup vector direction
     */
    public  Camera(Point p0, Vector vto, Vector vup) {
        this.p0 = p0;
        this.vto = vto.normalize();
        this.vup = vup.normalize();
        if (vto.dotProduct(vup) == 0) {
            this.vright = vto.crossProduct(vup).normalize();
        } else {
            throw new IllegalArgumentException("The two unit vectors vto and vup are not orthogonal");
        }
    }

    /**
     * Setter for the dimension of the View Plane
     * @param width of the View plane
     * @param height of the View Plane
     * @return this
     */
    public Camera setVPSize(double width, double height)
    {
        this.width=width;
        this.height=height;
        return this;
    }

    /**
     * Setter for the distance from the View Plane
     * @param distance between Camera and View Plane
     * @return this
     */
    public Camera setVPDistance(double distance)
    {
        this.distance=distance;
        return this;
    }

    /**
     * Setter for renderTest
     * @param imageWriter field
     * @return imageWriter
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Setter for renderTest
     * @param rayTracer field
     * @return rayTracer
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Function for to create ray through pixel
     * @param nX Represents amount of columns
     * @param nY Represents amount of rows
     * @param j Represents column of pixel
     * @param i Represent row of pixel
     * @return Ray through pixel
     */
    public Ray constructRay(int nX,int nY,int j,int i){

        // Image center
        Point pc= p0.add(vto.scale(distance));

        // Ratio (pixel width & height)
        double Ry=height/nY;
        double Rx=width/nX;

        // Pixel[i,j] center
        double yi=-(i-(double)(nY-1)/2)*Ry;
        double xj=(j-(double)(nX-1)/2)*Rx;

        Point pi_j=pc;

        if (xj!=0)
            pi_j=pi_j.add(vright.scale(xj));
        if (yi!=0)
            pi_j=pi_j.add(vup.scale(yi));

        Vector vi_j=pi_j.subtract(p0);

        return new Ray(p0, vi_j);
    }

    /**
     * This function create a ray, scan him with the traceRay and return the color
     * @param j index
     * @param i index
     * @param nX resolution
     * @param nY resolution
     * @return the color of the ray
     */
    private Color castRay(int j,int i,int nX,int nY) {
        return rayTracer.traceRay(constructRay(nX,nY,j,i));
    }

    /**
     * Color the image pixel by pixel
     */
    public void renderImage() {
        if (p0==null || vup==null || vto==null || vright==null || width==0||
                height==0 || distance==0 || imageWriter==null || rayTracer==null) {
            throw new MissingResourceException(
                    "Missing resource",
                    getClass().getName(),
                    ""
            );
        }

        Color color;
        for(int i = 0; i < imageWriter.getNx(); i++)
        {
            for(int j = 0 ; j < imageWriter.getNy(); j++)
            {
                color = castRay(j, i, imageWriter.getNx(), imageWriter.getNy());
                imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * Print the grid on the image
     * @param interval fix field
     * @param color color of the grid
     */
    public void printGrid(int interval, Color color){
        if(imageWriter==null){
            throw new MissingResourceException("imageWriter is null",getClass().getName(),"");
        }

        for(int row = 0; row < 10; row++) {
            for(int j = 0; j < 1000; j++) {
                imageWriter.writePixel(row*100, j, color);
            }
        }

        for(int col = 0; col < 10; col++) {
            for(int j = 0; j < 1000; j++) {
                imageWriter.writePixel(j, col*100, color);
            }
        }

        imageWriter.writeToImage();
    }

    /**
     * Generate the image in the folder
     */
    public void writeToImage(){
        if(imageWriter==null){
            throw new MissingResourceException(
                    "imageWriter is null",
                    getClass().getName(),
                    ""
            );
        }

        imageWriter.writeToImage();
    }


}
