package com.typersimulator.model;

public class Difficulty {

    private int tier;
    private double fallSpeed;
    private double spawnInterval;
    private int wordsForNextTier;
    private int wordsTypedInTier;

    private static final double BASE_FALL_SPEED = 1.2;
    private static final double BASE_SPAWN_INTERVAL = 1800;
    private static final double MIN_SPAWN_INTERVAL = 400;

    public Difficulty() {
        reset();
    }

    public void wordTyped() {
        wordsTypedInTier++;
        if (wordsTypedInTier >= wordsForNextTier && tier < 3) {
            tier++;
            wordsTypedInTier = 0;
            wordsForNextTier += 5;
            fallSpeed = BASE_FALL_SPEED + (tier * 0.4);
            spawnInterval = Math.max(MIN_SPAWN_INTERVAL, BASE_SPAWN_INTERVAL - (tier * 350));
        }
    }

    public void reset() {
        tier = 0;
        fallSpeed = BASE_FALL_SPEED;
        spawnInterval = BASE_SPAWN_INTERVAL;
        wordsForNextTier = 8;
        wordsTypedInTier = 0;
    }

    public int getTier() { return tier; }
    public double getFallSpeed() { return fallSpeed; }
    public double getSpawnInterval() { return spawnInterval; }
    public int getProgressToNextTier() { return wordsTypedInTier; }
    public int getWordsForNextTier() { return wordsForNextTier; }
}
