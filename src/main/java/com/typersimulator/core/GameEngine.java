package com.typersimulator.core;

import com.typersimulator.data.WordBank;
import com.typersimulator.model.*;
import com.typersimulator.ui.GamePanel;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameEngine {

    private GameState state;
    private Player player;
    private Difficulty difficulty;
    private List<Word> activeWords;
    private List<int[]> particles;
    private final Random random;
    private final GamePanel gamePanel;
    private int panelWidth;
    private int panelHeight;

    public GameEngine(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.player = new Player(5);
        this.difficulty = new Difficulty();
        this.activeWords = new ArrayList<>();
        this.particles = new ArrayList<>();
        this.random = new Random();
        this.state = GameState.MENU;
        this.panelWidth = 800;
        this.panelHeight = 600;
    }

    public void startGame() {
        player.reset();
        difficulty.reset();
        activeWords.clear();
        particles.clear();
        state = GameState.PLAYING;
        scheduleNextSpawn();
        spawnWord();
    }

    private void scheduleNextSpawn() {
        if (state != GameState.PLAYING) return;
        Timer timer = new Timer((int) difficulty.getSpawnInterval(), e -> {
            if (state == GameState.PLAYING) {
                spawnWord();
                scheduleNextSpawn();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void spawnWord() {
        if (state != GameState.PLAYING) return;
        String wordText = WordBank.getRandomWord(difficulty.getTier());
        int margin = 100;
        int xPos = margin + random.nextInt(Math.max(1, panelWidth - margin * 2));
        activeWords.add(new Word(wordText, xPos, difficulty.getFallSpeed()));
    }

    public void update() {
        if (state != GameState.PLAYING) return;

        Iterator<Word> it = activeWords.iterator();
        while (it.hasNext()) {
            Word word = it.next();
            word.update();
            if (word.isOffScreen(panelHeight)) {
                word.setActive(false);
                player.loseLife();
                it.remove();
                if (!player.isAlive()) {
                    state = GameState.GAME_OVER;
                    gamePanel.onGameOver();
                }
            }
        }

        Iterator<int[]> pIt = particles.iterator();
        while (pIt.hasNext()) {
            int[] p = pIt.next();
            p[0] += p[2];
            p[1] += p[3];
            p[4]--;
            if (p[4] <= 0) pIt.remove();
        }
    }

    public void handleInput(String input) {
        if (state != GameState.PLAYING) return;

        String currentInput = input.toLowerCase();
        for (Word word : activeWords) {
            String wordText = word.getText().toLowerCase();

            if (wordText.startsWith(currentInput)) {
                word.setMatchedChars(currentInput.length());

                if (currentInput.equals(wordText)) {
                    player.addScore(word.getPoints());
                    player.incrementWordsTyped();
                    player.addChars(wordText.length(), wordText.length());
                    difficulty.wordTyped();

                    spawnParticles((int) word.getX(), (int) word.getY());
                    word.setActive(false);
                    activeWords.remove(word);

                    gamePanel.clearInput();
                    gamePanel.flashSuccess();
                    return;
                }
            } else {
                word.setMatchedChars(0);
            }
        }
    }

    private void spawnParticles(int x, int y) {
        for (int i = 0; i < 15; i++) {
            int vx = random.nextInt(10) - 5;
            int vy = random.nextInt(10) - 5;
            int life = 20 + random.nextInt(20);
            particles.add(new int[]{x, y, vx, vy, life});
        }
    }

    public void setPanelSize(int w, int h) {
        this.panelWidth = w;
        this.panelHeight = h;
    }

    public GameState getState() { return state; }
    public Player getPlayer() { return player; }
    public Difficulty getDifficulty() { return difficulty; }
    public List<Word> getActiveWords() { return activeWords; }
    public List<int[]> getParticles() { return particles; }
}
