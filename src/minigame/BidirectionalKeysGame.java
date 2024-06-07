// package minigame;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.KeyAdapter;
// import java.awt.event.KeyEvent;
// import java.util.ArrayList;
// import java.util.List;

// public class BidirectionalKeysGame extends JFrame {
//     private final int MAX_TIME = 60; // 최대 제한 시간 (초)
//     private int currentStage = 1; // 현재 스테이지
//     private int timeLeft = MAX_TIME; // 남은 시간
//     private Timer timer;
//     private Timer scrollTimer;
//     private List<Integer> keySequence; // 키 시퀀스
//     private int keyIndex = 0; // 현재 키 인덱스
//     private int scrollX = 800; // 시작 위치

//     private JLabel stageLabel;
//     private JLabel timeLabel;
//     private KeyPanel keyPanel;

//     private boolean[] keyStates = new boolean[256]; // 키 상태 추적 배열

//     public BidirectionalKeysGame() {
//         setTitle("방향키 누르기 게임");
//         setSize(800, 600);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLayout(new BorderLayout());

//         stageLabel = new JLabel("STAGE: " + currentStage);
//         stageLabel.setFont(new Font("CookieRunOTF", Font.BOLD, 24));
//         add(stageLabel, BorderLayout.NORTH);

//         keyPanel = new KeyPanel();
//         add(keyPanel, BorderLayout.CENTER);

//         timeLabel = new JLabel("제한시간: " + timeLeft);
//         timeLabel.setFont(new Font("CookieRunOTF", Font.BOLD, 24));
//         add(timeLabel, BorderLayout.SOUTH);

//         addKeyListener(new KeyAdapter() {
//             @Override
//             public void keyPressed(KeyEvent e) {
//                 keyStates[e.getKeyCode()] = true;
//                 checkKeyPress(e.getKeyCode());
//             }

//             @Override
//             public void keyReleased(KeyEvent e) {
//                 keyStates[e.getKeyCode()] = false;
//             }
//         });

//         startNewStage();
//     }

//     private void startNewStage() {
//         keySequence = generateKeySequence();
//         scrollX = 800; // 시작 위치 초기화
//         startTimer();
//         startScrollTimer();
//     }

//     private List<Integer> generateKeySequence() {
//         List<Integer> sequence = new ArrayList<>();
//         for (int i = 0; i < 20; i++) { // 더 많은 키 시퀀스를 생성합니다.
//             sequence.add(KeyEvent.VK_LEFT);
//             sequence.add(KeyEvent.VK_RIGHT);
//             sequence.add(KeyEvent.VK_UP);
//             sequence.add(KeyEvent.VK_DOWN);
//         }
//         return sequence;
//     }

//     private void startTimer() {
//         if (timer != null) {
//             timer.stop();
//         }
//         timeLeft = MAX_TIME;
//         timeLabel.setText("제한시간: " + timeLeft);
//         timer = new Timer(1000, new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 timeLeft--;
//                 timeLabel.setText("제한시간: " + timeLeft);
//                 if (timeLeft <= 0) {
//                     timer.stop();
//                     scrollTimer.stop();
//                     gameOver();
//                 }
//             }
//         });
//         timer.start();
//     }

//     private void startScrollTimer() {
//         if (scrollTimer != null) {
//             scrollTimer.stop();
//         }
//         scrollTimer = new Timer(30, new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 scrollX -= 5;
//                 if (scrollX + keySequence.size() * 100 < 0) {
//                     scrollX = 800;
//                 }
//                 keyPanel.repaint();
//                 handleContinuousKeyPress();
//             }
//         });
//         scrollTimer.start();
//     }

//     private void handleContinuousKeyPress() {
//         for (int keyCode = 0; keyCode < keyStates.length; keyCode++) {
//             if (keyStates[keyCode]) {
//                 checkKeyPress(keyCode);
//             }
//         }
//     }

//     private void checkKeyPress(int keyCode) {
//         int squareCenterX = 200; // 네모 칸의 중심 X 좌표 (예: 200)
//         int squareCenterY = 300; // 네모 칸의 중심 Y 좌표 (예: 300)
//         int keyPosition = scrollX + keyIndex * 100;
//         if (Math.abs(squareCenterX - keyPosition) <= 50 && keyCode == keySequence.get(keyIndex)) {
//             keyIndex++;
//             if (keyIndex == keySequence.size()) {
//                 nextStage();
//             }
//         } else if (Math.abs(squareCenterX - keyPosition) <= 50 && keyCode != keySequence.get(keyIndex)) {
//             gameOver();
//         }
//     }

//     private void nextStage() {
//         timer.stop();
//         scrollTimer.stop();
//         currentStage++;
//         keyIndex = 0;
//         stageLabel.setText("STAGE: " + currentStage);
//         startNewStage();
//     }

//     private void gameOver() {
//         JOptionPane.showMessageDialog(this, "게임 오버! 당신은 " + currentStage + " 스테이지까지 도달했습니다.");
//         System.exit(0);
//     }

//     private class KeyPanel extends JPanel {
//         @Override
//         protected void paintComponent(Graphics g) {
//             super.paintComponent(g);

//             int squareCenterX = 390; // 네모 칸의 중심 X 좌표 (예: 200)
//             int squareCenterY = 280; // 네모 칸의 중심 Y 좌표 (예: 300)
//             int squareSize = 110;

//             // 네모 칸을 그립니다.
//             g.setColor(Color.RED);
//             g.drawRect(squareCenterX - squareSize / 2, squareCenterY - squareSize / 2, squareSize, squareSize);

//             int x = scrollX;
//             g.setColor(Color.BLACK); // 방향키 색상을 검은색으로 설정합니다.
//             for (int key : keySequence) {
//                 g.setFont(new Font("CookieRunOTF", Font.BOLD, 80));
//                 g.drawString(getKeyName(key), x, squareCenterY + 40);
//                 x += 100;
//             }
//         }

//         private String getKeyName(int keyCode) {
//             switch (keyCode) {
//                 case KeyEvent.VK_LEFT: return "←";
//                 case KeyEvent.VK_RIGHT: return "→";
//                 case KeyEvent.VK_UP: return "↑";
//                 case KeyEvent.VK_DOWN: return "↓";
//                 default: return "";
//             }
//         }
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(new Runnable() {
//             @Override
//             public void run() {
//                 new BidirectionalKeysGame().setVisible(true);
//             }
//         });
//     }
// }