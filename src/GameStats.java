import javafx.stage.Stage;

import java.util.*;

public class GameStats {
    private int totalScore;
    private double totalTime;
    private double maxMultiplier = 1.0;

    // Destroyed asteroids
    private int asteroidsDestroyed;
    private int spaceshipsDestroyed;


    private Map<String, List<String>> stats;


    GameStats() {
        this.asteroidsDestroyed = 0;
        this.spaceshipsDestroyed = 0;
    }


    public void setScore(int score) {
        this.totalScore = score;
    }

    public void setTime(double time) {
        this.totalTime = time;
    }

    public void saveMaxMultiplier(double multiplier) {
        if (multiplier > this.maxMultiplier) {
            this.maxMultiplier = multiplier;
        }
    }

    public double getMaxMultiplier() {
        return this.maxMultiplier;
    }

    public Map<String, List<String>> getFinalStats() {
        recordFinalStats();
        return this.stats;
    }

    public void incrementAsteroidCount() {
        this.asteroidsDestroyed++;
    }

    public void incrementSpaceshipCount() {
        this.spaceshipsDestroyed++;
    }


    private void recordFinalStats() {
         stats = new LinkedHashMap<>();

        // Record Asteroids Destroyed
        List<String> asteroidStats = new ArrayList<>();
        asteroidStats.add("Asteroids  Destroyed");
        asteroidStats.add(String.valueOf(asteroidsDestroyed));
        stats.put("asteroids", asteroidStats);

        // Record Spaceships Destroyed
        List<String> spaceshipsStats = new ArrayList<>();
        spaceshipsStats.add("Spaceships  Destroyed");
        spaceshipsStats.add(String.valueOf(spaceshipsDestroyed));
        stats.put("spaceships", spaceshipsStats);

        // Record Max Multiplier
        List<String> multiplierStats = new ArrayList<>();
        multiplierStats.add("Max Multiplier");
        multiplierStats.add("x" + String.format("%.1f", maxMultiplier));
        stats.put("multiplier", multiplierStats);

        // Record Total Time
        List<String> timeStats = new ArrayList<>();
        timeStats.add("Time survived");
        timeStats.add(formatTime(totalTime));
        stats.put("time", timeStats);

        // Record Score
        List<String> scoreStats = new ArrayList<>();
        scoreStats.add("Total Score");
        scoreStats.add(String.valueOf(totalScore));
        stats.put("score", scoreStats);
    }

    private String formatTime(double timeInMillis) {
        int totalSeconds = (int) (timeInMillis / 1000);

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        // Return the time in HH:MM:SS format
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }






}





