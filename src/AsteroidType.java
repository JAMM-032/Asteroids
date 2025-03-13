public enum AsteroidType {

    SMALL(10, 15, 3, Math.toRadians(2)),
    MEDIUM(20, 25, 1, Math.toRadians(1)),
    LARGE(40, 50, 0.5, Math.toRadians(0.5));

    private final int minSize;
    private final int maxSize;
    private final double speed;
    private final double rotationSpeed;

    AsteroidType(int minSize, int maxSize, double speed, double rotationSpeed) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.speed = speed;
        this.rotationSpeed = rotationSpeed;
    }

    public int[] getSizeRange() {
        return new int[] {minSize, maxSize};
    }

    public double getSpeed() {
        return speed;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }
}
