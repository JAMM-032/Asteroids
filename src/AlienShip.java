import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public class AlienShip extends Obstacle {

    private Shape playerShip;
    private double rotation;
    private static final double ACCELERATION = 0.2;
    private static final double MAX_SPEED = 2.5;

    public AlienShip(Shape player, AsteroidType type, Vector2 velocity, Vector2 position) {
        super(type, new Vector2(0, -1), new Vector2());
        playerShip = player;
        shape = new Shape(
                new double[] {-5, -10, 10, -10, -5},
                new double[] {0, -10, 0, 10, 0});
        shape.translate(position);
        rotation = 0.0;
    }

    @Override
    public void update(int x, int y, int w, int h) {

        Vector2 diff = shape.getPosition().negate();
        diff.vecAdd(playerShip.getPosition());
        diff.normalise();
        diff.scalarMul(ACCELERATION);

        vel.vecAdd(diff);

        double mag = vel.getMagnitude();
        if (mag > MAX_SPEED) {
            vel.scalarMul(MAX_SPEED / mag);
        }

        double angleDiff = vel.getRotation() - rotation;

        rotation += angleDiff;
        shape.rotate(angleDiff);

        shape.translate(vel);
        shape.wrapAround(x, y, w, h);
    }

    @Override
    public Obstacle[] spawnAsteroids(Vector2 bulletVel) {
        return new Obstacle[0];
    }
}
