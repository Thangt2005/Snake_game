package view;

import controller.GameController;
import utils.LevelConfig;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LevelSelectPanel extends JPanel {

    private final MainFrame frame;
    private final GameController controller;
    private BufferedImage backgroundImage;
    private BufferedImage buttonImage;

    private final Font buttonFont = new Font("Arial", Font.BOLD, 23);

    public LevelSelectPanel(MainFrame f, GameController c) {
        this.frame = f;
        this.controller = c;
        setLayout(null);

        loadImages();

        // Nút Quay lại dùng ảnh button_normal.png
        JButton backBtn = createButton("← QUAY LẠI");
        backBtn.setBounds(30, 20, 220, 55);
        backBtn.addActionListener(e -> frame.showMenu());
        add(backBtn);

        // 5 nút màn chơi - khoảng cách đều và đẹp hơn
        int startY = 130;
        int spacing = 80;

        for (int i = 1; i <= 5; i++) {
            final int lvl = i;
            JButton btn = createButton("MÀN " + i);
            btn.setBounds(180, startY, 240, 68);
            btn.addActionListener(e -> {
                LevelConfig cfg = controller.getLevelConfig(lvl);
                frame.startLevel(cfg);
            });
            add(btn);
            startY += spacing;
        }
    }

   private void loadImages() {
    try {
        backgroundImage = ImageIO.read(new File("images/menu_background.png"));
        buttonImage = ImageIO.read(new File("images/button_normal.png"));
    } catch (IOException e) {
        System.err.println("Không load được ảnh Level Select");
    }
}

    private JButton createButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (buttonImage != null) {
                    g.drawImage(buttonImage, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g.setColor(new Color(139, 69, 19));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
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

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(new Color(34, 139, 34));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
