package view;

import utils.Difficulty;
import utils.GameMode;
import utils.SoundManager;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EndlessModePanel extends JPanel {

    private final MainFrame frame;
    private BufferedImage backgroundImage;
    private BufferedImage buttonImage;

    private final Font labelFont = new Font("Arial", Font.BOLD, 26);
    private final Font comboFont = new Font("Arial", Font.BOLD, 22);
    private final Font buttonFont = new Font("Arial", Font.BOLD, 23);

    public EndlessModePanel(MainFrame f) {
        this.frame = f;
        setLayout(null);
        setPreferredSize(new Dimension(600, 700));

        loadImages();

        JButton backBtn = createButton("← QUAY LẠI");
        backBtn.setBounds(30, 20, 220, 55);
        backBtn.addActionListener(e -> {
            SoundManager.playClick();
            frame.showMenu();
        });
        add(backBtn);

        JLabel title = new JLabel("CHẾ ĐỘ VÔ TẬN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setForeground(new Color(40, 25, 10));
        title.setBounds(0, 90, 600, 80);
        add(title);

        JLabel lblDiff = new JLabel("Độ khó:");
        lblDiff.setFont(labelFont);
        lblDiff.setForeground(new Color(40, 25, 10));
        lblDiff.setBounds(80, 200, 200, 40);
        add(lblDiff);

        JComboBox<String> diffBox = new JComboBox<>(new String[]{"Dễ", "Bình thường", "Khó"});
        diffBox.setFont(comboFont);
        diffBox.setBounds(320, 200, 200, 45);
        add(diffBox);

        JLabel lblMode = new JLabel("Chế độ chơi:");
        lblMode.setFont(labelFont);
        lblMode.setForeground(new Color(40, 25, 10));
        lblMode.setBounds(80, 270, 220, 40);
        add(lblMode);

        JComboBox<String> modeBox = new JComboBox<>(new String[]{"Bình thường", "Xuyên tường", "Tăng tốc"});
        modeBox.setFont(comboFont);
        modeBox.setBounds(320, 270, 200, 45);
        add(modeBox);

        JLabel lblSize = new JLabel("Kích thước map:");
        lblSize.setFont(labelFont);
        lblSize.setForeground(new Color(40, 25, 10));
        lblSize.setBounds(80, 340, 220, 40);
        add(lblSize);

        JComboBox<String> sizeBox = new JComboBox<>(new String[]{"10x10", "15x15", "20x20"});
        sizeBox.setFont(comboFont);
        sizeBox.setBounds(320, 340, 200, 45);
        add(sizeBox);

        JButton startBtn = createButton("BẮT ĐẦU");
        startBtn.setBounds(180, 460, 240, 68);
        startBtn.addActionListener(e -> {
            SoundManager.playClick();
            startEndless(diffBox, modeBox, sizeBox);
        });
        add(startBtn);
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(new File("images/menu_background.png"));
            buttonImage = ImageIO.read(new File("images/button_normal.png"));
        } catch (IOException e) {}
    }

    private JButton createButton(String text) {
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
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void startEndless(JComboBox<String> diffBox, JComboBox<String> modeBox, JComboBox<String> sizeBox) {
        int size = switch (sizeBox.getSelectedIndex()) {
            case 0 -> 10;
            case 1 -> 15;
            default -> 20;
        };

        Difficulty diff = switch (diffBox.getSelectedIndex()) {
            case 0 -> Difficulty.EASY;
            case 1 -> Difficulty.MEDIUM;
            default -> Difficulty.HARD;
        };

        GameMode mode = switch (modeBox.getSelectedIndex()) {
            case 0 -> GameMode.CLASSIC;
            case 1 -> GameMode.NO_WALLS;
            default -> GameMode.SPEED_RUSH;
        };

        frame.startEndless(diff, mode, size);
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
