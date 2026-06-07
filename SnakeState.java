package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * THÀNH PHẦN MODEL: Lưu giữ dữ liệu, trạng thái cốt lõi của con rắn.
 * Ánh xạ trực tiếp tới UC-02: Điều khiển hướng di chuyển.
 */
public class SnakeState {
    private final List<Point> body;
    private Direction currentDirection;

    public SnakeState() {
        body = new ArrayList<>();
        // Khởi tạo rắn ban đầu dài 3 đốt
        body.add(new Point(5, 5)); // Đầu rắn (Head)
        body.add(new Point(4, 5));
        body.add(new Point(3, 5));
        // Hướng đi mặc định ban đầu là sang phải (RIGHT)
        currentDirection = Direction.RIGHT;
    }

    /**
     * Phương thức Getter để cung cấp trạng thái hướng hiện tại cho Controller
     * Ánh xạ tới [Bước 3 trong Use Case / Sequence Diagram]
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Phương thức Setter để lưu giữ hướng đi mới được xác nhận hợp lệ từ Controller truyền qua
     * Ánh xạ tới [Bước 5 & 6 trong Use Case / Sequence Diagram]
     */
    public void setDirection(Direction newDir) {
        this.currentDirection = newDir;
    }

    /**
     * Hàm dịch chuyển con rắn theo hướng hiện tại (Chạy theo nhịp máy của Controller)
     */
    public void move() {
        // Lấy tọa độ đầu rắn hiện tại
        Point head = body.get(0);
        Point newHead = new Point(head.x, head.y);

        // Tính toán tọa độ ô kế tiếp dựa theo trạng thái currentDirection đang lưu giữ
        switch (currentDirection) {
            case UP:    newHead.y--; break;
            case DOWN:  newHead.y++; break;
            case LEFT:  newHead.x--; break;
            case RIGHT: newHead.x++; break;
        }

        // Đẩy đầu mới vào vị trí tiên phong của danh sách đốt thân
        body.add(0, newHead);
        // Cắt bỏ đốt đuôi cuối cùng để tạo hiệu ứng tịnh tiến (Nếu không ăn mồi)
        body.remove(body.size() - 1);
    }

    public List<Point> getBody() {
        return body;
    }
}