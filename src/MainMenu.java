import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;


public class MainMenu extends VBox{

    private Stage stage;
    private Main parent;
    private Label titleLabel;
    private Label startLabel;

    public MainMenu(Stage stage, Main parent){
        this.stage = stage;
        this.parent = parent;

        Label titleLabel = new Label("Asteroidz");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 32px;");

        startLabel = new Label("Press SPACE to Start");
        startLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        startBlickingText(); // Calls blinking method

        // Control Text at the bottom of the screen
        Label controlsLabel = new Label("W / S : Thrust & Reverse | A / D : Rotate | Spacebar : Fire");
        controlsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 20px;");


        // Layout Settings for the Menu
        setSpacing(20);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black; -fx-padding: 50px;");

        getChildren().addAll(titleLabel, startLabel, controlsLabel);
    }

    /**
     * Start method of game - switches to the game screen
     */
    private void startGame(){
        Scene gameScene = parent.getGameScene(stage);
        stage.setScene(gameScene);
    }

    private void startBlickingText(){
        FadeTransition fade = new FadeTransition(Duration.millis(500), startLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.2);
        fade.setCycleCount(FadeTransition.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();
    }


    public Scene createScene(){
        Scene scene = new Scene(this, 600, 600);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE){
                animateTitleAndStartGame();
            }
            else if (event.getCode() == KeyCode.ESCAPE){
                System.exit(0);
            }
        });
        return scene;
    }

    private void animateTitleAndStartGame(){
        TranslateTransition titleAnimation = new TranslateTransition(Duration.millis(1000), titleLabel);
        titleAnimation.setByY(-1);
        titleAnimation.setOnFinished(event -> {
            Scene gameScene = parent.getGameScene(stage);
            stage.setScene(gameScene);
        });
        titleAnimation.play();
    }
}