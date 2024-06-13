package main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import java.awt.Image;

import ingame.CookieImg;
import panels.*;
import main.listenAdapter;
import minigame.MiniGames;
import potion.*;

import java.awt.CardLayout;

public class Main extends listenAdapter {

	private JFrame frame; // 창을 띄우기 위한 프레임

	private IntroPanel introPanel; // 인트로

	private StorePanel storePanel; // 상점

	private SelectPanel selectPanel; // 캐릭터 선택

	private GamePanel gamePanel; // 게임진행

	private SpeedrunPanel speedrunPanel;

	private MiniGamePanel minigamePanel;

	private List<Potion> potions;

	private HealthPotion healthPotion;
	private CoinPotion coinPotion;
	private SpeedUpPotion speedUpPotion;

	private EndPanel endPanel; // 게임결과

	private RankPanel rankPanel; // 순위 패널 추가

	private CardLayout cl; // 카드레이아웃 오브젝트

	private int totalCoinScore = 1000; // 코인 점수

	private int healthPotionCount = 1; // HealthPotion 갯수
	private boolean healthPotionSelected = false; // HealthPotion 선택 여부

	private int coinPotionCount = 1;
	private int speedupPotionCount = 1;

	public int getTotalCoinScore() {
		return totalCoinScore;
	}

	public void addCoins(int coins) {
		this.totalCoinScore += coins;
	}

	public void spendCoins(int coins) {
		if (totalCoinScore >= coins) {
			this.totalCoinScore -= coins;
		} else {
			System.out.println("코인이 부족합니다!");
		}
	}

	public void addHealthPotion(int count) {
		this.healthPotionCount += count;
	}

	public void useHealthPotion() {
		if (healthPotionCount > 0) {
			healthPotionCount--;
		}
	}

	public int getHealthPotionCount() {
		return healthPotionCount;
	}

	public void addCoinPotion(int count) {
		this.coinPotionCount += count;
	}

	public void useCoinPotion() {
		if (coinPotionCount > 0) {
			coinPotionCount--;
		}
	}

	public int getCoinPotionCount() {
		return coinPotionCount;
	}

	public void addSpeedupPotion(int count) {
		this.speedupPotionCount += count;
	}

	public void useSpeedupPotion() {
		if (speedupPotionCount > 0) {
			speedupPotionCount--;
		}
	}

	public int getSpeedupPotionCount() {
		return speedupPotionCount;
	}

	public boolean isHealthPotionSelected() {
		return healthPotionSelected;
	}

	public void setHealthPotionSelected(boolean selected) {
		this.healthPotionSelected = selected;
	}

	// 포션 목록에 추가하는 메서드
	public void addPotion(Potion potion) {
		this.potions.add(potion);
	}

	public List<Potion> getPotions() {
		return potions;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public HealthPotion getHealthPotion() {
		return healthPotion;
	}

	public CoinPotion getCoinPotion() {
		return coinPotion;
	}

	public SpeedUpPotion getSpeedUpPotion() {
		return speedUpPotion;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public SpeedrunPanel getSpeedrunPanel() {
		return speedrunPanel;
	}

	public EndPanel getEndPanel() {
		return endPanel;
	}

	public RankPanel getRankPanel() {
		return rankPanel;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		potions = new ArrayList<>();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 500); // 창 사이즈
		frame.setLocationRelativeTo(null); // 창을 화면 중앙에 배치
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 엑스버튼을 누르면 종료
		cl = new CardLayout(0, 0); // 카드레이아웃 객체 생성
		frame.getContentPane().setLayout(cl); // 프레임을 카드레이아웃으로 변경

		introPanel = new IntroPanel(frame, cl);
		introPanel.addMouseListener(this); // intro패널은 여기서 바로 넣는 방식으로 마우스리스너를 추가함.

		storePanel = new StorePanel(this, frame, cl); // 상점
		selectPanel = new SelectPanel(frame, cl, this); // Main의 리스너를 넣기위한 this
		gamePanel = new GamePanel(frame, cl, this);
		endPanel = new EndPanel(this, this);
		speedrunPanel = new SpeedrunPanel(frame, cl, this); // 스피드런 모드 패널
		minigamePanel = new MiniGamePanel(speedrunPanel); // 미니게임 패널
		rankPanel = new RankPanel(frame, cl); // 순위 패널

		// 포션 이미지 로드 및 초기화
		Image healthPotionImage = new ImageIcon("img/store/potion1.png").getImage().getScaledInstance(100, 100,
				java.awt.Image.SCALE_SMOOTH);
		Image coinPotionImage = new ImageIcon("img/store/potion2.png").getImage().getScaledInstance(100, 100,
				java.awt.Image.SCALE_SMOOTH);
		Image speedUpPotionImage = new ImageIcon("img/store/potion3.png").getImage().getScaledInstance(100, 100,
				java.awt.Image.SCALE_SMOOTH);

		healthPotion = new HealthPotion(healthPotionImage, "Health Potion", 0, 0, 100, 100, 1);
		coinPotion = new CoinPotion(coinPotionImage, "Coin Potion", 0, 0, 100, 100, 1);
		speedUpPotion = new SpeedUpPotion(speedUpPotionImage, "Speed Up Potion", 0, 0, 100, 100, 1);

		// 모든 패널의 레이아웃을 null로 변환
		introPanel.setLayout(null);
		storePanel.setLayout(null);
		selectPanel.setLayout(null);
		gamePanel.setLayout(null);
		endPanel.setLayout(null);
		rankPanel.setLayout(null);

		// 프레임에 패널들을 추가한다.(카드 레이아웃을 위한 패널들)
		frame.getContentPane().add(introPanel, "intro");
		frame.getContentPane().add(storePanel, "store");
		frame.getContentPane().add(selectPanel, "select");
		frame.getContentPane().add(gamePanel, "game");
		frame.getContentPane().add(speedrunPanel, "speedrun");
		frame.getContentPane().add(endPanel, "end");
		frame.getContentPane().add(rankPanel, "rank"); // RankPanel 추가
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getComponent().toString().contains("IntroPanel")) { // IntroPanel에서 마우스를 눌렀다면
			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			cl.show(frame.getContentPane(), "select"); // select패널을 카드레이아웃 최상단으로 변경
			selectPanel.requestFocus(); // 리스너를 select패널에 강제로 줌

		} else if (e.getComponent().getName().equals("StartBtn")) { // StartBtn이라는 이름을 가진 버튼을 눌렀다면
			if (selectPanel.getCi() == null) {
				JOptionPane.showMessageDialog(null, "캐릭터를 골라주세요"); // 캐릭터를 안골랐을경우 팝업
			} else {
				cl.show(frame.getContentPane(), "game"); // 캐릭터를 골랐다면 게임패널을 카드레이아웃 최상단으로 변경
				if (isHealthPotionSelected()) {
					setHealthPotionSelected(false);
					addHealthPotion(-1);
					gamePanel.gameSet(selectPanel.getCi(), true); // 쿠키이미지를 넘겨주고 게임패널 세팅
				} else
					gamePanel.gameSet(selectPanel.getCi(), false); // 쿠키이미지를 넘겨주고 게임패널 세팅
				gamePanel.gameStart(); // 게임시작
				gamePanel.requestFocus(); // 리스너를 game패널에 강제로 줌
			}
		} else if (e.getComponent().getName().equals("SpeedrunBtn")) {
			if (selectPanel.getCi() == null) {
				JOptionPane.showMessageDialog(null, "캐릭터를 골라주세요"); // 캐릭터를 안골랐을경우 팝업
			} else {
				cl.show(frame.getContentPane(), "speedrun"); // 게임패널을 카드레이아웃 최상단으로 변경
				if (isHealthPotionSelected()) {
					setHealthPotionSelected(false);
					addHealthPotion(-1);
					speedrunPanel.gameSet(selectPanel.getCi(), true); // 쿠키이미지를 넘겨주고 게임패널 세팅
				} else
					speedrunPanel.gameSet(selectPanel.getCi(), false); // 쿠키이미지를 넘겨주고 게임패널 세팅
				speedrunPanel.gameStart(); // 게임시작
				speedrunPanel.requestFocus(); // 리스너를 speedrun패널에 강제로 줌
			}
		} else if (e.getComponent().getName().equals("endAccept")) { // endAccept 이라는 이름을 가진 버튼을 눌렀다면
			frame.getContentPane().remove(gamePanel); // 방금 했던 게임 패널을 프레임에서 삭제
			gamePanel = new GamePanel(frame, cl, this); // 게임패널을 새 패널로 교체
			gamePanel.setLayout(null);
			frame.getContentPane().add(gamePanel, "game"); // 프레임에 새 게임패널 추가(카드레이아웃 하단)

			frame.getContentPane().remove(speedrunPanel); // 방금 했던 게임 패널을 프레임에서 삭제
			speedrunPanel = new SpeedrunPanel(frame, cl, this); // speedrun패널을 새 패널로 교체
			speedrunPanel.setLayout(null);
			frame.getContentPane().add(speedrunPanel, "speedrun");

			frame.getContentPane().remove(selectPanel); // 방금 선택했던 select패널을 삭제
			selectPanel = new SelectPanel(frame, cl, this); // select 패널을 새 패널로 교체
			selectPanel.setLayout(null);
			frame.getContentPane().add(selectPanel, "select"); // 프레임에 새 select패널 추가(카드레이아웃 하단)

			// 순위 업데이트
			if (endPanel.isSpeedrunGame()) {
				rankPanel.updateRanks(0, endPanel.getTime());
			} else {
				rankPanel.updateRanks(endPanel.getJellyScore(), "00:00:00");
			}

			cl.show(frame.getContentPane(), "intro"); // 새 select패널을 카드레이아웃 최상단으로 이동 (화면에 보임)
			introPanel.requestFocus(); // 리스너를 intro패널에 강제로 줌
		} else if (e.getComponent().getName().equals("StoreBtn")) {
			cl.show(frame.getContentPane(), "store");
			storePanel.requestFocus();
		} else if (e.getComponent().getName().equals("HealthPotionBtn")) {
			if (getHealthPotionCount() > 0) {
				healthPotionSelected = !healthPotionSelected;
				setHealthPotionSelected(isHealthPotionSelected());
				JOptionPane.showMessageDialog(frame,
						"Health Potion " + (isHealthPotionSelected() ? "Selected" : "Deselected"));
			} else {
				JOptionPane.showMessageDialog(frame, "No Health Potions available!");
			}
		}
	}

	public void switchToMiniGame(SpeedrunPanel speedrunPanel, CardLayout cl, JFrame superFrame,
			MiniGames currentMiniGame) {
		minigamePanel.setLayout(cl);
		frame.getContentPane().add(minigamePanel, "minigame");
		cl.show(superFrame.getContentPane(), "minigame");
		minigamePanel.requestFocus();
	}

	public void returnToSpeedrunPanel(SpeedrunPanel speedrunPanel, CardLayout cl, JFrame superFrame, boolean success) {
		speedrunPanel.miniGameFinished(success);

		cl.show(superFrame.getContentPane(), "speedrun");
		speedrunPanel.requestFocus();
	}

	public JFrame getFrame() {
		return frame;
	}
}
