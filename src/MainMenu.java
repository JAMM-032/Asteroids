import javafx.animation.FadeTransition;
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


public class MainMenu extends VBox{

    private Stage stage;
    private Main parent;
    private Label titleLabel;
    private Canvas canvas;
    private Label startLabel;
    private Group group;
    private boolean gameStarted;

    private Polygon spaceshipShape;

    private Pane MaxPayne;

    public MainMenu(Stage stage, Main parent){
        this.stage = stage;
        this.parent = parent;
        gameStarted = false;

        Group group = new Group();

        MaxPayne = new Pane();

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
        spaceshipShape.translate(new Vector2(100, 100));
        spaceshipShape.rotate(-Math.PI / 2.0);

        canvas = new Canvas(200,200); // Rendering of spaceship
        canvas.setTranslateY(500);
        drawSpaceship();

        group = new Group();
        group.getChildren().addAll(titleLabel, controlsLabel, startLabel);

        // Layout Settings for the Menu
        setSpacing(20);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black; -fx-padding: 50px;");

        getChildren().addAll(titleLabel, startLabel, controlsLabel, canvas);
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
        TranslateTransition titleAnimation = new TranslateTransition(Duration.millis(1000), group);
        titleAnimation.setByY(-500);

        TranslateTransition spaceshipAnimation = new TranslateTransition(Duration.millis(1500), canvas);
        //spaceshipAnimation.setByY(-300); // Starts from under the canvas // Moves by (-300)
        spaceshipAnimation.setToY(-60); // Moves towards the centre   // Move to -500

        spaceshipAnimation.setOnFinished( event -> startGame() );

        //titleAnimation.play();
        spaceshipAnimation.play();
    }
}