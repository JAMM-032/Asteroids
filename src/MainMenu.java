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

/**
 * The main menu class aims to initialise the menu which
 * greets the user upon starting the game.
 * It has a simple GUI display, and will shift the scene once start is
 * initiated
 */
public class MainMenu extends VBox {

    private Stage stage;
    private Main parent;
    private Canvas canvas;
    private Label startLabel;
    private boolean gameStarted;
    private FadeTransition fade;
    private Shape spaceshipShape;

    private VBox MaxPayne;

    /**
     * Main menu of the game - starting menu - initialised
     *
     * @param stage - the current Stage
     * @param parent - the Parent of the Main class
     */
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

        spaceshipShape = new Shape(
                new double[] {10, -5, -5},
                new double[] {0, 5, -5}
        );
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
        spaceshipShape.drawFill(gc, Color.WHITE);
    }

    /**
     * Start method of game - switches to the game screen
     */
    private void startGame(){
        Scene gameScene = parent.getGameScene(stage);
        stage.setScene(gameScene);
    }

    /**
     * Animation handler for the 'press Start' text
     * plays an automatic cycle
     */
    private void startBlinkingText(){
        fade = new FadeTransition(Duration.millis(500), startLabel);
        fade.setFromValue(1.0);
        fade.setToValue(0.2);
        fade.setCycleCount(FadeTransition.INDEFINITE);
        fade.setAutoReverse(true);
        fade.play();
    }

    /**
     * Creates the scene of the game
     * has an animation timeline should the game be started.
     *
     * @return - returns the Scene of the game after starting
     */
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

    /**
     * Calls for the animation methods upon the call of start method
     */
    private void animateTitleAndStartGame(){
        TranslateTransition spaceshipAnimation = new TranslateTransition(Duration.millis(2000), MaxPayne);
        spaceshipAnimation.setByY(-345); // Moves towards the centre   // Move to -500

        spaceshipAnimation.setOnFinished( event -> startGame() );

        spaceshipAnimation.play();
    }
}