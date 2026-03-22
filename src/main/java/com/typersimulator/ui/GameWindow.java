package com.typersimulator.ui;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel;

    public static final String MENU = "menu";
    public static final String GAME = "game";
    public static final String GAMEOVER = "gameover";

    public GameWindow() {
        setTitle("Typer Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 500));
        setLocationRelativeTo(null);

        try {
            setBackground(new Color(6, 6, 20));
        } catch (Exception ignored) {}

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);

        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();
        gameOverPanel = new GameOverPanel();

        cardPanel.add(menuPanel, MENU);
        cardPanel.add(gamePanel, GAME);
        cardPanel.add(gameOverPanel, GAMEOVER);

        setContentPane(cardPanel);
        setupActions();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                menuPanel.stopAnimations();
                gameOverPanel.stopAnimations();
            }
        });
    }

    private void setupActions() {
        menuPanel.setStartAction(e -> {
            gamePanel.startGame();
            cardLayout.show(cardPanel, GAME);
            gamePanel.getInputField().requestFocusInWindow();
        });

        menuPanel.setQuitAction(e -> {
            menuPanel.stopAnimations();
            System.exit(0);
        });

        gamePanel.setOnGameOverCallback(() -> {
            gameOverPanel.updateStats(
                gamePanel.getEngine().getPlayer(),
                gamePanel.getEngine().getDifficulty().getTier()
            );
            cardLayout.show(cardPanel, GAMEOVER);
        });

        gameOverPanel.setRetryAction(e -> {
            gamePanel.startGame();
            cardLayout.show(cardPanel, GAME);
            gamePanel.getInputField().requestFocusInWindow();
        });

        gameOverPanel.setMenuAction(e -> cardLayout.show(cardPanel, MENU));
    }
}
