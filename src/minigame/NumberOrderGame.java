package minigame;

import panels.MiniGamePanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class NumberOrderGame extends MiniGames {
    private ArrayList<JButton> numberButtons;
    private int nextNumber;

    public NumberOrderGame(MiniGamePanel parentPanel) {
        super(parentPanel);
        setupGame();
    }

    private void setupGame() {
        numberButtons = new ArrayList<>();
        nextNumber = 1;

        // 1부터 9까지의 숫자를 랜덤하게 섞는다.
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        // 랜덤하게 섞인 숫자 버튼을 생성하고 패널에 추가한다.
        for (int i = 0; i < 9; i++) {
            int number = numbers.get(i);
            JButton button = new JButton(String.valueOf(number));
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBounds((i % 3) * 100 + 250, (i / 3) * 100 + 100, 80, 80);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(number, button);
                }
            });
            numberButtons.add(button);
            add(button);
        }

        JLabel instructionLabel = new JLabel("Press the numbers in order");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        instructionLabel.setBounds(250, 30, 300, 50);
        add(instructionLabel);
    }

    private void handleButtonClick(int number, JButton button) {
        if (number == nextNumber) {
            nextNumber++;
            button.setVisible(false);
            if (nextNumber > 9) {
                parentPanel.gameFinished(true);
            }
        } else {
            parentPanel.gameFinished(false);
        }
    }

    @Override
    public void startGame() {
        removeAll(); // 모든 컴포넌트를 제거하여 초기 상태로 만듦
        setupGame(); // 게임을 새로 설정
        revalidate(); // 컴포넌트 트리를 다시 검증
        repaint(); // 다시 그리기
    }
}
