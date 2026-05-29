package snake;

import java.awt.*;
import java.util.ArrayList;

public class DestructibleWallManager {
    public ArrayList<Point> walls = new ArrayList<>();
    private int unitSize;

    public DestructibleWallManager(int unitSize) {
        this.unitSize = unitSize;
        // Đặt một vài khối gạch ở góc map để test
        walls.add(new Point(300, 100));
        walls.add(new Point(300, 125));
        walls.add(new Point(300, 150));
    }

    public void draw(Graphics g) {
        g.setColor(new Color(139, 69, 19)); // Màu gạch nung
        for (Point p : walls) {
            g.fillRect(p.x, p.y, unitSize, unitSize);
            g.setColor(Color.ORANGE);
            g.drawRect(p.x, p.y, unitSize, unitSize);
            g.setColor(new Color(139, 69, 19));
        }
    }

    // Trả về true nếu đâm vào tường thường (chết), false nếu phá hủy được tường
    public boolean handleCollision(Point head, boolean hasHammerBuff) {
        for (int i = 0; i < walls.size(); i++) {
            if (walls.get(i).x == head.x && walls.get(i).y == head.y) {
                if (hasHammerBuff) {
                    walls.remove(i); // Phá hủy khối gạch
                    return false;    // Không chết
                }
                return true; // Chết vì không có buff Búa
            }
        }
        return false;
    }
}
