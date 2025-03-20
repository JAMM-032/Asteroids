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
     * Constructs a new vector from polar coordinate form (i.e. magnitude and angle)
     * to the expected cartesian form.
     *
     * @param magnitude The absolute size of the vector (expected to be positive).
     * @param rotation The angle to rotate by [from coordinate (1,0)]
     * @return A new Vector2 object in cartesian form.
     */
    public static Vector2 fromPolar(double magnitude, double rotation) {
        Vector2 vec = new Vector2(1.0, 0.0);

        vec.scalarMul(magnitude);
        vec.rotate(rotation);

        return vec;
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
    public void vecAdd(double x, double y){
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

    public void setX(double x) {
        this.x = x;
    }

    /**
     * This method returns the y-coordinate of the object
     * @return - returns the y-coordinate associated with the object
     */
    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
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

    public static double dotProduct(Vector2 v1, Vector2 v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public double getMagnitude() {
        return  Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public void normalise() {
        double mag = getMagnitude();
        x /= mag;
        y /= mag;
    }

    /**
     * Rotate the vector about (0,0) by the angle amount.
     *
     * @param angle The angle to rotate (in radians).
     */
    public void rotate(double angle) {
        double tempX = getX();
        double tempY = getY();

        x = (tempX * Math.cos(angle)) - (tempY * Math.sin(angle));
        y = (tempX * Math.sin(angle)) + (tempY * Math.cos(angle));
    }

    /**
     * Give a copy of the current Vector2.
     *
     * @return A new Vector2 object.
     */
    public Vector2 copy() {
        return new Vector2(x, y);
    }

    public double getRotation() {
        double angle = Math.atan2(this.y, this.x);
        if (angle < 0)
            angle += 2.0 * Math.PI;
        return angle;
    }
}
