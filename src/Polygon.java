import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Polygon {

    private double[] xPoints;
    private double[] yPoints;
    private double[][] AABB; // Access aligned bounding box - max & min x,y [ border of vector graphic ]
    // Used for collisions - Hitbox essentially

    private Vector2 translation;
    private Random rand = new Random();

    /**
     * Generate a regular polygon with given
     * number of sides and radius.
     * The points are around (0, 0).
     *
     * @param nSides The number of sides to the polygon.
     * @param radius Distance from center to a vertex of the (regular) polygon.
     */
    public Polygon(int nSides, double radius) {
        genPoints(nSides, radius);
        translation = new Vector2();
    }

    /**
     * Translate all the points of the polygon by the given amount.
     *
     * @param amount The amount (2D vector) to translate by.
     */
    public void translate(Vector2 amount) {
        // save the translation to be used during rotation
        translation.vecAdd(amount);

        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] += amount.getX();
            yPoints[i] += amount.getY();
        }
        updateAABB();
    }

    public void wrapAround(int minX, int minY, int maxX, int maxY) {
        double x = translation.getX();
        double y = translation.getY();

        if (x < minX)
            x = maxX;
        else if (x > maxX)
            x = minX;

        if (y < minY)
            y = maxY;
        else if (y > maxY)
            y = minY;

        Vector2 vec = new Vector2(x, y);

        if (!vec.equals(translation)) {
            vec.vecAdd(translation.negate());
            translate(vec);
        }
    }

    public void printPos() {
        System.out.println(translation.getX() + " " + translation.getY());
    }

    /**
     * Rotates all the points (around center of the polygon)
     * by the given angle.
     *
     * @param angle The amount to rotate (in radians).
     */
    public void rotate(double angle) {

        for (int i = 0; i < xPoints.length; i++) {

            // translate the point such that it's centered at (0, 0)
            // ensuring that rotation is around center of the shape
            xPoints[i] -= translation.getX();
            yPoints[i] -= translation.getY();

            double tempX = xPoints[i];
            double tempY = yPoints[i];

            // rotate the point by the given angle
            xPoints[i] = tempX * Math.cos(angle) - tempY * Math.sin(angle);
            yPoints[i] = tempX * Math.sin(angle) + tempY * Math.cos(angle);

            // translate the point to center back to the shape's original center
            xPoints[i] += translation.getX();
            yPoints[i] += translation.getY();
        }

        updateAABB();
    }

    /**
     * Generates the points of the polygon by
     * generating points of a regular polygon
     * then varying the radius for each point.
     *
     * @param nSides Number of sides of the polygon.
     * @param radius The average radius (from polygon center to each point).
     */
    private void genPoints(int nSides, double radius) {

        xPoints = new double[nSides];
        yPoints = new double[nSides];

        double theta_increment = (2.0 * Math.PI) / (double) nSides;

        for (int i = 0; i < nSides; i++) {
            double angle = theta_increment * i;
            double rad = radius - rand.nextDouble(radius * 0.75);

            xPoints[i] = rad * Math.cos(angle);
            yPoints[i] = rad * Math.sin(angle);
        }
        updateAABB();
    }

    /**
     * Updates the bounding box of the polygon,
     * called when ever there is a change to the points
     * (i.e. rotation, translation, or new points).
     */
    private void updateAABB() {
        // contains two arrays, one for x points and another for y points
        AABB = new double[2][2];
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

        // top-right point
        AABB[0][1] = xMax;
        AABB[1][1] = yMax;

        // bottom-left point
        AABB[0][0] = xMin;
        AABB[1][0] = yMin;
    }

    /**
     * Checks whether the given point lies within the bounding box.
     *
     * @param point The 2D point to check against.
     * @return True if there is a collision between the point and bounding box, else false.
     */
    private boolean AABBPointCollision(Vector2 point) {

        boolean collides = AABB[0][0] <= point.getX() && point.getX() <= AABB[0][1];
        collides = collides && AABB[1][0] <= point.getY() && point.getY() <= AABB[1][1];

        return collides;
    }

    /**
     * First checks collision against bounding box and if that returns true,
     * it checks whether the point is inside the polygon by checking number
     * of intersections of an infinite horizontal line (going to the right)
     * and each edge of the polygon.
     *
     * @param point The point to check collision against.
     * @return True if point's inside polygon, else false.
     */
    public boolean collides(Vector2 point) {

        if (!AABBPointCollision(point)) {
            return false;
        }

        double px = point.getX();
        double py = point.getY();

        int intersections = 0;

        for (int index = 0; index < xPoints.length; index++) {

            int nextIndex = (index + 1) % xPoints.length;

            double x1 = xPoints[index];
            double y1 = yPoints[index];

            double x2 = xPoints[nextIndex];
            double y2 = yPoints[nextIndex];

            if (y1 > py != py < y2) {

                double xIntersect = x1 + (py - y1) * (x2 - x1) / (y2 - y1);

                if (px <= xIntersect)
                    intersections++;
            }
        }

        return intersections % 2 != 0;
    }

    public void drawFill(GraphicsContext gc, Color col) {
        gc.setFill(col);
        gc.fillPolygon(xPoints, yPoints, xPoints.length);
    }

    public void drawStroke(GraphicsContext gc, Color col) {
        gc.setStroke(col);
        gc.strokePolygon(xPoints, yPoints, xPoints.length);
    }

    public void createIsoscelesTriangle(double base, double height) {

        this.xPoints = new double[]{-height / 2, -height / 2, height / 2};
        this.yPoints = new double[]{base / 2, -base / 2, 0};
    }

    /**
     * Returns the translation (position) of the origin
     * @return Translation
     */
    public Vector2 getPosition() {
        return translation;
    }
}
