package panels;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import main.Main;
import minigame.MiniGames;
import util.Util;

public class SpeedrunPanel extends GamePanel {
    private Main main;
    private int elapsedTime = 0;
    private int addTime = 0;
    private Timer timer;
    private boolean inMiniGame = false;
    private String currentTimerText = "00:00:00";
    private boolean isGameEnded = false; // 게임 종료 상태 변수 추가
    private boolean isMiniGameFailed = false;
    private int score = 0, maxscore = 300000;
    float alpha = 0.7f;

    public SpeedrunPanel(JFrame superFrame, CardLayout cl, Object o) {
        super(superFrame, cl, (Main) o);
        setLayout(null);
        showScore = false; // 점수 표시를 비활성화합니다.

        elapsedTime = 0;
        timer = new Timer();
        score = 0;
    }

    public void gameStart() {
        resetGame();
        super.gameStart();
        startSpeedrunTimer();
    }

    public String getTime() {
        return currentTimerText;
    }

    public void setMiniGameFailed(boolean setKey) {
        this.isMiniGameFailed = setKey;
    }

    private void startSpeedrunTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!inMiniGame && !isGameEnded && !isEscKeyOn()) {
                    elapsedTime += 10;
                    int minutes = (elapsedTime / 60000) % 60;
                    int seconds = (elapsedTime / 1000) % 60;
                    int milliseconds = (elapsedTime / 10) % 100;
                    currentTimerText = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
                    repaint();

                    resultScore = getResultScore();
                }
                if (isHit()) {
                    elapsedTime += 3000;
                    setHit(false);
                }
                if (isMiniGameFailed) {
                    elapsedTime += 10000; // 미니게임 실패 시 타이머에 10초 추가
                    isMiniGameFailed = false;
                }
                if (addTime != 0) {
                    elapsedTime += addTime;
                    addTime = 0;
                }

                score = resultScore;
                if (resultScore == maxscore)
                    endGame();
                else if (score >= maxscore / 4 && score % (maxscore / 4) == 0 && !inMiniGame) {
                    startMiniGame();
                }
            }
        }, 10, 10); // 10 밀리초마다 업데이트
    }

    private void startMiniGame() {
        inMiniGame = true;
        setEscKeyOn(true);

        MiniGamePanel miniGamePanel = new MiniGamePanel(this);
        main.getFrame().getContentPane().add(miniGamePanel, "minigame");
        miniGamePanel.startMiniGame();
    }

    public void miniGameFinished(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, "미니게임 성공!");
        } else {
            JOptionPane.showMessageDialog(this, "미니게임 실패! 10초가 추가됩니다.");
            isMiniGameFailed = true;
        }
        inMiniGame = false;
        setEscKeyOn(false); // 게임을 다시 시작하도록 설정
        main.getFrame().getContentPane().remove(main.getFrame().getContentPane().getComponentCount() - 1); // Remove
                                                                                                           // MiniGamePanel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // 타이머와 게이지 바를 그립니다.
        Util.drawFancyString(g2, currentTimerText, 600, 58, 30, Color.WHITE);

        // 점수 게이지바를 그립니다
        g2.drawImage(gaugeBar.getImage(), 20, 70, null);
        g2.setColor(Color.BLACK);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fillRect(55 + (int) (453 * ((double) score / maxscore)), 105,
                1 + 453 - (int) (453 * ((double) score / maxscore)), 21);

        // 점수를 그립니다.
        if (showScore) {
            Util.drawFancyString(g2, Integer.toString(resultScore), 600, 100, 30, Color.WHITE);
        }
    }

    private void endGame() {
        if (!isGameEnded) {
            isGameEnded = true; // 게임 종료 상태로 설정
            timer.cancel();
            c1.setHealth(0); // 쿠키의 체력을 모두 깎음
            JOptionPane.showMessageDialog(this, "게임 종료! 플레이 시간: " + currentTimerText);

            // 종료 로직
            main.getEndPanel().setResultTime(currentTimerText);
            main.getEndPanel().setSpeedrunGame(true);
            cl.show(superFrame.getContentPane(), "end");
            resetGame(); // 게임 상태 초기화
            superFrame.requestFocus();
        }
    }

    private void resetGame() {
        elapsedTime = 0;
        addTime = 0;
        inMiniGame = false;
        currentTimerText = "00:00:00";
        isGameEnded = false;
        isMiniGameFailed = false;
        score = 0;
        resultScore = 0;
        c1.setHealth(1000); // 쿠키의 체력을 초기화합니다.
    }

    public Main getMain() {
        return main;
    }

}
