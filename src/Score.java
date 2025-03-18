import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;

public class Score {
    private final int MAX_MULTIPLIER = 3;
    private int score;
    private double multiplier;
    private Shape multiplierBar;
    private Shape multiplierOutline;
    private int BAR_WIDTH = 60;
    private int BAR_HEIGHT = 10;


    public Score() {
        score = 0;
        multiplier = 1.1;


        multiplierOutline = new Shape(
                new double[]{BAR_WIDTH/2, BAR_WIDTH/2,-BAR_WIDTH/2,-BAR_WIDTH/2},
                new double[]{BAR_HEIGHT/2, -BAR_HEIGHT/2,-BAR_HEIGHT/2,BAR_HEIGHT/2}
        );
        multiplierOutline.translate(new Vector2(550, 20)); // Move to top-right corner

        multiplierBar = new Shape(
                new double[]{BAR_WIDTH/2, BAR_WIDTH/2, 0, 0},
                new double[]{BAR_HEIGHT/2, -BAR_HEIGHT/2,-BAR_HEIGHT/2,BAR_HEIGHT/2}
        );
        multiplierBar.translate(new Vector2(550-BAR_WIDTH/2, 20));
    }

    public void increase(int scoreIncrease) {
        score += (int) (scoreIncrease * multiplier);
    }

    public void incrementMultiplier() {
        multiplier = (multiplier >= MAX_MULTIPLIER) ? MAX_MULTIPLIER : multiplier+0.1;
    }

    public void resetMultiplier() {
        multiplier = 1;
    }

    public int getScore() {
        return score;
    }

    public double getMultiplier() {
        return multiplier;
    }

    private void updateBar() {
        double[] xPoints = new double[]{BAR_WIDTH*((multiplier-1)/(MAX_MULTIPLIER-1)), BAR_WIDTH*((multiplier-1)/(MAX_MULTIPLIER-1)), 0, 0};
        double[] yPoints = new double[]{BAR_HEIGHT/2, -BAR_HEIGHT/2,-BAR_HEIGHT/2,BAR_HEIGHT/2};

        multiplierBar = new Shape(xPoints, yPoints);
        multiplierBar.translate(new Vector2(550 - BAR_WIDTH/2, 20));
    }

    public void draw(GraphicsContext gc) {
        updateBar(); // resize the bar according to the multiplier value
        multiplierOutline.drawStroke(gc, Color.WHITE);
        multiplierBar.drawFill(gc, Color.WHITE);

        // Number positions
        double leftX = 550 - BAR_WIDTH / 2;   // Left bar edge
        double rightX = 550 + BAR_WIDTH / 2;  // Right bar edge
        double centerX = 550;                 // Bar center
        double textY = 20 + BAR_HEIGHT + 10;  // Position below the bar

        // Set text color
        gc.setFill(Color.WHITE);
        gc.setFont(new javafx.scene.text.Font(10)); // Set font size

        // Draw numbers
        gc.fillText("1", leftX, textY);
        gc.fillText(String.valueOf(MAX_MULTIPLIER), rightX, textY);
        gc.fillText(String.format("%.1f", multiplier), centerX, textY); // draw current multiplier value
    }
}
