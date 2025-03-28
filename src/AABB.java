/**
 * Axis aligned bounding box - max & min x,y [ border of vector graphic ].
 * Used for collisions - Hitbox essentially.
 */
public class AABB {

    private Vector2 minPoint;
    private Vector2 maxPoint;

    /**
     * Constructor method - initalises the min and max points of the hitbox ( minimum & maximum )
     */
    public AABB() {
        minPoint = new Vector2();
        maxPoint = new Vector2();
    }

    /**
     * Checks whether the given point lies within the bounding box.
     *
     * @param point The 2D point to check against.
     * @return True if there is a collision between the point and bounding box, else false.
     */
    public boolean checkPointCollision(Vector2 point) {

        return  minPoint.getX() <= point.getX() && point.getX() <= maxPoint.getX() &&
                minPoint.getY() <= point.getY() && point.getY() <= maxPoint.getY();
    }

    /**
     * Checks whether two AABB bounds are colliding.
     *
     * @param aabb The bounding box to check against.
     * @return True if there's a collision, false otherwise.
     */
    public boolean checkAABBCollision(AABB aabb) {

        Vector2 min = aabb.getMin();
        Vector2 max = aabb.getMax();

        boolean collides = minPoint.getX() < max.getX() && maxPoint.getX() > min.getX();
        collides = collides && minPoint.getY() < max.getY() && min.getY() < maxPoint.getY();

        return collides;
    }

    /**
     * Updates the bounding box of the shape,
     * called whenever there is a change to the points
     * (i.e. rotation, translation, or new points).
     */
    public void updateAABB(double[] xPoints, double[] yPoints) {
        // contains two arrays, one for x points and another for y points
        double xMin = Double.MAX_VALUE;
        double xMax = Double.MIN_VALUE;

        double yMin = Double.MAX_VALUE;
        double yMax = Double.MIN_VALUE;

        for (int i = 0; i < xPoints.length; i++) {
            xMax = Math.max(xMax, xPoints[i]);
            xMin = Math.min(xMin, xPoints[i]);

            yMax = Math.max(yMax, yPoints[i]);
            yMin = Math.min(yMin, yPoints[i]);
        }

        // top-left point
        minPoint.setX(xMin);
        minPoint.setY(yMin);

        // bottom-right point
        maxPoint.setX(xMax);
        maxPoint.setY(yMax);
    }

    /** Calls for the minimum point of the hitbox **/
    public Vector2 getMin() {
        return minPoint;
    }

    /** Calls for the maximum point of the hitbox **/
    public Vector2 getMax() {
        return maxPoint;
    }
}
