package potion;

import java.awt.Image;
import javax.swing.JOptionPane;

import ingame.Cookie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import panels.GamePanel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Potion {
    private Image image; // 물약 이미지
    private String name; // 포션 이름

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

    // 생성자
    public Potion(Image image, String name, int x, int y, int width, int height, int numOfPotion) {
        this.image = image;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.numOfPotion = numOfPotion;
    }

    public void use(GamePanel gamePanel) {
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
            return true;
        }
        return false;
    }
}
