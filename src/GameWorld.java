import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

public class GameWorld {

    ArrayList<Bullet> bullets;
    ArrayList<Obstacle> asteroids;
    Spaceship player;
    Score score;

    private static final int PADDING = 30;

    private boolean shotsFired = false;

    private static double WIDTH;
    private static double HEIGHT;

    public GameWorld(double width, double height) {
        player = new Spaceship(new Vector2(width / 2, height / 2));
        score = new Score();
        bullets = new ArrayList<>();
        asteroids = new ArrayList<>();
        WIDTH = width;
        HEIGHT = height;
        asteroids.add(new Obstacle(AsteroidType.LARGE, new Vector2(3, 3), new Vector2(100, 100)));
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
            b.update(-PADDING, -PADDING, (int) WIDTH+PADDING, (int) HEIGHT+PADDING);
        }

        // Remove 'dead' bullets
        bullets.removeIf(bullet -> !bullet.isAlive());

        for (Obstacle ob : asteroids) {
            ob.update(-PADDING, -PADDING, (int) WIDTH+PADDING, (int) HEIGHT+PADDING);
        }

        player.move(-PADDING, -PADDING, (int) WIDTH+PADDING, (int) HEIGHT+PADDING);

        collisionResolution();
    }

    public void collisionResolution() {

        ArrayList<Obstacle> newAsteroids = new ArrayList<>();

        for (Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
            Bullet b = it.next();
            Vector2 bulletPos = b.getPos();
            Vector2 bulletVel = b.getVel();

            for (Iterator<Obstacle> it1 = asteroids.iterator(); it1.hasNext();) {
                Obstacle ob = it1.next();

                if (ob.bulletCollision(bulletPos)) {
                    Obstacle[] newObjects = ob.spawnAsteroids(bulletVel);
                    if (newObjects != null)
                        newAsteroids.addAll(Arrays.asList(newObjects));
                    it1.remove();
                    b.setDead();
                    break;
                }
            }
            if (!b.isAlive())
                it.remove();
        }
        asteroids.addAll(newAsteroids);
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

                    if (!shotsFired) {
                        shotsFired = true;
                        Vector2 pos = player.getShape().getPosition();
                        bullets.add(new Bullet(pos.getX(), pos.getY(), 0, player.getRotation()));
                    }
                }
            }
        }
        if (!keys.contains(KeyCode.SPACE)) {
            shotsFired = false;
        }
    }
}
