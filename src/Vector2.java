import java.util.Arrays;

public class Vector2 {

    private double x;
    private double y;

    public Vector2() {
        x = 0.0;
        y = 0.0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the values of x and y ( this ) to the given values
     * @param values - the value of x,y inputted as an array
     */
    public Vector2(double[] values) {
        x = values[0];
        y = values[1];
    }

    /**
     * Translates the x & y of an object to the given parameters
     * @param v - is the vector2 inputted by the user
     */
    public void translate(Vector2 v) {

        this.x += v.getX();
        this.y += v.getY();

    }

    /**
     * This method multiplies the current vectors by a given scalar
     * @param scalar - the amount that will multiply the current vectors
     * @return - returns the new result (vector2 format) of the scaled vectors.
     */
    public Vector2 scalarMul(double scalar) {
        return new Vector2(scalar * getX(), scalar * getY());
    }

    /**
     * This method returns the x-coordinate of the object
     * @return - returns the x-coordinate associated with the object
     */
    public double getX() {
        return this.x;
    }

    /**
     * This method returns the y-coordinate of the object
     * @return - returns the y-coordinate associated with the object
     */
    public double getY() {
        return this.y;
    }

    /**
     * This method aims to inverse the current vector of the object
     * @return - returns the inverted vector ( reflection in y = -x )
     */
    public Vector2 negate() {
        return new Vector2(-x, -y);
    }
}
