package view;

import utils.SoundManager;
import javax.swing.*;
import java.awt.*;

public class SettingsDiaLog extends JDialog {

    public SettingsDiaLog(JFrame parent) {
        super(parent, "Cài đặt Âm thanh", true);
        setSize(420, 320);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 1, 15, 15));

        JLabel title = new JLabel("CÀI ĐẶT ÂM THANH", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(0, 86, 150));
        add(title);

        // Nhạc nền
        JPanel musicPanel = new JPanel(new BorderLayout(10, 0));
        musicPanel.add(new JLabel("Nhạc nền:"), BorderLayout.WEST);
        JSlider musicSlider = new JSlider(0, 100, 80);
        musicSlider.setMajorTickSpacing(20);
        musicSlider.setPaintTicks(true);
        musicSlider.setPaintLabels(true);
        musicSlider.addChangeListener(e -> SoundManager.setMusicVolume(musicSlider.getValue() / 100f));
        musicPanel.add(musicSlider, BorderLayout.CENTER);
        add(musicPanel);

        // Âm thanh hiệu ứng
        JPanel sfxPanel = new JPanel(new BorderLayout(10, 0));
        sfxPanel.add(new JLabel("Âm thanh hiệu ứng:"), BorderLayout.WEST);
        JCheckBox sfxCheck = new JCheckBox("Bật", true);
        sfxCheck.addActionListener(e -> SoundManager.setSfxEnabled(sfxCheck.isSelected()));
        sfxPanel.add(sfxCheck, BorderLayout.EAST);
        add(sfxPanel);

        JButton closeBtn = new JButton("Đóng");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 18));
        closeBtn.setBackground(new Color(0, 86, 150));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.addActionListener(e -> dispose());

        JPanel bottom = new JPanel();
        bottom.add(closeBtn);
        add(bottom);

        setVisible(true);
    }
}
