import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet{

    Vector2 vector;
    private Vector2 position;
    private Vector2 velocity; // Current velocity of the bullet
    private int TTL = 3 * 1000; // Time to live in milliseconds-
    private static final double radius = 2.0;
    private static final int baseVel = 200;

    private long startTime;

    private boolean alive; // If the bullet still exists or not [ True = Alive,  False = Dead ]

    /**
     * Generates a bullet in space - with a given angle, and position and velocity
     * @param position where the bullet starts.
     * @param angle - angle ( IN RADIANS ) of the spaceship
     * @param velocity - the velocity of the spaceship
     */
    public Bullet(Vector2 position, double velocity, double angle){
        this.position = position;

        this.velocity = Vector2.fromPolar(velocity + baseVel, angle);
        // Increases speed so that it moves faster than the player

        alive = true; // Initally the bullet is alive
        startTime = System.currentTimeMillis();
    }

    /**
     * Increments the position of the object - decrements the TTL
     * This allows for the translation of the bullet - moving in positions
     */
    public void update(int minX, int minY, int maxX, int maxY, float dt) {

        Vector2 newVel = velocity.copy();
        newVel.scalarMul(dt);

        position.vecAdd(newVel);

        double x = position.getX();
        double y = position.getY();

        if (x > maxX)
            position.setX(minX);
        else if (x < minX)
            position.setX(maxX);

        if (y > maxY)
            position.setY(minY);
        else if (y < minY)
            position.setY(maxY);

        if (System.currentTimeMillis() - startTime >= TTL){
            alive = false;
        }
    }

    /**
     * Collision checking method
     */
    // Collision checking method
    public Vector2 getPos() {
        return position.copy();
    }

    public Vector2 getVel() {
        return velocity.copy();
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(position.getX() - radius, position.getY() - radius, radius, radius);
    }

    public void setDead() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}