package panels;

import java.awt.CardLayout;
import javax.swing.JPanel;
import minigame.*;
import main.Main;

public class MiniGamePanel extends JPanel {
    private Main main;
    private CardLayout cl;
    private SpeedrunPanel speedrunPanel;
    private MiniGames currentMiniGame;

    public MiniGamePanel(SpeedrunPanel speedrunPanel) {
        this.speedrunPanel = speedrunPanel;
        this.main = speedrunPanel.getMain();
        this.cl = (CardLayout) main.getFrame().getContentPane().getLayout();
        setLayout(cl);

        currentMiniGame = new NumberOrderGame(this);
        add(currentMiniGame, "numberOrderGame");
    }

    public void startMiniGame() {
        cl.show(this, "numberOrderGame");
        currentMiniGame.startGame();
    }

    public void gameFinished(boolean success) {
        if (success) {
            speedrunPanel.miniGameFinished(true);
        } else {
            speedrunPanel.miniGameFinished(false);
        }
        cl.show(main.getFrame().getContentPane(), "speedrun");
    }
}
