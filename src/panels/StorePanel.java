package panels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JFrame;

public class StorePanel extends JPanel {

    private ImageIcon storeIc;
    private JButton StoreBtn;
    private JButton backButton; 
    private JFrame mainFrame;
    private CardLayout cardLayout;

    public StorePanel(JFrame mainFrame, CardLayout cardLayout) {
        this.mainFrame = mainFrame;
        this.cardLayout = cardLayout;

        ImageIcon originalIcon = new ImageIcon("img/store/storePanel.png");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(800, 480, Image.SCALE_SMOOTH); // 원하는 크기로 조정
        storeIc = new ImageIcon(scaledImage);

        StoreBtn = new JButton(storeIc);
        StoreBtn.setName("StoreBtn");
        StoreBtn.setBorderPainted(false);
        StoreBtn.setContentAreaFilled(false);
        StoreBtn.setFocusPainted(false);

        backButton = new JButton("Back");
        backButton.setBounds(10, 10, 100, 30); // 버튼의 위치와 크기 설정
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainFrame.getContentPane(), "intro");
            }
        });

        setLayout(null); // 레이아웃 설정
        add(StoreBtn);
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 화면을 비운다
        g.drawImage(storeIc.getImage(), 0, 0, null); // 상점 화면을 그린다
    }
}
