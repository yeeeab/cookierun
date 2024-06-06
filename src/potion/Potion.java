package potion;

import java.awt.Image;
import javax.swing.JOptionPane;

import ingame.Cookie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Potion {
    private Image image; // Potion image

    // Potion coordinates and size
    private int x;
    private int y;
    private int width;
    private int height;

    // Potion quantity
    private int numOfPotion;

    // Flag indicating if potion is used
    private boolean isPotionUsed = false;
    protected boolean drinkHealthPotion = false; // default

    // Flag indicating if potion is selected
    private boolean isPotionSelected = false;

    // Potion transparency (0 is transparent, 255 is opaque)
    private int alpha = 255;

    // Game speed
    private int gameSpeed = 10;

    public void tap() {
        if (isPotionSelected) {
            use();
        } else {
            isPotionSelected = true;
        }
    }

    public void use() {
        if (numOfPotion > 0) {
            numOfPotion--;
            isPotionUsed = true;
            isPotionSelected = false;
        } else {
            JOptionPane.showMessageDialog(null, "No more potions left!");
            isPotionSelected = false;
        }
    }

    public boolean getIsPotionUsed() {
        return isPotionUsed;
    }

    public void setIsPotionUsed(boolean isUsed) {
        this.isPotionUsed = isUsed;
    }

    public boolean getIsPotionSelected() {
        return isPotionSelected;
    }

    public void setIsPotionSelected(boolean isSelected) {
        this.isPotionSelected = isSelected;
    }

    public boolean drinkHealthPotion(Cookie c1) {
        if (drinkHealthPotion) {
            c1.setHealth(1100);
            System.err.println("health = 1100");
            drinkHealthPotion = false; // Reset after usage
            return true;
        }
        return false;
    }
}
