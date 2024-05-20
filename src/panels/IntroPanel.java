package panels;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

// import javafx.scene.image.Image;

public class IntroPanel extends JPanel {
	
	ImageIcon introIc = new ImageIcon("img/intro/intro.png"); // 인트로 이미지
	private ImageIcon storeIcon = new ImageIcon("img/store/storeBtn.png"); // 상점 버튼(수정 예정)
	private JButton storeBtn;

	public IntroPanel() {
        initialize();
    }

	private void initialize() {
        setLayout(null); // 절대 위치 지정을 위해 레이아웃을 null로 설정

		ImageIcon originalIcon = new ImageIcon("img/store/storeBtn.png"); // 원본 이미지
        Image originalImage = originalIcon.getImage(); // Image 객체로 변환
        Image scaledImage = originalImage.getScaledInstance(130, 40, Image.SCALE_SMOOTH); // 원하는 크기로 조정
        ImageIcon storeIcon = new ImageIcon(scaledImage); // 다시 ImageIcon으로 변환


        storeBtn = new JButton(storeIcon);
        storeBtn.setName("StoreBtn"); // 버튼의 이름을 설정하여 식별 가능하도록 함
        storeBtn.setBounds(640, 80, 130, 40); // 버튼의 위치와 크기 설정
        add(storeBtn); // 패널에 버튼 추가
    }

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // 화면을 비운다
		
		// 인트로 화면을 그린다
		g.drawImage(introIc.getImage(), -60, 0, /* this.getWidth(), this.getHeight(), */ null);
	}
}