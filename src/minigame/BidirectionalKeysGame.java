package minigame;

import panels.MiniGamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class BidirectionalKeysGame extends MiniGames {
    private final int MAX_TIME = 60; // 최대 제한 시간 (초)
    private int currentStage = 1; // 현재 스테이지
    private int timeLeft = MAX_TIME; // 남은 시간
    private Timer timer;
    private List<Integer> keySequence; // 키 시퀀스
    private int keyIndex = 0; // 현재 키 인덱스

    private JLabel stageLabel;
    private JLabel timeLabel;
    private JPanel keyPanel;

    public BidirectionalKeysGame(MiniGamePanel parentPanel) {
        super(parentPanel);
        setupGame();
    }

    private void setupGame() {
        setLayout(new BorderLayout());

        stageLabel = new JLabel("STAGE: " + currentStage);
        stageLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(stageLabel, BorderLayout.NORTH);

        keyPanel = new JPanel();
        add(keyPanel, BorderLayout.CENTER);

        timeLabel = new JLabel("제한시간: " + timeLeft);
        timeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(timeLabel, BorderLayout.SOUTH);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                checkKeyPress(e.getKeyCode());
            }
        });

        startNewStage();
    }

    private void startNewStage() {
        keySequence = generateKeySequence();
        displayKeySequence();
        startTimer();
    }

    private List<Integer> generateKeySequence() {
        List<Integer> sequence = new ArrayList<>();
        sequence.add(KeyEvent.VK_LEFT);
        sequence.add(KeyEvent.VK_RIGHT);
        sequence.add(KeyEvent.VK_UP);
        sequence.add(KeyEvent.VK_DOWN);
        // 필요한 경우 추가 키 시퀀스를 생성하세요.
        return sequence;
    }

    private void displayKeySequence() {
        keyPanel.removeAll();
        for (int key : keySequence) {
            JLabel keyLabel = new JLabel(getKeyName(key));
            keyLabel.setFont(new Font("Serif", Font.BOLD, 48));
            keyPanel.add(keyLabel);
        }
        keyPanel.revalidate();
        keyPanel.repaint();
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

    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }
        timeLeft = MAX_TIME;
        timeLabel.setText("제한시간: " + timeLeft);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timeLabel.setText("제한시간: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.stop();
                    gameFailure();
                }
            }
        });
        timer.start();
    }

    private void checkKeyPress(int keyCode) {
        if (keyCode == keySequence.get(keyIndex)) {
            keyIndex++;
            if (keyIndex == keySequence.size()) {
                nextStage();
            }
        } else {
            gameFailure();
        }
    }

    private void nextStage() {
        timer.stop();
        currentStage++;
        keyIndex = 0;
        stageLabel.setText("STAGE: " + currentStage);
        startNewStage();
    }

    @Override
    public void startGame() {
        removeAll(); // 모든 컴포넌트를 제거하여 초기 상태로 만듦
        currentStage = 1;
        keyIndex = 0;
        setupGame(); // 게임을 새로 설정
        revalidate(); // 컴포넌트 트리를 다시 검증
        repaint(); // 다시 그리기
    }
}
