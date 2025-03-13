import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Random;

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



    private Spaceship spaceship;
    private Canvas canvas;  // The game canvas
    private GraphicsContext graphicsContext;


    /**
     * Starting code - once the game is run, the method is called
     */
    @Override
    public void start(Stage stage){
        Pane root = new Pane();

        // Create Canvas and get Graphics Context
        this.canvas = new Canvas(600, 600);
        graphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);


        spaceship = new Spaceship(new Vector2(canvas.getWidth() / 2, canvas.getHeight() / 2));

        this.stage = stage;

        makeMenuBar(root);

        Scene scene = new Scene(root, 600, 600);

        statusLabel = new Label(VERSION);

        Pane contentPane = new BorderPane(null, null, null, null, null);
        root.getChildren().add(contentPane);

        // Handle keyboard input
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode()));

        // Animation loop to move spaceship
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                spaceship.move();
                drawCanvas();
            }
        };
        gameLoop.start();


        stage.setTitle("Asteroidz");
        stage.setScene(scene);
        stage.show();

        AnimationTimer gameloop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                gc.setFill(Color.BLUE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            }
        };
        gameloop.start();
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
        alert.setTitle("About Software");
        alert.setHeaderText("Asteroidz");
        alert.setContentText("A game based on the Atari Classic \n" + VERSION +
                "\n\n Developed by Janit, Dmitrij & Aria");

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

        MenuItem aboutItem = new MenuItem("About Software");
        aboutItem.setOnAction(this::aboutAction);

        helpMenu.getItems().addAll(aboutItem); // Adds to menu segment [ Help ]
        menubar.getMenus().addAll(fileMenu, helpMenu); // Ads to menu segment [ Menu ]
    }


        private void handleKeyPress(KeyCode code) {
            switch (code) {
                case UP:
                    spaceship.accelerate(0.1);
                    break;
                case DOWN:
//                    spaceship.setVelocity(0, speed);
                    break;
                case LEFT:
                    spaceship.rotateLeft();
                    break;
                case RIGHT:
                    spaceship.rotateRight();
                    break;
                default:
                    break;
            }
        }

        private void drawCanvas() {
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());  // Clear screen
            spaceship.draw(graphicsContext); // Draw Spaceship
        }
}
