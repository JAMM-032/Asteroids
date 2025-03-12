import java.util.Arrays;

public class Vector2 {

    private double x;
    private double y;

    public Vector2() {
        x = 0.0;
        y = 0.0;
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(double[] values) {
        x = values[0];
        y = values[1];
    }

    public void translate(Vector2 v) {

        this.x += v.getX();
        this.y += v.getY();

    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public Vector2 negate() {
        return new Vector2(-x, -y);
    }
}
