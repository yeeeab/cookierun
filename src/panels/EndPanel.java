package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Main;

public class EndPanel extends JPanel {

	ImageIcon btn = new ImageIcon("img/end/button.png");
	JButton btnNewButton;
	JLabel lblNewLabel_1;
	JLabel lblJellyScore;
	JLabel lblCoinScore;
	JLabel lblTotalCoins;
	JLabel lblNewLabel;

	private int jellyScore;
	private int coinScore;

	private Main main; // 메인 클래스 참조

	// 점수
	public void setScore(int jellyScore, int coinScore) {
		this.jellyScore = jellyScore;
		this.coinScore = coinScore;
		main.addCoins(coinScore); // 총 코인 점수 업데이트
		lblJellyScore.setText("Jelly Score: " + jellyScore);
		lblCoinScore.setText("Coin Score: " + coinScore);
		lblTotalCoins.setText("Total Coins: " + main.getTotalCoinScore());
	}

	public EndPanel(Object o, Main main) {
		this.main = main; // Main 클래스 인스턴스 할당
		setLayout(null);

		// ��ư
		btnNewButton = new JButton(btn);
		btnNewButton.setName("endAccept");
		btnNewButton.addMouseListener((MouseListener) o);
		btnNewButton.setBounds(550, 370, 199, 81);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setContentAreaFilled(false);
		add(btnNewButton);

		// ���� ����
		lblNewLabel_1 = new JLabel("SCORE");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 37));
		lblNewLabel_1.setBounds(451, 0, 205, 55);
		add(lblNewLabel_1);

		// Jelly Score 라벨
		lblJellyScore = new JLabel("Jelly Score: 0");
		lblJellyScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblJellyScore.setFont(new Font("Harlow Solid Italic", Font.PLAIN, 30));
		lblJellyScore.setBounds(313, 52, 459, 87);
		add(lblJellyScore);

		// Coin Score 라벨
		lblCoinScore = new JLabel("Coin Score: 0");
		lblCoinScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoinScore.setFont(new Font("Harlow Solid Italic", Font.PLAIN, 30));
		lblCoinScore.setBounds(313, 100, 459, 87);
		add(lblCoinScore);

		// Total Coins 라벨
		lblTotalCoins = new JLabel("Total Coins: 0");
		lblTotalCoins.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotalCoins.setFont(new Font("Harlow Solid Italic", Font.PLAIN, 30));
		lblTotalCoins.setBounds(313, 150, 459, 87);
		add(lblTotalCoins);

		lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBackground(SystemColor.activeCaptionText);
		lblNewLabel.setIcon(new ImageIcon("img/end/cookierunbg.jpg"));
		lblNewLabel.setBounds(0, 0, 784, 461);
		add(lblNewLabel);
	}
}
