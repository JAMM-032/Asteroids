public class Bullet{

    Vector2 vector;

    double x; // Current x-coordinate
    double y; // Current y-coordinate
    double angle; // The current angle of the bullet - based on the player's orientation
    double velocity; // Current velocity of the bullet
    int TTL; // Time to live - object will disappear -

    boolean alive; // If the bullet still exists or not [ True = Alive,  False = Dead ]

    int speedModifier = 5; // This is the modifier increase of the speed

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
        this.velocity = velocity + speedModifier; // Increases speed so that it moves faster than
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
    public void update(){

        // Look into incorporating this into the Vector2 class(?)
        double dx = Math.cos(angle) * velocity * 0.1;
        double dy = Math.sin(angle) * velocity * 0.1;
        //####################################################\\

        x += dx;
        y += dy;


        TTL -= 1; // Decrements TTL - once it is 0, the bullet will despawn
        if (TTL <= 0){
            alive = false;
        }
    }

    /**
     * Collision checking method
     */
    // Collision checking method



}