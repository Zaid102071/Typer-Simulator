package com.typersimulator.model;

public class Player {

    private int score;
    private int lives;
    private final int maxLives;
    private int wordsTyped;
    private int totalChars;
    private int correctChars;
    private long startTime;
    private int combo;
    private int maxCombo;

    public Player(int maxLives) {
        this.maxLives = maxLives;
        reset();
    }

    public void reset() {
        score = 0;
        lives = maxLives;
        wordsTyped = 0;
        totalChars = 0;
        correctChars = 0;
        startTime = System.currentTimeMillis();
        combo = 0;
        maxCombo = 0;
    }

    public void addScore(int points) {
        combo++;
        if (combo > maxCombo) maxCombo = combo;
        score += points * Math.min(combo, 5);
    }

    public void loseLife() {
        lives--;
        combo = 0;
    }

    public void incrementWordsTyped() { wordsTyped++; }

    public void addChars(int total, int correct) {
        totalChars += total;
        correctChars += correct;
    }

    public double getWPM() {
        double minutes = (System.currentTimeMillis() - startTime) / 60000.0;
        return minutes > 0 ? wordsTyped / minutes : 0;
    }

    public double getAccuracy() {
        return totalChars == 0 ? 100.0 : ((double) correctChars / totalChars) * 100.0;
    }

    public boolean isAlive() { return lives > 0; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getMaxLives() { return maxLives; }
    public int getWordsTyped() { return wordsTyped; }
    public int getCombo() { return combo; }
    public int getMaxCombo() { return maxCombo; }
    public long getElapsedTime() { return System.currentTimeMillis() - startTime; }
}
