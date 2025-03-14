import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet{

    private Vector2 position;
    private double angle; // The angle of the spaceship - determines the direction of the bullet
    private Vector2 velocity = new Vector2(0,0); // Current velocity of the bullet

    private int TTL; // Time to live - object will disappear -
    private static final double radius = 5.0;
    private static final double BASE_VELOCITY = 4; // Constant base velocity of the bullet

    boolean isAlive; // If the bullet still exists or not [ True = Alive,  False = Dead ]

    /**
     * Generates a bullet in space - with a given angle, and position and velocity
     * @param spaceshipPos Position of the spaceship when it fired the bullet
     * @param spaceshipVel Velocity of the spaceship when it fired the bullet
     * @param spaceshipAngle Angle of the spaceship when it fired the bullet
     */
    public Bullet(Vector2 spaceshipPos, Vector2 spaceshipVel, double spaceshipAngle){

        TTL = 150; // Initialise time-to-live

        this.position = new Vector2(spaceshipPos.getX(), spaceshipPos.getY());

        this.angle = spaceshipAngle;
        Vector2 facingDirection = new Vector2(Math.cos(angle), Math.sin(angle));

        // Base Velocity of the bullet in the direction that spaceship is facing
        Vector2 baseVel = new Vector2(Math.cos(angle) * BASE_VELOCITY,
                Math.sin(angle) * BASE_VELOCITY);

        if (Vector2.dotProduct(facingDirection, spaceshipVel) > 0) {
            velocity = new Vector2(spaceshipVel.getX(), spaceshipVel.getY());
        }

        velocity.vecAdd(baseVel); // Increases speed so that it moves faster than the player

        isAlive = true; // Initially the bullet is isAlive
    }

    /**
     * Requests the bullet object to see if it is alive
     * @return - gives back the boolean - announcing if the object is alive
     */
    public boolean isAlive(){
        return isAlive;
    }


    /**
     * Increments the position of the object - decrements the TTL
     * This allows for the translation of the bullet - moving in positions
     */
    public void update(Score currentScore){
        position.vecAdd(velocity);

        TTL -= 1; // Decrements TTL - once it is 0, the bullet will despawn
        if (TTL <= 0){
            isAlive = false;


            // Reset multiplier
            currentScore.resetMultiplier();
            System.out.println("Bullet despawned");
        }
    }

    /**
     * Collision checking method
     */
    // Collision checking method
    public Vector2 getPos() {
        return position;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(position.getX() - radius, position.getY() - radius, radius, radius);
    }
}