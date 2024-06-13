package potion;

import java.awt.Image;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ingame.Coin;

public class CoinPotion extends Potion {
    // 효과 지속 시간 (7초)
    private static final int EFFECT_DURATION = 7000;

    // 타이머
    private Timer timer;
    private boolean isUsed;

    public CoinPotion(Image image, String name, int x, int y, int width, int height, int numOfPotion) {
        super(image, name, x, y, width, height, numOfPotion);
        this.isUsed = false;
    }

    public void use() {
        if (getNumOfPotion() > 0) {
            setNumOfPotion(getNumOfPotion() - 1);
            setIsPotionUsed(true);
            isUsed = true;
            startEffect();
        } else {
            JOptionPane.showMessageDialog(null, "No more potions left!");
        }
    }

    private void startEffect() {
        // 코인 점수를 두배로 증가
        Coin.setDoubleScore(true);

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
        return isUsed;
    }
}