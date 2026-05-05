package controller;

import model.*;
import utils.*;
import view.GamePanel;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Snake snake;
    private Food food;
    private GamePanel gamePanel;
    private Timer timer;

    private int score = 0;
    private boolean isPaused = false;
    private boolean isMoving = false;
    private int currentGridSize = 15;

    private List<Obstacle> obstacles = new ArrayList<>();
    private int eatenFood = 0;
    private int requiredFood = 0;
    private boolean isLevelMode = true;
    private LevelConfig currentLevel;

    private GameMode gameMode = GameMode.CLASSIC;
    private Difficulty difficulty = Difficulty.MEDIUM;
    private boolean isSpeedRush = false;

    private int highScore = FileHandler.readHighScore();

    public void setGamePanel(GamePanel gp) { 
        this.gamePanel = gp; 
    }

    public LevelConfig getLevelConfig(int lvl) {
        return switch (lvl) {
            case 1 -> new LevelConfig(1, 5, 3, 160);
            case 2 -> new LevelConfig(2, 5, 6, 140);
            case 3 -> new LevelConfig(3, 5, 9, 120);
            case 4 -> new LevelConfig(4, 5, 12, 100);
            default -> new LevelConfig(5, 5, 15, 80);
        };
    }

    public void startLevel(LevelConfig cfg) {
        currentLevel = cfg;
        isLevelMode = true;
        requiredFood = cfg.requiredFood;
        eatenFood = 0;
        currentGridSize = 15;
        score = 0;
        isMoving = false;
        isSpeedRush = false;

        snake = new Snake(3, 7);
        snake.getBody().add(new int[]{2, 7});
        food = new Food(12, 7);

        obstacles.clear();
        switch (cfg.level) {
            case 1 -> { obstacles.add(new Obstacle(4,3)); obstacles.add(new Obstacle(5,3)); obstacles.add(new Obstacle(6,3)); }
            case 2 -> { obstacles.add(new Obstacle(3,2)); obstacles.add(new Obstacle(3,3)); obstacles.add(new Obstacle(3,4));
                        obstacles.add(new Obstacle(7,5)); obstacles.add(new Obstacle(8,5)); obstacles.add(new Obstacle(9,5)); }
            case 3 -> { obstacles.add(new Obstacle(2,4)); obstacles.add(new Obstacle(3,4)); obstacles.add(new Obstacle(4,4));
                        obstacles.add(new Obstacle(10,2)); obstacles.add(new Obstacle(10,3)); obstacles.add(new Obstacle(10,4)); }
            case 4 -> { obstacles.add(new Obstacle(3,3)); obstacles.add(new Obstacle(4,3));
                        obstacles.add(new Obstacle(10,3)); obstacles.add(new Obstacle(11,3));
                        obstacles.add(new Obstacle(6,8)); obstacles.add(new Obstacle(7,8)); obstacles.add(new Obstacle(8,8)); }
            case 5 -> { obstacles.add(new Obstacle(2,2)); obstacles.add(new Obstacle(3,2)); obstacles.add(new Obstacle(4,2));
                        obstacles.add(new Obstacle(2,10)); obstacles.add(new Obstacle(3,10)); obstacles.add(new Obstacle(4,10));
                        obstacles.add(new Obstacle(10,2)); obstacles.add(new Obstacle(11,2)); obstacles.add(new Obstacle(12,2));
                        obstacles.add(new Obstacle(10,10)); obstacles.add(new Obstacle(11,10)); obstacles.add(new Obstacle(12,10)); }
        }

        if (timer != null) timer.stop();
        timer = new Timer(cfg.delay, e -> update());
        timer.start();

        if (gamePanel != null) gamePanel.updateGridSize(currentGridSize);
    }

    public void startEndless(Difficulty d, GameMode m, int size) {
        isLevelMode = false;
        difficulty = d;
        gameMode = m;
        isSpeedRush = (m == GameMode.SPEED_RUSH);
        currentGridSize = size;
        score = 0;
        eatenFood = 0;
        isMoving = false;

        snake = new Snake(3, size / 2);
        snake.getBody().add(new int[]{2, size / 2});
        food = new Food(size - 4, size / 2);
        obstacles.clear();

        if (timer != null) timer.stop();
        timer = new Timer(d.getDelay(), e -> update());
        timer.start();

        if (gamePanel != null) gamePanel.updateGridSize(currentGridSize);
    }

    void update() {
        if (!isMoving || isPaused || snake == null || !snake.isAlive()) return;
        snake.move();
        checkCollisions();
        checkFood();
        if (gamePanel != null) gamePanel.repaint();
    }

    private void checkCollisions() {
        int[] head = snake.getBody().get(0);

        if (isLevelMode) {
            if (head[0] < 0 || head[0] >= currentGridSize || head[1] < 0 || head[1] >= currentGridSize) {
                snake.setAlive(false);
            }
        } else {
            if (gameMode == GameMode.NO_WALLS) {
                if (head[0] < 0) head[0] = currentGridSize - 1;
                else if (head[0] >= currentGridSize) head[0] = 0;
                if (head[1] < 0) head[1] = currentGridSize - 1;
                else if (head[1] >= currentGridSize) head[1] = 0;
            } else {
                if (head[0] < 0 || head[0] >= currentGridSize || head[1] < 0 || head[1] >= currentGridSize) {
                    snake.setAlive(false);
                }
            }
        }

        for (int i = 1; i < snake.getBody().size(); i++) {
            if (head[0] == snake.getBody().get(i)[0] && head[1] == snake.getBody().get(i)[1]) {
                snake.setAlive(false);
                break;
            }
        }

        for (Obstacle o : obstacles) {
            if (head[0] == o.getX() && head[1] == o.getY()) {
                snake.setAlive(false);
                break;
            }
        }

        if (!snake.isAlive()) {
            timer.stop();
            handleGameOver();
        }
    }

    private void checkFood() {
        int[] head = snake.getBody().get(0);
        if (head[0] == food.getX() && head[1] == food.getY()) {
            snake.grow();
            eatenFood++;
            if (!isLevelMode) score += 10;

            SoundManager.playEat();

            if (isSpeedRush) increaseSpeed();

            if (isLevelMode && eatenFood >= requiredFood) {
                timer.stop();
                showLevelWin();
                return;
            }
            // ==================== SỬA Ở ĐÂY ====================
            food.respawn(currentGridSize, currentGridSize, obstacles, snake);
            // =================================================
        }
    }

    public void increaseSpeed() {
        if (timer != null) {
            int currentDelay = timer.getDelay();
            int newDelay = Math.max(25, currentDelay - 7);
            timer.setDelay(newDelay);
        }
    }

    private void showLevelWin() {
        SoundManager.playWin();
        MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(gamePanel);
        parent.showLevelWin(currentLevel.level);
    }

    private void handleGameOver() {
        SoundManager.playHit();
        SoundManager.playLose();

        if (!isLevelMode) FileHandler.saveHighScore(score);

        MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(gamePanel);
        parent.showGameOver(isLevelMode, isLevelMode ? currentLevel.level : score);
    }

    public void handleKeyPress(int keyCode) {
        if (snake == null) return;
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W ||
            keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S ||
            keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A ||
            keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            isMoving = true;
        }

        switch (keyCode) {
            case KeyEvent.VK_UP, KeyEvent.VK_W -> snake.setDirection("UP");
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> snake.setDirection("DOWN");
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> snake.setDirection("LEFT");
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> snake.setDirection("RIGHT");
            case KeyEvent.VK_P -> togglePause();
        }
    }

    public void togglePause() {
        showPauseScreen();
    }

    public void pauseGame() {
        isPaused = true;
        if (timer != null && timer.isRunning()) timer.stop();
    }

    public void resumeGame() {
        isPaused = false;
        if (timer != null && !timer.isRunning()) timer.start();
    }

    public void showPauseScreen() {
        pauseGame();
        if (gamePanel != null) {
            MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(gamePanel);
            if (parent != null) {
                parent.showPause();
            }
        }
    }

    public void restartCurrentLevel() {
        MainFrame parent = (gamePanel != null) 
                ? (MainFrame) SwingUtilities.getWindowAncestor(gamePanel) 
                : null;

        if (isLevelMode && currentLevel != null) {
            if (parent != null) {
                parent.startLevel(currentLevel);
            } else {
                startLevel(currentLevel);
            }
        } else {
            if (parent != null) {
                parent.startEndless(difficulty, gameMode, currentGridSize);
            } else {
                startEndless(difficulty, gameMode, currentGridSize);
            }
        }
    }

    public Snake getSnake() { return snake; }
    public Food getFood() { return food; }
    public List<Obstacle> getObstacles() { return obstacles; }
    public int getGridSize() { return currentGridSize; }
    public int getScore() { return isLevelMode ? eatenFood : score; }
    public int getHighScore() { return highScore; }
    public int getRequiredFood() { return requiredFood; }
    public boolean isLevelMode() { return isLevelMode; }
    public int getEatenFood() { return eatenFood; }
    public boolean isPaused() { return isPaused; }
    public int getCurrentLevel() { return currentLevel != null ? currentLevel.level : 0; }
    public GameMode getGameMode() { return gameMode; }

    public static int getGlobalHighScore() { 
        return FileHandler.readHighScore(); 
    }
}
