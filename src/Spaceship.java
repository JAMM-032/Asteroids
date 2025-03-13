import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Spaceship {
    private final Polygon spaceship;
    private final Vector2 position;
    private final Vector2 velocity = new Vector2(0, 0);
    private final Vector2 acceleration = new Vector2(0, 0);
    private double angle = 0;
    private final double angle_diff = 10; // Change of angle


    /**
     * Creates a new Spaceship object at the specified position.
     *
     * @param position the initial position of the spaceship.
     */
    public Spaceship(Vector2 position) {
        this.position = position;
        spaceship = new Polygon(3, 5);
        spaceship.createIsoscelesTriangle(10, 20);
        spaceship.translate(position);
    }

    /**
     * Updates the position and velocity of the spaceship based on its acceleration.
     * Applies resistance to the velocity and acceleration.
     * Updates the position of the spaceship by translating using velocity vector .
     */
    public void move() {
        velocity.translate(acceleration);
        position.translate(velocity);

        // Apply resistance
        velocity.scalarMul(0.99);
        acceleration.scalarMul(0.95);

        // Update position
        spaceship.translate(velocity);
    }

    /**
     * Accelerates the spaceship in the direction it is currently facing
     * Done by adding a vector to the acceleration based on its angle and the given acceleration value.
     *
     * @param accelValue the amount of acceleration to apply to the spaceship.
     */
    public void accelerate(double accelValue) {
        double radians = Math.toRadians(angle);
        acceleration.translate(new Vector2(Math.cos(radians) * accelValue, Math.sin(radians) * accelValue));
    }

    /**
     * Increments the angle by angle_diff
     * Rotates the points that spaceship consists of by angle_diff
     * using the rotate() method of Polygon class
     */
    public void rotateRight() {
        angle += angle_diff;
        spaceship.rotate(Math.toRadians(angle_diff));
    }

    /**
     * Decrements the angle by angle_diff
     * Rotates the points that spaceship consists of by -angle_diff
     * using the rotate() method of Polygon class
     */
    public void rotateLeft() {
        angle -= angle_diff;
        spaceship.rotate(Math.toRadians(-angle_diff));
    }

    /**
     * Draws the spaceship on the given GraphicsContext with a blue stroke color.
     *
     * @param gc the GraphicsContext used for drawing the spaceship.
     */
    public void draw(GraphicsContext gc) {
        spaceship.drawStroke(gc, Color.WHITE);
    }
}
