import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MainMenu extends VBox{

    private Stage stage;
    private Main parent;
    private Label titleLabel;
    private Canvas canvas;
    private Label startLabel;
    private boolean gameStarted;

    private Polygon spaceshipShape;

    public MainMenu(Stage stage, Main parent){
        this.stage = stage;
        this.parent = parent;
        gameStarted = false;

        Label titleLabel = new Label("Asteroidz");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 32px;");

        startLabel = new Label("Press SPACE to Start");
        startLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        startBlinkingText(); // Calls blinking method

        // Control Text at the bottom of the screen
        Label controlsLabel = new Label("W / S : Thrust & Reverse |A / D : Rotate |Spacebar : Fire");
        controlsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 20px;");

        spaceshipShape = new Polygon(1, 1);
        spaceshipShape.createIsoscelesTriangle(10, 20);

        canvas = new Canvas(200,200); // Rendering of spaceship
        drawSpaceship();

        // Layout Settings for the Menu
        setSpacing(20);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black; -fx-padding: 50px;");

        getChildren().addAll(titleLabel, startLabel, controlsLabel);
    }

    private void drawSpaceship(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
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
            if (!gameStarted) {
                if (event.getCode() == KeyCode.SPACE) {
                    animateTitleAndStartGame();
                    gameStarted = true;
                }
            }
            else if (event.getCode() == KeyCode.ESCAPE){
                System.exit(0);
            }
        });
        return scene;
    }

    private void animateTitleAndStartGame(){
        TranslateTransition titleAnimation = new TranslateTransition(Duration.millis(1000), titleLabel);
        titleAnimation.setByY(-50);

        TranslateTransition spaceshipAnimation = new TranslateTransition(Duration.millis(1000), canvas);
        spaceshipAnimation.setByY(300); // Starts from under the canvas
        spaceshipAnimation.setToY(0); // Moves towards the centre

        /*
        titleAnimation.setOnFinished(event -> {
            Scene gameScene = parent.getGameScene(stage);
            stage.setScene(gameScene);
        });
        */
        spaceshipAnimation.setOnFinished( event -> startGame() );

        titleAnimation.play();
        spaceshipAnimation.play();
    }
}