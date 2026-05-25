package check;

import java.util.List;

import model.Food;
import model.Obstacle;
import model.Snake;

public class FoodChecker {

    private Snake snake;
    private Food food;

    public FoodChecker(Snake snake, Food food) {
        this.snake = snake;
        this.food = food;
    }

    public int checkFood(
            int score,
            boolean isLevelMode,
            boolean isSpeedRush,
            int eatenFood,
            int currentGridSize,
            List<Obstacle> obstacles
    ) {

        int[] head = snake.getBody().get(0);

        // Va chạm thức ăn
        if (head[0] == food.getX()
                && head[1] == food.getY()) {

            snake.grow();

            eatenFood++;

            if (!isLevelMode) {
                score += 10;
            }

            // Spawn lại food
            food.respawn(
                    currentGridSize,
                    currentGridSize,
                    obstacles,
                    snake
            );
        }

        return score;
    }
}