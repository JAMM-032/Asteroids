import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Spaceship {
    private final Polygon spaceship;
    private final Vector2 position;
    private final Vector2 velocity = new Vector2(0, 0);
    private final Vector2 acceleration = new Vector2(0, 0);
    private double angle = 0;
    private final double angle_diff = 10; // Change of angle

    public Spaceship(Vector2 position) {
        this.position = position;
        spaceship = new Polygon(3, 5);
        spaceship.createIsoscelesTriangle(10, 20);
    }

    public void move() {
        velocity.translate(acceleration);
        position.translate(velocity);

        // Apply resistance
        velocity.scalarMul(0.99);
        acceleration.scalarMul(0.95);

        // Update position
        spaceship.translate(velocity);
    }

    public void accelerate(double accelValue) {
        double radians = Math.toRadians(angle);
        acceleration.translate(new Vector2(Math.cos(radians) * accelValue, Math.sin(radians) * accelValue));
    }

    public void rotateRight() {
        angle += angle_diff;
        spaceship.rotate(Math.toRadians(angle_diff));
    }

    public void rotateLeft() {
        angle -= angle_diff;
        spaceship.rotate(Math.toRadians(-angle_diff));
    }

    public void draw(GraphicsContext gc) {
        spaceship.drawStroke(gc, Color.BLUE);
    }
}
