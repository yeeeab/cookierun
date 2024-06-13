package potion;

public class HealthPotion {
    private int healAmount;

    public HealthPotion() {
        this.healAmount = 50; // 포션 사용 시 회복할 체력량
    }

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }
}
