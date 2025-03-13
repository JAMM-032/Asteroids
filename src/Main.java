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
    // Please update the Github readme once you change it
    private static final String VERSION = "0.10V"; //

    private Label statusLabel;
    private Stage stage;


    /**
     * Starting code - once the game is run, the method is called
     */
    @Override
    public void start(Stage stage){

        this.stage = stage;
        Pane root = new VBox();
        makeMenuBar(root);

        statusLabel = new Label(VERSION);


        Pane contentPane = new BorderPane(null, null, null, null, null);
        root.getChildren().add(contentPane);
        Canvas canvas = new Canvas(600, 500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        contentPane.getChildren().add(canvas);

        //showFilename(null);
        Scene scene = new Scene(root, 600, 600);
        //scene.getStylesheets().add("mystyle.css"); // For CSS
        stage.setTitle("Asteroidz"); // Title of the game
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
        alert.setContentText(VERSION + " A game based on the Atari Classic \n" +
                " created by : Aria, Dmitrij & Janit");

        alert.showAndWait();
    }

    private void controlAction(ActionEvent event){

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Controls");
        alert.setHeaderText("Controls");
        alert.setContentText(("W / S : Spaceship Thrust & Reverse \nA / D : Rotation of Spaceship \n" +
                "Spacebar : Fire Button  \n"));

        alert.showAndWait();
    }

    /**
     * This initalises the menu bar - creating the relevant fields
     */
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

        MenuItem controlItem = new MenuItem("Controls");
        controlItem.setOnAction(this::controlAction);

        helpMenu.getItems().addAll(aboutItem, controlItem); // Adds to menu segment [ Help ]
        menubar.getMenus().addAll(fileMenu, helpMenu); // Ads to menu segment [ Menu ]


    }

}
