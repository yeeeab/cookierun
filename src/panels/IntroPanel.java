package panels;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IntroPanel extends JPanel {

	ImageIcon introIc = new ImageIcon("img/intro/intro.png"); // 인트로 이미지
	private ImageIcon storeIcon = new ImageIcon("img/store/storeBtn.png"); // 상점 버튼(수정 예정)
	private JButton storeBtn;
	private JButton rankBtn; // 순위 버튼 추가
	private JFrame mainFrame;
	private CardLayout cardLayout;

	public IntroPanel(JFrame mainFrame, CardLayout cardLayout) {
		this.mainFrame = mainFrame;
		this.cardLayout = cardLayout;
		initialize();
	}

	private void initialize() {
		setLayout(null); // 절대 위치 지정을 위해 레이아웃을 null로 설정

		ImageIcon originalStoreIcon = new ImageIcon("img/store/storeBtn.png"); // 원본 이미지
		Image originalStoreImage = originalStoreIcon.getImage(); // Image 객체로 변환
		Image scaledStoreImage = originalStoreImage.getScaledInstance(130, 40, Image.SCALE_SMOOTH); // 원하는 크기로 조정
		ImageIcon scaledStoreIcon = new ImageIcon(scaledStoreImage); // 다시 ImageIcon으로 변환

		storeBtn = new JButton(scaledStoreIcon);
		storeBtn.setName("StoreBtn"); // 버튼의 이름을 설정하여 식별 가능하도록 함
		storeBtn.setBounds(640, 80, 130, 40); // 버튼의 위치와 크기 설정
		storeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainFrame.getContentPane(), "store");
			}
		});

		add(storeBtn); // 패널에 버튼 추가

		// 순위 버튼 추가
		ImageIcon originalRankIcon = new ImageIcon("img/select/rankBtn.png"); // 원본 이미지
		Image originalRankImage = originalRankIcon.getImage(); // Image 객체로 변환
		Image scaledRankImage = originalRankImage.getScaledInstance(130, 40, Image.SCALE_SMOOTH); // 원하는 크기로 조정
		ImageIcon scaledRankIcon = new ImageIcon(scaledRankImage); // 다시 ImageIcon으로 변환

		rankBtn = new JButton(scaledRankIcon);
		rankBtn.setName("RankBtn"); // 버튼의 이름을 설정하여 식별 가능하도록 함
		rankBtn.setBounds(640, 130, 130, 40); // 버튼의 위치와 크기 설정
		rankBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainFrame.getContentPane(), "rank");
			}
		});

		add(rankBtn); // 패널에 버튼 추가
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // 화면을 비운다

		// 인트로 화면을 그린다
		g.drawImage(introIc.getImage(), -60, 0, null);
	}
}
