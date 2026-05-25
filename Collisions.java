package check;

import java.util.List;

import model.Obstacle;
import model.Snake;

public class Collisions {

    private Snake snake;

    public Collisions(Snake snake) {
        this.snake = snake;
    }

    public boolean checkCollision(
    		 boolean isLevelMode,
    	        boolean noWallsMode,
    	        int currentGridSize,
    	        List<Obstacle> obstacles
    ) {

        int[] head = snake.getBody().get(0);

        // Va chạm tường
        if (isLevelMode) {

            if (head[0] < 0 || head[0] >= currentGridSize
                    || head[1] < 0 || head[1] >= currentGridSize) {

                snake.setAlive(false);
            }

        } else {

        	if (noWallsMode) {

                // Xuyên tường
                if (head[0] < 0)
                    head[0] = currentGridSize - 1;
                else if (head[0] >= currentGridSize)
                    head[0] = 0;

                if (head[1] < 0)
                    head[1] = currentGridSize - 1;
                else if (head[1] >= currentGridSize)
                    head[1] = 0;

            } else {

                // Đụng tường chết
                if (head[0] < 0 || head[0] >= currentGridSize
                        || head[1] < 0 || head[1] >= currentGridSize) {

                    snake.setAlive(false);
                }
            }
        }

        // Va chạm thân rắn
        for (int i = 1; i < snake.getBody().size(); i++) {

            if (head[0] == snake.getBody().get(i)[0]
                    && head[1] == snake.getBody().get(i)[1]) {

                snake.setAlive(false);
                break;
            }
        }

        // Va chạm chướng ngại vật
        for (Obstacle o : obstacles) {

            if (head[0] == o.getX()
                    && head[1] == o.getY()) {

                snake.setAlive(false);
                break;
            }
        }

        return snake.isAlive();
    }
}