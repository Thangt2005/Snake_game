import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanel = new JPanel(cardLayout);

	private MainMenuPanel menuPanel;
	private EndlessModePanel endlessPanel;
	private SettingsPanel settingsPanel;
	private PausePanel pausePanel;

	public MainFrame() {
		setTitle("Snake Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 840);
		setResizable(false);
		setLocationRelativeTo(null);

		menuPanel = new MainMenuPanel(this);
		endlessPanel = new EndlessModePanel(this);
		settingsPanel = new SettingsPanel(this);
		pausePanel = new PausePanel(this);

		LevelSelectPanel levelSelect = new LevelSelectPanel(this);
		GamePanel gamePanel = new GamePanel(this);

		mainPanel.add(menuPanel, "MENU");
		mainPanel.add(levelSelect, "LEVEL");
		mainPanel.add(gamePanel, "GAME");
		mainPanel.add(endlessPanel, "ENDLESS");
		mainPanel.add(settingsPanel, "SETTINGS");
		mainPanel.add(pausePanel, "PAUSE");

		add(mainPanel);
		showMenu();
	}

	public void showMenu() {
		cardLayout.show(mainPanel, "MENU");
	}

	public void showLevelSelect() {
		cardLayout.show(mainPanel, "LEVEL");
	}

	public void showEndlessMode() {
		cardLayout.show(mainPanel, "ENDLESS");
	}

	public void showSettings() {
		cardLayout.show(mainPanel, "SETTINGS");
	}

	public void showPause() {
		cardLayout.show(mainPanel, "PAUSE");
	}

	public void showGame() {
		cardLayout.show(mainPanel, "GAME");
	}

	public void showGameOver(boolean isLevelMode, int value) {
		GameOverPanel p = new GameOverPanel(this, isLevelMode, value);
		for (Component c : mainPanel.getComponents())
			if (c instanceof GameOverPanel)
				mainPanel.remove(c);
		mainPanel.add(p, "GAMEOVER");
		cardLayout.show(mainPanel, "GAMEOVER");
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void showLevelWin(int currentLevel) {
		LevelWinPanel p = new LevelWinPanel(this, currentLevel);
		for (Component c : mainPanel.getComponents())
			if (c instanceof LevelWinPanel)
				mainPanel.remove(c);
		mainPanel.add(p, "LEVELWIN");
		cardLayout.show(mainPanel, "LEVELWIN");
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void startLevel(int level) {
		showGame();
	}

	public void startEndless(String diff, String mode, int size) {
		showGame();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}
}
