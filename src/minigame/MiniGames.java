package minigame;

import javax.swing.JPanel;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import panels.SpeedrunPanel;

public class MiniGames extends JPanel {
    protected SpeedrunPanel parentPanel;
    protected Timer timer;
    protected int elapsedTime; // 경과 시간을 밀리초 단위로 저장
    protected boolean isSuccess;

    public MiniGames(SpeedrunPanel parentPanel) {
        this.parentPanel = parentPanel;
        this.elapsedTime = 0;
        this.isSuccess = false;

        setLayout(null);
        setBounds(0, 0, 800, 500);

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

    protected void endGame() {
        timer.cancel();
        parentPanel.miniGameFinished(isSuccess);
    }

    protected void gameSuccess() {
        isSuccess = true;
        endGame();
    }

    protected void gameFailure() {
        isSuccess = false;
        endGame();
    }

    public static MiniGames startRandomGame(SpeedrunPanel parentPanel) {
        Random random = new Random();
        int gameIndex = random.nextInt(1); // 현재는 1개의 미니게임만 있음
        MiniGames miniGame = null;

        switch (gameIndex) {
            case 0:
                miniGame = new NumberOrderGame(parentPanel);
                break;
            // 다른 미니게임을 추가할 경우, 여기에 case를 추가합니다.
        }

        if (miniGame != null) {
            parentPanel.add(miniGame);
            miniGame.setVisible(true);
        }

        return miniGame;
    }
}
