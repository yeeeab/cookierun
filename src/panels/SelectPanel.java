package panels;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import javax.swing.JFrame;

import ingame.CookieImg;
import potion.HealthPotion;

public class SelectPanel extends JPanel {

	// 선택할 캐릭터 이미지 아이콘
	private ImageIcon ch01 = new ImageIcon("img/select/selectCh1.png");
	private ImageIcon ch02 = new ImageIcon("img/select/selectCh2.png");
	private ImageIcon ch03 = new ImageIcon("img/select/selectCh3.png");
	private ImageIcon ch04 = new ImageIcon("img/select/selectCh4.png");

	// 선택된 캐릭터 이미지 아이콘
	private ImageIcon ch011 = new ImageIcon("img/select/selectedCh1.png");
	private ImageIcon ch022 = new ImageIcon("img/select/selectedCh2.png");
	private ImageIcon ch033 = new ImageIcon("img/select/selectedCh3.png");
	private ImageIcon ch044 = new ImageIcon("img/select/selectedCh4.png");

	// 시작 버튼 이미지 아이콘
	private ImageIcon start = new ImageIcon("img/select/GameStartBtn.png");

	// 스피드런 모드 시작 버튼 이미지 아이콘
	private ImageIcon speedrunStart = new ImageIcon("img/select/SpeedrunBtn.png");

	// hp 증가 포션 이미지 아이콘
	private ImageIcon healthPotion;

	// 이미지를 선택할 버튼
	private JButton ch1;
	private JButton ch2;
	private JButton ch3;
	private JButton ch4;

	// 시작 버튼
	private JButton StartBtn;

	// 스피드런 모드 시작 버튼
	private JButton SpeedrunBtn;

	// hp 증가 포션 버튼
	private JButton HealthPotionBtn;

	// 뒤로가기 버튼
	private JButton backButton;

	private JFrame mainFrame;
	private CardLayout cardLayout;

	// 게임에서 사용할 쿠키 이미지들을 담을 오브젝트
	private CookieImg ci;
	private HealthPotion healthPotionObj;

	public CookieImg getCi() {
		return ci;
	}

	public HealthPotion getHealthPotion() {
		return healthPotionObj;
	}

	public SelectPanel(JFrame mainFrame, CardLayout cardLayout, Object o) {
		this.mainFrame = mainFrame;
		this.cardLayout = cardLayout;

		// 시작 버튼
		ImageIcon originalIcon = new ImageIcon("img/select/itemBtn.png"); // 원본 이미지
		Image originalImage = originalIcon.getImage(); // Image 객체로 변환
		Image scaledImage = originalImage.getScaledInstance(291, 80, Image.SCALE_SMOOTH); // 원하는 크기로 조정
		ImageIcon ItemStartIcon = new ImageIcon(scaledImage); // 다시 ImageIcon으로 변환

		StartBtn = new JButton(ItemStartIcon);
		StartBtn.setName("StartBtn");
		StartBtn.addMouseListener((MouseListener) o);
		StartBtn.setBounds(254, 284, 291, 81);
		add(StartBtn);
		StartBtn.setBorderPainted(false);
		StartBtn.setContentAreaFilled(false);
		StartBtn.setFocusPainted(false);

		// 뒤로가기 버튼
		ImageIcon backIcon = new ImageIcon("img/select/backBtn.png");
		backButton = new JButton(backIcon);
		backButton.setBounds(670, 10, 100, 50); // 버튼의 위치와 크기 설정
		backButton.setBorderPainted(false);
		backButton.setContentAreaFilled(false);
		backButton.setFocusPainted(false);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(mainFrame.getContentPane(), "intro");
			}
		});

		// 스피드런 모드 시작 버튼
		ImageIcon originalIcon2 = new ImageIcon("img/select/SpeedrunBtn.png"); // 원본 이미지
		Image originalImage2 = originalIcon2.getImage(); // Image 객체로 변환
		Image scaledImage2 = originalImage2.getScaledInstance(291, 80, Image.SCALE_SMOOTH); // 원하는 크기로 조정
		ImageIcon SpeedrunStartIcon = new ImageIcon(scaledImage2); // 다시 ImageIcon으로 변환

		SpeedrunBtn = new JButton(SpeedrunStartIcon);
		SpeedrunBtn.setName("SpeedrunBtn");
		SpeedrunBtn.addMouseListener((MouseListener) o);
		SpeedrunBtn.setBounds(254, 364, 291, 81);
		add(SpeedrunBtn);
		SpeedrunBtn.setBorderPainted(false);
		SpeedrunBtn.setContentAreaFilled(false);
		SpeedrunBtn.setFocusPainted(false);

		// hp 증가 포션 버튼
		ImageIcon originalIcon3 = new ImageIcon("img/store/potion2Btn.png"); // 원본 이미지
		Image originalImage3 = originalIcon3.getImage(); // Image 객체로 변환
		Image scaledImage3 = originalImage3.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // 원하는 크기로 조정
		ImageIcon HealthPotionIcon = new ImageIcon(scaledImage3); // 다시 ImageIcon으로 변환

		HealthPotionBtn = new JButton(HealthPotionIcon);
		HealthPotionBtn.setName("HealthPotionBtn");
		HealthPotionBtn.addMouseListener((MouseListener) o);
		HealthPotionBtn.setBounds(104, 304, 100, 100);

		add(HealthPotionBtn);
		HealthPotionBtn.setBorderPainted(false);
		HealthPotionBtn.setContentAreaFilled(false);
		HealthPotionBtn.setFocusPainted(false);

		// 캐릭터 ch1
		ch1 = new JButton(ch01);
		ch1.setName("ch1");
		ch1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				ch1.setIcon(ch011);
				ch2.setIcon(ch02);
				ch3.setIcon(ch03);
				ch4.setIcon(ch04);
				ci = new CookieImg(new ImageIcon("img/cookieimg/cookie1/player_origin.gif"),
						new ImageIcon("img/cookieimg/cookie1/player_up.gif"),
						new ImageIcon("img/cookieimg/cookie1/player_doubleup.gif"),
						new ImageIcon("img/cookieimg/cookie1/player_jumpend.png"),
						new ImageIcon("img/cookieimg/cookie1/player_down.gif"),
						new ImageIcon("img/cookieimg/cookie1/player_attack.png"));
			}
		});
		ch1.setBounds(90, 62, 150, 200);
		add(ch1);
		add(backButton);
		ch1.setBorderPainted(false);
		ch1.setContentAreaFilled(false);
		ch1.setFocusPainted(false);

		// 캐릭터 ch2
		ch2 = new JButton(ch02);
		ch2.setName("ch2");
		ch2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				ch1.setIcon(ch01);
				ch2.setIcon(ch022);
				ch3.setIcon(ch03);
				ch4.setIcon(ch04);
				ci = new CookieImg(new ImageIcon("img/cookieimg/cookie2/normal.gif"),
						new ImageIcon("img/cookieimg/cookie2/jump.gif"),
						new ImageIcon("img/cookieimg/cookie2/doublejump.gif"),
						new ImageIcon("img/cookieimg/cookie2/fall.png"),
						new ImageIcon("img/cookieimg/cookie2/slide.gif"),
						new ImageIcon("img/cookieimg/cookie2/hit.gif"));
			}
		});
		ch2.setBounds(238, 62, 150, 200);
		add(ch2);
		ch2.setBorderPainted(false);
		ch2.setContentAreaFilled(false);
		ch2.setFocusPainted(false);

		// 캐릭터 ch3
		ch3 = new JButton(ch03);
		ch3.setName("ch3");
		ch3.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				ch1.setIcon(ch01);
				ch2.setIcon(ch02);
				ch3.setIcon(ch033);
				ch4.setIcon(ch04);
				ci = new CookieImg(new ImageIcon("img/cookieimg/cookie3/cookie.gif"),
						new ImageIcon("img/cookieimg/cookie3/jump.png"),
						new ImageIcon("img/cookieimg/cookie3/doublejump.gif"),
						new ImageIcon("img/cookieimg/cookie3/fall.png"),
						new ImageIcon("img/cookieimg/cookie3/slide.gif"),
						new ImageIcon("img/cookieimg/cookie3/hit.png"));
			}
		});
		ch3.setBounds(386, 62, 150, 200);
		add(ch3);
		ch3.setBorderPainted(false);
		ch3.setContentAreaFilled(false);
		ch3.setFocusPainted(false);

		// 캐릭터 ch4
		ch4 = new JButton(ch04);
		ch4.setName("ch4");
		ch4.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				ch1.setIcon(ch01);
				ch2.setIcon(ch02);
				ch3.setIcon(ch03);
				ch4.setIcon(ch044);
				ci = new CookieImg(new ImageIcon("img/cookieimg/cookie4/main.gif"),
						new ImageIcon("img/cookieimg/cookie4/jump.png"),
						new ImageIcon("img/cookieimg/cookie4/doublejump.png"),
						new ImageIcon("img/cookieimg/cookie4/fall.png"),
						new ImageIcon("img/cookieimg/cookie4/slide.png"),
						new ImageIcon("img/cookieimg/cookie4/hit.png"));
			}
		});
		ch4.setBounds(534, 62, 150, 200);
		add(ch4);
		ch4.setBorderPainted(false);
		ch4.setContentAreaFilled(false);
		ch4.setFocusPainted(false);

		// 배경
		JLabel selectBg = new JLabel("");
		selectBg.setForeground(Color.ORANGE);
		selectBg.setHorizontalAlignment(SwingConstants.CENTER);
		selectBg.setIcon(new ImageIcon("img/select/selectBg.png"));
		selectBg.setBounds(0, 0, 784, 461);
		add(selectBg);

		// 캐릭터 선택 타이틀
		JLabel selectTxt = new JLabel("");
		selectTxt.setIcon(new ImageIcon("img/select/selectText.png"));
		selectTxt.setBounds(152, 10, 495, 55);
		add(selectTxt);

		setLayout(null);
		setBounds(0, 0, 800, 500);
	}
}
