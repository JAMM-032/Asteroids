import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;

/**
 * A class which manaages the game performance of the player's
 * ... current session - upon losing, the player's statistics will be displayed
 */
public class GameStats {
    private int totalScore;
    private double totalTime;
    private double maxMultiplier = 1.0;

    private int asteroidsDestroyed;
    private int spaceshipsDestroyed;

    private Map<String, List<String>> stats;

    /**
     * Class constructor. Initializes counters for destroyed spaceships and asteroids.
     */
    GameStats() {
        this.asteroidsDestroyed = 0;
        this.spaceshipsDestroyed = 0;
    }

    /**
     * Sets the player's total score.
     * @param score - current player score.
     */
    public void setScore(int score) {
        this.totalScore = score;
    }

    /**
     * Sets the total game duration.
     * @param time - time elapsed since the start of the game in milliseconds.
     */
    public void setTime(double time) {
        this.totalTime = time;
    }

    /**
     * Saves the highest multiplier the player reached by the end of the game.
     * @param multiplier - value of the maximum multiplier (between 1.0 and 3.0).
     */
    public void saveMaxMultiplier(double multiplier) {
        if (multiplier > this.maxMultiplier) {
            this.maxMultiplier = multiplier;
        }
    }

    /**
     * Returns a map storing all player statistics.
     * @return A map containing lists with two strings: display text and value.
     */
    public Map<String, List<String>> getFinalStats() {
        recordFinalStats();
        return this.stats;
    }

    /**
     * Increments the number of destroyed asteroids by 1.
     */
    public void incrementAsteroidCount() {
        this.asteroidsDestroyed++;
    }

    /**
     * Increments the number of destroyed spaceships by 1.
     */
    public void incrementSpaceshipCount() {
        this.spaceshipsDestroyed++;
    }

    /**
     * Populates a LinkedHashMap with game statistics.
     * Each entry contains a list: the first string is the display label, and the second is the value.
     */
    private void recordFinalStats() {
        stats = new LinkedHashMap<>();

        // Record Asteroids Destroyed
        List<String> asteroidStats = new ArrayList<>();
        asteroidStats.add("Asteroids Destroyed");
        asteroidStats.add(String.valueOf(asteroidsDestroyed));
        stats.put("asteroids", asteroidStats);

        // Record Spaceships Destroyed
        List<String> spaceshipsStats = new ArrayList<>();
        spaceshipsStats.add("Spaceships Destroyed");
        spaceshipsStats.add(String.valueOf(spaceshipsDestroyed));
        stats.put("spaceships", spaceshipsStats);

        // Record Max Multiplier
        List<String> multiplierStats = new ArrayList<>();
        multiplierStats.add("Max Multiplier");
        multiplierStats.add("x" + String.format("%.1f", maxMultiplier));
        stats.put("multiplier", multiplierStats);

        // Record Total Time
        List<String> timeStats = new ArrayList<>();
        timeStats.add("Time Survived");
        timeStats.add(formatTime(totalTime));
        stats.put("time", timeStats);

        // Record Score
        List<String> scoreStats = new ArrayList<>();
        scoreStats.add("Total Score");
        scoreStats.add(String.valueOf(totalScore));
        stats.put("score", scoreStats);
    }

    /**
     * Converts time from milliseconds to HH:MM:SS format.
     * @param timeInMillis - time in milliseconds.
     * @return A formatted string representing time in HH:MM:SS format.
     */
    private String formatTime(double timeInMillis) {
        int totalSeconds = (int) (timeInMillis / 1000);
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
