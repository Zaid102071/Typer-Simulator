package com.typersimulator.ui;

import com.typersimulator.model.Player;
import com.typersimulator.ui.components.ModernButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameOverPanel extends JPanel {

    private JLabel scoreValue, wordsValue, wpmValue, accuracyValue, comboValue, timeValue, tierValue;
    private final ModernButton retryButton;
    private final ModernButton menuButton;
    private float animationPhase = 0f;
    private Timer animationTimer;
    private final List<float[]> particles = new ArrayList<>();
    private final Random random = new Random();

    public GameOverPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.insets = new Insets(18, 0, 12, 0);
        add(createTitle(), gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(12, 0, 15, 0);
        add(createStatsPanel(), gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 8, 0);
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttons.setOpaque(false);

        retryButton = new ModernButton("PLAY AGAIN", ModernButton.Style.SUCCESS);
        retryButton.withIcon("↻");
        retryButton.setPreferredSize(new Dimension(160, 44));

        menuButton = new ModernButton("MAIN MENU", ModernButton.Style.SECONDARY);
        menuButton.withIcon("⌂");
        menuButton.setPreferredSize(new Dimension(160, 44));

        buttons.add(retryButton);
        buttons.add(menuButton);
        add(buttons, gbc);

        initParticles();
        startAnimations();
    }

    private JLabel createTitle() {
        JLabel title = new JLabel("GAME OVER") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Font font = new Font("Segoe UI", Font.BOLD, 40);
                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics();

                String text = "GAME OVER";
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = fm.getAscent();

                for (int i = 5; i >= 0; i--) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.12f - i * 0.02f));
                    g2.setColor(new Color(255, 80, 100));
                    g2.drawString(text, x - i, y - i);
                    g2.drawString(text, x + i, y + i);
                }

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                GradientPaint gradient = new GradientPaint(x, y, new Color(255, 100, 120), x + fm.stringWidth(text), y, new Color(255, 60, 90));
                g2.setPaint(gradient);
                g2.drawString(text, x, y);

                g2.dispose();
            }
        };
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setForeground(new Color(255, 80, 100));
        title.setPreferredSize(new Dimension(320, 55));
        return title;
    }

    private void initParticles() {
        for (int i = 0; i < 20; i++) {
            particles.add(new float[]{
                random.nextFloat() * 900,
                random.nextFloat() * 700,
                (random.nextFloat() - 0.5f) * 0.3f,
                (random.nextFloat() - 0.5f) * 0.3f,
                random.nextFloat() * 1.5f + 0.5f
            });
        }
    }

    private void startAnimations() {
        animationTimer = new Timer(25, e -> {
            animationPhase += 0.04f;
            for (float[] p : particles) {
                p[0] += p[2];
                p[1] += p[3];
            }
            repaint();
        });
        animationTimer.start();
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 25, 10, 25);

        scoreValue = createStatRow(panel, gbc, 0, "FINAL SCORE", new Color(255, 255, 255));
        wordsValue = createStatRow(panel, gbc, 1, "WORDS TYPED", new Color(200, 220, 255));
        wpmValue = createStatRow(panel, gbc, 2, "WPM", new Color(0, 255, 180));
        accuracyValue = createStatRow(panel, gbc, 3, "ACCURACY", new Color(100, 200, 255));
        comboValue = createStatRow(panel, gbc, 4, "MAX COMBO", new Color(255, 200, 50));
        tierValue = createStatRow(panel, gbc, 5, "FINAL TIER", new Color(200, 150, 255));
        timeValue = createStatRow(panel, gbc, 6, "SURVIVAL TIME", new Color(180, 180, 190));

        return panel;
    }

    private JLabel createStatRow(JPanel panel, GridBagConstraints gbc, int row, String label, Color valueColor) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(new Color(100, 115, 145));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel value = new JLabel("--");
        value.setFont(new Font("Consolas", Font.BOLD, 24));
        value.setForeground(valueColor);
        panel.add(value, gbc);

        return value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        GradientPaint bg = new GradientPaint(0, 0, new Color(10, 6, 18), w, h, new Color(25, 12, 20));
        g2.setPaint(bg);
        g2.fillRect(0, 0, w, h);

        for (float[] p : particles) {
            float alpha = (float) (Math.sin(animationPhase + p[0] * 0.01) + 1) / 2 * 0.25f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setColor(new Color(255, 100, 120));
            g2.fillOval((int) p[0], (int) p[1], (int) p[4], (int) p[4]);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.025f));
        g2.setColor(new Color(255, 100, 120));
        g2.fillOval(w / 2 - 200, h / 2 - 200, 400, 400);

        g2.dispose();
        super.paintComponent(g);
    }

    public void updateStats(Player player, int finalTier) {
        scoreValue.setText(String.format("%,d", player.getScore()));
        wordsValue.setText(String.valueOf(player.getWordsTyped()));
        wpmValue.setText(String.format("%.0f", player.getWPM()));
        accuracyValue.setText(String.format("%.1f%%", player.getAccuracy()));
        comboValue.setText("x" + player.getMaxCombo());
        tierValue.setText(getTierName(finalTier));
        tierValue.setForeground(Theme.getTierColor(finalTier));

        long seconds = player.getElapsedTime() / 1000;
        timeValue.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
    }

    private String getTierName(int tier) {
        return switch (tier) {
            case 0 -> "EASY";
            case 1 -> "MEDIUM";
            case 2 -> "HARD";
            case 3 -> "EXPERT";
            default -> "MASTER";
        };
    }

    public void setRetryAction(ActionListener action) { retryButton.addActionListener(action); }
    public void setMenuAction(ActionListener action) { menuButton.addActionListener(action); }

    public void stopAnimations() {
        if (animationTimer != null) animationTimer.stop();
        retryButton.stopAnimation();
        menuButton.stopAnimation();
    }
}
