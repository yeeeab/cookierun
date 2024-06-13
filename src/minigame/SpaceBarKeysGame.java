package minigame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import panels.MiniGamePanel;

public class SpaceBarKeysGame extends MiniGames implements KeyListener {

    private int progress;
    private int timeLeft;
    private Timer timer;
    private ImageIcon gaugeBar;
    private float alpha = 0.7f; // 투명도 설정 (예시값)

    public SpaceBarKeysGame(MiniGamePanel parentPanel) {
        super(parentPanel);
        this.progress = 0;
        this.timeLeft = 10; // 10초
        this.setPreferredSize(new Dimension(800, 600));
        addKeyListener(this);

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                if (timeLeft <= 0) {
                    timer.stop();
                    checkGameEnd();
                }
                repaint();
            }
        });

        gaugeBar = new ImageIcon("img/Objectimg/lifebar/GaugeBar1.png");
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 배경 색상
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // 스페이스 연타 메세지
        g.setColor(Color.BLACK);
        g.setFont(new Font("CookierunOTF", Font.BOLD, 40));
        g.drawString("스페이스 연타!", 300, 50);

        // 게이지 바
        g2.drawImage(gaugeBar.getImage(), 20, 70, null);
        g2.setColor(Color.BLACK);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fillRect(55 + (int) (453 * ((double) progress / 400)), 105,
                1 + 453 - (int) (453 * ((double) progress / 400)), 21);

        // 남은 시간
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("CookierunOTF", Font.BOLD, 24));
        g2.drawString("남은 시간: " + timeLeft + "초", 50, 50);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && timeLeft > 0) {
            progress += 10;
            if (progress >= 400) {
                progress = 400; // 최대 게이지 제한
                timer.stop();
                gameSuccess();
                resetGame();
            }
            repaint();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void startGame() {
        this.progress = 0;
        this.timeLeft = 10; // 게임 시간 초기화
        timer.start();
        this.requestFocusInWindow(); // 키 이벤트를 받을 수 있도록 포커스 설정
    }

    private void checkGameEnd() {
        if (progress >= 400) {
            gameSuccess();
        } else {
            gameFailure();
        }
        resetGame();
    }

    private void resetGame() {
        this.progress = 0;
        this.timeLeft = 10;
        repaint();
    }
}
