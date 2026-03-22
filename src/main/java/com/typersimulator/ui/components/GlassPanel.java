package com.typersimulator.ui.components;

import com.typersimulator.ui.Theme;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GlassPanel extends JPanel {

    private Color backgroundColor;
    private Color borderColor;
    private float cornerRadius;
    private float glowIntensity;
    private Color glowColor;
    private boolean animated;
    private float animationPhase;
    private Timer animationTimer;

    public GlassPanel() {
        this(Theme.SECONDARY_BG, Theme.withAlpha(Theme.ACCENT_CYAN, 50), 20);
    }

    public GlassPanel(Color background, Color border, float radius) {
        this.backgroundColor = background;
        this.borderColor = border;
        this.cornerRadius = radius;
        this.glowIntensity = 0f;
        this.glowColor = Theme.ACCENT_CYAN;
        this.animated = false;
        setOpaque(false);
    }

    public GlassPanel animated(boolean animated) {
        this.animated = animated;
        if (animated) {
            animationTimer = new Timer(25, e -> {
                animationPhase += 0.04f;
                repaint();
            });
            animationTimer.start();
        }
        return this;
    }

    public GlassPanel glow(Color color, float intensity) {
        this.glowColor = color;
        this.glowIntensity = intensity;
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();
        int r = (int) cornerRadius;

        if (glowIntensity > 0) {
            float pulse = animated ? (float) Math.sin(animationPhase * 2) * 0.3f + 0.7f : 1f;
            for (int i = 6; i >= 0; i--) {
                float alpha = glowIntensity * pulse * (0.12f - i * 0.015f);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2.setColor(glowColor);
                int expand = i * 4;
                g2.fillRoundRect(-expand, -expand, w + expand * 2, h + expand * 2, r + expand, r + expand);
            }
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));

        GradientPaint bgGradient = new GradientPaint(
            0, 0, backgroundColor.brighter(),
            0, h, backgroundColor
        );
        g2.setPaint(bgGradient);
        g2.fillRoundRect(0, 0, w, h, r, r);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(borderColor);
        g2.drawRoundRect(1, 1, w - 3, h - 3, r, r);

        g2.dispose();
        super.paintComponent(g);
    }

    public void stopAnimation() {
        if (animationTimer != null) animationTimer.stop();
    }
}
