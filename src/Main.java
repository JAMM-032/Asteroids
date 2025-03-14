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
    private static final String VERSION = "0.10V"; //

    private Label statusLabel;
    private Stage stage;

    private final Set<KeyCode> pressedKeys = new HashSet<>();

    private Canvas canvas;  // The game canvas
    private GraphicsContext gc;


    /**
     * Starting code - once the game is run, the method is called
     */
    @Override
    public void start(Stage stage){
        VBox root = new VBox();
        makeMenuBar(root);

        // Create Canvas and get Graphics Context
        this.canvas = new Canvas(600, 500);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        this.stage = stage;

        Scene scene = new Scene(root, 600, 600);

        statusLabel = new Label(VERSION);

        stage.setTitle("Asteroidz");
        stage.setScene(scene);
        stage.show();

        GameWorld game = new GameWorld(canvas.getWidth(), canvas.getHeight());

        // Handle keyboard input
        scene.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode()); // Store pressed key
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());  // Remove released key
        });

        // Animation loop to move spaceship
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
    }

    /**
     * Method to exit the game - will close everything
     */
    private void quitAction(ActionEvent event){
        System.exit(0);
    }

    /**
     * Will display information about the game
     */
    private void aboutAction(ActionEvent event){

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("ElegooTeam");
        alert.setHeaderText("Asteroidz");
        alert.setContentText("A game based on the Atari Classic \n" + VERSION +
                "\n\n Developed by ElegooTeam [Janit, Dmitrij & Aria]");

        alert.showAndWait();
    }

    /**
     * Displays the controls of the game
     */
    private void controls(ActionEvent event){

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Controls");
        alert.setHeaderText("Asteroidz");
        alert.setContentText("The left/right arrow keys cause the ship to rotate. \n" +
                "The up/down arrow keys are for acceleration in the direction the ship faces in. \n" +
                "Space is to shoot bullets in the direction the ship faces.");

        alert.showAndWait();
    }

    private void makeMenuBar(Pane parent){

        MenuBar menubar = new MenuBar();
        parent.getChildren().add(menubar);

        Menu fileMenu = new Menu("File");

        MenuItem quitItem = new MenuItem("Quit");
        quitItem.setOnAction(this::quitAction);

        fileMenu.getItems().addAll(quitItem); // Adds to menu segment [ File ]

        Menu helpMenu = new Menu("Help");

        MenuItem aboutItem = new MenuItem("About ElegooTeam");
        aboutItem.setOnAction(this::aboutAction);

        MenuItem controlsItem = new MenuItem("Controls");
        controlsItem.setOnAction(this::controls);

        helpMenu.getItems().addAll(aboutItem, controlsItem); // Adds to menu segment [ Help ]
        menubar.getMenus().addAll(fileMenu, helpMenu); // Ads to menu segment [ Menu ]
    }
}
