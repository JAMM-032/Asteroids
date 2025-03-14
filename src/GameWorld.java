import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.Set;
import java.util.ArrayList;

public class GameWorld {

    ArrayList<Bullet> bullets;
    ArrayList<Obstacle> asteroids;
    Spaceship player;

    private static final int PADDING = 30;

    private static double WIDTH;
    private static double HEIGHT;

    public GameWorld(double width, double height) {
        player = new Spaceship(new Vector2(width / 2, height / 2));
        bullets = new ArrayList<>();
        asteroids = new ArrayList<>();
        WIDTH = width;
        HEIGHT = height;
    }

    public void draw(GraphicsContext gc) {
        for (Bullet b : bullets) {
            b.draw(gc);
        }

        for (Obstacle ob : asteroids) {
            ob.draw(gc, Color.WHITE);
        }

        player.draw(gc);
    }

    public void update() {
        for (Bullet b : bullets) {
            b.update();
        }

        for (Obstacle ob : asteroids) {
            ob.update();
        }

        player.move(-PADDING, -PADDING, (int) WIDTH+PADDING, (int) HEIGHT+PADDING);
    }

    public void collisionResolution() {

    }

    private boolean collisionDetection() {
        return true;
    }

    public void handleKeyPress(Set<KeyCode> keys) {
        for (KeyCode code : keys) {
            switch (code) {
                case UP, W -> {
                    player.accelerate(0.01);
                }
                case DOWN, S -> {
                    player.accelerate(-0.01);
                }
                case LEFT, A -> {
                    player.rotateLeft();
                }
                case RIGHT, D -> {
                    player.rotateRight();
                }
                case SPACE -> {
//                    System.out.println("Player should have fired a bullet.");
                    player.printPos();
                }
            }
        }
    }
}
