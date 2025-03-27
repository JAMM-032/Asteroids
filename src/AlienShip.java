import java.util.Random;

/**
 * In much correlation to the obstacles, the alienship also
 * acts akin to them
 * However, the alienship possesses a unique property, being able
 * to track and follow the player at any given moment.
 */
public class AlienShip extends Obstacle {

    private boolean isSpaceStation;
    private Shape playerShip;
    private double rotation;
    private final double ACCELERATION = (new Random()).nextDouble(0.05,0.2);
    private static final double MAX_SPEED = 1.2;

    /**
     * Constructor Method  { Mothership Class Spaceship }
     * @param player - Given shape of the spaceship
     * @param type - Asteroid type - similar property to asteroid in score, health, etc...
     *             ... Defines that it will split into two smaller ships when broken
     * @param position - Position of the alienship when spawning into the world
     */
    public AlienShip(Shape player, AsteroidType type, Vector2 position) {
        super(type, new Vector2(0, -1), new Vector2());
        playerShip = player;
        isSpaceStation = true;
        setSpaceStationShape();
        shape.translate(position);
        rotation = 0.0;
        scoreValue = 50;
    }

    /**
     * Alternative constructor - { Drone class Spaceship } smaller alienship - unlike the larger spaceship,
     *                      ...doesn't have
     * any lower forms to break into, will be permanently removed upon destruction
     * @param player - Given shape of the spaceship
     * @param velocity - Current velocity of the spaceship - two smaller spaceships are launched in equal
     *                 ...and opposite directions [ conservation of momentum ]
     * @param position - Position of the alienship when spawning into the world
     */
    private AlienShip(Shape player, Vector2 velocity, Vector2 position) {
        super(AsteroidType.LARGE, velocity, new Vector2());
        playerShip = player;
        isSpaceStation = false;
        setAlienShipShape();
        shape.translate(position);
        rotation = 0.0;
        scoreValue = 20;
    }


    /**
     * Updates the drawing of the spaceship at any given moment
     *
     * @param x - Position in x
     * @param y - Position in y
     * @param w - Width of a canvas - padding accounted
     * @param h - Height of a canvas - padding accounted
     */
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

    /**
     * Sets the spaceship shape - new points defined as a rhombus - Mothership class spaceship
     */
    private void setSpaceStationShape() {
        shape = new Shape(
                new double[] {-20, 0, 20, 0, -20},
                new double[] {0, -10, 0, 10, 0});
    }

    /**
     * Sets the spaceship shape - new points defined as an arrow akin to the player's shape
     */
    private void setAlienShipShape() {
        shape = new Shape(
                new double[] {-5, -10, 10, -10, -5},
                new double[] {0, -10, 0, 10, 0});
    }

    /**
     * Spawns the asteroids - adding them to a list
     * @param bulletVel - Bullet velocity defined
     * @return - Returns the current information of the new Obstacle[] array
     */
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
