import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Abstract class for the base definitions of the entities
 * All methods are primary methods are paved here.
 */
public abstract class Entity {

    protected Shape shape;

    /**
     * Updates the entity's position, velocity and acceleration if applicable.
     *
     * @param x Position in x
     * @param y Position in y
     * @param w Width of a canvas - padding accounted
     * @param h Height of a canvas - padding accounted
     * @param dt The deltaTime to ensure frame independent movement.
     */
    public abstract void update(int x, int y, int w, int h, float dt);

    /**
     * Create the shape for the current entity.
     */
    protected abstract void createShape();

    /**
     * Draw the entity's shape onto the given context.
     * @param gc The graphics context to draw using.
     * @param col The color to draw the entity as.
     */
    public void draw(GraphicsContext gc, Color col) {
        shape.drawStroke(gc, col);
    }

    /**
     * @return The shape of the current entity.
     */
    public Shape getShape() {
        return this.shape;
    }

    /**
     * @return The score associated with destroying the current type of entity.
     */
    public abstract int getScoreValue();

    /**
     * Spawn new entities based on the type of the current one and
     * the bullet's velocity.
     * Can return null if the current entity shouldn't create new entities.
     *
     * @param bulletVel The velocity of the bullet which hit the entity.
     * @return Array of new entities or null.
     */
    public abstract Entity[] spawnObstacles(Vector2 bulletVel);

    /**
     * Conducts collision between the given point and the shape of the entity.
     */
    public boolean pointCollision(Vector2 point) {
        return shape.collides(point);
    }
}
