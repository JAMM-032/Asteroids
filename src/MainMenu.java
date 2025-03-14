import com.sun.jdi.request.MonitorContendedEnteredRequest;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MainMenu extends VBox {

    private Stage stage;
    private Main parent;
    private Canvas canvas;
    private Label startLabel;
    private boolean gameStarted;
    private FadeTransition fade;
    private Polygon spaceshipShape;

    private VBox MaxPayne;

    public MainMenu(Stage stage, Main parent){
        this.stage = stage;
        this.parent = parent;
        gameStarted = false;

        MaxPayne = new VBox();
        MaxPayne.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Asteroidz");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 32px;");

        startLabel = new Label("Press SPACE to Start");
        startLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        startBlinkingText(); // Calls blinking method

        // Control Text at the bottom of the screen
        Label controlsLabel = new Label("W/S : Thrust & Reverse | A/D : Rotate | Spacebar : Fire");
        controlsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 20px;");

        spaceshipShape = new Polygon(1, 1);
        spaceshipShape.createIsoscelesTriangle(10, 20);
        spaceshipShape.translate(new Vector2(100, 100));
        spaceshipShape.rotate(-Math.PI / 2.0);

        canvas = new Canvas(200,200); // Rendering of spaceship
        canvas.setTranslateY(300);
        drawSpaceship();

        // Layout Settings for the Menu
        setSpacing(20);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black; -fx-padding: 50px;");

        MaxPayne.getChildren().addAll(titleLabel, startLabel, controlsLabel, canvas);
        getChildren().add(MaxPayne);
    }

    private void drawSpaceship(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.WHITE);
        spaceshipShape.drawStroke(gc, Color.WHITE);
    }

    /**
     * Start method of game - switches to the game screen
     */
    private void startGame(){
        Scene gameScene = parent.getGameScene(stage);
        stage.setScene(gameScene);
    }

    private void startBlinkingText(){
        fade = new FadeTransition(Duration.millis(500), startLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.2);
        fade.setCycleCount(FadeTransition.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();
    }


    public Scene createScene(){
        Scene scene = new Scene(this, 600, 600);
        scene.setOnKeyPressed(event -> {
            if (!gameStarted) {
                if (event.getCode() == KeyCode.SPACE) {
                    animateTitleAndStartGame();
                    gameStarted = true;
                    fade.setDuration(Duration.millis(100));
                    fade.setFromValue(1.0);
                    fade.setToValue(0.0);
                    fade.stop();
                    fade.play();
                }
            }
            else if (event.getCode() == KeyCode.ESCAPE){
                System.exit(0);
            }
        });
        return scene;
    }

    private void animateTitleAndStartGame(){
        TranslateTransition spaceshipAnimation = new TranslateTransition(Duration.millis(2000), MaxPayne);
        spaceshipAnimation.setByY(-345); // Moves towards the centre   // Move to -500

        spaceshipAnimation.setOnFinished( event -> startGame() );

        spaceshipAnimation.play();
    }
}