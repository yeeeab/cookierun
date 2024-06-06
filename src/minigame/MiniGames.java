package minigame;

import javax.swing.JPanel;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import panels.MiniGamePanel;
import main.Main;
import java.awt.CardLayout;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.AlphaComposite;
import panels.*;

public abstract class MiniGames extends JPanel {
    protected MiniGamePanel parentPanel;
    protected Timer timer;
    protected int elapsedTime; // 경과 시간을 밀리초 단위로 저장
    protected boolean isSuccess;
    private Image backgroundImage;

    public MiniGames(MiniGamePanel parentPanel) {
        this.parentPanel = parentPanel;
        this.elapsedTime = 0;
        this.isSuccess = false;

        setLayout(null);
        setBounds(0, 0, 800, 500);

        // 배경 이미지 로드
        ImageIcon icon = new ImageIcon("img/end/cookierunbg.jpg");
        backgroundImage = icon.getImage();

        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedTime += 10; // 10 밀리초마다 업데이트
            }
        }, 10, 10); // 10 밀리초마다 업데이트
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // 배경 이미지 그리기
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // 명도 낮추기 위해 투명도 설정
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }

    protected void endGame() {
        timer.cancel();
        parentPanel.gameFinished(isSuccess);
    }

    protected void gameSuccess() {
        isSuccess = true;
        endGame();
    }

    protected void gameFailure() {
        isSuccess = false;
        endGame();
    }

    public static void startRandomGame(SpeedrunPanel parentPanel, Main main, CardLayout cl, JFrame superFrame) {
        Random random = new Random();
        int gameIndex = random.nextInt(1); // 현재는 1개의 미니게임만 있음
        MiniGames miniGame = null;

        switch (gameIndex) {
            case 0:
                miniGame = new NumberOrderGame(new MiniGamePanel(parentPanel));
                break;
            // 다른 미니게임을 추가할 경우, 여기에 case를 추가합니다.
        }

        if (miniGame != null) {
            main.switchToMiniGame(parentPanel, cl, superFrame, miniGame);
        }
    }

    public void startGame() {
        // Initialize or reset any necessary components before starting the game
    }
}
