package potion;

import panels.GamePanel;

public class SpeedUpPotion {
    private boolean used;

    public SpeedUpPotion() {
        this.used = false;
    }

    public void use(GamePanel gamePanel) {
        if (!used) {
            gamePanel.increaseSpeedTemporarily(5, 5000); // Increase speed by 5 for 5 seconds
            used = true;
        }
    }

    public boolean isUsed() {
        return used;
    }
}