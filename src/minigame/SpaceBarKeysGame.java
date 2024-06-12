package minigame;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SpaceBarKeysGame extends JPanel implements KeyListener {

    private int progress;
    private int timeLeft;
    private Timer timer; 

    public SpaceBarKeysGame() {
        this.progress = 0;
        this.timeLeft = 10; // 10초
        this.setPreferredSize(new Dimension(800, 600));

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                if (timeLeft <= 0) {
                    timer.stop();
                }
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 색상
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 게이지 바
        g.setColor(Color.YELLOW);
        g.fillRect(50, 500 - progress, 50, progress);

        // 남은 시간
        g.setColor(Color.BLACK);
        g.setFont(new Font("CookierunOTF", Font.BOLD, 24));
        g.drawString("남은 시간: " + timeLeft + "초", 50, 50);

        // 게임 종료 시 메시지
        if (timeLeft <= 0) {
            g.drawString("게임 종료!", 50, 100);
            g.drawString("최종 점수: " + progress, 50, 150);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && timeLeft > 0) {
            progress += 5;
            if (progress > 480) {
                progress = 480; // 최대 게이지 제한
            }
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
