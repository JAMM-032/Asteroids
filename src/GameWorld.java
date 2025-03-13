import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.Set;
import java.util.ArrayList;

public class GameWorld {

    ArrayList<Bullet> bullets;
    ArrayList<Obstacle> asteroids;
    Spaceship player;

    public GameWorld(double width, double height) {
        player = new Spaceship(new Vector2(width / 2, height / 2));
        bullets = new ArrayList<>();
        asteroids = new ArrayList<>();
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

        player.move();
    }

    public void collisionResolution() {

    }

    private boolean collisionDetection() {
        return true;
    }

    public void handleKeyPress(Set<KeyCode> keys) {
        for (KeyCode code : keys) {
            switch (code) {
                case UP:
                    player.accelerate(0.001);
                    break;
                case DOWN:
                    player.accelerate(-0.001);
                    break;
                case LEFT:
                    player.rotateLeft();
                    break;
                case RIGHT:
                    player.rotateRight();
                    break;
                case SPACE:
                    System.out.println("Player should have fired a bullet.");
                    break;
                default:
                    break;
            }
        }
    }
}
