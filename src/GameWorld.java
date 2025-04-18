import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A class to initalise the GameWorld - will create the basic canvas
 * The padding serves as an intermediary between the active game screen
 * ...(what the player sees) and the passive game screen (what is hidden)
 * This allows for the seamless transition from one side to the other for
 * the player and the obstacles
 */
public class GameWorld {

    private ArrayList<Bullet> bullets;
    private ArrayList<Entity> asteroids;
    private Spaceship player;
    private Score score;
    private GameStats stats;
    private boolean gameOver = false;

    private static final int PADDING = 30;

    private boolean shotsFired = false;

    private final double WIDTH;
    private final double HEIGHT;
    private final Random rand = new Random();

    private static final double ASTEROID_PROB = 0.7;
    private static final double SHIP_PROB = 0.3;

    // frequency in seconds of obstacle spawning
    private static final int MAX_DURATION = 10 * 1000;

    private static final int MAX_ASTEROIDS = 20;
    private static final int MAX_BULLETS = 6;

    private long timeDur;
    private long totalTime = 0;

    private static final String[] EDGES = {
        "LEFT", "RIGHT", "TOP", "BOTTOM"
    };

    /**
     * Constructor class - initalises the general statistics of the game
     * @param width - width of the canvas - this information is parsed into other classes
     * @param height - height of the canvas - this information is parsed into other classes
     */
    public GameWorld(double width, double height) {
        player = new Spaceship(new Vector2(width / 2, height / 2));
        score = new Score();
        bullets = new ArrayList<>();
        asteroids = new ArrayList<>();
        stats = new GameStats();
        WIDTH = width;
        HEIGHT = height;
        timeDur = MAX_DURATION;
    }

    /**
     * A method to draw the active items - items which have animations / move in the game
     * Of these involve the bullet - fired by the player
     * the asteroids - spawn as an antagonist to the player
     * Collision resolution is also called from this class
     *
     * @param gc - graphics context of the current (given) scene
     */
    public void draw(GraphicsContext gc) {
        for (Bullet b : bullets) {
            b.draw(gc);
        }

        for (Entity ob : asteroids) {
            ob.draw(gc, Color.WHITE);
        }

        player.draw(gc);
        score.draw(gc);
    }

    /**
     * Calls for the updating of the display
     * Changes the current scenery, updating to the (new) positions
     * @param timeDiff - Time in (1x10^-9) nanoseconds, between the current & last
     *               ... frame finishing & starting {respectively}
     */
    public void update(float dt, long timeDiff) {

        totalTime += timeDiff;

        for (Bullet b : bullets) {
            b.update(-PADDING, -PADDING, (int) WIDTH+PADDING, (int) HEIGHT+PADDING, dt, timeDiff);
        }

        // Remove 'dead' bullets
        bullets.removeIf(bullet -> !bullet.isAlive());

        for (Entity ob : asteroids) {
            ob.update(-PADDING, -PADDING, (int) WIDTH+PADDING, (int) HEIGHT+PADDING, dt);
        }

        player.move(-PADDING, -PADDING, (int) WIDTH+PADDING, (int) HEIGHT+PADDING, dt);

        collisionResolution();

        score.update(timeDiff); // resets multiplier after some time

        /*
        Player collision is handled here
        If a collision occurs, the player will be hurt
         */
        for (Entity ob : asteroids) {
            if (player.getShape().polygonPolygonCollision(ob.getShape())) {
                gameOver = true;

                // Record final game stats
                stats.setScore(score.getScore());
                stats.setTime(totalTime);

                break;
            }
        }
        spawnObstacles(timeDiff);
    }

    /**
     * Boolean to check if the game has been lost
     * @return - if True, the game will end, as the player has no more lives / is dead
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Method to check for collisions
     * Calls for every bullet - if an asteroid is in proximity / in contact
     * then the asteroid will be called for its destruction
     */
    public void collisionResolution() {

        ArrayList<Entity> newAsteroids = new ArrayList<>();

        for (Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
            Bullet b = it.next();
            Vector2 bulletPos = b.getPos();
            Vector2 bulletVel = b.getVel();

            for (Iterator<Entity> it1 = asteroids.iterator(); it1.hasNext();) {
                Entity ob = it1.next();

                if (ob.pointCollision(bulletPos)) {
                    Entity[] newObjects = ob.spawnObstacles(bulletVel);
                    if (newObjects != null)
                        newAsteroids.addAll(Arrays.asList(newObjects));

                    // Add score and increment multiplier
                    score.increase(ob.getScoreValue());
                    score.incrementMultiplier();

                    // Save Max reached multiplier
                    stats.saveMaxMultiplier(score.getMultiplier());

                    // Determine type of object destroyed and increment suitable field in GameStats object
                    if (ob instanceof AlienShip) {
                        stats.incrementSpaceshipCount();
                    } else {
                        stats.incrementAsteroidCount();
                    }

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

    /**
     * Handler for the key presses by the user
     * Will handle user input smoothly
     * @param keys - the current input of the user
     */
    public void handleKeyPress(Set<KeyCode> keys, float deltaTime) {
        for (KeyCode code : keys) {
            switch (code) {
                case UP, W -> {
                    player.accelerate(75.0);
                }
                case DOWN, S -> {
                    player.accelerate(-40.0);
                }
                case LEFT, A -> {
                    player.rotateLeft(deltaTime);
                }
                case RIGHT, D -> {
                    player.rotateRight(deltaTime);
                }
                case SPACE -> {
                    if (!shotsFired && bullets.size() < MAX_BULLETS) {
                        shotsFired = true;
                        bullets.add(player.fire());
                    }
                }
            }
        }
        if (!keys.contains(KeyCode.SPACE)) {
            shotsFired = false;
        }
    }

    /**
     * Spawns the obstaclesof whic hthe player would face, these involve
     * ... - Asteroids [ Small, Medium & Large ]
     *     - Spaceships [ Drone Class, Mothership Class Spaceships ]
     * @param timeDiff - Time in (1x10^-9) nanoseconds, between the current & last
     *               ... frame finishing & starting {respectively}
     */
    private void spawnObstacles(long timeDiff) {
        timeDur += timeDiff;

        if (timeDur < MAX_DURATION || asteroids.size() >= MAX_ASTEROIDS)
            return;
        timeDur = 0;

        for (int i = 0; i < 2; i++) {
            double prob = rand.nextDouble();
            Vector2 pos = generateRandomPos();
            if (prob <= SHIP_PROB) {
                asteroids.add(new AlienShip(player.getShape(), pos));
            }
            else if (prob-SHIP_PROB <= ASTEROID_PROB) {
                Vector2 vel = Vector2.fromPolar(AsteroidType.LARGE.getSpeed(), rand.nextDouble(0.0, 2*Math.PI));
                asteroids.add(new Obstacle(AsteroidType.LARGE, vel, pos));
            }
        }
    }

    /**
     * Generates a random position in the map
     * The padding allows for the object to be spawned
     * ... within the constraints of the world
     * @return - Returns the corrected position in the game space
     */
    private Vector2 generateRandomPos() {

        Vector2 pos = new Vector2();
        int index = rand.nextInt(0, 4);

        switch (EDGES[index]) {
            case "LEFT" -> {
                pos.setX(-PADDING);
                pos.setY(rand.nextInt(0, (int) HEIGHT));
            }
            case "RIGHT" -> {
                pos.setX(WIDTH+PADDING);
                pos.setY(rand.nextInt(0, (int) HEIGHT));
            }
            case "TOP" -> {
                pos.setX(rand.nextInt(0, (int) WIDTH));
                pos.setY(-PADDING);
            }
            case "BOTTOM" -> {
                pos.setX(rand.nextInt(0, (int) WIDTH));
                pos.setY(HEIGHT+PADDING);
            }
        }

        return pos;
    }


    /**
     * Triggers the displaying of the statistics menu at the
     * ... end of the player's session
     * @param gc - Graphics Context of the user parsed in
     */
    public void displayStats(GraphicsContext gc) {
        // Reset canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;

        // Menu Box
        double boxWidth = 400;
        double boxHeight = 350;
        double boxX = centerX -(boxWidth / 2);
        double boxY = centerY -(boxHeight / 2);

        double currentYOffset = 40;

        // Display Box
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        gc.strokeRect(boxX, boxY, boxWidth, boxHeight);


        // Set text parameters
        gc.setFont(new Font("Verdana", 30));
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);

        // Game Over Text
        gc.fillText("GAME OVER", centerX, boxY+currentYOffset);

        // Header Line
        currentYOffset+= 20;
        gc.strokeLine(boxX, boxY + currentYOffset, boxX + boxWidth, boxY + currentYOffset);

        // Statistics Text
        currentYOffset+= 30;
        gc.setFont(new Font("Verdana", 20));
        gc.fillText("Statistics", centerX, boxY+currentYOffset);

        // Retrieve Game Statistics
        Map<String, List<String>> statsMap = stats.getFinalStats();

        // Game Stats Text
        gc.setFont(new Font("Verdana", 18));
        int xOffset = 20;

        // Display fields along with values
        for (Map.Entry<String, List<String>> entry : statsMap.entrySet()) {
            List<String> fields = entry.getValue();
            currentYOffset += 35;

            // Ensure we have both elements
            if (fields.size() >= 2) {
                // Display field on the left-side
                gc.setTextAlign(TextAlignment.LEFT);
                gc.fillText(fields.get(0), boxX+xOffset, boxY+currentYOffset);

                // Display value on the right-side
                gc.setTextAlign(TextAlignment.RIGHT);
                gc.fillText(fields.get(1), boxX+boxWidth-xOffset, boxY+currentYOffset);

            }
        }

        // Restart message
        currentYOffset+= 30;
        gc.strokeLine(boxX, boxY + currentYOffset, boxX + boxWidth, boxY + currentYOffset);
        gc.setFont(new Font("Verdana", 16));
        gc.setTextAlign(TextAlignment.CENTER);
        currentYOffset+=25;
        gc.fillText("Press Enter to Play Again...\nCtrl+Q to Quit!", centerX, boxY+currentYOffset);
    }
}
