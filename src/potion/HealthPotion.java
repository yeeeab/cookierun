package potion;

import java.awt.Image;
import javax.swing.JOptionPane;
import panels.GamePanel;

public class HealthPotion extends Potion {
    public HealthPotion(Image image, String name, int x, int y, int width, int height, int numOfPotion) {
        super(image, name, x, y, width, height, numOfPotion);
    }

    @Override
    public void use(GamePanel gaemPanel) {
        if (getNumOfPotion() > 0) {
            setNumOfPotion(getNumOfPotion() - 1);
            setIsPotionUsed(true);
            // Additional logic to increase health by 1.2x can be added here
        } else {
            JOptionPane.showMessageDialog(null, "No more potions left!");
        }
    }
}
