import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public class Obstacle {

    private Vector2 acc;
    private Vector2 vel;
    private Polygon shape;
    private AsteroidType type;

    public Obstacle(AsteroidType type, Vector2 velocity, Vector2 position) {
        acc = new Vector2();
        vel = velocity;
        shape = new Polygon(7, type.getSizeRange());
        shape.translate(position);
        this.type = type;
    }

    /**
     * Updates the location of the obstacle based on its velocity.
     */
    public void update(int x, int y, int w, int h) {
        shape.translate(vel);
        shape.wrapAround(x, y, w, h);
        vel.vecAdd(acc);
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

    public Obstacle[] spawnAsteroids(Vector2 bulletVel) {

        Obstacle[] obstacles = new Obstacle[2];

        Vector2 pos = shape.getPosition();

        switch (type) {
            case LARGE -> {
                obstacles[0] = new Obstacle(AsteroidType.MEDIUM, bulletVel, pos);
                obstacles[1] = new Obstacle(AsteroidType.MEDIUM, bulletVel.negate(), pos);
            }
            case MEDIUM -> {
                obstacles[0] = new Obstacle(AsteroidType.SMALL, bulletVel, pos);
                obstacles[1] = new Obstacle(AsteroidType.SMALL, bulletVel.negate(), pos);
            }
            case SMALL -> {
                return null;
            }
        }
        return obstacles;
    }
}
