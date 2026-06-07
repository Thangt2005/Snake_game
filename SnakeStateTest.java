import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

public class SnakeStateTest {
    private SnakeState snake;

    @BeforeEach
    public void setUp() {
        // Khởi tạo con rắn mới trước mỗi bài test (Giả sử ban đầu đi sang RIGHT)
        snake = new SnakeState(); 
    }

    // 1. Test case kiểm thử phân vùng hợp lệ: Đổi hướng thành công
    @Test
    public void testSetDirectionValid() {
        snake.setDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, snake.getCurrentDirection(), 
            "Rắn phải đổi hướng xuống dưới thành công!");
    }

    // 2. Test case kiểm thử lỗi quay đầu 180 độ (Thầy Song rất thích phần logic này)
    @Test
    public void testPrevent180DegreeTurn() {
        // Rắn đang đi RIGHT, cố tình ép bẻ lái sang LEFT
        snake.setDirection(Direction.LEFT);
        
        // Kết quả mong đợi: Lệnh bị từ chối, rắn vẫn phải giữ hướng cũ là RIGHT
        assertEquals(Direction.RIGHT, snake.getCurrentDirection(), 
            "Hệ thống phải chặn lỗi quay đầu 180 độ, hướng phải giữ nguyên là RIGHT!");
    }
}