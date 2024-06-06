package minigame;

import panels.MiniGamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockScissorsPaper extends MiniGames implements ActionListener {
    private JButton rockButton, paperButton, scissorsButton;
    private JLabel resultLabel;

    public RockScissorsPaper(MiniGamePanel parentPanel) {
        super(parentPanel);
        setupGame();
    }

    private void setupGame() {
        setLayout(new FlowLayout());

        // 이미지 아이콘 로드
        ImageIcon rockIcon = new ImageIcon("img/miniGame/rock.jpg");
        ImageIcon paperIcon = new ImageIcon("img/miniGame/paper.jpg");
        ImageIcon scissorsIcon = new ImageIcon("img/miniGame/scissors.jpg");

        // 버튼 초기화 및 이미지 설정
        rockButton = new JButton("바위", rockIcon);
        paperButton = new JButton("보", paperIcon);
        scissorsButton = new JButton("가위", scissorsIcon);

        // 버튼 리스너 추가
        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorsButton.addActionListener(this);

        // 버튼을 패널에 추가
        add(rockButton);
        add(paperButton);
        add(scissorsButton);

        // 결과 라벨 추가
        resultLabel = new JLabel("결과가 여기 표시됩니다.");
        add(resultLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playGame(((JButton) e.getSource()).getText());
    }

    private void playGame(String userChoice) {
        // 가위, 바위, 보 중 하나를 랜덤으로 선택
        String[] choices = { "가위", "바위", "보" };
        int computerChoice = new Random().nextInt(3);

        // 승패 결정 로직
        if (userChoice.equals(choices[computerChoice])) {
            resultLabel.setText("결과: 무승부! 다시 시도하세요.");
        } else if ((userChoice.equals("바위") && computerChoice == 0) ||
                (userChoice.equals("보") && computerChoice == 1) ||
                (userChoice.equals("가위") && computerChoice == 2)) {
            resultLabel.setText("결과: 이겼습니다!");
            gameSuccess();
        } else {
            resultLabel.setText("결과: 졌습니다!");
            gameFailure();
        }
    }

    @Override
    public void startGame() {
        resultLabel.setText("결과가 여기 표시됩니다.");
    }
}
