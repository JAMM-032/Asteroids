import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;
import java.util.Random;

public class Obstacle {

    private Vector2 acc;
    private Vector2 vel;
    private Polygon shape;
    private AsteroidType type;

    public Obstacle(Vector2 velocity, Vector2 position) {
        acc = new Vector2();
        vel = velocity;
        shape = new Polygon(7, 50);
        shape.translate(position);
    }

    /**
     * Updates the location of the obstacle based on its velocity.
     */
    public void update() {
        shape.translate(vel);
        vel.translate(acc);
    }

    public void rotate(double angle) {
        shape.rotate(angle);
    }

    /**
     * Draws the obstacle onto the given canvas.
     *
     * @param gc The graphical context to draw on.
     */
    public void draw(GraphicsContext gc, Color col) {
        shape.drawStroke(gc, col);
    }

    public boolean bulletCollision(Vector2 point) {
        return shape.collides(point);
    }

    public Polygon getPolygon() {
        return this.shape;
    }

    public Obstacle[] spawnChildren() {
        return new Obstacle[1];
    }
}
