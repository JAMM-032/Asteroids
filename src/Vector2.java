public class Vector2 {

    private double x;
    private double y;

    private static final double THRESHOLD = 0.01;

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
     * Translates the x & y of an object to the given (vector2) parameters
     * @param v - is the vector2 inputted by the user
     */
    public void vecAdd(Vector2 v) {

        this.x += v.getX();
        this.y += v.getY();
    }

    /**
     * Translate the x & y of an object to the given (double) parameters
     * @param x - x coordinate given
     * @param y - y coordinate given
     */
    public void translate(double x, double y){
        this.x += x;
        this.y += y;
    }

    /**
     * This method multiplies the current vectors by a given scalar
     * @param scalar - the amount that will multiply the current vectors
     */
    public void scalarMul(double scalar) {
        x *= scalar;
        y *= scalar;
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

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Vector2))
            return false;

        Vector2 vec = (Vector2) obj;

        return (Math.abs(this.x - vec.getX()) <= THRESHOLD) && (Math.abs(this.y - vec.getY()) <= THRESHOLD);
    }
}
