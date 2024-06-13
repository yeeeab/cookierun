package panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RankPanel extends JPanel {
    private Image backgroundImage;
    private static final int MAX_RANKS = 5;
    private JLabel[] itemModeLabels;
    private JLabel[] speedrunModeLabels;
    private JButton backButton;
    private JFrame mainFrame;
    private CardLayout cardLayout;

    private int[] jellyScores;
    private String[] speedrunTimes;

    public RankPanel(JFrame mainFrame, CardLayout cardLayout) {
        this.mainFrame = mainFrame;
        this.cardLayout = cardLayout;

        setLayout(null);

        ImageIcon icon = new ImageIcon("img/end/cookierunbg.jpg");
        backgroundImage = icon.getImage();

        itemModeLabels = new JLabel[MAX_RANKS];
        speedrunModeLabels = new JLabel[MAX_RANKS];

        jellyScores = new int[MAX_RANKS];
        speedrunTimes = new String[MAX_RANKS];

        Font labelFont = new Font("Arial", Font.BOLD, 18); // 글씨 크기 설정

        // Initialize scores
        for (int i = 0; i < MAX_RANKS; i++) {
            jellyScores[i] = 0;
            speedrunTimes[i] = "99:99:99"; // Initialize with a high value

            itemModeLabels[i] = new JLabel((i + 1) + ". " + jellyScores[i], SwingConstants.LEFT);
            speedrunModeLabels[i] = new JLabel((i + 1) + ". " + speedrunTimes[i], SwingConstants.LEFT);

            itemModeLabels[i].setBounds(50, 100 + i * 40, 200, 30);
            speedrunModeLabels[i].setBounds(450, 100 + i * 40, 200, 30);

            itemModeLabels[i].setFont(labelFont); // 폰트 설정
            speedrunModeLabels[i].setFont(labelFont); // 폰트 설정

            add(itemModeLabels[i]);
            add(speedrunModeLabels[i]);
        }

        JLabel itemModeTitle = new JLabel("Item Mode", SwingConstants.LEFT);
        JLabel speedrunModeTitle = new JLabel("Speedrun Mode", SwingConstants.LEFT);

        itemModeTitle.setBounds(50, 60, 200, 30);
        speedrunModeTitle.setBounds(450, 60, 200, 30);

        itemModeTitle.setFont(labelFont); // 타이틀 폰트 설정
        speedrunModeTitle.setFont(labelFont); // 타이틀 폰트 설정

        add(itemModeTitle);
        add(speedrunModeTitle);

        // 뒤로가기 버튼
        ImageIcon backIcon = new ImageIcon("img/select/backBtn.png");
        backButton = new JButton(backIcon);
        backButton.setBounds(670, 10, 100, 50);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainFrame.getContentPane(), "intro");
            }
        });
        add(backButton);

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // 배경 이미지 그리기
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // 명도 낮추기 위해 투명도 설정
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }

    public void updateRanks(int jellyScore, String speedrunTime) {
        System.out.println("Updating ranks..."); // Debug
        updateJellyScores(jellyScore);
        updateSpeedrunTimes(speedrunTime);

        // Update labels
        for (int i = 0; i < MAX_RANKS; i++) {
            itemModeLabels[i].setText((i + 1) + ". " + jellyScores[i]);
            speedrunModeLabels[i].setText((i + 1) + ". " + speedrunTimes[i]);
            System.out.println("Speedrun time rank " + (i + 1) + ": " + speedrunTimes[i]); // Debug
        }
    }

    private void updateJellyScores(int jellyScore) {
        for (int i = 0; i < MAX_RANKS; i++) {
            if (jellyScore > jellyScores[i]) {
                for (int j = MAX_RANKS - 1; j > i; j--) {
                    jellyScores[j] = jellyScores[j - 1];
                }
                jellyScores[i] = jellyScore;
                break;
            }
        }
    }

    private void updateSpeedrunTimes(String speedrunTime) {
        for (int i = 0; i < MAX_RANKS; i++) {
            if (compareTimes(speedrunTime, speedrunTimes[i]) < 0) {
                for (int j = MAX_RANKS - 1; j > i; j--) {
                    speedrunTimes[j] = speedrunTimes[j - 1];
                }
                speedrunTimes[i] = speedrunTime;
                break;
            }
        }
    }

    private int compareTimes(String time1, String time2) {
        String[] parts1 = time1.split(":");
        String[] parts2 = time2.split(":");

        int minutes1 = Integer.parseInt(parts1[0]);
        int seconds1 = Integer.parseInt(parts1[1]);
        int milliseconds1 = Integer.parseInt(parts1[2]);

        int minutes2 = Integer.parseInt(parts2[0]);
        int seconds2 = Integer.parseInt(parts2[1]);
        int milliseconds2 = Integer.parseInt(parts2[2]);

        if (minutes1 != minutes2) {
            return Integer.compare(minutes1, minutes2);
        } else if (seconds1 != seconds2) {
            return Integer.compare(seconds1, seconds2);
        } else {
            return Integer.compare(milliseconds1, milliseconds2);
        }
    }
}
