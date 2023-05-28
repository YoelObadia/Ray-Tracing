package renderer;

import primitives.*;

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
}
