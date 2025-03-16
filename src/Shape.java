import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Shape {

    private double[] xPoints;
    private double[] yPoints;
    private AABB aabb; // Axis aligned bounding box - max & min x,y [ border of vector graphic ]
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
    public Shape(int nSides, double radius) {
        aabb = new AABB();
        genPoints(nSides, radius);
        translation = new Vector2();
    }

    public Shape(double[] xPoints, double[] yPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        aabb = new AABB();
        aabb.updateAABB(this.xPoints, this.yPoints);
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
        aabb.updateAABB(xPoints, yPoints);
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

        aabb.updateAABB(xPoints, yPoints);
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
        aabb.updateAABB(xPoints, yPoints);
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

        if (!aabb.checkPointCollision(point)) {
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

    public boolean polygonPolygonCollision(Shape shape) {

        if (!aabb.checkAABBCollision(shape.getAABB())) {
            return false;
        }

        return true;
    }

    public void drawFill(GraphicsContext gc, Color col) {
        gc.setFill(col);
        gc.fillPolygon(xPoints, yPoints, xPoints.length);
    }

    public void drawStroke(GraphicsContext gc, Color col) {
        gc.setStroke(col);
        gc.strokePolygon(xPoints, yPoints, xPoints.length);
    }

    /**
     * Returns the translation (position) of the polygon center
     * as a copy of the original translation.
     *
     * @return Vector2 representing translation of the shape;
     */
    public Vector2 getPosition() {
        return translation.copy();
    }

    public AABB getAABB() {
        return aabb;
    }
}
