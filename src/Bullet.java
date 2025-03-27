import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet{

    Vector2 vector;
    private Vector2 position;
    private Vector2 velocity; // Current velocity of the bullet
    private int TTL = 120; // Time to live - object will disappear -
    private static final double radius = 4.0;
    private static final int baseVel = 5;

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
    }

    /**
     * Increments the position of the object - decrements the TTL
     * This allows for the translation of the bullet - moving in positions
     */
    public void update(int minX, int minY, int maxX, int maxY){

        // Look into incorporating this into the Vector2 class(?)
        // mmmm, yesn't
        //####################################################\\

        position.vecAdd(velocity);

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

        TTL -= 1; // Decrements TTL - once it is 0, the bullet will despawn
        if (TTL <= 0){
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