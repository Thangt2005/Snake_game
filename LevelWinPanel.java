package view;

import controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LevelWinPanel extends JPanel {

    private final MainFrame frame;
    private final GameController controller;
    private final int currentLevel;

    private BufferedImage backgroundImage;
    private BufferedImage buttonImage;

    private final Font titleFont = new Font("Arial", Font.BOLD, 48);
    private final Font subtitleFont = new Font("Arial", Font.BOLD, 24);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 22);

    public LevelWinPanel(MainFrame frame, GameController controller, int currentLevel) {
        this.frame = frame;
        this.controller = controller;
        this.currentLevel = currentLevel;

        setLayout(null);
        setPreferredSize(new Dimension(600, 700));

        loadImages();

        JLabel title = new JLabel("CHÚC MỪNG!", SwingConstants.CENTER);
        title.setFont(titleFont);
        title.setForeground(new Color(255, 215, 0));
        title.setBounds(0, 80, 600, 90);
        add(title);

        JLabel subtitle = new JLabel("Bạn đã hoàn thành Màn " + currentLevel, SwingConstants.CENTER);
        subtitle.setFont(subtitleFont);
        subtitle.setForeground(new Color(255, 240, 180));
        subtitle.setBounds(0, 170, 600, 50);
        add(subtitle);

        createImageButton("Màn tiếp theo", 180, 250, e -> nextLevel());
        createImageButton("Chơi lại màn này", 180, 320, e -> replayLevel());
        createImageButton("Quay về Menu", 180, 390, e -> frame.showMenu());
    }

    private void loadImages() {
    try {
        backgroundImage = ImageIO.read(new File("images/menu_background.png"));
        buttonImage = ImageIO.read(new File("images/button_normal.png"));
    } catch (IOException e) {
        System.err.println("Không load được ảnh Win Panel");
    }
}
    private void createImageButton(String text, int x, int y, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (buttonImage != null) {
                    g.drawImage(buttonImage, 0, 0, getWidth(), getHeight(), null);
                }
                Graphics2D g2 = (Graphics2D) g;
                g2.setFont(buttonFont);
                g2.setColor(new Color(40, 25, 10));
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent()) / 2 - 3;
                g2.drawString(getText(), tx, ty);
            }
        };
        btn.setBounds(x, y, 240, 68);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(listener);
        add(btn);
    }

    private void nextLevel() {
        int next = Math.min(currentLevel + 1, 5);
        frame.startLevel(controller.getLevelConfig(next));
    }

    private void replayLevel() {
        frame.startLevel(controller.getLevelConfig(currentLevel));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
