package panels;

import javax.swing.JPanel;
import java.awt.CardLayout;
import minigame.*;

public class MiniGamePanel extends JPanel {
    private SpeedrunPanel speedrunPanel;
    private MiniGames currentMiniGame;

    public MiniGamePanel(SpeedrunPanel speedrunPanel) {
        this.speedrunPanel = speedrunPanel;
        setLayout(new CardLayout());
    }

    public void startMiniGame() {
        ((CardLayout) getLayout()).show(this, "currentMiniGame");
        if (currentMiniGame != null) {
            currentMiniGame.startGame();
        }
    }

    public void gameFinished(boolean success) {
        speedrunPanel.miniGameFinished(success);
    }

    public void startRandomGame() {
        currentMiniGame = getRandomMiniGame();
        add(currentMiniGame, "currentMiniGame");
        startMiniGame();
    }

    private MiniGames getRandomMiniGame() {
        MiniGames[] miniGames = { new NumberOrderGame(this), new RockScissorsPaper(this), new BidirectionalKeysGame(this) };
        return miniGames[new java.util.Random().nextInt(miniGames.length)];
    }
}
