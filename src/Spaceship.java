import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This is the player spaceship class
 * All the necessary attributes of the player are listed here
 * The spaceship is controlled directly by the player and can
 * fire projectiles at enemies.
 * The player, like other entities, can be destroyed in one shot.
 */
public class Spaceship {
    private final Shape spaceship;
    private final Vector2 velocity = new Vector2(0, 0);
    private final Vector2 acceleration = new Vector2(0, 0);
    private double angle = Math.toRadians(-90);
    private final double ANGLE_DIFF = Math.toRadians(270); // Change of angle

    private static final double MAX_ACCELERATION = 750;
    private static final double MAX_VELOCITY = 1250;

    /**
     * Creates a new Spaceship object at the specified position.
     *
     * @param position the initial position of the spaceship.
     */
    public Spaceship(Vector2 position) {
        spaceship = new Shape(
                new double[] {10, -5, -5},
                new double[] {0, 5, -5}
        );
        spaceship.translate(position);
        spaceship.rotate(angle);
    }

    /**
     * Updates the position and velocity of the spaceship based on its acceleration.
     * Applies resistance to the velocity and acceleration.
     * Updates the position of the spaceship by translating using velocity vector .
     */
    public void move(int x, int y, int w, int h, float dt) {

        Vector2 newAcc = acceleration.copy();
        newAcc.scalarMul(dt);

        velocity.vecAdd(newAcc);

        // Check if current velocity exceeds max velocity
        if (velocity.getMagnitude() > MAX_VELOCITY) {
            velocity.normalise(); // Convert to unit vector
            velocity.scalarMul(MAX_VELOCITY); // Scale to max
        }

        // Apply resistance
        velocity.scalarMul(Math.pow(0.1, dt));
        acceleration.scalarMul(Math.pow(0.25, dt));

        // Update position
        Vector2 newVel = velocity.copy();
        newVel.scalarMul(dt);

        spaceship.translate(newVel);
        spaceship.wrapAround(x, y, w, h);
    }

    /**
     * Accelerates the spaceship in the direction it is currently facing
     * Done by adding a vector to the acceleration based on its angle and the given acceleration value.
     *
     * @param accelValue the amount of acceleration to apply to the spaceship.
     */
    public void accelerate(double accelValue) {
        acceleration.vecAdd(new Vector2(Math.cos(angle) * accelValue, Math.sin(angle) * accelValue));

        if (acceleration.getMagnitude() > MAX_ACCELERATION) {
            acceleration.normalise(); // Convert to unit vector
            acceleration.scalarMul(MAX_ACCELERATION); // Scale to max
        }
    }

    /**
     * Increments the angle by angle_diff
     * Rotates the points that spaceship consists of by angle_diff
     * using the rotate() method of Polygon class
     */
    public void rotateRight(float dt) {
        angle += ANGLE_DIFF * dt;
        spaceship.rotate(ANGLE_DIFF * dt);
    }

    /**
     * Decrements the angle by angle_diff
     * Rotates the points that spaceship consists of by -angle_diff
     * using the rotate() method of Polygon class
     */
    public void rotateLeft(float dt) {
        angle -= ANGLE_DIFF * dt;
        spaceship.rotate(-ANGLE_DIFF * dt);
    }

    /**
     * Draws the spaceship on the given GraphicsContext with a blue stroke color.
     *
     * @param gc the GraphicsContext used for drawing the spaceship.
     */
    public void draw(GraphicsContext gc) {
        spaceship.drawFill(gc, Color.WHITE);
    }

    /**
     * Returns the shape of the spaceship.
     *
     * @return Shape object representing the spaceship.
     */
    public Shape getShape() {
        return spaceship;
    }

    /**
     * Returns the current rotation angle of the spaceship.
     *
     * @return The rotation angle in radians.
     */
    public double getRotation() {
        return angle;
    }

    /**
     * Returns the current velocity magnitude of the spaceship.
     *
     * @return The speed of the spaceship as a double.
     */
    public double getVelocity() {
        return velocity.getMagnitude();
    }


    /**
     * Fire a bullet from the player spaceship.
     * @return A new bullet object which has just been fired.
     */
    public Bullet fire() {
        Vector2 pos = spaceship.getPositionCopy();
        return new Bullet(pos, getVelocity(), getRotation());
    }
}