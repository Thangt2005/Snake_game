public class Logic {

    private Snake snake;
    private Food food;
    private int score;
    private int eatenFood;

    private boolean isLevelMode;
    private boolean isSpeedRush;
    private int requiredFood;
    private int gridSize;
    private List<Obstacle> obstacles;
    private Timer timer;

    public Logic(Snake snake, Food food) {
        this.snake = snake;
        this.food = food;
    }

    public void checkFood() {
        if (!isFoodEaten()) return;
        handleFoodEaten();
    }

    private boolean isFoodEaten() {
        int[] head = snake.getBody().get(0);
        return head[0] == food.getX() && head[1] == food.getY();
    }
    
    private void handleFoodEaten() {
        growSnake();
        updateScore();
        playEatSound();
        handleSpeedMode();

        if (checkLevelComplete()) return;

        respawnFood();
    }

    private void growSnake() {
        snake.grow();
        eatenFood++;
    }

    private void updateScore() {
        if (!isLevelMode) {
            score += 10;
        }
    }

    private void playEatSound() {
        SoundManager.playEat();
    }

    private void handleSpeedMode() {
        if (isSpeedRush) {
            increaseSpeed();
        }
    }

    private boolean checkLevelComplete() {
        if (isLevelMode && eatenFood >= requiredFood) {
            timer.stop();
            showLevelWin();
            return true;
        }
        return false;
    }
    private void respawnFood() {
    food.respawn(gridSize, gridSize, obstacles, snake);
} 
    }

    // giả lập
    private void increaseSpeed() {}
    private void showLevelWin() {}
}
