import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Score class manages the player's score and multiplier system.
 */
public class Score {
    private static final int MAX_MULTIPLIER = 3;
    private static final int INITIAL_MULTIPLIER_TTL = 300;
    private static final int BAR_WIDTH = 60;
    private static final int BAR_HEIGHT = 8;
    private static final int TEXT_OFFSET = 20;

    private int multiplierTTL; // Time after which multiplier resets
    private int score;
    private double multiplier;
    private Shape multiplierBar;
    private Shape multiplierOutline;

    public Score() {
        score = 0;
        multiplier = 1.0;
        multiplierTTL = INITIAL_MULTIPLIER_TTL;

        initializeMultiplierBar();
    }

    /**
     * Initializes the multiplier bar and outline.
     */
    private void initializeMultiplierBar() {
        // Outline remains constant
        multiplierOutline = new Shape(
                new double[]{BAR_WIDTH / 2, BAR_WIDTH / 2, -BAR_WIDTH / 2, -BAR_WIDTH / 2},
                new double[]{BAR_HEIGHT / 2, -BAR_HEIGHT / 2, -BAR_HEIGHT / 2, BAR_HEIGHT / 2}
        );
        multiplierOutline.translate(new Vector2(550, 10 + BAR_HEIGHT));

        // Multiplier bar (changes dynamically)
        multiplierBar = new Shape(
                new double[]{BAR_WIDTH / 2, BAR_WIDTH / 2, 0, 0},
                new double[]{BAR_HEIGHT / 2, -BAR_HEIGHT / 2, -BAR_HEIGHT / 2, BAR_HEIGHT / 2}
        );
        multiplierBar.translate(new Vector2(550 - BAR_WIDTH / 2, 10 + BAR_HEIGHT));
    }

    /**
     * Increases the score with the current multiplier.
     *
     * @param scoreIncrease Base score to increase
     */
    public void increase(int scoreIncrease) {
        score += (int) (scoreIncrease * multiplier);
    }

    /**
     * Increments the multiplier, capping at MAX_MULTIPLIER.
     */
    public void incrementMultiplier() {
        if (multiplier < MAX_MULTIPLIER) {
            multiplier += 0.1;
        }
        multiplierTTL = INITIAL_MULTIPLIER_TTL;
    }

    /**
     * Resets the multiplier to its base value.
     */
    public void resetMultiplier() {
        multiplier = 1.0;
        multiplierTTL = INITIAL_MULTIPLIER_TTL;
    }

    /**
     * Updates the multiplier's time-to-live (TTL).
     * If TTL expires, resets the multiplier.
     */
    public void update() {
        if (--multiplierTTL <= 0) {
            resetMultiplier();
        }
    }

    /**
     * Updates the multiplier bar's size based on the multiplier value.
     */
    private void updateBar() {
        double widthScale = (multiplier - 1) / (MAX_MULTIPLIER - 1);
        double[] xPoints = {BAR_WIDTH * widthScale, BAR_WIDTH * widthScale, 0, 0};
        double[] yPoints = {BAR_HEIGHT / 2, -BAR_HEIGHT / 2, -BAR_HEIGHT / 2, BAR_HEIGHT / 2};

        multiplierBar = new Shape(xPoints, yPoints);
        multiplierBar.translate(new Vector2(550 - BAR_WIDTH / 2, 10 + BAR_HEIGHT));
    }

    /**
     * Renders the score, multiplier bar, and associated text.
     *
     * @param gc The GraphicsContext to draw on
     */
    public void draw(GraphicsContext gc) {
        updateBar();

        // Draw multiplier bar & outline
        multiplierOutline.drawStroke(gc, Color.WHITE);
        multiplierBar.drawFill(gc, Color.WHITE);

        // Draw multiplier numbers below the bar
        drawMultiplierLabels(gc);

        // Draw Score
        gc.setFont(new javafx.scene.text.Font(15));
        gc.fillText("Score: " + score, 35, 20);
    }

    /**
     * Draws numbers below the multiplier bar (1, MAX_MULTIPLIER, and current value).
     */
    private void drawMultiplierLabels(GraphicsContext gc) {
        double leftX = 550 - BAR_WIDTH / 2;   // Left bar edge
        double rightX = 550 + BAR_WIDTH / 2;  // Right bar edge
        double centerX = 550;                 // Bar center
        double textY = 10 + BAR_HEIGHT + TEXT_OFFSET; // Position below the bar

        gc.setFill(Color.WHITE);
        gc.setFont(new javafx.scene.text.Font(10));

        gc.fillText("1", leftX, textY);
        gc.fillText(String.valueOf(MAX_MULTIPLIER), rightX, textY);
        gc.fillText(String.format("%.2f", multiplier), centerX, textY);
    }

    public int getScore() {
        return score;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
