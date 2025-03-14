public class Score {
    private int score;
    private double multiplier;


    public Score() {
        score = 0;
        multiplier = 1;
    }

    public void increase(int scoreIncrease) {
        score += (int) (scoreIncrease * multiplier);
    }

    public void incrementMultiplier() {
        multiplier = (multiplier >= 2) ? 2 : multiplier+0.1;
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
}
