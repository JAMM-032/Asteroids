/**
 * A class representing a 2D vector
 *
 * Provides methods for vector arithmetic, scalar multiplication,
 * rotation, and other vector operations.
 *
 * The vector follows a standard Cartesian coordinate system with (x, y) components.
 */

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
     * Sets the values of x and y (this) to the given values.
     *
     * @param values - the value of x, y inputted as an array
     */
    public Vector2(double[] values) {
        x = values[0];
        y = values[1];
    }

    /**
     * Translates the x & y of an object to the given (vector2) parameters.
     *
     * @param v - the Vector2 to add to this vector
     */
    public void vecAdd(Vector2 v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    /**
     * Multiplies the current vector by a given scalar.
     *
     * @param scalar - the amount that will multiply the current vector
     */
    public void scalarMul(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    /**
     * Returns the x-coordinate of the object.
     *
     * @return The x-coordinate of the vector
     */
    public double getX() {
        return this.x;
    }

    /**
     * Sets the x-coordinate of the object.
     *
     * @param x - the new x-coordinate value
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the object.
     *
     * @return The y-coordinate of the vector
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets the y coordinate.
     *
     * @param y - the new y-coordinate value
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Inverts the current vector (reflection in y = -x).
     *
     * @return A new Vector2 object representing the negated vector
     */
    public Vector2 negate() {
        return new Vector2(-x, -y);
    }

    /**
     * Checks if two vectors are equal within a certain threshold.
     *
     * @param obj - the object to compare with
     * @return True if the vectors are equal within the threshold, false otherwise
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2))
            return false;

        Vector2 vec = (Vector2) obj;
        return (Math.abs(this.x - vec.getX()) <= THRESHOLD) && (Math.abs(this.y - vec.getY()) <= THRESHOLD);
    }

    /**
     * Calculates the magnitude (length) of the vector.
     *
     * @return The magnitude of the vector
     */
    public double getMagnitude() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    /**
     * Normalizes the vector, making it unit length.
     */
    public void normalise() {
        double mag = getMagnitude();
        if (mag != 0) { // Avoid division by zero
            x /= mag;
            y /= mag;
        }
    }

    /**
     * Rotate the vector about (0,0) by the given angle.
     *
     * @param angle - The angle to rotate (in radians)
     */
    public void rotate(double angle) {
        double tempX = getX();
        double tempY = getY();
        x = (tempX * Math.cos(angle)) - (tempY * Math.sin(angle));
        y = (tempX * Math.sin(angle)) + (tempY * Math.cos(angle));
    }

    /**
     * Returns a copy of the current Vector2 object.
     *
     * @return A new Vector2 object with the same x and y values
     */
    public Vector2 copy() {
        return new Vector2(x, y);
    }

    /**
     * Returns the rotation (angle) of the vector in radians.
     *
     * @return The angle of the vector in radians
     */
    public double getRotation() {
        double angle = Math.atan2(this.y, this.x);
        if (angle < 0)
            angle += 2.0 * Math.PI;
        return angle;
    }
}
