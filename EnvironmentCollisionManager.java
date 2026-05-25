package check;


import model.Snake;

public class EnvironmentCollisionManager {
    private Snake snake;
    private int gridSize;

    public EnvironmentCollisionManager(Snake snake, int gridSize) {
        this.snake = snake;
        this.gridSize = gridSize;
    }
    public void checkPortalCollision(int portalA_X, int portalA_Y, int portalB_X, int portalB_Y) {
        int[] head = snake.getBody().get(0);

        // Nếu đầu rắn va chạm trùng khít vào Cổng A
        if (head[0] == portalA_X && head[1] == portalA_Y) {
            System.out.println("[Advanced Collision] Rắn đi vào Cổng Dịch Chuyển A -> Đi ra ở Cổng B!");
            // Dịch chuyển đầu rắn sang tọa độ của Cổng B tức thì
            head[0] = portalB_X;
            head[1] = portalB_Y;
        }
    }
}