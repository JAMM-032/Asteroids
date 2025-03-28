/**
 * [?] - calls for the types of asteroids that can be created
 * Large Asteroids : Can split into Medium Asteroids
 * Medium Asteroids : Can split into Small Asteroids
 * Small Asteroids : Will be destroyed upon being shot
 */
public enum AsteroidType {

    SMALL(15, 180, Math.toRadians(75), 5),
    MEDIUM(25, 120, Math.toRadians(60), 10),
    LARGE(50, 60, Math.toRadians(45), 15);

    private final int size;
    private final double speed;
    private final double rotationSpeed;
    private final int scoreValue;

    /**
     * Constructor class of the asteroid types - size & speed (base) of the asteroid
     * ... will effect the way it moves through space - capped (ranged) values assigned
     * The rotationSpeed and Score Value are also ultimately restricted by the type of
     * ... asteroid the player will be facing
     * @param size - Size of the asteroid, different between large (L), medium (M) & small (S)
     * @param speed - S asteroids typically move the fastest, with L being the slowest
     * @param rotationSpeed - S asteroids rotate the fastest - L rotate the slowest
     * @param scoreValue - S asteroids have the LEAST score, L have the MOST score
     */
    AsteroidType(int size, double speed, double rotationSpeed, int scoreValue) {
        this.size = size;
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        this.scoreValue = scoreValue;
    }

    /**
     * Queries the size of the given asteroid
     * @return - returns the size (int value)
     */
    public int getSizeRange() {
        return size;
    }

    /**
     * Queries the speed of the given asteroid
     * @return - returns the speed (double value)
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Queries the rotation speed of the given asteroid
     * @return - returns the rotation speed (double value)
     */
    public double getRotationSpeed() {
        return rotationSpeed;
    }

    /**
     * Queries the Score Value of the asteroid
     * @return - returns the score value assigned ( double )
     */
    public int getScoreValue() {
        return scoreValue;
    }
}
