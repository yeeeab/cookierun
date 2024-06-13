package panels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import javax.swing.JFrame;
import main.Main;

public class StorePanel extends JPanel {

	private Main main;

	// 상점 배경 이미지
	private ImageIcon backgroundImage;

	// 포션 이미지 아이콘
	private ImageIcon potion1;
	private ImageIcon potion2;
	private ImageIcon potion3;

	// 상점 버튼 이미지 아이콘
	private ImageIcon storeIc = new ImageIcon("img/store/storePanel.png");

	private ImageIcon buyButtonIcon;

	// 구매 버튼
	private JButton buyPotion1Btn;
	private JButton buyPotion2Btn;
	private JButton buyPotion3Btn;

	private JButton backButton;
	private JButton StoreBtn;

	private JFrame mainFrame;

	private CardLayout cardLayout;

	// 현재 코인 표시 레이블
	private JLabel coinLabel;

	public StorePanel(Main main, JFrame mainFrame, CardLayout cardLayout) {
		this.mainFrame = mainFrame;
		this.cardLayout = cardLayout;
		this.main = main;

		ImageIcon originalIcon = new ImageIcon("img/store/storePanel.png");
		Image originalImage = originalIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(800, 480, Image.SCALE_SMOOTH); // 원하는 크기로 조정
		storeIc = new ImageIcon(scaledImage);

		initialize();

		StoreBtn = new JButton(storeIc);
		StoreBtn.setName("StoreBtn");
		add(StoreBtn);
		StoreBtn.setBorderPainted(false);
		StoreBtn.setContentAreaFilled(false);
		StoreBtn.setFocusPainted(false);

		ImageIcon backIcon = new ImageIcon("img/select/backBtn.png");
		backButton = new JButton(backIcon);
		backButton.setBounds(670, 10, 100, 50);
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainFrame.getContentPane(), "intro");
			}
		});

		setLayout(null);
		add(StoreBtn);
		add(backButton);

		coinLabel = new JLabel("Coins: " + main.getTotalCoinScore());
		coinLabel.setForeground(Color.WHITE);
		coinLabel.setBounds(530, 17, 200, 17);
		add(coinLabel);

		buyButtonIcon = new ImageIcon(
				new ImageIcon("img/store/getitembutton.png").getImage().getScaledInstance(130, 40, Image.SCALE_SMOOTH));

		// 포션 구매 버튼
		buyPotion1Btn = new JButton(buyButtonIcon);
		buyPotion1Btn.setBounds(110, 370, buyButtonIcon.getIconWidth(), buyButtonIcon.getIconHeight());
		buyPotion1Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buyPotion(1);
			}
		});
		add(buyPotion1Btn);

		buyPotion2Btn = new JButton(buyButtonIcon);
		buyPotion2Btn.setBounds(310, 370, buyButtonIcon.getIconWidth(), buyButtonIcon.getIconHeight());
		buyPotion2Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buyPotion(2);
			}
		});
		add(buyPotion2Btn);

		buyPotion3Btn = new JButton(buyButtonIcon);
		buyPotion3Btn.setBounds(510, 370, buyButtonIcon.getIconWidth(), buyButtonIcon.getIconHeight());
		buyPotion3Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buyPotion(3);
			}
		});
		add(buyPotion3Btn);
	}

	private void initialize() {
		backgroundImage = new ImageIcon("img/store/storePanel.png");

		potion1 = new ImageIcon(
				new ImageIcon("img/store/potion1Store.png").getImage().getScaledInstance(130, 200, Image.SCALE_SMOOTH));
		potion2 = new ImageIcon(
				new ImageIcon("img/store/potion2Store.png").getImage().getScaledInstance(130, 200, Image.SCALE_SMOOTH));
		potion3 = new ImageIcon(
				new ImageIcon("img/store/potion3Store.png").getImage().getScaledInstance(130, 200, Image.SCALE_SMOOTH));

		setLayout(null);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(storeIc.getImage(), -60, 0, /* this.getWidth(), this.getHeight(), */ null);

		g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

		int boxWidth = 100;
		int boxHeight = 100;
		int[] boxX = { 120, 320, 520 };
		int boxY = 270;

		g.drawImage(potion1.getImage(), boxX[0] + (boxWidth - potion1.getIconWidth()) / 2,
				boxY + (boxHeight - potion1.getIconHeight()) / 2, this);
		g.drawImage(potion2.getImage(), boxX[1] + (boxWidth - potion2.getIconWidth()) / 2,
				boxY + (boxHeight - potion2.getIconHeight()) / 2, this);
		g.drawImage(potion3.getImage(), boxX[2] + (boxWidth - potion3.getIconWidth()) / 2,
				boxY + (boxHeight - potion3.getIconHeight()) / 2, this);

		coinLabel.setText("Coins: " + main.getTotalCoinScore());
	}

	// 포션 구매
	private void buyPotion(int potionNumber) {
		if (main.getTotalCoinScore() >= 1000) {
			main.spendCoins(1000);
			coinLabel.setText("Coins: " + main.getTotalCoinScore());

			String potionName = "";
			switch (potionNumber) {
				case 1:
					main.addPotion(main.getCoinPotion());
					potionName = main.getCoinPotion().getName();
					break;
				case 2:
					main.addHealthPotion(1);
					potionName = main.getHealthPotion().getName();
					break;
				case 3:
					main.addPotion(main.getSpeedUpPotion());
					potionName = main.getSpeedUpPotion().getName();
					break;
			}
			JOptionPane.showMessageDialog(this, potionName + " purchased!");
		} else {
			JOptionPane.showMessageDialog(this, "No enough coins!");
		}
	}
}
