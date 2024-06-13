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

	ImageIcon introIc = new ImageIcon("img/intro/intro.png");
	private ImageIcon storeIcon = new ImageIcon("img/store/storeBtn.png");
	private JButton storeBtn;
	private JButton rankBtn;
	private JFrame mainFrame;
	private CardLayout cardLayout;

	public IntroPanel(JFrame mainFrame, CardLayout cardLayout) {
		this.mainFrame = mainFrame;
		this.cardLayout = cardLayout;
		initialize();
	}

	private void initialize() {
		setLayout(null);

		ImageIcon originalStoreIcon = new ImageIcon("img/store/storeBtn.png");
		Image originalStoreImage = originalStoreIcon.getImage();
		Image scaledStoreImage = originalStoreImage.getScaledInstance(130, 40, Image.SCALE_SMOOTH);
		ImageIcon scaledStoreIcon = new ImageIcon(scaledStoreImage);

		storeBtn = new JButton(scaledStoreIcon);
		storeBtn.setName("StoreBtn");
		storeBtn.setBounds(640, 80, 130, 40);
		storeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainFrame.getContentPane(), "store");
			}
		});

		add(storeBtn);

		// 순위 버튼 추가
		ImageIcon originalRankIcon = new ImageIcon("img/select/rankBtn.png");
		Image originalRankImage = originalRankIcon.getImage();
		Image scaledRankImage = originalRankImage.getScaledInstance(130, 40, Image.SCALE_SMOOTH); // 원하는 크기로 조정
		ImageIcon scaledRankIcon = new ImageIcon(scaledRankImage);

		rankBtn = new JButton(scaledRankIcon);
		rankBtn.setName("RankBtn");
		rankBtn.setBounds(640, 130, 130, 40);
		rankBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainFrame.getContentPane(), "rank");
			}
		});

		add(rankBtn);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(introIc.getImage(), -60, 0, null);
	}
}
