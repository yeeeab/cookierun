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

		// 초기화 메서드 호출
		initialize();

		// 상점 버튼 생성 및 설정
		StoreBtn = new JButton(storeIc);
		StoreBtn.setName("StoreBtn");
		add(StoreBtn);
		StoreBtn.setBorderPainted(false);
		StoreBtn.setContentAreaFilled(false);
		StoreBtn.setFocusPainted(false);

		// 뒤로가기 버튼
		ImageIcon backIcon = new ImageIcon("img/select/backBtn.png");
		backButton = new JButton(backIcon);
		backButton.setBounds(670, 10, 100, 50); // 버튼의 위치와 크기 설정
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainFrame.getContentPane(), "intro");
			}
		});

		setLayout(null); // 레이아웃 설정
		add(StoreBtn);
		add(backButton);

		// 코인 레이블 설정
		coinLabel = new JLabel("Coins: " + main.getTotalCoinScore());
		coinLabel.setForeground(Color.WHITE);
		coinLabel.setBounds(530, 17, 200, 17);
		add(coinLabel);

		// 구매 버튼 이미지 로드 및 크기 조정
		buyButtonIcon = new ImageIcon(
				new ImageIcon("img/store/getitembutton.png").getImage().getScaledInstance(130, 40, Image.SCALE_SMOOTH));

		// 포션 구매 버튼 설정
		buyPotion1Btn = new JButton(buyButtonIcon);
		buyPotion1Btn.setBounds(110, 370, buyButtonIcon.getIconWidth(), buyButtonIcon.getIconHeight());
		buyPotion1Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buyPotion(1);
			}
		});
		add(buyPotion1Btn);

		buyPotion2Btn = new JButton(buyButtonIcon);
		buyPotion2Btn.setBounds(310, 370, buyButtonIcon.getIconWidth(), buyButtonIcon.getIconHeight());
		buyPotion2Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buyPotion(2);
			}
		});
		add(buyPotion2Btn);

		buyPotion3Btn = new JButton(buyButtonIcon);
		buyPotion3Btn.setBounds(510, 370, buyButtonIcon.getIconWidth(), buyButtonIcon.getIconHeight());
		buyPotion3Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buyPotion(3);
			}
		});
		add(buyPotion3Btn);
	}

	private void initialize() {
		// 배경 이미지 로드
		backgroundImage = new ImageIcon("img/store/storePanel.png");

		// 포션 이미지 로드 및 크기 조정
		potion1 = new ImageIcon(
				new ImageIcon("img/store/potion1.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		potion2 = new ImageIcon(
				new ImageIcon("img/store/potion2.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
		potion3 = new ImageIcon(
				new ImageIcon("img/store/potion3.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

		setLayout(null);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // 화면을 비운다

		// 인트로 화면을 그린다
		g.drawImage(storeIc.getImage(), -60, 0, /* this.getWidth(), this.getHeight(), */ null);

		// 배경 이미지를 패널 크기에 맞게 조정하여 그린다
		g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

		// 포션 이미지를 그릴 위치와 크기 설정
		int boxWidth = 100;
		int boxHeight = 100;
		int[] boxX = { 120, 320, 520 }; // X 좌표
		int boxY = 280; // Y 좌표

		// 포션 이미지를 박스 위치에 그리기
		g.drawImage(potion1.getImage(), boxX[0] + (boxWidth - potion1.getIconWidth()) / 2,
				boxY + (boxHeight - potion1.getIconHeight()) / 2, this);
		g.drawImage(potion2.getImage(), boxX[1] + (boxWidth - potion2.getIconWidth()) / 2,
				boxY + (boxHeight - potion2.getIconHeight()) / 2, this);
		g.drawImage(potion3.getImage(), boxX[2] + (boxWidth - potion3.getIconWidth()) / 2,
				boxY + (boxHeight - potion3.getIconHeight()) / 2, this);

		coinLabel.setText("Coins: " + main.getTotalCoinScore());
	}

	// 포션 구매 메서드
	private void buyPotion(int potionNumber) {
		if (main.getTotalCoinScore() >= 1000) {
			main.spendCoins(1000);
			coinLabel.setText("Coins: " + main.getTotalCoinScore());

			String potionName = "";
			switch (potionNumber) {
				case 1:
					main.addPotion(main.getHealthPotion());
					potionName = main.getHealthPotion().getName();
					break;
				case 2:
					main.addHealthPotion(1);
					potionName = main.getCoinPotion().getName();
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
