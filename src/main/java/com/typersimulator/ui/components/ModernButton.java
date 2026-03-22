package com.typersimulator.ui.components;

import com.typersimulator.ui.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Area;

public class ModernButton extends JButton {

    public enum Style {
        PRIMARY, SECONDARY, DANGER, SUCCESS, GHOST
    }

    private Color baseColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color textColor;
    private float cornerRadius = 16;
    private boolean hovering = false;
    private boolean pressed = false;
    private float hoverProgress = 0f;
    private float glowProgress = 0f;
    private float pulsePhase = 0f;
    private Timer animationTimer;
    private Timer pulseTimer;
    private String icon;

    public ModernButton(String text) {
        this(text, Style.PRIMARY);
    }

    public ModernButton(String text, Style style) {
        super(text);
        setStyle(style);
        init();
    }

    private void setStyle(Style style) {
        switch (style) {
            case PRIMARY -> {
                baseColor = new Color(0, 180, 120);
                hoverColor = new Color(0, 220, 150);
                pressedColor = new Color(0, 150, 100);
                textColor = Color.WHITE;
            }
            case SECONDARY -> {
                baseColor = new Color(60, 80, 120);
                hoverColor = new Color(80, 100, 150);
                pressedColor = new Color(50, 70, 100);
                textColor = Color.WHITE;
            }
            case DANGER -> {
                baseColor = new Color(200, 60, 80);
                hoverColor = new Color(230, 80, 100);
                pressedColor = new Color(170, 50, 70);
                textColor = Color.WHITE;
            }
            case SUCCESS -> {
                baseColor = new Color(0, 200, 100);
                hoverColor = new Color(0, 230, 120);
                pressedColor = new Color(0, 170, 80);
                textColor = Color.WHITE;
            }
            case GHOST -> {
                baseColor = new Color(255, 255, 255, 20);
                hoverColor = new Color(255, 255, 255, 40);
                pressedColor = new Color(255, 255, 255, 15);
                textColor = Color.WHITE;
            }
        }
    }

    public ModernButton withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public ModernButton withRadius(float radius) {
        this.cornerRadius = radius;
        return this;
    }

    private void init() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(Theme.FONT_BUTTON);
        setForeground(textColor);
        setPreferredSize(new Dimension(200, 52));

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { hovering = true; startAnimation(); }
            @Override public void mouseExited(MouseEvent e) { hovering = false; pressed = false; startAnimation(); }
            @Override public void mousePressed(MouseEvent e) { pressed = true; startAnimation(); }
            @Override public void mouseReleased(MouseEvent e) { pressed = false; startAnimation(); }
        });

        pulseTimer = new Timer(30, e -> {
            pulsePhase += 0.06f;
            if (!hovering) repaint();
        });
        pulseTimer.start();
    }

    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) return;

        animationTimer = new Timer(12, e -> {
            float targetHover = hovering ? 1f : 0f;
            float targetGlow = hovering ? 1f : 0f;

            float hoverSpeed = hovering ? 0.15f : 0.08f;
            float glowSpeed = hovering ? 0.12f : 0.06f;

            if (hoverProgress < targetHover) hoverProgress = Math.min(targetHover, hoverProgress + hoverSpeed);
            else if (hoverProgress > targetHover) hoverProgress = Math.max(targetHover, hoverProgress - hoverSpeed);

            if (glowProgress < targetGlow) glowProgress = Math.min(targetGlow, glowProgress + glowSpeed);
            else if (glowProgress > targetGlow) glowProgress = Math.max(targetGlow, glowProgress - glowSpeed);

            repaint();

            if (Math.abs(hoverProgress - targetHover) < 0.01f && Math.abs(glowProgress - targetGlow) < 0.01f) {
                hoverProgress = targetHover;
                glowProgress = targetGlow;
                ((Timer) e.getSource()).stop();
            }
        });
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        int w = getWidth();
        int h = getHeight();
        int r = (int) cornerRadius;

        float pulse = (float) Math.sin(pulsePhase) * 0.08f;
        float totalGlow = glowProgress + (hovering ? 0 : pulse);

        if (totalGlow > 0) {
            for (int i = 5; i >= 0; i--) {
                float alpha = totalGlow * (0.15f - i * 0.025f);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.max(0, alpha)));
                g2.setColor(hoverColor);
                int expand = i * 3;
                g2.fill(new RoundRectangle2D.Float(-expand, -expand, w + expand * 2, h + expand * 2, r + expand, r + expand));
            }
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        Color bg = pressed ? pressedColor : lerp(baseColor, hoverColor, hoverProgress);

        GradientPaint gradient = new GradientPaint(
            0, 0, bg.brighter(),
            0, h, bg
        );
        g2.setPaint(gradient);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, r, r));

        if (hoverProgress > 0) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, hoverProgress * 0.3f));
            g2.setColor(Color.WHITE);
            g2.fill(new RoundRectangle2D.Float(0, 0, w, h / 2f, r, r));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f + glowProgress * 0.4f));
        g2.setColor(hoverColor);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Float(1, 1, w - 3, h - 3, r, r));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setFont(getFont());

        String displayText = icon != null ? icon + "  " + getText() : getText();
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(displayText);
        int textX = (w - textWidth) / 2;
        int textY = (h + fm.getAscent() - fm.getDescent()) / 2;

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        g2.setColor(Color.BLACK);
        g2.drawString(displayText, textX + 1, textY + 1);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setColor(textColor);
        g2.drawString(displayText, textX, textY);

        g2.dispose();
    }

    private Color lerp(Color a, Color b, float t) {
        return new Color(
            (int) (a.getRed() + (b.getRed() - a.getRed()) * t),
            (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t),
            (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t)
        );
    }

    public void stopAnimation() {
        if (animationTimer != null) animationTimer.stop();
        if (pulseTimer != null) pulseTimer.stop();
    }
}
