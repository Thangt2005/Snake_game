package snake;

import java.awt.*;

public class PortalManager {
    public Point portalA = new Point(100, 500);
    public Point portalB = new Point(500, 100);
    private int unitSize;

    public PortalManager(int unitSize) {
        this.unitSize = unitSize;
    }

    public void draw(Graphics g) {
        // Vẽ Cổng A màu xanh dương
        g.setColor(new Color(0, 191, 255));
        g.fillOval(portalA.x, portalA.y, unitSize, unitSize);
        g.setColor(Color.WHITE);
        g.drawOval(portalA.x, portalA.y, unitSize, unitSize);

        // Vẽ Cổng B màu cam
        g.setColor(new Color(255, 140, 0));
        g.fillOval(portalB.x, portalB.y, unitSize, unitSize);
        g.setColor(Color.WHITE);
        g.drawOval(portalB.x, portalB.y, unitSize, unitSize);
    }

    // Kiểm tra va chạm đầu rắn với Portal và dịch chuyển
    public void checkPortalCollision(Point head) {
        if (head.x == portalA.x && head.y == portalA.y) {
            head.x = portalB.x;
            head.y = portalB.y;
        } else if (head.x == portalB.x && head.y == portalB.y) {
            head.x = portalA.x;
            head.y = portalA.y;
        }
    }
}
