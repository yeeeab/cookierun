package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EndPanel extends JPanel {

	ImageIcon btn = new ImageIcon("img/end/button.png");
	JButton btnNewButton;
	JLabel lblNewLabel_1;
	JLabel lblNewLabel_2;
	JLabel lblNewLabel;
	boolean isSpeedrunGame;

	private int resultScore;

	public void setResultScore(int resultScore) {
		lblNewLabel_2.setText(resultScore + "");
	}

	public void setResultTime(String time) {
		lblNewLabel_2.setText(time);
	}

	public void setSpeedrunGame(boolean isIt) {
		isSpeedrunGame = isIt;
		updateLabel();
	}

	public EndPanel(Object o) {
		setLayout(null);
		// 버튼
		btnNewButton = new JButton(btn);
		btnNewButton.setName("endAccept");
		btnNewButton.addMouseListener((MouseListener) o);
		btnNewButton.setBounds(550, 370, 199, 81);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setContentAreaFilled(false);
		add(btnNewButton);

		// 점수 또는 시간 라벨
		lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 37));
		lblNewLabel_1.setBounds(451, 0, 205, 55);
		add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("0");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Harlow Solid Italic", Font.PLAIN, 49));
		lblNewLabel_2.setBounds(313, 52, 459, 87);
		add(lblNewLabel_2);

		lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBackground(SystemColor.activeCaptionText);
		lblNewLabel.setIcon(new ImageIcon("img/end/cookierunbg.jpg"));
		lblNewLabel.setBounds(0, 0, 784, 461);
		add(lblNewLabel);

		// 초기 라벨 업데이트
		updateLabel();
	}

	private void updateLabel() {
		if (isSpeedrunGame) {
			lblNewLabel_1.setText("TIME");
		} else {
			lblNewLabel_1.setText("SCORE");
		}
	}
}
