import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class aims to initalise the menu - and all parameters about it
 * The menubar is created here
 */
public class Main extends Application{

    // THIS IS FOR DEBUG ONLY - CHANGE AS YOU UPDATE, ie: 0.10V -> 0.11V
    // Please update the GitHub readme once you change it
    private static final String VERSION = "0.11V"; //
    private Label statusLabel;
    private Stage stage;

    // Background Attributes
    private int backgroundDetail;
    private Random rand = new Random();
    private int mapSeed; // Random map seed
    private double[][] stars;


    private menuBar menuBar;
    private AtomicBoolean pause;

    private final Set<KeyCode> pressedKeys = new HashSet<>();

    private Canvas canvas;  // The game canvas
    private GraphicsContext gc;


    /**
     * Starting code - once the game is run, the method is called
     */
    @Override
    public void start(Stage stage){
        this.stage = stage;
        MainMenu mainMenu = new MainMenu(stage, this);
        menuBar = new menuBar(VERSION);

        // Handling of the background
        backgroundDetail = 50; // The number of stars in the background
        mapSeed = rand.nextInt(1,50); // Generates a Background Seed

        Scene menuScene = mainMenu.createScene();

        stage.setTitle("Asteroidz");
        stage.setScene(menuScene);
        stage.setResizable(false);
        stage.show();

    }

    /**
     * Game Loop within this method
     * @param stage
     * @return
     */
    public Scene getGameScene(Stage stage){
        this.stage = stage;
        Pane root = new VBox();
        menuBar.makeMenuBar(root);

        pause = new AtomicBoolean(false);

        statusLabel = new Label(VERSION);

        Pane contentPane = new BorderPane(null, null, null, null, null);
        root.getChildren().add(contentPane);
        Canvas canvas = new Canvas(600,600);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        contentPane.getChildren().add(canvas);

        stars = generateStars(canvas.getWidth(), canvas.getHeight(), mapSeed);

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

    private AnimationTimer getAnimationTimer(Canvas canvas, GraphicsContext gc) {
        GameWorld game = new GameWorld(canvas.getWidth(), canvas.getHeight());

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!pause.get()) {
                    game.handleKeyPress(pressedKeys);
                    game.update();
                }
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                drawPixelatedStars(gc);
                game.draw(gc);
            }
        };
        return gameLoop;
    }

    private double[][] generateStars(double width, double height, int mapSeed) {

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

    private void drawPixelatedStars(GraphicsContext gc){
        Color[] starColour = {
                Color.rgb(255, 255, 255),
                Color.rgb(200, 15, 15),
                Color.rgb(65, 62, 156),
                Color.rgb(190, 190, 190),
        };

        for (int i = 0; i < stars.length; i++){
            double x = stars[i][0];
            double y = stars[i][1];
            double size = stars[i][2];

            gc.setFill(starColour[rand.nextInt(starColour.length)]);
            gc.fillRect(x, y, size, size);
        }
    }
}
