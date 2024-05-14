// 상점 화면

package panels;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StorePanel {

    // 상점 버튼 이미지 아이콘 
    private ImageIcon store = new ImageIcon("img/store/storePanel.png");

    private JButton StoreBtn;

    public StorePanel(Object o) {
        StoreBtn = new JButton(store);
		StoreBtn.setName("StoreBtn");
		StoreBtn.addMouseListener((MouseListener) o);
		StoreBtn.setBounds(254, 334, 291, 81);
		add(StoreBtn);
		StoreBtn.setBorderPainted(false);
		StoreBtn.setContentAreaFilled(false);
		StoreBtn.setFocusPainted(false);
    }
}