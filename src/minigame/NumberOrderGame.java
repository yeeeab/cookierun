package minigame;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

import panels.SpeedrunPanel;

public class NumberOrderGame extends MiniGames {
    private ArrayList<JButton> numberButtons;
    private int nextNumber;

    public NumberOrderGame(SpeedrunPanel parentPanel) {
        super(parentPanel);

        numberButtons = new ArrayList<>();
        nextNumber = 1;

        setupGame();
    }

    private void setupGame() {
        // 1부터 9까지의 숫자 버튼을 생성하고 섞는다.
        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBounds((i - 1) % 3 * 100 + 250, (i - 1) / 3 * 100 + 100, 80, 80);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(Integer.parseInt(button.getText()));
                }
            });
            numberButtons.add(button);
        }

        Collections.shuffle(numberButtons);
        for (JButton button : numberButtons) {
            add(button);
        }

        JLabel instructionLabel = new JLabel("숫자를 순서대로 눌러주세요!");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        instructionLabel.setBounds(250, 30, 300, 50);
        add(instructionLabel);

        JButton skipButton = new JButton("Skip Minigame");
        skipButton.setBounds(350, 400, 150, 30);
        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "미니게임을 건너뛰시겠습니까? 10초가 추가됩니다.", "Skip Minigame",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    parentPanel.setMiniGameFailed(true);
                    gameFailure();
                }
            }
        });
        add(skipButton);
    }

    private void handleButtonClick(int number) {
        if (number == nextNumber) {
            nextNumber++;
            if (nextNumber > 9) {
                gameSuccess();
            }
        } else {
            gameFailure();
        }
    }
}
