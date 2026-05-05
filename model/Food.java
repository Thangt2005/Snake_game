package model;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public class Food extends GameObject {
    private Random random = new Random();
    private Color color = Color.RED;

    public Food(int x, int y) { 
        super(x, y);
    }

    public Color getColor() { return color; }
    public void setColor(Color c) { if (c != null) color = c; }

    @Override
    public void respawn(int maxW, int maxH) {
        setX(random.nextInt(maxW));
        setY(random.nextInt(maxH));
    }

    /**
     * Respawn an toàn: tránh vật cản + tránh toàn bộ thân rắn
     */
    public void respawn(int maxW, int maxH, List<Obstacle> obstacles, Snake snake) {
        int attempts = 0;
        do {
            respawn(maxW, maxH);
            attempts++;
            if (attempts > 1000) break; // an toàn nếu lưới gần đầy
        } while (collidesWithObstacles(obstacles) || collidesWithSnake(snake));
    }

    private boolean collidesWithObstacles(List<Obstacle> obstacles) {
        for (Obstacle o : obstacles) {
            if (o.getX() == getX() && o.getY() == getY()) return true;
        }
        return false;
    }

    private boolean collidesWithSnake(Snake snake) {
        if (snake == null) return false;
        for (int[] part : snake.getBody()) {
            if (part[0] == getX() && part[1] == getY()) return true;
        }
        return false;
    }
}
