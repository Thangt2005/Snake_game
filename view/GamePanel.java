package view;

import controller.GameController;
import model.Obstacle;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class GamePanel extends JPanel {
    private final GameController controller;
    private int gridSize = 15;
    private static final int HEADER_HEIGHT = 80;

    public GamePanel(GameController c) {
        this.controller = c;
        setLayout(null);
        setFocusable(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                controller.handleKeyPress(e.getKeyCode());
            }
        });

        JButton pauseBtn = new JButton("(P) Tạm dừng");
        pauseBtn.setBounds(420, 15, 150, 40);
        pauseBtn.setBackground(new Color(0, 86, 150));
        pauseBtn.setForeground(Color.WHITE);
        pauseBtn.setFont(new Font("Arial", Font.BOLD, 14));
        pauseBtn.addActionListener(e -> controller.togglePause());
        add(pauseBtn);
    }

    public void updateGridSize(int newSize) {
        this.gridSize = newSize;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics raw) {
        super.paintComponent(raw);
        Graphics2D g = (Graphics2D) raw;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int availableHeight = getHeight() - HEADER_HEIGHT;

        int side = Math.min(w, availableHeight);
        int tile = side / gridSize;
        int offsetX = (w - tile * gridSize) / 2;
        int offsetY = HEADER_HEIGHT + (availableHeight - tile * gridSize) / 2;

        // ==================== HEADER ====================
        g.setColor(new Color(0, 86, 150));
        g.fillRect(0, 0, w, HEADER_HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 26));

        String levelText = controller.isLevelMode() ? "Màn " + controller.getCurrentLevel() : "Vô tận";
        g.drawString(levelText, 30, 55);

        String foodText = "Mồi: " + controller.getEatenFood() + "/" + 
                         (controller.isLevelMode() ? controller.getRequiredFood() : "∞");
        g.drawString(foodText, 220, 55);

        // ==================== NỀN MAP ====================
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                g.setColor((r + c) % 2 == 0 ? new Color(170, 215, 81) : new Color(155, 200, 70));
                g.fillRect(offsetX + c * tile, offsetY + r * tile, tile, tile);
            }
        }

        // ==================== VẬT CẢN ====================
        for (Obstacle o : controller.getObstacles()) {
            int x = offsetX + o.getX() * tile;
            int y = offsetY + o.getY() * tile;

            g.setColor(new Color(139, 69, 19));
            g.fillRoundRect(x + 4, y + 4, tile - 8, tile - 8, 10, 10);

            g.setColor(new Color(180, 100, 40));
            g.drawRoundRect(x + 6, y + 6, tile - 12, tile - 12, 8, 8);

            g.setColor(new Color(80, 40, 10));
            g.setStroke(new BasicStroke(3.5f));
            g.drawLine(x + 10, y + 10, x + tile - 10, y + tile - 10);
            g.drawLine(x + 10, y + tile - 10, x + tile - 10, y + 10);
        }

        // ==================== QUẢ TÁO ====================
        int fx = offsetX + controller.getFood().getX() * tile;
        int fy = offsetY + controller.getFood().getY() * tile;

        g.setColor(new Color(220, 20, 60));
        g.fillOval(fx + 4, fy + 4, tile - 8, tile - 8);

        g.setColor(new Color(34, 100, 34));
        g.fillRect(fx + tile/2 - 3, fy + 4, 6, tile/4);

        g.setColor(new Color(34, 139, 34));
        g.fillOval(fx + tile/2, fy + 6, tile/3, tile/4);

        // ==================== RẮN KIỂU KHỐI VUÔNG (MỚI) ====================
        List<int[]> body = controller.getSnake().getBody();
        if (body.isEmpty()) return;

        for (int i = 0; i < body.size(); i++) {
            int x = offsetX + body.get(i)[0] * tile;
            int y = offsetY + body.get(i)[1] * tile;

            if (i == 0) {
                // ==================== ĐẦU RẮN + MẮT XOAY ====================
                g.setColor(new Color(30, 144, 255));
                g.fillRoundRect(x + 3, y + 3, tile - 6, tile - 6, 28, 28);

                String dir = controller.getSnake().getDirection();
                int eyeSize = tile / 3;
                int pupilSize = tile / 6;
                int pupilOffset = tile / 10;

                // Mắt trắng
                g.setColor(Color.WHITE);
                switch (dir) {
                    case "RIGHT" -> { g.fillOval(x + tile*2/3-4, y + tile/5, eyeSize, eyeSize); g.fillOval(x + tile*2/3-4, y + tile*3/5-4, eyeSize, eyeSize); }
                    case "LEFT"  -> { g.fillOval(x + tile/5-2, y + tile/5, eyeSize, eyeSize); g.fillOval(x + tile/5-2, y + tile*3/5-4, eyeSize, eyeSize); }
                    case "UP"    -> { g.fillOval(x + tile/5, y + tile/5-2, eyeSize, eyeSize); g.fillOval(x + tile*3/5-4, y + tile/5-2, eyeSize, eyeSize); }
                    case "DOWN"  -> { g.fillOval(x + tile/5, y + tile*2/3-6, eyeSize, eyeSize); g.fillOval(x + tile*3/5-4, y + tile*2/3-6, eyeSize, eyeSize); }
                }

                // Đồng tử đen
                g.setColor(Color.BLACK);
                switch (dir) {
                    case "RIGHT" -> { g.fillOval(x + tile*2/3 + pupilOffset, y + tile/5 + 6, pupilSize, pupilSize); g.fillOval(x + tile*2/3 + pupilOffset, y + tile*3/5 + 2, pupilSize, pupilSize); }
                    case "LEFT"  -> { g.fillOval(x + tile/5 - pupilOffset - 2, y + tile/5 + 6, pupilSize, pupilSize); g.fillOval(x + tile/5 - pupilOffset - 2, y + tile*3/5 + 2, pupilSize, pupilSize); }
                    case "UP"    -> { g.fillOval(x + tile/5 + 6, y + tile/5 - pupilOffset, pupilSize, pupilSize); g.fillOval(x + tile*3/5 + 2, y + tile/5 - pupilOffset, pupilSize, pupilSize); }
                    case "DOWN"  -> { g.fillOval(x + tile/5 + 6, y + tile*2/3 - pupilOffset - 2, pupilSize, pupilSize); g.fillOval(x + tile*3/5 + 2, y + tile*2/3 - pupilOffset - 2, pupilSize, pupilSize); }
                }
            } else {
                // ==================== THÂN RẮN (KHỐI VUÔNG BO TRÒN) ====================
                g.setColor(new Color(30, 144, 255));
                RoundRectangle2D rect = new RoundRectangle2D.Float(x + 3, y + 3, tile - 6, tile - 6, 22, 22);
                g.fill(rect);

                // Viền sáng
                g.setColor(new Color(80, 170, 255));
                g.setStroke(new BasicStroke(2.5f));
                g.drawRoundRect(x + 5, y + 5, tile - 10, tile - 10, 20, 20);
            }
        }
    }
}
