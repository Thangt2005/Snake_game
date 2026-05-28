import java.util.Scanner;

public class GameView {
    public static void main(String[] args) {
        // Khởi tạo các thành phần MVC
        Snake snakeModel = new Snake();
        SnakeController controller = new SnakeController(snakeModel);
        
        System.out.println("--- GAME RẮN SĂN MỒI ---");
        System.out.println("Hướng hiện tại: " + snakeModel.getCurrentDirection());
        
        // Giả lập người chơi nhấn phím
        // Đang đi RIGHT, thử nhấn LEFT
        controller.handleInput(Snake.Direction.LEFT); 
        
        // Thử nhấn UP (Hợp lệ)
        controller.handleInput(Snake.Direction.UP);
        
        // Sau khi đã đi UP, thử nhấn DOWN (Bị chặn)
        controller.handleInput(Snake.Direction.DOWN);
    }
}
