package com.typersimulator.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class NeonButton extends JButton {

    private final Color baseColor;
    private final Color glowColor;
    private final Color textColor;
    private boolean hovering = false;
    private float glowIntensity = 0f;
    private float pulsePhase = 0f;
    private Timer glowTimer;
    private Timer pulseTimer;

    public NeonButton(String text, Color baseColor) {
        this(text, baseColor, Color.WHITE);
    }

    public NeonButton(String text, Color baseColor, Color textColor) {
        super(text);
        this.baseColor = baseColor;
        this.textColor = textColor;
        this.glowColor = new Color(
            Math.min(255, baseColor.getRed() + 50),
            Math.min(255, baseColor.getGreen() + 50),
            Math.min(255, baseColor.getBlue() + 50)
        );

        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(new Font("Segoe UI", Font.BOLD, 18));
        setForeground(textColor);
        setPreferredSize(new Dimension(240, 55));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                startGlow();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                startGlow();
            }
        });

        pulseTimer = new Timer(30, e -> {
            pulsePhase += 0.08f;
            if (!hovering) repaint();
        });
        pulseTimer.start();
    }

    private void startGlow() {
        if (glowTimer != null && glowTimer.isRunning()) {
            glowTimer.stop();
        }
        glowTimer = new Timer(16, e -> {
            float target = hovering ? 1f : 0f;
            float speed = hovering ? 0.12f : 0.06f;

            if (glowIntensity < target) {
                glowIntensity = Math.min(target, glowIntensity + speed);
            } else if (glowIntensity > target) {
                glowIntensity = Math.max(target, glowIntensity - speed);
            }

            repaint();

            if (Math.abs(glowIntensity - target) < 0.01f) {
                glowIntensity = target;
                ((Timer) e.getSource()).stop();
            }
        });
        glowTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        int w = getWidth();
        int h = getHeight();
        int arc = 30;

        float pulse = hovering ? 0 : (float) Math.sin(pulsePhase) * 0.05f;
        float totalGlow = glowIntensity + pulse;

        if (totalGlow > 0) {
            for (int i = 4; i >= 0; i--) {
                float alpha = totalGlow * (0.15f - i * 0.025f);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.max(0, alpha)));
                g2.setColor(glowColor);
                int expand = i * 3;
                g2.fill(new RoundRectangle2D.Float(-expand, -expand, w + expand * 2, h + expand * 2, arc + expand, arc + expand));
            }
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f + totalGlow * 0.15f));

        GradientPaint bgGradient = new GradientPaint(
            0, 0,
            new Color(
                Math.min(255, baseColor.getRed() + (int) (totalGlow * 40)),
                Math.min(255, baseColor.getGreen() + (int) (totalGlow * 40)),
                Math.min(255, baseColor.getBlue() + (int) (totalGlow * 40))
            ),
            0, h,
            baseColor.darker()
        );
        g2.setPaint(bgGradient);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f + totalGlow * 0.7f));
        g2.setColor(glowColor);
        g2.setStroke(new BasicStroke(2f));
        g2.draw(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, arc, arc));

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setColor(textColor);
        g2.setFont(getFont());

        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textX = (w - textWidth) / 2;
        int textY = (h + fm.getAscent() - fm.getDescent()) / 2;

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2.setColor(Color.BLACK);
        g2.drawString(text, textX + 1, textY + 1);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2.setColor(textColor);
        g2.drawString(text, textX, textY);

        g2.dispose();
    }

    public void stopAnimation() {
        if (glowTimer != null) glowTimer.stop();
        if (pulseTimer != null) pulseTimer.stop();
    }
}
