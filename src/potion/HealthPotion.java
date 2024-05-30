package potion;

import javax.swing.JOptionPane;

public class HealthPotion extends Potion {
    public HealthPotion() {
        super();
    }

    @Override
    public void use() {
        if (getNumOfPotion() > 0) {
            setNumOfPotion(getNumOfPotion() - 1);
            setIsPotionUsed(true);
            // Additional logic to increase health by 1.2x can be added here
        } else {
            JOptionPane.showMessageDialog(null, "No more potions left!");
        }
        setIsPotionSelected(false); // Reset selection after use
    }
}
