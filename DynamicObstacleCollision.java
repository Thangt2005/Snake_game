package check;

	import java.util.List;
	import model.Snake;
	import model.Obstacle;

	public class DynamicObstacleCollision {
	    private Snake snake;

	    public DynamicObstacleCollision(Snake snake) {
	        this.snake = snake;
	    }

	    /**
	     * PHẦN LÀM THÊM - LẦN 2: Kiểm tra va chạm với các chướng ngại vật di động (Quái vật / Thiên thạch)
	     * @param movingObstacles Danh sách các vật cản có tọa độ thay đổi liên tục
	     * @return true nếu xảy ra va chạm nguy hiểm (Game Over), false nếu an toàn
	     */
	    public boolean checkDynamicCollision(List<Obstacle> movingObstacles) {
	        if (movingObstacles == null || movingObstacles.isEmpty()) {
	            return false;
	        }

	        // Nếu rắn đang có buff Khiên Bất Tử từ class SpecialFoodHandler, bỏ qua va chạm vật cản
	        if (snake.isInvincible()) {
	            return false; 
	        }

	        int[] head = snake.getBody().get(0);

	        for (Obstacle mo : movingObstacles) {
	            // Kiểm tra xem đầu rắn có trùng khít với tọa độ hiện tại của vật cản di động không
	            if (head[0] == mo.getX() && head[1] == mo.getY()) {
	                System.out.println("[Advanced Collision] Rắn va chạm với Vật cản di động tại vị trí: (" + mo.getX() + "," + mo.getY() + ")");
	                snake.setAlive(false); // Rắn chết
	                return true;
	            }
	        }
	        return false;
	    }
	}
}
