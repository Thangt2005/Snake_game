public class Logic {

    private Snake snake;
    private Food food;
    private int score;
    private int eatenFood;

    private boolean isLevelMode;
    private boolean isSpeedRush;
    private int requiredFood;

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
