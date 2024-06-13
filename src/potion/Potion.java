package potion;

import java.awt.Image;

import javax.swing.JOptionPane;

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

    // 물약의 좌표와 크기
    private int x;
    private int y;
    private int width;
    private int height;

    // 포션 보유량
    private int numOfPotion;

    // 포션을 사용했는가
    private boolean isPotionUsed = false;

    // 물약의 투명도 (0이 투명, 255가 완전 불투명)
    private int alpha = 255;

    // 속도
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
        } else {
            JOptionPane.showMessageDialog(null, "No more potions left!");
        }
    }

    public boolean getIsPotionUsed() {
        return isPotionUsed;
    }

    public void setIsPotionUsed(boolean isUsed) {
        this.isPotionUsed = isUsed;
    }
}
