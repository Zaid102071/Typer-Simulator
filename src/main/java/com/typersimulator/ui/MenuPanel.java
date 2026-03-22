package com.typersimulator.ui;

import com.typersimulator.ui.components.GlassPanel;
import com.typersimulator.ui.components.ModernButton;
import com.typersimulator.ui.components.GradientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MenuPanel extends JPanel {

    private final ModernButton startButton;
    private final ModernButton quitButton;
    private float animationPhase = 0f;
    private Timer animationTimer;
    private Timer particleTimer;
    private final List<float[]> particles = new ArrayList<>();
    private final Random random = new Random();

    public MenuPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        gbc.insets = new Insets(15, 0, 8, 0);
        add(createTitle(), gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(3, 0, 15, 0);
        JLabel subtitle = new JLabel("SURVIVAL MODE");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(100, 180, 255));
        add(subtitle, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 8, 0);
        startButton = new ModernButton("START GAME", ModernButton.Style.PRIMARY);
        startButton.withIcon("▶");
        startButton.setPreferredSize(new Dimension(200, 44));
        add(startButton, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(6, 0, 18, 0);
        quitButton = new ModernButton("QUIT", ModernButton.Style.GHOST);
        quitButton.withIcon("✕");
        quitButton.setPreferredSize(new Dimension(200, 44));
        add(quitButton, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(15, 0, 0, 0);
        add(createInstructions(), gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(12, 0, 0, 0);
        JLabel version = new JLabel("v1.0.0 • Build Your Typing Speed");
        version.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        version.setForeground(new Color(70, 85, 110));
        add(version, gbc);

        initParticles();
        startAnimations();
    }

    private JLabel createTitle() {
        JLabel title = new JLabel("TYPER SIMULATOR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

                Font font = new Font("Segoe UI", Font.BOLD, 42);
                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics();

                String text = "TYPER SIMULATOR";
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = fm.getAscent();

                for (int i = 6; i >= 0; i--) {
                    float alpha = 0.12f - i * 0.018f;
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    g2.setColor(new Color(0, 230, 255));
                    g2.drawString(text, x - i, y - i);
                    g2.drawString(text, x + i, y + i);
                }

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

                GradientPaint gradient = new GradientPaint(
                    x, y - fm.getAscent(), new Color(0, 255, 180),
                    x + fm.stringWidth(text), y + fm.getDescent(), new Color(0, 180, 255)
                );
                g2.setPaint(gradient);
                g2.drawString(text, x, y);

                g2.dispose();
            }
        };
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(new Color(0, 255, 180));
        title.setPreferredSize(new Dimension(420, 65));
        return title;
    }

    private void initParticles() {
        for (int i = 0; i < 30; i++) {
            particles.add(new float[]{
                random.nextFloat() * 900,
                random.nextFloat() * 700,
                (random.nextFloat() - 0.5f) * 0.5f,
                (random.nextFloat() - 0.5f) * 0.5f,
                random.nextFloat() * 2 + 1
            });
        }
    }

    private void startAnimations() {
        animationTimer = new Timer(25, e -> {
            animationPhase += 0.04f;
            repaint();
        });
        animationTimer.start();

        particleTimer = new Timer(30, e -> {
            for (float[] p : particles) {
                p[0] += p[2];
                p[1] += p[3];
                if (p[0] < 0 || p[0] > getWidth()) p[2] *= -1;
                if (p[1] < 0 || p[1] > getHeight()) p[3] *= -1;
            }
            repaint();
        });
        particleTimer.start();
    }

    private JPanel createInstructions() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel header = new JLabel("HOW TO PLAY");
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setForeground(new Color(120, 140, 170));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(header);
        panel.add(Box.createVerticalStrut(8));

        String[][] tips = {
            {"Type falling words before they hit the red zone"},
            {"Longer words = More points"},
            {"Build combos for score multipliers"},
            {"Difficulty increases as you progress"}
        };

        for (String[] tip : tips) {
            JLabel label = new JLabel("• " + tip[0]);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            label.setForeground(new Color(100, 120, 150));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
            panel.add(Box.createVerticalStrut(3));
        }

        return panel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();

        GradientPaint bg = new GradientPaint(0, 0, new Color(6, 6, 22), w, h, new Color(15, 10, 40));
        g2.setPaint(bg);
        g2.fillRect(0, 0, w, h);

        for (float[] p : particles) {
            float alpha = (float) (Math.sin(animationPhase * 2 + p[0] * 0.01) + 1) / 2 * 0.4f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setColor(new Color(0, 200, 255));
            g2.fillOval((int) p[0], (int) p[1], (int) p[4], (int) p[4]);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.03f));
        g2.setColor(new Color(100, 180, 255));
        int gridSize = 60;
        for (int x = 0; x < w; x += gridSize) g2.drawLine(x, 0, x, h);
        for (int y = 0; y < h; y += gridSize) g2.drawLine(0, y, w, y);

        g2.dispose();
        super.paintComponent(g);
    }

    public void setStartAction(ActionListener action) {
        startButton.addActionListener(action);
    }

    public void setQuitAction(ActionListener action) {
        quitButton.addActionListener(action);
    }

    public void stopAnimations() {
        if (animationTimer != null) animationTimer.stop();
        if (particleTimer != null) particleTimer.stop();
        startButton.stopAnimation();
        quitButton.stopAnimation();
    }
}
