package potion;

import java.awt.Image;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ingame.Coin;
import panels.GamePanel;

public class CoinPotion extends Potion {
    // 포션 사용 후 효과 지속 시간 (7초)
    private static final int EFFECT_DURATION = 7000; // 7 seconds in milliseconds

    // 타이머 객체
    private Timer timer;
    private boolean isUsed;

    private boolean used;

    public CoinPotion() {
        this.used = false;
    }

    public void use(GamePanel gamePanel) {
        if (!used) {
            gamePanel.doubleCoinsTemporarily(5000); // 5초 동안 코인 두 배
            used = true;
        }
    }


    // 효과 시작 메서드
    private void startEffect() {
        // 코인 점수를 두배로 증가시키는 로직을 추가합니다.
        Coin.setDoubleScore(true);

        // EFFECT_DURATION 후에 효과를 종료하는 타이머를 설정합니다.
        timer = new Timer(EFFECT_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coin.setDoubleScore(false);
                setIsPotionUsed(false);
                isUsed = false;
                timer.stop();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public boolean isUsed() {
        return used;
    }
}