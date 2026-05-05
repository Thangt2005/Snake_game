package view;

import controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PausePanel extends JPanel {

    private final MainFrame frame;
    private final GameController controller;

    private BufferedImage backgroundImage;
    private BufferedImage buttonImage;

    private final Font titleFont = new Font("Arial", Font.BOLD, 48);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 22);

    public PausePanel(MainFrame frame, GameController controller) {
        this.frame = frame;
        this.controller = controller;

        setLayout(null);
        setPreferredSize(new Dimension(600, 700));

        loadImages();

        JLabel title = new JLabel("TẠM DỪNG", SwingConstants.CENTER);
        title.setFont(titleFont);
        title.setForeground(new Color(40, 25, 10));
        title.setBounds(0, 80, 600, 90);
        add(title);

        createImageButton("Tiếp tục chơi", 180, 200, e -> resumeGame());
        createImageButton("Chơi lại", 180, 280, e -> restartGame());
        createImageButton("Thoát về Menu", 180, 360, e -> frame.showMenu());
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("images/menu_background.png"));
            buttonImage = ImageIO.read(new File("images/button_normal.png"));
        } catch (IOException e) {
            System.err.println("Không load được ảnh Pause Panel");
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

    private void resumeGame() {
        frame.showGame();
        controller.resumeGame();
    }

    private void restartGame() {
        controller.restartCurrentLevel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
