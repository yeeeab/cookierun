package minigame;

import javax.swing.*;

import main.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import panels.MiniGamePanel;
import panels.SpeedrunPanel;

public class BidirectionalKeysGame extends MiniGames {
    private int scrollSpeed = 20; // 스크롤 초기 속도
    private Timer scrollTimer;
    private List<Integer> keySequence; // 키 시퀀스
    private int keyIndex = 0; // 현재 키 인덱스
    private int scrollX = 800; // 시작 위치

    private KeyPanel keyPanel;

    private boolean keyPressed = false; // 키가 눌렸는지 여부를 추적
    private boolean gameOver = false; // 게임 오버 상태 추적

    public BidirectionalKeysGame(MiniGamePanel parentPanel) {
        super(parentPanel);
        setupGame();
    }

    private void setupGame() {
        setLayout(new BorderLayout());
        keyPanel = new KeyPanel();
        add(keyPanel, BorderLayout.CENTER);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyPressed = true;
                checkKeyPress(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyPressed = false;
            }
        });

        startNewSequence();
        startScrollTimer();
    }

    private void startNewSequence() {
        if (gameOver) {
            return;
        }
        keySequence = generateKeySequence();
        scrollX = 800; // 시작 위치 초기화
        keyIndex = 0; // 키 인덱스 초기화
    }

    private List<Integer> generateKeySequence() {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(KeyEvent.VK_LEFT);
        sequence.add(KeyEvent.VK_RIGHT);
        sequence.add(KeyEvent.VK_UP);
        sequence.add(KeyEvent.VK_DOWN);
        return sequence;
    }

    private void startScrollTimer() {
        if (scrollTimer != null) {
            scrollTimer.stop();
        }
        scrollTimer = new Timer(scrollSpeed, new ActionListener() {
            @Override
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
            gameFailure(); // 네모 칸을 지나갔는데도 방향키를 누르지 않으면 게임 종료
        }
    }

    private void checkKeyPress(int keyCode) {
        int squareCenterX = 390; // 네모 칸의 중심 X 좌표 
        int keyPosition = scrollX + keyIndex * 150; // 간격 변경

        if (Math.abs(squareCenterX - keyPosition) <= 50) {
            if (keyCode == keySequence.get(keyIndex)) {
                keyPanel.setKeyHit(true);
                Timer animationTimer = new Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        keyPanel.setKeyHit(false);
                        ((Timer) e.getSource()).stop();
                    }
                });
                animationTimer.setRepeats(false);
                animationTimer.start();

                keyIndex++;
                if (keyIndex == keySequence.size()) {
                    gameSuccess();
                }
            } else {
                gameFailure();
            }
        }
    }

    private class KeyPanel extends JPanel {
        private boolean keyHit = false;

        @Override
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
                case KeyEvent.VK_LEFT: return "←";
                case KeyEvent.VK_RIGHT: return "→";
                case KeyEvent.VK_UP: return "↑";
                case KeyEvent.VK_DOWN: return "↓";
                default: return "";
            }
        }
    }

    @Override
    public void startGame() {
        removeAll(); // 모든 컴포넌트를 제거하여 초기 상태로 만듦
        setupGame(); // 게임을 새로 설정
        revalidate(); // 컴포넌트 트리를 다시 검증
        repaint(); // 다시 그리기
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
             public void run() {
                JFrame frame = new JFrame();
                frame.setSize(800, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new CardLayout());
                Main main = new Main();
                SpeedrunPanel speedrunPanel = new SpeedrunPanel(frame, new CardLayout(), main);
                MiniGamePanel miniGamePanel = new MiniGamePanel(speedrunPanel);
                frame.add(miniGamePanel, "miniGamePanel");
                frame.setVisible(true);
                miniGamePanel.startRandomGame();
            }
        });
    }
}
