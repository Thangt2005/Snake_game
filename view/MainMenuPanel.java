package view;

import utils.FileHandler;      
import utils.SoundManager;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainMenuPanel extends JPanel {

    private final MainFrame frame;
    private BufferedImage backgroundImage;
    private BufferedImage buttonImage;

    private final Font titleFont = new Font("Arial", Font.BOLD, 48);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 22);
    private final Font smallFont = new Font("Arial", Font.BOLD, 18);

    private JLabel highScoreLabel;          // ← ĐÃ LÀM THÀNH FIELD
    private float titleScale = 1.0f;
    private boolean scaleUp = true;
    private Timer bounceTimer;

    public MainMenuPanel(MainFrame f) {
        this.frame = f;
        setLayout(null);
        loadImages();
        startBounceEffect();

        JLabel title = new JLabel("Snake Game J14", SwingConstants.CENTER);
        title.setFont(titleFont);
        title.setForeground(new Color(40, 25, 10));
        title.setBounds(0, 35, 600, 110);
        add(title);

        // Điểm cao (đã là field để có thể update sau)
        highScoreLabel = new JLabel("ĐIỂM CAO: " + FileHandler.readHighScore(), SwingConstants.CENTER);
        highScoreLabel.setFont(smallFont);
        highScoreLabel.setForeground(new Color(65, 45, 25));
        highScoreLabel.setBounds(0, 145, 600, 50);
        add(highScoreLabel);

        createButton("Chơi", 180, 215, e -> frame.showLevelSelect());
        createButton("Chế độ vô tận", 180, 290, e -> frame.showEndlessMode());
        createButton("Cài đặt", 180, 365, e -> frame.showSettings());
        createButton("Thoát", 180, 440, e -> System.exit(0));
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("images/menu_background.png"));
            buttonImage = ImageIO.read(new File("images/button_normal.png"));
        } catch (IOException e) {
            System.err.println("Không load được ảnh: " + e.getMessage());
        }
    }

    private void startBounceEffect() {
        bounceTimer = new Timer(65, e -> {
            if (scaleUp) {
                titleScale += 0.018f;
                if (titleScale >= 1.08f) scaleUp = false;
            } else {
                titleScale -= 0.018f;
                if (titleScale <= 0.94f) scaleUp = true;
            }
            repaint();
        });
        bounceTimer.start();
    }

    private void createButton(String text, int x, int y, java.awt.event.ActionListener listener) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (buttonImage != null) {
                    g.drawImage(buttonImage, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g.setColor(new Color(139, 69, 19));
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                }
                Graphics2D g2 = (Graphics2D) g;
                g2.setFont(buttonFont);
                g2.setColor(new Color(40, 25, 10));
                FontMetrics fm = g2.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent()) / 2 - 3;
                g2.drawString(getText(), textX, textY);
            }
        };
        btn.setBounds(x, y, 240, 68);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            SoundManager.playClick();
            listener.actionPerformed(e);
        });
        add(btn);
    }

    /** Phương thức mới: cập nhật điểm cao mỗi khi quay về menu */
    public void updateHighScore() {
        highScoreLabel.setText("ĐIỂM CAO: " + FileHandler.readHighScore());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
