package ingame;

import java.awt.Image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coin {
	private Image image; // 코인 이미지

	// 코인의 좌표와 크기
	private int x;
	private int y;
	private int width;
	private int height;

	// 코인의 투명도(0이 투명, 255가 완전 불투명)
	private int alpha;

	// 코인의 점수
	private int score;

	// 코인 포션(점수를 두배로 할지 여부를 나타내는 플래그)
	private static boolean doubleScore = false;

	// doubleScore 플래그 설정 메서드
	public static void setDoubleScore(boolean doubleScore) {
		Coin.doubleScore = doubleScore;
	}

	// 점수를 반환하는 메서드
	public int getScore() {
		return doubleScore ? score * 2 : score;
	}
}