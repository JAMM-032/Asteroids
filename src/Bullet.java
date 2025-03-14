import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet{

    Vector2 vector;

    double x; // Current x-coordinate
    double y; // Current y-coordinate
    double angle; // The current angle of the bullet - based on the player's orientation
    double velocity; // Current velocity of the bullet
    private int TTL = 180; // Time to live - object will disappear -
    private static final double radius = 2.0;
    private static final int baseVel = 15;

    boolean alive; // If the bullet still exists or not [ True = Alive,  False = Dead ]

    /**
     * Generates a bullet in space - with a given angle, and position and velocity
     * @param x - x coordinate of the object
     * @param y - y coordinate of the object
     * @param angle - angle ( IN RADIANS ) of the spaceship
     * @param velocity - the velocity of the spaceship
     */
    public Bullet(double x, double y, double velocity, double angle){
        this.x = x; // This will be the x of the spaceship
        this.y = y; // This will be the y of the spaceship --
                    // BOTH MUST be provided by the ship when firing

        this.angle = angle;
        this.velocity = velocity + baseVel; // Increases speed so that it moves faster than
                                                  // the player

        alive = true; // Initally the bullet is alive
        this.vector = new Vector2(x,y);
    }

    /**
     * Requests the bullet object to see if it is alive
     * @return - gives back the boolean - announcing if the object is alive
     */
    public boolean getAlive(){
        return alive;
    }


    /**
     * Increments the position of the object - decrements the TTL
     * This allows for the translation of the bullet - moving in positions
     */
    public void update(int minX, int minY, int maxX, int maxY){

        // Look into incorporating this into the Vector2 class(?)
        double dx = Math.cos(angle) * velocity * 0.1;
        double dy = Math.sin(angle) * velocity * 0.1;
        //####################################################\\

        x += dx;
        y += dy;

        if (x > maxX)
            x = minX;
        else if (x < minX)
            x = maxX;

        if (y > maxY)
            y = minY;
        else if (y < minY)
            y = maxY;


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
        return new Vector2(x, y);
    }

    public Vector2 getVel() {
        double dx = Math.cos(angle) * velocity * 0.1;
        double dy = Math.sin(angle) * velocity * 0.1;

        return new Vector2(dx, dy);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x - radius, y - radius, radius, radius);
    }

    public void setDead() {
        alive = false;
        System.out.println("died :(");
    }

    public boolean isAlive() {
        return alive;
    }
}