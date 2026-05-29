import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JFrame {
    public SnakeGame() {
        this.add(new ProGamePanel());
        this.setTitle("Snake Luxury Edition - Smooth Graphics & Tech");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new SnakeGame();
    }
}

class ProGamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;

    Point playerHead = new Point(100, 100);
    ArrayList<Point> playerBody = new ArrayList<>();
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random = new Random();

    // Thức ăn & Buff thần thoại
    int appleX, appleY;
    int buffX, buffY;
    boolean isBuffSpawned = false;
    int buffTimer = 0; 

    // Bộ quản lý tính năng (Đã tích hợp bên dưới)
    LocalPortalManager portalManager;
    LocalDestructibleWallManager wallManager;

    public ProGamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(18, 22, 30)); // Nền Dark Tech đẳng cấp
        this.setFocusable(true);
        this.addKeyListener(new ControlAdapter());

        portalManager = new LocalPortalManager(UNIT_SIZE);
        wallManager = new LocalDestructibleWallManager(UNIT_SIZE);

        initGame();
    }

    public void initGame() {
        playerBody.clear();
        playerHead.setLocation(100, 100);
        playerBody.add(new Point(playerHead));
        playerBody.add(new Point(75, 100));
        playerBody.add(new Point(50, 100));
        
        // Reset lại tường nếu chơi ván mới
        wallManager = new LocalDestructibleWallManager(UNIT_SIZE);
        isBuffSpawned = false;
        buffTimer = 0;
        direction = 'R';

        spawnApple();
        running = true;
        timer = new Timer(130, this); 
        timer.start();
    }

    public void spawnApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
        
        // Tỷ lệ 35% xuất hiện Đồng Xu Vàng phá tường
        if (!isBuffSpawned && random.nextInt(100) < 35) {
            buffX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            buffY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
            isBuffSpawned = true;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // KÍCH HOẠT KHỬ RĂNG CƯA CAO CẤP (Giúp đồ họa mượt như Slither.io)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Vẽ lưới nền mờ carbon
        g2d.setColor(new Color(28, 35, 48));
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g2d.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g2d.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }

        if (running) {
            // 2. Vẽ các thực thể địa hình động
            portalManager.draw(g2d);
            wallManager.draw(g2d);

            // 3. Vẽ Trái táo 3D bóng bẩy
            g2d.setColor(new Color(0, 0, 0, 80));
            g2d.fillOval(appleX + 2, appleY + 4, UNIT_SIZE - 2, UNIT_SIZE - 2); 
            RadialGradientPaint appleGlow = new RadialGradientPaint(
                new Point(appleX + 8, appleY + 8), UNIT_SIZE/2f,
                new float[]{0.0f, 1.0f}, new Color[]{new Color(255, 80, 80), new Color(180, 0, 0)}
            );
            g2d.setPaint(appleGlow);
            g2d.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // 4. Vẽ Vật Phẩm Đồng Xu Vàng Lấp Lánh
            if (isBuffSpawned) {
                g2d.setColor(new Color(255, 215, 0, 80)); 
                g2d.fillOval(buffX - 4, buffY - 4, UNIT_SIZE + 8, UNIT_SIZE + 8);
                
                GradientPaint goldPaint = new GradientPaint(buffX, buffY, new Color(255, 255, 120), buffX + UNIT_SIZE, buffY + UNIT_SIZE, new Color(218, 165, 32));
                g2d.setPaint(goldPaint);
                g2d.fillOval(buffX, buffY, UNIT_SIZE, UNIT_SIZE);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString("★", buffX + 6, buffY + 17); 
            }

            // 5. Vẽ rắn chuỗi ngọc uốn lượn
            boolean hasBuff = (buffTimer > 0);
            for (int i = playerBody.size() - 1; i >= 0; i--) {
                Point p = playerBody.get(i);
                
                if (i == 0) { 
                    // Đầu rắn đổ bóng
                    g2d.setColor(new Color(0, 0, 0, 100));
                    g2d.fillOval(p.x + 2, p.y + 3, UNIT_SIZE, UNIT_SIZE);

                    g2d.setColor(hasBuff ? new Color(255, 215, 0) : new Color(0, 200, 110));
                    g2d.fillOval(p.x, p.y, UNIT_SIZE, UNIT_SIZE);

                    // Vẽ đôi mắt động xoay theo hướng
                    g2d.setColor(Color.WHITE);
                    int eyeSize = 6;
                    int eyeOffset = 3;
                    int e1x = p.x + eyeOffset, e1y = p.y + eyeOffset;
                    int e2x = p.x + UNIT_SIZE - eyeSize - eyeOffset, e2y = p.y + eyeOffset;

                    if (direction == 'D') { e1y += 10; e2y += 10; }
                    else if (direction == 'L') { e2x -= 10; e1y += 5; e2y -= 5; }
                    else if (direction == 'R') { e1x += 10; e1y -= 5; e2y += 5; }
                    
                    g2d.fillOval(e1x, e1y, eyeSize, eyeSize);
                    g2d.fillOval(e2x, e2y, eyeSize, eyeSize);
                    g2d.setColor(Color.BLACK); 
                    g2d.fillOval(e1x + 1, e1y + 1, eyeSize - 2, eyeSize - 2);
                    g2d.fillOval(e2x + 1, e2y + 1, eyeSize - 2, eyeSize - 2);

                } else {
                    // Thân rắn dạng hạt tròn liên kết bo góc mượt mà
                    if (hasBuff) {
                        g2d.setColor(i % 2 == 0 ? new Color(255, 235, 150) : new Color(230, 190, 50));
                    } else {
                        g2d.setColor(i % 2 == 0 ? new Color(0, 170, 90) : new Color(0, 140, 70));
                    }
                    g2d.fill(new Ellipse2D.Double(p.x - 1, p.y - 1, UNIT_SIZE + 2, UNIT_SIZE + 2));
                }
            }

            // 6. Giao diện HUD hiện đại
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
            g2d.drawString("SCORE: " + (playerBody.size() - 3), 20, 30);
            
            if (hasBuff) {
                g2d.setColor(new Color(255, 69, 0));
                g2d.drawString("🔥 TRẠNG THÁI HỎA CẦU (Phá Tường): " + (buffTimer / 7) + "s", 180, 30);
            }
        } else {
            drawGameOver(g2d);
        }
    }

    public void move() {
        for (int i = playerBody.size() - 1; i > 0; i--) {
            playerBody.get(i).setLocation(playerBody.get(i - 1));
        }

        switch (direction) {
            case 'U' -> playerHead.y -= UNIT_SIZE;
            case 'D' -> playerHead.y += UNIT_SIZE;
            case 'L' -> playerHead.x -= UNIT_SIZE;
            case 'R' -> playerHead.x += UNIT_SIZE;
        }
        playerBody.get(0).setLocation(playerHead);
    }

    public void checkGameMechanics() {
        portalManager.update();

        if (buffTimer > 0) buffTimer--;

        // 1. Va chạm Cổng Dịch Chuyển
        portalManager.checkPortalCollision(playerHead);

        // 2. Va chạm Tường Gạch Phá Hủy
        if (wallManager.handleCollision(playerHead, (buffTimer > 0))) {
            running = false;
        }

        // Biên màn hình hoặc tự cắn thân
        if (playerHead.x < 0 || playerHead.x >= SCREEN_WIDTH || playerHead.y < 0 || playerHead.y >= SCREEN_HEIGHT) {
            running = false;
        }
        for (int i = playerBody.size() - 1; i > 0; i--) {
            if (playerHead.x == playerBody.get(i).x && playerHead.y == playerBody.get(i).y) {
                running = false;
            }
        }

        // Ăn táo
        if (playerHead.x == appleX && playerHead.y == appleY) {
            playerBody.add(new Point(-50, -50)); 
            spawnApple();
        }

        // Ăn Đồng xu vàng kích hoạt trạng thái Hỏa cầu phá tường
        if (isBuffSpawned && playerHead.x == buffX && playerHead.y == buffY) {
            isBuffSpawned = false;
            buffTimer = 70; // Duy trì trạng thái trong tầm 7-10 giây
        }

        if (!running) timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkGameMechanics();
        }
        repaint();
    }

    private void drawGameOver(Graphics2D g2d) {
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g2d.setColor(new Color(255, 75, 75));
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 42));
        g2d.drawString("GAME OVER", 180, SCREEN_HEIGHT / 2 - 20);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        g2d.drawString("Nhấn [SPACE] để làm lại ván mới", 165, SCREEN_HEIGHT / 2 + 30);
    }

    private class ControlAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && direction != 'R') direction = 'L';
            if (key == KeyEvent.VK_RIGHT && direction != 'L') direction = 'R';
            if (key == KeyEvent.VK_UP && direction != 'D') direction = 'U';
            if (key == KeyEvent.VK_DOWN && direction != 'U') direction = 'D';
            if (key == KeyEvent.VK_SPACE && !running) {
                initGame(); 
            }
        }
    }

    // ==========================================
    // INNER CLASS 1: QUẢN LÝ CỔNG DỊCH CHUYỂN
    // ==========================================
    class LocalPortalManager {
        public Point portalA = new Point(125, 450);
        public Point portalB = new Point(450, 125);
        private final int unitSize;
        private float pulse = 0f;

        public LocalPortalManager(int unitSize) {
            this.unitSize = unitSize;
        }

        public void update() {
            pulse += 0.1f;
        }

        public void draw(Graphics2D g2d) {
            double offset = Math.sin(pulse) * 4;
            // Cổng A: Màu xanh neon
            drawGlowingPortal(g2d, portalA, new Color(0, 238, 255), offset);
            // Cổng B: Màu cam Plasma
            drawGlowingPortal(g2d, portalB, new Color(255, 102, 0), offset);
        }

        private void drawGlowingPortal(Graphics2D g2d, Point p, Color baseColor, double offset) {
            g2d.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 60));
            g2d.fill(new Ellipse2D.Double(p.x - 6 - offset/2, p.y - 6 - offset/2, unitSize + 12 + offset, unitSize + 12 + offset));

            RadialGradientPaint rgp = new RadialGradientPaint(
                new Point(p.x + unitSize/2, p.y + unitSize/2), unitSize/2f,
                new float[]{0.0f, 0.8f, 1.0f},
                new Color[]{Color.WHITE, baseColor, new Color(15, 15, 15)}
            );
            g2d.setPaint(rgp);
            g2d.fill(new Ellipse2D.Double(p.x, p.y, unitSize, unitSize));
        }

        public void checkPortalCollision(Point head) {
            if (head.x == portalA.x && head.y == portalA.y) {
                head.setLocation(portalB);
            } else if (head.x == portalB.x && head.y == portalB.y) {
                head.setLocation(portalA);
            }
        }
    }

    // ==========================================
    // INNER CLASS 2: QUẢN LÝ TƯỜNG GẠCH PHÁ HỦY
    // ==========================================
    class LocalDestructibleWallManager {
        public ArrayList<Point> walls = new ArrayList<>();
        private final int unitSize;

        public LocalDestructibleWallManager(int unitSize) {
            this.unitSize = unitSize;
            // Đặt hàng gạch chắn ở trung tâm bản đồ
            for (int i = 0; i < 6; i++) {
                walls.add(new Point(275, 175 + (i * unitSize)));
            }
        }

        public void draw(Graphics2D g2d) {
            for (Point p : walls) {
                // Đổ bóng vật lý cho khối gạch
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRect(p.x + 3, p.y + 3, unitSize, unitSize);

                // Màu Gradient khối gạch 3D cực đẹp
                GradientPaint gp = new GradientPaint(p.x, p.y, new Color(205, 92, 92), p.x + unitSize, p.y + unitSize, new Color(105, 30, 30));
                g2d.setPaint(gp);
                g2d.fillRect(p.x, p.y, unitSize, unitSize);

                g2d.setColor(new Color(255, 160, 122));
                g2d.drawRect(p.x, p.y, unitSize, unitSize);
            }
        }

        public boolean handleCollision(Point head, boolean hasHammerBuff) {
            for (int i = 0; i < walls.size(); i++) {
                if (walls.get(i).x == head.x && walls.get(i).y == head.y) {
                    if (hasHammerBuff) {
                        walls.remove(i); // Đập tan viên gạch ra khỏi map
                        return false;   
                    }
                    return true; 
                }
            }
            return false;
        }
    }
}
