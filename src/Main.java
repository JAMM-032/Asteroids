import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class aims to initalise the menu - and all parameters about it
 * The menubar is created here
 */
public class Main extends Application{

    private Stage stage;

    private final AudioPlayer player = new AudioPlayer();

    // Background Attributes
    private int backgroundDetail;
    private Random rand = new Random();
    private double[][] stars;

    private menuBar menuBar;
    private AtomicBoolean pause;

    private final Set<KeyCode> pressedKeys = new HashSet<>();

    float deltaTime;
    private long prevTime = 0;

    /**
     * Starting code - once the game is run, the method is called
     */
    @Override
    public void start(Stage stage){
        this.stage = stage;
        MainMenu mainMenu = new MainMenu(stage, this);
        menuBar = new menuBar();

        // Handling of the background
        backgroundDetail = 50; // The number of stars in the background

        Scene menuScene = mainMenu.createScene();

        player.playLoop(new File("audio/atomic_amnesia.wav").getAbsolutePath());

        stage.setTitle("Asteroidz");
        stage.setScene(menuScene);
        stage.setResizable(false);
        stage.show();

    }

    /**
     * Game Loop within this method
     * @param stage - Stage of the game is parsed in for use
     * @return - Returns the Scene after processing - generates a scene
     */
    public Scene getGameScene(Stage stage){
        this.stage = stage;
        Pane root = new VBox();
        menuBar.makeMenuBar(root);

        pause = new AtomicBoolean(false);

        Pane contentPane = new BorderPane(null, null, null, null, null);
        root.getChildren().add(contentPane);
        Canvas canvas = new Canvas(600,600);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        contentPane.getChildren().add(canvas);

        stars = generateStars(canvas.getWidth(), canvas.getHeight());

        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Asteroidz - Game Session Active");

        scene.setOnKeyPressed(e -> {
            pressedKeys.add(e.getCode());
            if (e.getCode() == KeyCode.P)
                pause.set(!pause.get());
        });
        scene.setOnKeyReleased(e -> {
            pressedKeys.remove(e.getCode());
        });

        AnimationTimer gameLoop = getAnimationTimer(canvas, gc);

        gameLoop.start(); // Game loop called
        return scene;
    }

    /**
     * Has a timeline for animations to be handled
     *
     * @param canvas - current canvas given with positions - allows for modularised approach
     * @param gc - graphics context given - parses current screen's information
     * @return - returns the AnimationTimer Type - handled by the animation handler
     */
    private AnimationTimer getAnimationTimer(Canvas canvas, GraphicsContext gc) {
        GameWorld game = new GameWorld(canvas.getWidth(), canvas.getHeight());

        gc.setTextAlign(TextAlignment.CENTER);

        Font pauseFont = new Font(30);

        prevTime = System.nanoTime();

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {

                long diff = now - prevTime;
                prevTime = now;
                // converts from nanoseconds to seconds.
                deltaTime = diff / 1_000_000_000.0f;

                // get milliseconds between last and current frame
                long timeDiff = diff / 1_000_000;

                // Clear the screen
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // Check if the game is over
                if (game.isGameOver()) {
                    // Stop the game loop
                    this.stop();
                    // Display game over stats
                    game.displayStats(gc);

                    // Wait for the restart key (ENTER)
                    stage.getScene().setOnKeyPressed(e -> {
                        if (e.getCode() == KeyCode.ENTER) {
                            restartGame(stage);
                        }
                    });

                    // Return to prevent drawing
                    return;
                }

                // Game logic for drawing objects (if not game over)

                // Display pause screen
                if (pause.get()) {
                    gc.setFont(pauseFont);
                    gc.setFill(Color.WHITE);
                    gc.fillText("PAUSED", canvas.getWidth()/2, canvas.getHeight()/2);
                }
                else {
                    game.handleKeyPress(pressedKeys, deltaTime);
                    game.update(deltaTime, timeDiff);
                }

                // Draw background stars
                drawPixelatedStars(gc);
                game.draw(gc);
            }
        };
        return gameLoop;
    }

    /**
     * Generates the background imagery for the game
     * Each star is animated - allows for a more immersive experience
     *
     * @param width - Width of the current canvas
     * @param height - Height of the current canvas
     * @return - returns a 2D array (type Double) - has the positions of the stars
     */
    private double[][] generateStars(double width, double height) {

        double[][] stars = new double[backgroundDetail][3]; // x, y, seed retrieved

        for (int i =0; i < backgroundDetail; i++) {
            double x = rand.nextDouble() * width;
            double y = rand.nextDouble() * height;
            double size = 2 + rand.nextInt(2); // [ 2, 6 ]

            stars[i][0] = x;
            stars[i][1] = y;
            stars[i][2] = size;
        }
        return stars;
    }

    /**
     * Draws the background stars
     * The stars will oscillate between a fixed pattern - colours listed below
     * Allows for the illusion of 'twinkling'
     * @param gc - Graphics Context parsed in, allowing for the effective rendering
     *           ...of the stars
     */
    private void drawPixelatedStars(GraphicsContext gc){
        Color[] starColour = {
                Color.rgb(255, 255, 255),
                Color.rgb(150, 150, 150),
                Color.rgb(65, 62, 60),
                Color.rgb(20, 20, 20),
                Color.rgb(180, 190, 175)
        };

        for (double[] star : stars) {
            double x = star[0];
            double y = star[1];
            double size = star[2];

            gc.setFill(starColour[rand.nextInt(starColour.length)]);
            gc.fillRect(x, y, size, size);
        }
    }

    /**
     * Restarts the game - equivalent to pressing start
     * @param stage - Stage parsed in
     */
    private void restartGame(Stage stage){
        Scene gameScene = getGameScene(stage);
        stage.setScene(gameScene);
    }
}
