package panels;

import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import minigame.*;
import main.Main;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

public class MiniGamePanel extends JPanel {
    private Main main;
    private SpeedrunPanel speedrunPanel;
    private MiniGames currentMiniGame;

    JLabel lblNewLabel;

    public MiniGamePanel(SpeedrunPanel speedrunPanel) {
        this.speedrunPanel = speedrunPanel;
        this.main = speedrunPanel.getMain();
        setLayout(new CardLayout());

        lblNewLabel = new JLabel("");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setBackground(SystemColor.activeCaptionText);
        lblNewLabel.setIcon(new ImageIcon("img/end/cookierunbg.jpg"));
        lblNewLabel.setBounds(0, 0, 784, 461);
        add(lblNewLabel);
    }

    public void startMiniGame() {
        ((CardLayout) getLayout()).show(this, "numberOrderGame");
        currentMiniGame.startGame();
    }

    public void gameFinished(boolean success) {
        if (success) {
            speedrunPanel.miniGameFinished(true);
        } else {
            speedrunPanel.miniGameFinished(false);
        }
        ((CardLayout) main.getFrame().getContentPane().getLayout()).show(main.getFrame().getContentPane(), "speedrun");
    }

    public void startRandomGame(SpeedrunPanel parentPanel, Main main, CardLayout cl, JFrame superFrame) {
        currentMiniGame = getRandomMiniGame(parentPanel);
        add(currentMiniGame, "currentMiniGame");
        ((CardLayout) getLayout()).show(this, "currentMiniGame");
        currentMiniGame.startGame();
    }

    private MiniGames getRandomMiniGame(SpeedrunPanel parentPanel) {
        MiniGames[] miniGames = { new NumberOrderGame(this), new BidirectionalKeysGame(this),
                new RockScissorsPaper(this) };
        return miniGames[new java.util.Random().nextInt(miniGames.length)];
    }
}
