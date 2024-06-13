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

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        // 랜덤하게 섞인 숫자 버튼을 생성하고 패널에 추가
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

    public void startGame() {
        removeAll();
        setupGame();
        revalidate();
        repaint();
    }
}
