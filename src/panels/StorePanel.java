// 상점 화면

package panels;

import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StorePanel extends JPanel {
	// 상점 배경 이미지
	private ImageIcon backgroundImage;

	// 포션 이미지 아이콘
	private ImageIcon potion1;
	private ImageIcon potion2;
	private ImageIcon potion3;

	// 상점 버튼 이미지 아이콘
	private ImageIcon storeIc = new ImageIcon("img/store/storeBtn.png");

	private JButton StoreBtn;

	public StorePanel(Object o) {
		// 초기화 메서드 호출
		initialize();

		// 상점 버튼 생성 및 설정
		StoreBtn = new JButton(storeIc);
		StoreBtn.setName("StoreBtn");
		StoreBtn.addMouseListener((MouseListener) o);
		StoreBtn.setBounds(254, 334, 291, 81);
		add(StoreBtn);
		StoreBtn.setBorderPainted(false);
		StoreBtn.setContentAreaFilled(false);
		StoreBtn.setFocusPainted(false);
	}

	private void initialize() {
		// 배경 이미지 로드
		backgroundImage = new ImageIcon("img/store/storePanel.png");

		// 포션 이미지 로드
		potion1 = new ImageIcon("img/store/potion1.png");
		potion2 = new ImageIcon("img/store/potion2.png");
		potion3 = new ImageIcon("img/store/potion3.png");

		setLayout(null);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // 화면을 비운다

		// 배경 이미지를 패널 크기에 맞게 조정하여 그린다
		g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

		// 포션 이미지를 그릴 위치와 크기 설정
		int boxWidth = 100;
		int boxHeight = 100;
		int[] boxX = { 90, 280, 470 }; // X 좌표
		int boxY = 300; // Y 좌표

		// 포션 이미지를 박스 위치에 그리기
		g.drawImage(potion1.getImage(), boxX[0] + (boxWidth - potion1.getIconWidth()) / 2,
				boxY + (boxHeight - potion1.getIconHeight()) / 2, this);
		g.drawImage(potion2.getImage(), boxX[1] + (boxWidth - potion2.getIconWidth()) / 2,
				boxY + (boxHeight - potion2.getIconHeight()) / 2, this);
		g.drawImage(potion3.getImage(), boxX[2] + (boxWidth - potion3.getIconWidth()) / 2,
				boxY + (boxHeight - potion3.getIconHeight()) / 2, this);
	}
}