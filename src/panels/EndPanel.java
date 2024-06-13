package panels;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import main.Main;
import java.awt.event.MouseListener;
import java.awt.Font;

public class EndPanel extends JPanel {
	ImageIcon btn = new ImageIcon("img/end/button.png");
	JButton btnNewButton;
	JLabel lblTitle;
	JLabel lblTimeOrJellyScore;
	JLabel lblCoinScore;
	JLabel lblTotalCoins;
	JLabel lblBackground;

	int jellyScore = 0;
	String time = null;

	boolean isSpeedrunGame;

	private Main main;

	public void setResultScore(int resultScore) {
		if (!isSpeedrunGame) {
			lblTimeOrJellyScore.setText("Jelly Score: " + resultScore);
		}
	}

	public void setResultTime(String time) {
		if (isSpeedrunGame) {
			this.time = time;
			lblTimeOrJellyScore.setText("Time: " + time);
		}
	}

	public void setScore(int jellyScore, int coinScore) {
		this.jellyScore = jellyScore;
		lblTimeOrJellyScore.setText("Jelly Score: " + jellyScore);
		lblCoinScore.setText("Coin Score: " + coinScore);
	}

	public void setSpeedrunScore(String time, int coinScore) {
		this.time = time;
		lblTimeOrJellyScore.setText("Time: " + time);
		lblCoinScore.setText("Coin Score: " + coinScore);
	}

	public void setCoinScore(int coinScore) {
		lblCoinScore.setText("Coin Score: " + coinScore);
	}

	public void setTotalCoins(int totalCoins) {
		lblTotalCoins.setText("Total Coins: " + totalCoins);
	}

	public void setSpeedrunGame(boolean isIt) {
		isSpeedrunGame = isIt;
		updateLabel();
	}

	public boolean isSpeedrunGame() {
		return isSpeedrunGame;
	}

	public String getTime() {
		return time;
	}

	public int getJellyScore() {
		return jellyScore;
	}

	public EndPanel(Object o, Main main) {
		this.main = main;
		setLayout(null);

		btnNewButton = new JButton(btn);
		btnNewButton.setName("endAccept");
		btnNewButton.addMouseListener((MouseListener) o);
		btnNewButton.setBounds(550, 370, 199, 81);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setContentAreaFilled(false);
		add(btnNewButton);

		lblTitle = new JLabel();
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 37));
		lblTitle.setBounds(451, 0, 205, 55);
		add(lblTitle);

		lblTimeOrJellyScore = new JLabel("0");
		lblTimeOrJellyScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeOrJellyScore.setFont(new Font("Gill Sans", Font.PLAIN, 49));
		lblTimeOrJellyScore.setBounds(313, 52, 459, 87);
		add(lblTimeOrJellyScore);

		lblCoinScore = new JLabel("Coin Score: 0");
		lblCoinScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoinScore.setFont(new Font("Gill Sans", Font.PLAIN, 30));
		lblCoinScore.setBounds(313, 150, 459, 87);
		add(lblCoinScore);

		lblTotalCoins = new JLabel("Total Coins: " + main.getTotalCoinScore());
		lblTotalCoins.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalCoins.setFont(new Font("Gill Sans", Font.PLAIN, 30));
		lblTotalCoins.setBounds(313, 200, 459, 87);
		add(lblTotalCoins);

		lblBackground = new JLabel("");
		lblBackground.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBackground.setBackground(SystemColor.activeCaptionText);
		lblBackground.setIcon(new ImageIcon("img/end/cookierunbg.jpg"));
		lblBackground.setBounds(0, 0, 784, 461);
		add(lblBackground);

		updateLabel();
	}

	public void updateLabel() {
		if (isSpeedrunGame) {
			lblTitle.setText("TIME");
			lblTimeOrJellyScore.setText(time);
		} else {
			lblTitle.setText("SCORE");
			lblTimeOrJellyScore.setText(String.valueOf(jellyScore));
		}
	}
}