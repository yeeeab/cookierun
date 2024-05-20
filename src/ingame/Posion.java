package ingame;

import java.awt.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Posion {
    private Image image; // 속도증가 물약 이미지

    // 물약의 좌표와 크기
	private int x;
	private int y;
	private int width;
	private int height;

    // 물약의 투명도 (0이 투명, 255가 완전 불투명)
	private int alpha = 255;

    // 속도
    private int gameSpeed = 10;
}
