package view;

import controller.GameController;
import utils.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private GameController controller = new GameController();
    private GamePanel gamePanel = new GamePanel(controller);
    
    private EndlessModePanel endlessPanel;
    private SettingsPanel settingsPanel;
    private PausePanel pausePanel;
    private MainMenuPanel menuPanel;          // ← THÊM FIELD

    public MainFrame() {
        setTitle("Snake Game J14 – Dọc");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 840);
        setResizable(false);
        setLocationRelativeTo(null);

        controller.setGamePanel(gamePanel);

        menuPanel = new MainMenuPanel(this);   // ← DÙNG FIELD
        LevelSelectPanel levelSelect = new LevelSelectPanel(this, controller);
        
        endlessPanel = new EndlessModePanel(this);
        settingsPanel = new SettingsPanel(this);
        pausePanel = new PausePanel(this, controller);

        mainPanel.add(menuPanel, "MENU");      // ← DÙNG FIELD
        mainPanel.add(levelSelect, "LEVEL");
        mainPanel.add(gamePanel, "GAME");
        mainPanel.add(endlessPanel, "ENDLESS");
        mainPanel.add(settingsPanel, "SETTINGS");
        mainPanel.add(pausePanel, "PAUSE");

        add(mainPanel);
        showMenu();

        SoundManager.playBackgroundMusic();
    }

    public void showMenu() { 
        cardLayout.show(mainPanel, "MENU"); 
        menuPanel.updateHighScore();           // ← CẬP NHẬT ĐIỂM CAO
    }
    
    public void showLevelSelect() { 
        cardLayout.show(mainPanel, "LEVEL"); 
    }
    
    public void showEndlessMode() { 
        cardLayout.show(mainPanel, "ENDLESS"); 
    }
    
    public void showSettings() { 
        cardLayout.show(mainPanel, "SETTINGS"); 
    }

    public void showPause() { 
        cardLayout.show(mainPanel, "PAUSE"); 
    }

    public void showGame() {                   
        cardLayout.show(mainPanel, "GAME");
        gamePanel.requestFocusInWindow();
    }

    public void showGameOver(boolean isLevelMode, int value) {
        GameOverPanel overPanel = new GameOverPanel(this, controller, isLevelMode, value);
        for (Component c : mainPanel.getComponents()) {
            if (c instanceof GameOverPanel) mainPanel.remove(c);
        }
        mainPanel.add(overPanel, "GAMEOVER");
        cardLayout.show(mainPanel, "GAMEOVER");
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showLevelWin(int currentLevel) {
        LevelWinPanel winPanel = new LevelWinPanel(this, controller, currentLevel);
        for (Component c : mainPanel.getComponents()) {
            if (c instanceof LevelWinPanel) mainPanel.remove(c);
        }
        mainPanel.add(winPanel, "LEVELWIN");
        cardLayout.show(mainPanel, "LEVELWIN");
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void startLevel(LevelConfig cfg) {
        controller.startLevel(cfg);
        showGame();
    }

    public void startEndless(Difficulty d, GameMode m, int size) {
        controller.startEndless(d, m, size);
        showGame();
    }
}
