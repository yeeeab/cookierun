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
        setLayout(null);

        // 이미지 아이콘 로드 및 크기 조정
        ImageIcon rockIcon = new ImageIcon("img/miniGame/rock.jpg");
        Image rockImage = rockIcon.getImage();
        Image scaledRockImage = rockImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledRockIcon = new ImageIcon(scaledRockImage);

        ImageIcon paperIcon = new ImageIcon("img/miniGame/paper.jpg");
        Image paperImage = paperIcon.getImage();
        Image scaledPaperImage = paperImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledPaperIcon = new ImageIcon(scaledPaperImage);

        ImageIcon scissorsIcon = new ImageIcon("img/miniGame/scissors.jpg");
        Image scissorsImage = scissorsIcon.getImage();
        Image scaledScissorsImage = scissorsImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledScissorsIcon = new ImageIcon(scaledScissorsImage);

        // 버튼 초기화 및 이미지 설정
        rockButton = createButton("바위", scaledRockIcon);
        paperButton = createButton("보", scaledPaperIcon);
        scissorsButton = createButton("가위", scaledScissorsIcon);

        // 버튼 크기 및 위치 설정
        rockButton.setBounds(200, 150, 120, 120);
        paperButton.setBounds(340, 150, 120, 120);
        scissorsButton.setBounds(480, 150, 120, 120);

        // 버튼을 패널에 추가
        add(rockButton);
        add(paperButton);
        add(scissorsButton);

        // 결과 라벨 추가 및 위치 설정
        resultLabel = new JLabel("Rock, paper, scissors!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setBounds(300, 300, 400, 30); // 라벨 크기 및 위치 설정
        add(resultLabel);
    }

    private JButton createButton(String text, ImageIcon icon) {
        JButton button = new JButton(text, icon);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setIconTextGap(10); // 아이콘과 텍스트 간격 설정
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.addActionListener(this);
        return button;
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
            resultLabel.setText("Result: A draw, try again.");
        } else if ((userChoice.equals("바위") && computerChoice == 0) ||
                (userChoice.equals("보") && computerChoice == 1) ||
                (userChoice.equals("가위") && computerChoice == 2)) {
            resultLabel.setText("Result: You win!");
            gameSuccess();
        } else {
            resultLabel.setText("Result: You lose!");
            gameFailure();
        }
    }

    @Override
    public void startGame() {
        resultLabel.setText("The result will display here");
    }
}
