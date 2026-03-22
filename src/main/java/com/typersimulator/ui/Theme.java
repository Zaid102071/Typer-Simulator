package com.typersimulator.ui;

import java.awt.*;

public final class Theme {

    private Theme() {}

    // Primary Colors
    public static final Color PRIMARY_BG = new Color(6, 6, 20);
    public static final Color SECONDARY_BG = new Color(12, 12, 35);
    public static final Color TERTIARY_BG = new Color(18, 18, 50);

    // Accent Colors
    public static final Color ACCENT_CYAN = new Color(0, 230, 255);
    public static final Color ACCENT_GREEN = new Color(0, 255, 150);
    public static final Color ACCENT_PURPLE = new Color(150, 100, 255);
    public static final Color ACCENT_PINK = new Color(255, 100, 200);
    public static final Color ACCENT_ORANGE = new Color(255, 150, 50);
    public static final Color ACCENT_RED = new Color(255, 80, 100);

    // Text Colors
    public static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    public static final Color TEXT_SECONDARY = new Color(180, 190, 210);
    public static final Color TEXT_MUTED = new Color(100, 115, 140);

    // Gradients
    public static final Color GRADIENT_START = new Color(8, 8, 30);
    public static final Color GRADIENT_END = new Color(20, 10, 50);

    // Glow Colors
    public static final Color GLOW_CYAN = new Color(0, 230, 255, 60);
    public static final Color GLOW_GREEN = new Color(0, 255, 150, 60);
    public static final Color GLOW_PURPLE = new Color(150, 100, 255, 60);

    // Game Colors
    public static final Color WORD_NORMAL = new Color(220, 230, 255);
    public static final Color WORD_MATCHED = new Color(0, 255, 150);
    public static final Color DANGER_ZONE = new Color(255, 60, 80, 100);
    public static final Color SUCCESS_FLASH = new Color(0, 255, 150, 40);

    // Tier Colors
    public static Color getTierColor(int tier) {
        return switch (tier) {
            case 0 -> ACCENT_GREEN;
            case 1 -> ACCENT_CYAN;
            case 2 -> ACCENT_ORANGE;
            case 3 -> ACCENT_PINK;
            default -> ACCENT_PURPLE;
        };
    }

    public static String getTierHex(int tier) {
        return switch (tier) {
            case 0 -> "#00FF96";
            case 1 -> "#00E6FF";
            case 2 -> "#FF9632";
            case 3 -> "#FF64C8";
            default -> "#9664FF";
        };
    }

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 64);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 20);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font FONT_MONO = new Font("Consolas", Font.BOLD, 32);
    public static final Font FONT_MONO_SMALL = new Font("Consolas", Font.PLAIN, 18);
    public static final Font FONT_STATS = new Font("Consolas", Font.BOLD, 22);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_TINY = new Font("Segoe UI", Font.PLAIN, 11);

    // Animation durations
    public static final int ANIMATION_FAST = 150;
    public static final int ANIMATION_NORMAL = 300;
    public static final int ANIMATION_SLOW = 500;

    // Shadows
    public static Color withAlpha(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
}
