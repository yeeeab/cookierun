package panels;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import main.Main;

import minigame.*;

import util.Util;

public class SpeedrunPanel extends GamePanel {
    private Main main;
    private int elapsedTime = 0;
    private Timer timer; 
    private JProgressBar gaugeBar;
    private boolean inMiniGame = false;
    private String currentTimerText = "00:00:00";
    private int resultScore = 0;

    public SpeedrunPanel(JFrame superFrame, CardLayout cl, Object o) {
        super(superFrame, cl, (Main) o);
        setLayout(null);
        showScore = false; // 점수 표시를 비활성화합니다.
    }

    public void gameStart() {
        super.gameStart();
        startSpeedrunTimer();
    }

    private void startSpeedrunTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!inMiniGame) {
                    elapsedTime += 10;
                    int minutes = (elapsedTime / 60000) % 60;
                    int seconds = (elapsedTime / 1000) % 60;
                    int milliseconds = (elapsedTime / 10) % 100;
                    currentTimerText = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
                    repaint(); // 타이머 업데이트를 위해 다시 그리기
                }
            }
        }, 10, 10); // 10 밀리초마다 업데이트
    }

    private void startMiniGame() {
        inMiniGame = true;
        timer.cancel(); // 타이머 일시 정지

        MiniGames.startRandomGame(this);
    }

    public void miniGameFinished(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, "미니게임 성공!");
        } else {
            JOptionPane.showMessageDialog(this, "미니게임 실패! 10초 추가");
            elapsedTime += 10000;
        }

        inMiniGame = false;
        gaugeBar.setValue(0);

        startSpeedrunTimer(); // 타이머 재시작
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        Util.drawFancyString(g2, currentTimerText, 600, 58, 30, Color.WHITE);
        // 타이머와 게이지 바를 그립니다.

        // 점수를 그립니다.
        if (showScore) {
            Util.drawFancyString(g2, Integer.toString(resultScore), 600, 100, 30, Color.WHITE);
        }

        // 50,000점 이상일 때마다 미니게임을 시작하고, 200,000점 이상이면 게임을 종료합니다.
        if (resultScore >= 200000) {
            endGame();
        } else if (resultScore >= 50000 && (resultScore % 50000 == 0)) {
            resultScore += 1;
            startMiniGame();
        }
    }

    private void endGame() {
        timer.cancel();
        JOptionPane.showMessageDialog(this, "게임 종료! 플레이 시간: " + currentTimerText);
        // 종료 로직을 여기에 추가
    }
}
