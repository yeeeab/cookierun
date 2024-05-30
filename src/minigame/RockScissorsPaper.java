package minigame;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import javafx.event.ActionEvent;

public class RockScissorsPaper extends JFrame implements ActionListener {
    private JButton rockButton, paperButton, scissorsButton;
    private JLabel resultLabel;

    public RockScissorsPaper() {
        setTitle("가위 바위 보 게임");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // 이미지 아이콘 로드
        ImageIcon rockIcon = new ImageIcon(getClass().getResource("/resources/rock.png"));
        ImageIcon paperIcon = new ImageIcon(getClass().getResource("/resources/paper.png"));
        ImageIcon scissorsIcon = new ImageIcon(getClass().getResource("/resources/scissors.png"));

        // 버튼 초기화 및 이미지 설정
        rockButton = new JButton(rockIcon);
        paperButton = new JButton(paperIcon);
        scissorsButton = new JButton(scissorsIcon);

        // 버튼 리스너 추가
        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorsButton.addActionListener(this);

        // 버튼을 프레임에 추가
        add(rockButton);
        add(paperButton);
        add(scissorsButton);

        // 결과 라벨 추가
        resultLabel = new JLabel("결과가 여기 표시됩니다.");
        add(resultLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        playGame(e.getActionCommand());
    }

    private void playGame(String userChoice) {
        // 가위, 바위, 보 중 하나를 랜덤으로 선택
        String[] choices = { "가위", "바위", "보" };
        int computerChoice = new Random().nextInt(3);

        // 승패 결정 로직
        if (userChoice.equals(choices[computerChoice])) {
            resultLabel.setText("결과: 무승부! 다시 시도하세요.");
            playGame(userChoice); // 무승부일 경우 재귀적으로 게임을 다시 시작
        } else if ((userChoice.equals("바위") && computerChoice == 0) ||
                (userChoice.equals("보") && computerChoice == 1) ||
                (userChoice.equals("가위") && computerChoice == 2)) {
            resultLabel.setText("결과: 이겼습니다!");
            System.exit(0);
        } else {
            resultLabel.setText("결과: 졌습니다!");
            System.exit(0);
        }
    }
}
