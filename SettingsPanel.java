import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SettingsPanel extends JPanel {

	private final MainFrame frame;
	private BufferedImage backgroundImage;
	private BufferedImage buttonImage;
	private final Font titleFont = new Font("Arial", Font.BOLD, 42);
	private final Font buttonFont = new Font("Arial", Font.BOLD, 22);

	private boolean musicEnabled = true;
	private boolean sfxEnabled = true;

	public SettingsPanel(MainFrame frame) {
		this.frame = frame;
		setLayout(null);
		loadImages();

		JLabel title = new JLabel("CÀI ĐẶT", SwingConstants.CENTER);
		title.setFont(titleFont);
		title.setForeground(new Color(40, 25, 10));
		title.setBounds(0, 80, 600, 80);
		add(title);

		JCheckBox musicCheck = new JCheckBox("Nhạc nền", musicEnabled);
		musicCheck.setBounds(120, 220, 360, 50);
		musicCheck.setFont(buttonFont);
		musicCheck.setForeground(new Color(40, 25, 10));
		musicCheck.setOpaque(false);
		musicCheck.addActionListener(e -> musicEnabled = musicCheck.isSelected());
		add(musicCheck);

		JCheckBox sfxCheck = new JCheckBox("Âm thanh hiệu ứng", sfxEnabled);
		sfxCheck.setBounds(120, 290, 360, 50);
		sfxCheck.setFont(buttonFont);
		sfxCheck.setForeground(new Color(40, 25, 10));
		sfxCheck.setOpaque(false);
		sfxCheck.addActionListener(e -> sfxEnabled = sfxCheck.isSelected());
		add(sfxCheck);

		createButton("ĐÓNG", 180, 480, e -> frame.showMenu());
	}

	private void loadImages() {
		try {
			backgroundImage = ImageIO.read(new File("images/menu_background.png"));
			buttonImage = ImageIO.read(new File("images/button_normal.png"));
		} catch (IOException e) {
		}
	}

	private void createButton(String text, int x, int y, java.awt.event.ActionListener l) {
		JButton btn = new JButton(text) {
			@Override
			protected void paintComponent(Graphics g) {
				if (buttonImage != null)
					g.drawImage(buttonImage, 0, 0, getWidth(), getHeight(), null);
				Graphics2D g2 = (Graphics2D) g;
				g2.setFont(buttonFont);
				g2.setColor(new Color(40, 25, 10));
				FontMetrics fm = g2.getFontMetrics();
				g2.drawString(getText(), (getWidth() - fm.stringWidth(getText())) / 2,
						(getHeight() + fm.getAscent()) / 2 - 3);
			}
		};
		btn.setBounds(x, y, 240, 68);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setOpaque(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.addActionListener(l);
		add(btn);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backgroundImage != null)
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		else {
			g.setColor(new Color(139, 69, 19));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}
