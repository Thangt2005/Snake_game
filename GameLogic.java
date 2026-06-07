package Controller;

import Model.SnakeState;
import Model.Direction; // Giả sử nhóm có Enum Direction gồm: UP, DOWN, LEFT, RIGHT
import java.awt.event.KeyEvent;

/**
 * THÀNH PHẦN CONTROLLER: Điều phối và xử lý luật chơi cho game.
 * Ánh xạ trực tiếp tới UC-02: Điều khiển hướng di chuyển.
 */
public class GameLogic {
    private final SnakeState snake;
    private boolean isGameOver;
    private boolean isPaused;
    
    // PHẦN LÀM THÊM: Biến cờ hiệu để giải quyết [LUỒNG THAY THẾ 4.2] - Xung đột nhịp (Tick Conflict)
    // Đảm bảo người chơi chỉ được đổi hướng 1 lần duy nhất trong 1 chu kỳ thời gian (Tick) của Game Loop
    private boolean directionChangedThisTick = false;

    public GameLogic(SnakeState snake) {
        this.snake = snake;
        this.isGameOver = false;
        this.isPaused = false;
    }

    /**
     * Hàm tiếp nhận dữ liệu phím bấm đầu vào từ View gửi sang
     */
    public void handleInput(int keyCode) {
        /*
         * =================================────────────────===================
         * [LUỒNG THAY THẾ 4.5] & [4.6]: Kiểm tra Trạng thái Tạm dừng / Kết thúc
         * Nếu game đang Pause hoặc Game Over thì từ chối xử lý input điều hướng.
         * ====================================================================
         */
        if (isGameOver || isPaused) {
            return; 
        }

        /*
         * =================================────────────────===================
         * PHẦN LÀM THÊM - XỬ LÝ [LUỒNG THAY THẾ 4.2]: Xung đột nhịp (Tick Conflict)
         * Nếu trong cùng 1 Tick máy mà người chơi đã đổi hướng rồi, thì khóa 
         * và bỏ qua (Ignore) các phím hướng bấm sau cho đến khi qua Tick mới.
         * ====================================================================
         */
        if (directionChangedThisTick) {
            return; // Khóa input hướng bấm nhanh liên tục trong 1 chu kỳ
        }

        // Chuyển đổi mã phím vật lý (keyCode) sang hằng số logic hướng (Direction)
        Direction newDirection = null;
        switch (keyCode) {
            case KeyEvent.VK_UP:    case KeyEvent.VK_W: newDirection = Direction.UP; break;
            case KeyEvent.VK_DOWN:  case KeyEvent.VK_S: newDirection = Direction.DOWN; break;
            case KeyEvent.VK_LEFT:  case KeyEvent.VK_A: newDirection = Direction.LEFT; break;
            case KeyEvent.VK_RIGHT: case KeyEvent.VK_D: newDirection = Direction.RIGHT; break;
            
            /*
             * =================================────────────────===================
             * [LUỒNG NGOẠI LỆ E1 / LUỒNG THAY THẾ 4.1]: Phím không hợp lệ
             * Người chơi bấm các phím chữ, số ngẫu nhiên không liên quan -> Bỏ qua.
             * ====================================================================
             */
            default: return; 
        }

        if (newDirection != null) {
            /*
             * =================================────────────────===================
             * [BƯỚC 3 TRONG USE CASE / SEQUENCE DIAGRAM]:
             * Controller truy vấn trạng thái hướng hiện tại của con rắn từ SnakeState (Model).
             * ====================================================================
             */
            Direction currentDir = snake.getCurrentDirection();

            /*
             * =================================────────────────===================
             * [BƯỚC 4 TRONG USE CASE] & [LUỒNG THAY THẾ 4.1]: Chặn lỗi quay đầu 180 độ
             * Nếu hướng bấm mới đối nghịch trực tiếp 180 độ với hướng cũ (Ví dụ: Đang đi UP 
             * mà bấm DOWN) -> Thực hiện hành động Bỏ qua (Ignore) để tránh tự sát.
             * ====================================================================
             */
            if ((currentDir == Direction.UP && newDirection == Direction.DOWN) ||
                (currentDir == Direction.DOWN && newDirection == Direction.UP) ||
                (currentDir == Direction.LEFT && newDirection == Direction.RIGHT) ||
                (currentDir == Direction.RIGHT && newDirection == Direction.LEFT)) {
                return; // Bỏ qua phím bấm đối nghịch góc 180 độ
            }

            /*
             * =================================────────────────===================
             * [BƯỚC 5 & 6 TRONG USE CASE / SEQUENCE DIAGRAM]: Xác nhận hợp lệ & Cập nhật
             * Controller gọi hàm setDirection(newDirection) để cập nhật dữ liệu vào Model.
             * ====================================================================
             */
            snake.setDirection(newDirection);
            
            // Bật cờ đánh dấu đã đổi hướng thành công trong Tick này (Phần làm thêm bảo vệ nhịp máy)
            directionChangedThisTick = true; 
        }
    }

    /**
     * Hàm chạy theo chu kỳ thời gian (Được gọi liên tục bởi Timer của Game Loop)
     */
    public void updateGame() {
        if (!isGameOver && !isPaused) {
            // Cho rắn di chuyển bước tiếp theo dựa trên hướng đã được cập nhật ổn định trong Model
            snake.move();
            
            // Kiểm tra va chạm, ăn mồi... (Logic các UC khác)
            
            /*
             * =================================────────────────────────────────===
             * PHẦN LÀM THÊM: Giải phóng cờ hiệu nhịp máy
             * Khi một nhịp (Tick) kết thúc và rắn đã dịch chuyển xong, reset cờ hiệu 
             * về false để cho phép người chơi bấm đổi hướng ở chu kỳ (Tick) tiếp theo.
             * ====================================================================
             */
            directionChangedThisTick = false;
            
            // Gọi View làm mới khung hình (Vẽ lại đồ họa frame mới) -> Khớp với [Bước 7 Use Case]
        }
    }
}