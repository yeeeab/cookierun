package potion;

import java.awt.Image;
import panels.GamePanel;

public class SpeedUpPotion extends Potion {
    private boolean used;

    public SpeedUpPotion(Image image, String name, int x, int y, int width, int height, int numOfPotion) {
        super(image, name, x, y, width, height, numOfPotion);
        this.used = false;
    }

    public void use(GamePanel gamePanel) {
        if (!used) {
            gamePanel.increaseSpeedTemporarily(5, 5000); // Increase speed by 5 for 15 seconds
            used = true;
        }
    }

    public boolean isUsed() {
        return used;
    }
}
