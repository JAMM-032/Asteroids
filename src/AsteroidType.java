/**
 * [?] - calls for the types of asteroids that can be created
 * Large Asteroids : Can split into Medium Asteroids
 * Medium Asteroids : Can split into Small Asteroids
 * Small Asteroids : Will be destroyed upon being shot
 */
public enum AsteroidType {

    SMALL(15, 3, Math.toRadians(2), 5),
    MEDIUM(25, 1, Math.toRadians(1), 10),
    LARGE(50, 0.5, Math.toRadians(0.5), 15);

    private final int size;
    private final double speed;
    private final double rotationSpeed;
    private final int scoreValue;

    AsteroidType(int size, double speed, double rotationSpeed, int scoreValue) {
        this.size = size;
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
        this.scoreValue = scoreValue;
    }

    public int getSizeRange() {
        return size;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}
