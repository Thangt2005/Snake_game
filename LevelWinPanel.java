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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LevelWinPanel extends JPanel {

	private final MainFrame frame;
	private final int currentLevel;
	private BufferedImage backgroundImage;
	private BufferedImage buttonImage;
	private final Font titleFont = new Font("Arial", Font.BOLD, 48);
	private final Font subtitleFont = new Font("Arial", Font.BOLD, 24);
	private final Font buttonFont = new Font("Arial", Font.BOLD, 22);

	public LevelWinPanel(MainFrame frame, int currentLevel) {
		this.frame = frame;
		this.currentLevel = currentLevel;
		setLayout(null);
		loadImages();

		JLabel title = new JLabel("CHÚC MỪNG!", SwingConstants.CENTER);
		title.setFont(titleFont);
		title.setForeground(new Color(255, 215, 0));
		title.setBounds(0, 80, 600, 90);
		add(title);

		JLabel sub = new JLabel("Bạn đã hoàn thành Màn " + currentLevel, SwingConstants.CENTER);
		sub.setFont(subtitleFont);
		sub.setForeground(new Color(255, 240, 180));
		sub.setBounds(0, 170, 600, 50);
		add(sub);

		createButton("Màn tiếp theo", 180, 250, e -> frame.startLevel(Math.min(currentLevel + 1, 5)));
		createButton("Chơi lại màn này", 180, 320, e -> frame.startLevel(currentLevel));
		createButton("Quay về Menu", 180, 390, e -> frame.showMenu());
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
	}
}
