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
            setdrinkHealthPotion(); // Set the flag to true when potion is used
        } else {
            JOptionPane.showMessageDialog(null, "No more potions left!");
        }
        setIsPotionSelected(false); // Reset selection after use
    }

    public void setdrinkHealthPotion() {
        drinkHealthPotion = true;
    }
}
