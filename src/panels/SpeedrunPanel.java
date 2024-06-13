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
    private boolean isGameEnded = false;
    private boolean isMiniGameFailed = false;
    int resultScore = 0;
    private int maxscore = 300000;
    private int[] miniGameScoreThresholds = { 75000, 150000, 225000 };
    private int thresholdMargin = 1000;
    float alpha = 0.7f;
    int miniGamesPlayed = 0;

    public SpeedrunPanel(JFrame superFrame, CardLayout cl, Main main) {
        super(superFrame, cl, main);
        this.main = main;
        setLayout(null);
        showScore = false;

        elapsedTime = 0;
        timer = new Timer();

        removePotionBtn();
    }

    private void removePotionBtn() {
        main.getPotions().remove(speedUpPotion);
        super.remove(speedUpPotionBtn);
        super.repaint();

        main.getPotions().remove(coinPotion);
        super.remove(coinPotionBtn);
        super.repaint();
    }

    public void gameStart() {
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

                    if (!isGameEnded) {
                        if (resultScore >= maxscore) {
                            System.out.println("Game should end now due to max score");
                            endGame();
                        } else if (shouldStartMiniGame(resultScore)) {
                            System.out.println("Mini game start");
                            setEscKeyOn(true);
                            startMiniGame();
                        }
                    }
                }
                if (isHit()) {
                    elapsedTime += 3000;
                    setHit(false);
                }
                if (isMiniGameFailed) {
                    elapsedTime += 10000;
                    isMiniGameFailed = false;
                }
                if (addTime != 0) {
                    elapsedTime += addTime;
                    addTime = 0;
                }
            }
        }, 10, 10);
    }

    private void startMiniGame() {
        try {
            inMiniGame = true;
            MiniGames.startRandomGame(this);
        } catch (Exception e) {
            System.err.println("Exception occurred in startMiniGame: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addScoreToExitRange(int score) {
        System.out.println("Adding score to exit range...");
        if (miniGamesPlayed < miniGameScoreThresholds.length
                && score >= miniGameScoreThresholds[miniGamesPlayed] - thresholdMargin) {
            resultScore = miniGameScoreThresholds[miniGamesPlayed] + thresholdMargin + 1;
            setResultScore(resultScore);
            System.out.println("Score after adding to exit range: " + resultScore);
        }
    }

    public void miniGameFinished(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(this, "미니게임 성공!");
        } else {
            JOptionPane.showMessageDialog(this, "미니게임 실패! 10초가 추가됩니다.");
            isMiniGameFailed = true;
        }
        inMiniGame = false;
        miniGamesPlayed++;
        setEscKeyOn(false);
        ((CardLayout) main.getFrame().getContentPane().getLayout()).show(main.getFrame().getContentPane(), "speedrun");
        requestFocus();

        if (resultScore >= maxscore) {
            endGame();
        }
    }

    private boolean shouldStartMiniGame(int score) {
        if (miniGamesPlayed < miniGameScoreThresholds.length
                && score >= miniGameScoreThresholds[miniGamesPlayed]) {
            System.out.println("Score in range for mini game: " + score);
            return true;
        }
        return false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        Util.drawFancyString(g2, currentTimerText, 600, 58, 30, Color.WHITE);

        g2.drawImage(gaugeBar.getImage(), 20, 70, null);
        g2.setColor(Color.BLACK);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fillRect(55 + (int) (453 * ((double) resultScore / maxscore)), 105,
                1 + 453 - (int) (453 * ((double) resultScore / maxscore)), 21);

        if (showScore) {
            Util.drawFancyString(g2, Integer.toString(resultScore), 600, 100, 30, Color.WHITE);
        }
    }

    private void endGame() {
        if (!isGameEnded) {
            isGameEnded = true;
            timer.cancel();
            c1.setHealth(0);

            if (resultScore < maxscore) {
                main.getEndPanel().setSpeedrunScore("99:99:99", getResultScore());
            } else {
                main.getEndPanel().setSpeedrunScore(currentTimerText, getResultScore());
            }
            main.getEndPanel().setCoinScore(coinScore); // Ensure the coin score is set
            main.getEndPanel().setTotalCoins(main.getTotalCoinScore());
            main.getEndPanel().setSpeedrunGame(true);
            main.getEndPanel().updateLabel();

            cl.show(superFrame.getContentPane(), "end");
            superFrame.requestFocus();
        }
    }

    public Main getMain() {
        return main;
    }

    public void switchToMiniGame(MiniGames miniGame) {
        main.getFrame().getContentPane().add(miniGame, "minigame");
        ((CardLayout) main.getFrame().getContentPane().getLayout()).show(main.getFrame().getContentPane(), "minigame");
        miniGame.startGame();
    }
}
