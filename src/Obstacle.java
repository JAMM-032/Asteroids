import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public class Obstacle extends Entity {

    protected Vector2 acc;
    protected Vector2 vel;
    protected AsteroidType type;

    public Obstacle(AsteroidType type, Vector2 velocity, Vector2 position) {
        this.type = type;
        acc = new Vector2();
        vel = velocity;
        createShape();
        shape.translate(position);
    }

    @Override
    protected void createShape() {
        shape = new Shape(7, type.getSizeRange());
    }

    /**
     * Updates the location of the obstacle based on its velocity.
     */
    public void update(int x, int y, int w, int h, float dt) {

        Vector2 newVel = vel.copy();
        newVel.scalarMul(dt);

        shape.rotate(type.getRotationSpeed() * dt);

        shape.translate(newVel);
        shape.wrapAround(x, y, w, h);
        vel.vecAdd(acc);
    }

    /**
     * Draws the obstacle onto the given canvas.
     *
     * @param gc The graphical context to draw on.
     */
    public void draw(GraphicsContext gc, Color col) {
        shape.drawStroke(gc, col);
    }

    public Shape getShape() {
        return this.shape;
    }

    @Override
    public int getScoreValue() {
        return type.getScoreValue();
    }

    @Override
    public Obstacle[] spawnObstacles(Vector2 bulletVel) {

        Obstacle[] obstacles = new Obstacle[2];

        Vector2 pos = shape.getPositionCopy();
        Vector2 vel = bulletVel.copy();
        vel.rotate(Math.toRadians(90));
        vel.normalise();

        switch (type) {
            case LARGE -> {
                vel.scalarMul(AsteroidType.LARGE.getSpeed());
                obstacles[0] = new Obstacle(AsteroidType.MEDIUM, vel, pos);
                obstacles[1] = new Obstacle(AsteroidType.MEDIUM, vel.negate(), pos);
            }
            case MEDIUM -> {
                vel.scalarMul(AsteroidType.SMALL.getSpeed());
                obstacles[0] = new Obstacle(AsteroidType.SMALL, vel, pos);
                obstacles[1] = new Obstacle(AsteroidType.SMALL, vel.negate(), pos);
            }
            case SMALL -> {
                return null;
            }
        }
        return obstacles;
    }
}
