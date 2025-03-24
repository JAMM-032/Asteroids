import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

/**
 * Score class manages the player's score and multiplier system.
 */
public class Score {
    private static final int MAX_MULTIPLIER = 3;
    private static final int INITIAL_MULTIPLIER_TTL = 300;
    private static final int BAR_WIDTH = 90;
    private static final int BAR_HEIGHT = 12;
    private static final int TEXT_OFFSET = 40;

    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 600;
    private static final int X_OFFSET = 65;
    private static final int Y_OFFSET = 15;

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
     * Initializes the multiplier time bar and outline.
     */
    private void initializeMultiplierBar() {
        // Outline remains constant
        multiplierOutline = new Shape(
                new double[]{BAR_WIDTH / 2, BAR_WIDTH / 2, -BAR_WIDTH / 2, -BAR_WIDTH / 2},
                new double[]{BAR_HEIGHT / 2, -BAR_HEIGHT / 2, -BAR_HEIGHT / 2, BAR_HEIGHT / 2}
        );
        multiplierOutline.translate(new Vector2(CANVAS_WIDTH-X_OFFSET, Y_OFFSET + BAR_HEIGHT));

        // Multiplier bar (changes dynamically)
        multiplierBar = new Shape(
                new double[]{BAR_WIDTH / 2, BAR_WIDTH / 2, 0, 0},
                new double[]{BAR_HEIGHT / 2, -BAR_HEIGHT / 2, -BAR_HEIGHT / 2, BAR_HEIGHT / 2}
        );
        multiplierBar.translate(new Vector2(CANVAS_WIDTH-X_OFFSET - BAR_WIDTH / 2, Y_OFFSET + BAR_HEIGHT));
    }



    /**
     * Increases the score with the current multiplier.
     *
     * @param scoreIncrease Base score to increase
     */
    public void increase(double scoreIncrease) {
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
        multiplierTTL = 0;
    }

    /**
     * Updates the multiplier's time-to-live (TTL).
     * If TTL expires, resets the multiplier.
     */
    public void update() {
        if (multiplierTTL > 0) {
            --multiplierTTL;
            if (multiplierTTL <= 0) {
                resetMultiplier();
            }
        }
    }

    /**
     * Updates the multiplier bar's size based on the multiplier value.
     */
    private void updateMultiplierBar() {
        double widthScale = (double) multiplierTTL / (double) INITIAL_MULTIPLIER_TTL;
        double[] xPoints = {BAR_WIDTH * widthScale, BAR_WIDTH * widthScale, 0, 0};
        double[] yPoints = {BAR_HEIGHT / 2, -BAR_HEIGHT / 2, -BAR_HEIGHT / 2, BAR_HEIGHT / 2};

        multiplierBar = new Shape(xPoints, yPoints);
        multiplierBar.translate(new Vector2(CANVAS_WIDTH-X_OFFSET - BAR_WIDTH / 2, Y_OFFSET + BAR_HEIGHT));
    }

    /**
     * Renders the score, multiplier bar, and associated text.
     *
     * @param gc The GraphicsContext to draw on
     */
    public void draw(GraphicsContext gc) {
        updateMultiplierBar();

        // Draw multiplier bar & outline
        multiplierOutline.drawStroke(gc, Color.WHITE);
        multiplierBar.drawFill(gc, Color.WHITE);

        // Draw multiplier value below the bar
        drawMultiplierLabel(gc);

        // Draw Score
        gc.setFont(Font.font("Courier New",20));
        gc.setFill(Color.WHITE);
        String scoreText = "Score: " + score;
        double textWidth = gc.getFont().getSize() * scoreText.length() * 0.5;
        //gc.fillText(String.valueOf(score), 35, 20);
        gc.fillText(scoreText, (CANVAS_WIDTH - textWidth) / 2, 30);
    }

    /**
     * Draws number of time after which multiplier resets
     */
    private void drawMultiplierLabel(GraphicsContext gc) {
        double textY = Y_OFFSET + BAR_HEIGHT + TEXT_OFFSET; // Position below the bar

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Courier New", 15));
        gc.fillText(String.format("%.2f", multiplier), CANVAS_WIDTH-X_OFFSET, textY);
    }

    public int getScore() {
        return score;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
