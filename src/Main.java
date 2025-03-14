import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

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

    private menuBar menuBar;

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

        Scene menuScene = mainMenu.createScene();

        stage.setTitle("Asteroidz");
        stage.setScene(menuScene);
        stage.setResizable(false);
        stage.show();

    }

    public Scene getGameScene(Stage stage){
        this.stage = stage;
        Pane root = new VBox();
        menuBar.makeMenuBar(root);

        statusLabel = new Label(VERSION);

        Pane contentPane = new BorderPane(null, null, null, null, null);
        root.getChildren().add(contentPane);
        Canvas canvas = new Canvas(600,600);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        contentPane.getChildren().add(canvas);

        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Asteroidz - Game Session Active");

        scene.setOnKeyPressed(e -> {
            pressedKeys.add(e.getCode());
        });
        scene.setOnKeyReleased(e -> {
            pressedKeys.remove(e.getCode());
        });

        GameWorld game = new GameWorld(canvas.getWidth(), canvas.getHeight());

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.handleKeyPress(pressedKeys);
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                game.update();
                game.draw(gc);
            }
        };

        gameLoop.start();
        return scene;
    }
}
