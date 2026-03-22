package com.typersimulator.model;

import java.awt.*;

public class Word {

    private String text;
    private double x;
    private double y;
    private double speed;
    private boolean active;
    private int matchedChars;

    public Word(String text, double x, double speed) {
        this.text = text;
        this.x = x;
        this.y = -30;
        this.speed = speed;
        this.active = true;
        this.matchedChars = 0;
    }

    public void update() {
        if (active) y += speed;
    }

    public void draw(Graphics2D g2, Font font) {
        if (!active) return;

        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        int totalWidth = fm.stringWidth(text);
        int startX = (int) x - totalWidth / 2;
        int yPos = (int) y;

        // Glow effect
        Composite original = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.12f));
        g2.setColor(new Color(100, 180, 255));
        g2.drawString(text, startX - 1, yPos - 1);
        g2.drawString(text, startX + 1, yPos + 1);
        g2.setComposite(original);

        // Matched characters in green
        if (matchedChars > 0) {
            g2.setColor(new Color(0, 255, 136));
            g2.drawString(text.substring(0, matchedChars), startX, yPos);
        }

        // Remaining characters in white
        if (matchedChars < text.length()) {
            g2.setColor(Color.WHITE);
            int offset = fm.stringWidth(text.substring(0, matchedChars));
            g2.drawString(text.substring(matchedChars), startX + offset, yPos);
        }
    }

    public boolean isOffScreen(int screenHeight) {
        return y > screenHeight + 20;
    }

    public int getPoints() {
        return text.length() * 10;
    }

    public String getText() { return text; }
    public double getX() { return x; }
    public double getY() { return y; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public int getMatchedChars() { return matchedChars; }
    public void setMatchedChars(int matchedChars) { this.matchedChars = matchedChars; }
}
