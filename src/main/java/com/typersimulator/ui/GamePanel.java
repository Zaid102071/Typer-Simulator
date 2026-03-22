package com.typersimulator.ui;

import com.typersimulator.core.GameEngine;
import com.typersimulator.core.GameState;
import com.typersimulator.data.WordBank;
import com.typersimulator.model.Player;
import com.typersimulator.model.Word;
import com.typersimulator.ui.components.ModernButton;
import com.typersimulator.ui.components.GlassPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {

    private final GameEngine engine;
    private final JTextField inputField;
    private JLabel scoreValue, wpmValue, comboValue, tierValue, livesValue, progressValue;
    private final Timer gameTimer;
    private final Timer animationTimer;
    private Runnable onGameOverCallback;
    private boolean flashSuccess = false;
    private float backgroundPhase = 0f;
    private final List<Particle> particles = new ArrayList<>();
    private final Random random = new Random();
    private float dangerPulse = 0f;

    private static class Particle {
        float x, y, vx, vy, life, maxLife, size;
        Color color;
        Particle(float x, float y, float vx, float vy, float life, float size, Color color) {
            this.x = x; this.y = y; this.vx = vx; this.vy = vy;
            this.life = life; this.maxLife = life; this.size = size; this.color = color;
        }
    }

    public GamePanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        engine = new GameEngine(this);
        inputField = createInputField();
        
        buildHUD();
        buildInputWrapper();

        gameTimer = new Timer(16, e -> {
            engine.update();
            updateParticles();
            updateHUD();
            repaint();
        });

        animationTimer = new Timer(25, e -> {
            backgroundPhase += 0.03f;
            dangerPulse += 0.08f;
            repaint();
        });
        animationTimer.start();
    }

    private void updateParticles() {
        float dt = 1f;
        particles.removeIf(p -> {
            p.x += p.vx * dt;
            p.y += p.vy * dt;
            p.vy += 0.15f;
            p.life -= dt;
            return p.life <= 0;
        });
    }

    private void spawnParticles(int x, int y, Color color) {
        for (int i = 0; i < 25; i++) {
            float angle = random.nextFloat() * (float) Math.PI * 2;
            float speed = 3 + random.nextFloat() * 5;
            particles.add(new Particle(
                x, y,
                (float) Math.cos(angle) * speed,
                (float) Math.sin(angle) * speed - 3,
                40 + random.nextFloat() * 30,
                4 + random.nextFloat() * 5,
                color
            ));
        }
    }

    private void buildHUD() {
        JPanel hud = new JPanel(new BorderLayout());
        hud.setOpaque(false);
        hud.setBorder(new EmptyBorder(8, 15, 5, 15));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightPanel.setOpaque(false);

        scoreValue = createStatCard("SCORE", "0", new Color(255, 255, 255));
        livesValue = createStatCard("LIVES", "❤❤❤❤❤", new Color(255, 100, 120));
        comboValue = createStatCard("COMBO", "x1", new Color(255, 200, 50));

        wpmValue = createStatCard("WPM", "0", new Color(0, 255, 180));
        tierValue = createStatCard("TIER", "EASY", new Color(100, 200, 255));
        progressValue = createStatCard("NEXT", "0/8", new Color(150, 150, 170));

        leftPanel.add(scoreValue);
        leftPanel.add(livesValue);
        leftPanel.add(comboValue);

        rightPanel.add(wpmValue);
        rightPanel.add(tierValue);
        rightPanel.add(progressValue);

        hud.add(leftPanel, BorderLayout.WEST);
        hud.add(rightPanel, BorderLayout.EAST);

        add(hud, BorderLayout.NORTH);
    }

    private JLabel createStatCard(String label, String value, Color valueColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);

        JLabel labelLbl = new JLabel(label);
        labelLbl.setFont(new Font("Segoe UI", Font.PLAIN, 8));
        labelLbl.setForeground(new Color(100, 115, 140));
        labelLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("Consolas", Font.BOLD, 15));
        valueLbl.setForeground(valueColor);
        valueLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(labelLbl);
        card.add(valueLbl);

        JLabel container = new JLabel();
        container.setLayout(new BorderLayout());
        container.add(card, BorderLayout.CENTER);
        container.setPreferredSize(new Dimension(70, 38));

        return valueLbl;
    }

    private JTextField createInputField() {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();
                int r = 18;

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.92f));
                g2.setColor(new Color(12, 12, 35));
                g2.fillRoundRect(0, 0, w, h, r, r);

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                GradientPaint border = new GradientPaint(0, 0, new Color(0, 255, 180), w, h, new Color(0, 180, 255));
                g2.setPaint(border);
                g2.setStroke(new BasicStroke(2.5f));
                g2.drawRoundRect(1, 1, w - 3, h - 3, r, r);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        field.setFont(new Font("Consolas", Font.PLAIN, 22));
        field.setForeground(new Color(0, 255, 180));
        field.setCaretColor(new Color(0, 255, 180));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setOpaque(false);
        field.setBorder(new EmptyBorder(10, 20, 10, 20));

        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (engine.getState() == GameState.PLAYING) {
                    engine.handleInput(field.getText());
                }
            }
        });

        return field;
    }

    private void buildInputWrapper() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(8, 50, 18, 50));
        wrapper.add(inputField, BorderLayout.CENTER);
        add(wrapper, BorderLayout.SOUTH);
    }

    private void updateHUD() {
        Player p = engine.getPlayer();

        scoreValue.setText(String.format("%,d", p.getScore()));
        scoreValue.setForeground(p.getScore() > 2000 ? new Color(255, 215, 0) : Color.WHITE);

        StringBuilder hearts = new StringBuilder();
        for (int i = 0; i < p.getMaxLives(); i++) {
            hearts.append(i < p.getLives() ? "❤" : "♡");
        }
        livesValue.setText(hearts.toString());
        livesValue.setForeground(p.getLives() <= 2 ? new Color(255, 80, 100) : new Color(255, 130, 150));

        int combo = Math.min(p.getCombo() + 1, 5);
        comboValue.setText("x" + combo);
        comboValue.setForeground(combo >= 4 ? new Color(255, 200, 50) : new Color(200, 180, 100));

        wpmValue.setText(String.valueOf((int) p.getWPM()));

        tierValue.setText(WordBank.getTierName(engine.getDifficulty().getTier()));
        tierValue.setForeground(Theme.getTierColor(engine.getDifficulty().getTier()));

        progressValue.setText(engine.getDifficulty().getProgressToNextTier() + "/" + engine.getDifficulty().getWordsForNextTier());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        engine.setPanelSize(getWidth(), getHeight());
        int w = getWidth(), h = getHeight();

        GradientPaint bg = new GradientPaint(0, 0, new Color(6, 6, 22), w, h, new Color(12, 8, 35));
        g2.setPaint(bg);
        g2.fillRect(0, 0, w, h);

        float pulse = (float) Math.sin(backgroundPhase * 1.5f) * 30;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.04f));
        g2.setColor(new Color(0, 200, 255));
        g2.fillOval(w / 2 - 250 + (int) pulse, h / 2 - 250, 500, 500);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.025f));
        g2.setColor(new Color(150, 100, 255));
        g2.fillOval(w / 4 - 150, h / 3 - 150, 300, 300);

        Font wordFont = new Font("Consolas", Font.BOLD, 34);
        for (Word word : engine.getActiveWords()) {
            drawWord(g2, word, wordFont);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        for (Particle p : particles) {
            float alpha = p.life / p.maxLife;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha * 0.85f));
            g2.setColor(p.color);
            g2.fillOval((int) (p.x - p.size / 2), (int) (p.y - p.size / 2), (int) p.size, (int) p.size);
        }

        int dangerY = h - 90;
        float dangerAlpha = 0.15f + (float) Math.sin(dangerPulse) * 0.08f;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, dangerAlpha));
        g2.setColor(new Color(255, 50, 70));
        GradientPaint dangerGrad = new GradientPaint(0, dangerY - 60, new Color(255, 50, 70, 0), 0, dangerY + 40, new Color(255, 50, 70, 180));
        g2.setPaint(dangerGrad);
        g2.fillRect(0, dangerY - 60, w, 100);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f + (float) Math.sin(dangerPulse) * 0.2f));
        g2.setColor(new Color(255, 80, 100));
        g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{20, 12}, 0));
        g2.drawLine(0, dangerY, w, dangerY);

        if (flashSuccess) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.18f));
            g2.setColor(new Color(0, 255, 180));
            g2.fillRect(0, 0, w, h);
        }

        g2.dispose();
    }

    private void drawWord(Graphics2D g2, Word word, Font font) {
        if (!word.isActive()) return;

        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        String text = word.getText();
        int totalWidth = fm.stringWidth(text);
        int x = (int) word.getX() - totalWidth / 2;
        int y = (int) word.getY();

        int matched = word.getMatchedChars();

        for (int i = 4; i >= 0; i--) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f - i * 0.02f));
            g2.setColor(new Color(100, 200, 255));
            g2.drawString(text, x - i, y - i);
            g2.drawString(text, x + i, y + i);
        }

        if (matched > 0) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2.setColor(new Color(0, 255, 180));
            g2.drawString(text.substring(0, matched), x, y);
        }

        if (matched < text.length()) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g2.setColor(new Color(220, 235, 255));
            int offset = fm.stringWidth(text.substring(0, matched));
            g2.drawString(text.substring(matched), x + offset, y);
        }
    }

    public void startGame() {
        engine.startGame();
        inputField.setText("");
        inputField.requestFocusInWindow();
        gameTimer.start();
    }

    public void clearInput() {
        inputField.setText("");
    }

    public void flashSuccess() {
        flashSuccess = true;
        spawnParticles(getWidth() / 2, getHeight() / 2, new Color(0, 255, 180));
        Timer t = new Timer(80, e -> { flashSuccess = false; ((Timer) e.getSource()).stop(); repaint(); });
        t.setRepeats(false);
        t.start();
    }

    public void onGameOver() {
        gameTimer.stop();
        if (onGameOverCallback != null) onGameOverCallback.run();
    }

    public void setOnGameOverCallback(Runnable cb) { this.onGameOverCallback = cb; }
    public GameEngine getEngine() { return engine; }
    public JTextField getInputField() { return inputField; }
}
