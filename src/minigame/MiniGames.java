package minigame;

import javax.swing.JPanel;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.AlphaComposite;
import panels.MiniGamePanel;
import panels.SpeedrunPanel;

public abstract class MiniGames extends JPanel {
    protected MiniGamePanel parentPanel;
    protected boolean isSuccess;
    private Image backgroundImage;

    public MiniGames(MiniGamePanel parentPanel) {
        this.parentPanel = parentPanel;
        this.isSuccess = false;

        setLayout(null);
        setBounds(0, 0, 800, 500);

        ImageIcon icon = new ImageIcon("img/end/cookierunbg.jpg");
        backgroundImage = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }

    protected void endGame() {
        parentPanel.gameFinished(isSuccess);
    }

    public void gameSuccess() {
        isSuccess = true;
        endGame();
    }

    public void gameFailure() {
        isSuccess = false;
        endGame();
    }

    public static void startRandomGame(SpeedrunPanel parentPanel) {
        Random random = new Random();
        while (true) {
            int gameIndex = random.nextInt(4);
            MiniGames miniGame = null;

            switch (gameIndex) {
                case 0:
                    miniGame = new NumberOrderGame(new MiniGamePanel(parentPanel));
                    break;
                case 1:
                    miniGame = new RockScissorsPaper(new MiniGamePanel(parentPanel));
                    break;
                case 2:
                    // miniGame = new BidirectionalKeysGame(new MiniGamePanel(parentPanel));
                    break;
                case 3:
                    miniGame = new SpaceBarKeysGame(new MiniGamePanel(parentPanel));
                    break;
            }

            if (miniGame != null) {
                parentPanel.switchToMiniGame(miniGame);
                break;
            }
        }
    }

    public abstract void startGame();
}
