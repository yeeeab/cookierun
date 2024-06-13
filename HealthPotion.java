package potion;

import panels.GamePanel;

public class HealthPotion {
    private int healAmount;
    private boolean used;

    public HealthPotion() {
        this.healAmount = 50; // 포션 사용 시 회복할 체력량
        this.used = false;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    public void use(GamePanel gamePanel) {
        if (!used) {
            gamePanel.increaseHealthTemporarily(healAmount, 5000); // 5초 동안 체력 50 증가
            used = true;
        }
    }

    public boolean isUsed() {
        return used;
    }
}
