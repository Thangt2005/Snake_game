public class SnakeController {
    private Snake model;

    public SnakeController(Snake model) {
        this.model = model;
    }

    /**
     * Xử lý đầu vào và ngăn chặn lỗi quay đầu 180 độ (UC-02)
     * @param inputDirection Hướng người chơi vừa nhấn
     */
    public void handleInput(Snake.Direction inputDirection) {
        Snake.Direction current = model.getCurrentDirection();

        if (isValidMove(current, inputDirection)) {
            model.setDirection(inputDirection);
            System.out.println("Cập nhật hướng thành: " + inputDirection);
        } else {
            System.out.println("Lỗi 180 độ! Hướng " + inputDirection + " bị từ chối.");
        }
    }

    private boolean isValidMove(Snake.Direction current, Snake.Direction next) {
        // Logic: Nếu hướng mới ngược với hướng cũ thì không hợp lệ
        return !((current == Snake.Direction.UP && next == Snake.Direction.DOWN) ||
                 (current == Snake.Direction.DOWN && next == Snake.Direction.UP) ||
                 (current == Snake.Direction.LEFT && next == Snake.Direction.RIGHT) ||
                 (current == Snake.Direction.RIGHT && next == Snake.Direction.LEFT));
    }
}
