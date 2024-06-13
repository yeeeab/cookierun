package minigame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import panels.MiniGamePanel;

public class BidirectionalKeysGame extends JFrame {
    private final int MAX_TIME = 30; // 제한 시간
    private int currentStage = 1; // 현재 스테이지
    private int timeLeft = MAX_TIME; // 남은 시간
    private int scrollSpeed = 20; // 스크롤 초기 속도
    private Timer timer;
    private Timer scrollTimer;
    private List<Integer> keySequence; // 키 시퀀스
    private int keyIndex = 0; // 현재 키 인덱스
    private int scrollX = 800; // 시작 위치
    MiniGames minigame;

    private JLabel stageLabel;
    private JLabel timeLabel;
    private KeyPanel keyPanel;

    private boolean keyPressed = false; // 키가 눌렸는지 여부를 추적
    private boolean gameOver = false; // 게임 오버 상태 추적

    public BidirectionalKeysGame(MiniGamePanel parentpanel) {
        setTitle("방향키 누르기 게임");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        stageLabel = new JLabel("STAGE: " + currentStage);
        stageLabel.setFont(new Font("CookieRunOTF", Font.BOLD, 24));
        add(stageLabel, BorderLayout.NORTH);

        keyPanel = new KeyPanel();
        add(keyPanel, BorderLayout.CENTER);

        timeLabel = new JLabel("제한시간: " + timeLeft);
        timeLabel.setFont(new Font("CookieRunOTF", Font.BOLD, 24));
        add(timeLabel, BorderLayout.SOUTH);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                keyPressed = true;
                checkKeyPress(e.getKeyCode());
            }

            public void keyReleased(KeyEvent e) {
                keyPressed = false;
            }
        });

        startNewStage();
        startTimer();
    }

    private void startNewStage() {
        if (gameOver) {
            return;
        }
        keySequence = generateKeySequence();
        scrollX = 800; // 시작 위치 초기화
        keyIndex = 0; // 키 인덱스 초기화
        startScrollTimer();
    }

    private List<Integer> generateKeySequence() {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(KeyEvent.VK_LEFT);
        sequence.add(KeyEvent.VK_RIGHT);
        sequence.add(KeyEvent.VK_UP);
        sequence.add(KeyEvent.VK_DOWN);
        return sequence;
    }

    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timeLabel.setText("제한시간: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.stop();
                    scrollTimer.stop();
                    triggerTimeOut();
                }
            }
        });
        timer.start();
    }

    private void startScrollTimer() {
        if (scrollTimer != null) {
            scrollTimer.stop();
        }
        scrollTimer = new Timer(scrollSpeed, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scrollX -= 6;
                if (scrollX + keySequence.size() * 150 < 0) { // 간격 변경
                    scrollX = 800;
                }
                keyPanel.repaint();
                checkKeyMissed();
            }
        });
        scrollTimer.start();
    }

    private void checkKeyMissed() {
        int squareCenterX = 390; // 네모 칸의 중심 X 좌표
        int keyPosition = scrollX + keyIndex * 150; // 간격 변경
        if (keyPosition < squareCenterX - 50 && !keyPressed) {
            triggerGameOver(); // 네모 칸을 지나갔는데도 방향키를 누르지 않으면 게임 종료
        }
    }

    private void checkKeyPress(int keyCode) {
        int squareCenterX = 390; // 네모 칸의 중심 X 좌표
        int keyPosition = scrollX + keyIndex * 150; // 간격 변경

        if (Math.abs(squareCenterX - keyPosition) <= 50) {
            if (keyCode == keySequence.get(keyIndex)) {
                keyPanel.setKeyHit(true);
                Timer animationTimer = new Timer(100, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        keyPanel.setKeyHit(false);
                        ((Timer) e.getSource()).stop();
                    }
                });
                animationTimer.setRepeats(false);
                animationTimer.start();

                keyIndex++;
                if (keyIndex == keySequence.size()) {
                    nextStage();
                }
            } else {
                triggerGameOver();
            }
        }
    }

    private void nextStage() {
        if (gameOver) {
            return;
        }
        if (currentStage >= 5) {
            triggerSuccess();
            return;
        }
        scrollTimer.stop();
        currentStage++;
        keyIndex = 0;
        stageLabel.setText("STAGE: " + currentStage);

        // 속도 증가
        if (scrollSpeed > 5) {
            scrollSpeed -= 2; // 각 스테이지를 통과할 때마다 속도를 증가시킴
        }

        startNewStage();
    }

    private void triggerGameOver() {
        if (!gameOver) {
            gameOver = true;
            JOptionPane.showMessageDialog(this, "게임 오버! 당신은 " + currentStage + " 스테이지까지 도달했습니다.");
            restartStage();
        }
    }

    private void triggerTimeOut() {
        if (!gameOver) {
            gameOver = true;
            JOptionPane.showMessageDialog(this, "시간 초과! 당신은 " + currentStage + " 스테이지까지 도달했습니다.");
            minigame.gameFailure();
        }
    }

    private void triggerSuccess() {
        if (!gameOver) {
            gameOver = true;
            JOptionPane.showMessageDialog(this, "탈출 성공! 당신은 " + currentStage + " 스테이지까지 도달했습니다.");
            minigame.gameSuccess();
        }
    }

    private void restartStage() {
        gameOver = false;
        keyIndex = 0;
        scrollX = 800;
        startScrollTimer();
    }

    private class KeyPanel extends JPanel {
        private boolean keyHit = false;

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int squareCenterX = 390; // 네모 칸의 중심 X 좌표
            int squareCenterY = 280; // 네모 칸의 중심 Y 좌표
            int squareSize = 110;

            g.setColor(Color.RED);
            g.drawRect(squareCenterX - squareSize / 2, squareCenterY - squareSize / 2, squareSize, squareSize);

            int x = scrollX;
            g.setColor(Color.BLACK);
            for (int key : keySequence) {
                g.setFont(new Font("CookieRunOTF", Font.BOLD, 80));
                g.drawString(getKeyName(key), x, squareCenterY + 40);
                x += 150; // 간격 변경
            }

            if (keyHit) {
                g.setColor(Color.GREEN);
                g.fillRect(squareCenterX - squareSize / 2, squareCenterY - squareSize / 2, squareSize, squareSize);
            }
        }

        public void setKeyHit(boolean keyHit) {
            this.keyHit = keyHit;
            repaint();
        }

        private String getKeyName(int keyCode) {
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    return "←";
                case KeyEvent.VK_RIGHT:
                    return "→";
                case KeyEvent.VK_UP:
                    return "↑";
                case KeyEvent.VK_DOWN:
                    return "↓";
                default:
                    return "";

            }

        }

    }

}