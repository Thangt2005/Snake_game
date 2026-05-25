package check;

package check;

import java.util.List;
import model.Snake;
import model.Food;
import model.Obstacle;

public class SpecialFoodHandler {
    private Snake snake;

    public SpecialFoodHandler(Snake snake) {
        this.snake = snake;
    }

    /**
     * PHẦN LÀM THÊM - LẦN 2: Xử lý tương tác nâng cao với các loại thực thể thức ăn đặc biệt
     * @param food Loại thức ăn mà rắn vừa va chạm trúng
     * @param currentScore Số điểm hiện tại trước khi va chạm
     * @return Số điểm mới sau khi áp dụng hiệu ứng va chạm đặc biệt
     */
    public int applyCollisionEffect(Food food, int currentScore) {
        // Giả định Class Food hiện tại của bạn có phương thức getType() trả về String hoặc Enum
        String foodType = food.getType(); 
        
        if (foodType == null) {
            return currentScore + 10; // Mồi mặc định lần 1
        }

        switch (foodType) {
            case "POISON_MUSHROOM":
                // 1. Va chạm Mồi Độc: Bị phạt trừ điểm và co ngắn cơ thể (Xử lý nâng cao)
                System.out.println("[Collision Event] Rắn va chạm với nấm độc!");
                
                // Giảm chiều dài rắn (Bằng cách xóa bớt mắt xích cuối cùng ở đuôi)
                List<int[]> body = snake.getBody();
                if (body.size() > 2) { // Giữ lại độ dài tối thiểu để rắn không chết lập tức
                    body.remove(body.size() - 1); 
                }
                
                // Trừ 20 điểm (Đảm bảo điểm không bị âm)
                return Math.max(0, currentScore - 20);

            case "GOLDEN_APPLE":
                // 2. Va chạm Mồi Vàng: Tăng mạnh điểm số nhưng không làm rắn dài ra quá nhiều
                System.out.println("[Collision Event] Rắn va chạm với Táo Vàng!");
                snake.grow(); // Gọi hàm lớn lên cơ bản
                return currentScore + 50; // Thưởng lớn 50 điểm

            case "SHIELD_BUFF":
                // 3. Va chạm Vật phẩm Khiên: Kích hoạt trạng thái bất tử (Bỏ qua va chạm vật cản)
                System.out.println("[Collision Event] Kích hoạt Trạng thái Bất Tử nhờ vật phẩm Khiên!");
                // Bạn có thể set một flag trạng thái trong Snake hoặc biến môi trường game
                snake.setInvincible(true); 
                return currentScore + 5;

            default:
                // Mồi thường của lần 1
                snake.grow();
                return currentScore + 10;
        }
    }
}