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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
		loadImages();

		JButton backBtn = createButton("← QUAY LẠI");
		backBtn.setBounds(30, 20, 220, 55);
		backBtn.addActionListener(e -> frame.showMenu());
		add(backBtn);

		JLabel title = new JLabel("CHẾ ĐỘ VÔ TẬN", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 42));
		title.setForeground(new Color(40, 25, 10));
		title.setBounds(0, 90, 600, 80);
		add(title);

		addLabel("Độ khó:", 80, 200);
		JComboBox<String> diffBox = new JComboBox<>(new String[] { "Dễ", "Bình thường", "Khó" });
		diffBox.setFont(comboFont);
		diffBox.setBounds(320, 200, 200, 45);
		add(diffBox);

		addLabel("Chế độ chơi:", 80, 270);
		JComboBox<String> modeBox = new JComboBox<>(new String[] { "Bình thường", "Xuyên tường", "Tăng tốc" });
		modeBox.setFont(comboFont);
		modeBox.setBounds(320, 270, 200, 45);
		add(modeBox);

		addLabel("Kích thước map:", 80, 340);
		JComboBox<String> sizeBox = new JComboBox<>(new String[] { "10x10", "15x15", "20x20" });
		sizeBox.setFont(comboFont);
		sizeBox.setBounds(320, 340, 200, 45);
		add(sizeBox);

		JButton startBtn = createButton("BẮT ĐẦU");
		startBtn.setBounds(180, 460, 240, 68);
		startBtn.addActionListener(e -> {
			String diff = (String) diffBox.getSelectedItem();
			String mode = (String) modeBox.getSelectedItem();
			int size = new int[] { 10, 15, 20 }[sizeBox.getSelectedIndex()];
			frame.startEndless(diff, mode, size); // TODO: nối backend
		});
		add(startBtn);
	}

	private void addLabel(String text, int x, int y) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(labelFont);
		lbl.setForeground(new Color(40, 25, 10));
		lbl.setBounds(x, y, 220, 40);
		add(lbl);
	}

	private void loadImages() {
		try {
			backgroundImage = ImageIO.read(new File("images/menu_background.png"));
			buttonImage = ImageIO.read(new File("images/button_normal.png"));
		} catch (IOException e) {
		}
	}

	private JButton createButton(String text) {
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
		if (backgroundImage != null)
			g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		else {
			g.setColor(new Color(34, 139, 34));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}
