package com.typersimulator.ui.components;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GradientPanel extends JPanel {

    private final Color color1;
    private final Color color2;
    private final boolean animated;
    private float offset = 0f;
    private Timer animationTimer;
    private int[][] stars;
    private final Random random = new Random();

    public GradientPanel(Color color1, Color color2) {
        this(color1, color2, false);
    }

    public GradientPanel(Color color1, Color color2, boolean animated) {
        this.color1 = color1;
        this.color2 = color2;
        this.animated = animated;
        setOpaque(false);
        
        if (animated) {
            initStars();
            animationTimer = new Timer(50, e -> {
                offset += 0.01f;
                if (offset > 1) offset = 0;
                repaint();
            });
            animationTimer.start();
        }
    }

    private void initStars() {
        stars = new int[50][4];
        for (int i = 0; i < stars.length; i++) {
            stars[i][0] = random.nextInt(900);
            stars[i][1] = random.nextInt(700);
            stars[i][2] = 1 + random.nextInt(3);
            stars[i][3] = 50 + random.nextInt(150);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        GradientPaint gp = new GradientPaint(
            0, 0, color1,
            w, h, color2
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        if (animated && stars != null) {
            for (int[] star : stars) {
                float alpha = (float) (Math.sin(offset * Math.PI * 2 + star[0] * 0.01) + 1) / 2;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha * star[3] / 255f));
                g2.setColor(Color.WHITE);
                g2.fillOval(star[0], star[1], star[2], star[2]);
            }
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.03f));
        g2.setColor(Color.WHITE);
        int gridSize = 50;
        for (int x = 0; x < w; x += gridSize) {
            g2.drawLine(x, 0, x, h);
        }
        for (int y = 0; y < h; y += gridSize) {
            g2.drawLine(0, y, w, y);
        }

        g2.dispose();
    }

    public void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }
}
