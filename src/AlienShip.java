public class AlienShip extends Obstacle {

    private boolean isSpaceStation;
    private Shape playerShip;
    private double rotation;
    private static final double ACCELERATION = 0.2;
    private static final double MAX_SPEED = 2.5;

    public AlienShip(Shape player, AsteroidType type, Vector2 velocity, Vector2 position) {
        super(type, new Vector2(0, -1), new Vector2());
        playerShip = player;
        isSpaceStation = true;
        setSpaceStationShape();
        shape.translate(position);
        rotation = 0.0;
    }

    private AlienShip(Shape player, Vector2 velocity, Vector2 position) {
        super(AsteroidType.LARGE, velocity, new Vector2());
        playerShip = player;
        isSpaceStation = false;
        setAlienShipShape();
        shape.translate(position);
        rotation = 0.0;
    }

    @Override
    public void update(int x, int y, int w, int h) {

        Vector2 diff = shape.getPositionCopy().negate();
        diff.vecAdd(playerShip.getPositionCopy());
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

    private void setSpaceStationShape() {
        shape = new Shape(
                new double[] {-20, 0, 20, 0, -20},
                new double[] {0, -10, 0, 10, 0});
    }

    private void setAlienShipShape() {
        shape = new Shape(
                new double[] {-5, -10, 10, -10, -5},
                new double[] {0, -10, 0, 10, 0});
    }

    @Override
    public Obstacle[] spawnAsteroids(Vector2 bulletVel) {

        Vector2 vel = bulletVel.copy();
        vel.rotate(Math.PI * 0.5);
        vel.normalise();
        vel.scalarMul(5);

        if (isSpaceStation) {
            return new Obstacle[] {
                new AlienShip(playerShip, vel, shape.getPositionCopy()),
                new AlienShip(playerShip, vel.negate(), shape.getPositionCopy())
            };
        }
        return new Obstacle[0];
    }
}
