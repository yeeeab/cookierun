package panels;

import java.awt.AlphaComposite;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ingame.Back;
import ingame.Cookie;
import ingame.CookieImg;
import ingame.Field;
import ingame.Jelly;
import ingame.MapObjectImg;
import ingame.Tacle;
import main.Main;
import util.Util;

public class GamePanel extends JPanel {

	// ��Ű �̹��� �����ܵ�
	private ImageIcon cookieIc; // �⺻���
	private ImageIcon jumpIc; // �������
	private ImageIcon doubleJumpIc; // �����������
	private ImageIcon fallIc; // ���ϸ��(���� ���� ��)
	private ImageIcon slideIc; // �����̵� ���
	private ImageIcon hitIc; // �ε����� ���

	// ��� �̹���
	private ImageIcon backIc; // ���� �� ���
	private ImageIcon secondBackIc; // 2��° ���

	private ImageIcon backIc2;
	private ImageIcon secondBackIc2;

	private ImageIcon backIc3;
	private ImageIcon secondBackIc3;

	private ImageIcon backIc4;
	private ImageIcon secondBackIc4;

	// ���� �̹��� �����ܵ�
	private ImageIcon jelly1Ic;
	private ImageIcon jelly2Ic;
	private ImageIcon jelly3Ic;
	private ImageIcon jellyHPIc;

	private ImageIcon jellyEffectIc;

	// ���� �̹��� �����ܵ�
	private ImageIcon field1Ic; // ����
	private ImageIcon field2Ic; // ���߹���

	// ��ֹ� �̹��� �����ܵ�
	private ImageIcon tacle10Ic; // 1ĭ ��ֹ�
	private ImageIcon tacle20Ic; // 2ĭ ��ֹ�
	private ImageIcon tacle30Ic; // 3ĭ ��ֹ�
	private ImageIcon tacle40Ic; // 4ĭ ��ֹ�

	// ü�� ������
	private ImageIcon lifeBar;

	private ImageIcon redBg; // �ǰݽ� ���� ȭ��

	private ImageIcon jumpButtonIconUp;
	private ImageIcon jumpButtonIconDown;

	private ImageIcon slideIconUp;
	private ImageIcon slideIconDown;

	Image jumpBtn;
	Image slideBtn;

	// ����Ʈ ����
	private List<Jelly> jellyList; // ���� ����Ʈ

	private List<Field> fieldList; // ���� ����Ʈ

	private List<Tacle> tacleList; // ��ֹ� ����Ʈ

	private List<Integer> mapLengthList;

	private int mapLength = 0;

	private int runPage = 0; // �� ȭ�� �̵��Ҷ����� ü���� ��� ���� ����

	private int runStage = 1; // ���������� Ȯ���ϴ� �����̴�. (�̱���)

	private int resultScore = 0; // ��������� �����ϴ� ����

	private int gameSpeed = 5; // ���� �ӵ�

	private int nowField = 2000; // ������ ���̸� ����.

	private JButton escButton; // esc ��ư (�׽�Ʈ ��)

	private boolean fadeOn = false;

	private boolean escKeyOn = false; // �Ͻ������� ���� escŰ Ȯ��

	private boolean downKeyOn = false; // �ٿ�Ű �������� ����

	private boolean redScreen = false; // �ǰݽ� ��¦ ���� ȭ�� ����

	int face; // ��Ű�� ����
	int foot; // ��Ű�� ��

	// �̹��� ���Ϸ� �� ���� �����´�.
	private int[] sizeArr; // �̹����� ���̿� ���̸� �������� 1���� �迭
	private int[][] colorArr; // �̹����� x y ��ǥ�� �ȼ� ������ �����ϴ� 2�����迭

	private Image buffImage; // ������� �̹���
	private Graphics buffg; // ������� g

	private AlphaComposite alphaComposite; // ���� ���� ������Ʈ

	Cookie c1; // ��Ű ������Ʈ

	Back b11; // ���1-1 ������Ʈ
	Back b12; // ���1-2 ������Ʈ

	Back b21; // ���2-1 ������Ʈ
	Back b22; // ���2-2 ������Ʈ

	Color backFade; // ����� �ٲ� �� ���̵� �ƿ� ���̵� �� �ϱ� ���� �÷�����

	// �� ������Ʈ�� �̹�����
	MapObjectImg mo1;
	MapObjectImg mo2;
	MapObjectImg mo3;
	MapObjectImg mo4;

	// �ܺ�
	JFrame superFrame;
	CardLayout cl;
	Main main;

	// �����г� ������ (���� �����Ӱ� ī�巹�̾ƿ�, �׸��� Main�ν��Ͻ��� �޴´�)
	public GamePanel(JFrame superFrame, CardLayout cl, Object o) {

		this.superFrame = superFrame;
		this.cl = cl;
		this.main = (Main) o;

		// �Ͻ����� ��ư
		escButton = new JButton("back");
		escButton.setBounds(350, 200, 100, 30);
		escButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				remove(escButton);
				escKeyOn = false;
			}
		});

	}

	// ������ �����Ѵ�
	public void gameSet(CookieImg ci) {

		setFocusable(true);

		initCookieImg(ci); // ��Ű�̹����� ����

		initObject(); // ���� �� �������� �ν��Ͻ� ����

		initListener(); // Ű������ �߰�

		runRepaint(); // ������Ʈ ���ѹݺ� ����
	}

	// ������ �����Ѵ�
	public void gameStart() {

		mapMove(); // ��� ���� ���� ��ֹ� �۵�

		fall(); // ���� ������ �ߵ�

	}

	// ȭ���� �׸���
	@Override
	protected void paintComponent(Graphics g) {

		// ������۴� �׸��� �̸��׷����� ȭ�鿡 ����Ѵ�.

		// ������� ����
		if (buffg == null) {
			buffImage = createImage(this.getWidth(), this.getHeight());
			if (buffImage == null) {
				System.out.println("���� ���۸��� ���� ��ũ�� ���� ����");
			} else {
				buffg = buffImage.getGraphics();
			}
		}

		// ���� ����
		Graphics2D g2 = (Graphics2D) buffg;

		super.paintComponent(buffg); // ���� ȭ���� �����.

		// ����̹����� �׸���
		buffg.drawImage(b11.getImage(), b11.getX(), 0, b11.getWidth(), b11.getHeight() * 5 / 4, null);
		buffg.drawImage(b12.getImage(), b12.getX(), 0, b12.getWidth(), b12.getHeight() * 5 / 4, null);
		buffg.drawImage(b21.getImage(), b21.getX(), 0, b21.getWidth(), b21.getHeight() * 5 / 4, null);
		buffg.drawImage(b22.getImage(), b22.getX(), 0, b22.getWidth(), b22.getHeight() * 5 / 4, null);

		// �������� �Ѿ�� ���̵�ƿ� �� ȿ��
		if (fadeOn) {
			buffg.setColor(backFade); // �����ϰ� �ϴ¹�� 1
			buffg.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

		// ������ �׸���
		for (int i = 0; i < fieldList.size(); i++) {

			Field tempFoot = fieldList.get(i);

			// ����� �� ��Ƹ԰� �ϱ����� ��ġ
			if (tempFoot.getX() > -90 && tempFoot.getX() < 810) { // x���� -90~810�� ��ü�鸸 �׸���.

				buffg.drawImage(tempFoot.getImage(), tempFoot.getX(), tempFoot.getY(), tempFoot.getWidth(),
						tempFoot.getHeight(), null);
			}

		}

		// ������ �׸���
		for (int i = 0; i < jellyList.size(); i++) {

			Jelly tempJelly = jellyList.get(i);

			if (tempJelly.getX() > -90 && tempJelly.getX() < 810) {

				alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
						(float) tempJelly.getAlpha() / 255);
				g2.setComposite(alphaComposite); // �����ϰ� �ϴ¹�� 2

				buffg.drawImage(tempJelly.getImage(), tempJelly.getX(), tempJelly.getY(), tempJelly.getWidth(),
						tempJelly.getHeight(), null);

				// alpha���� �ǵ�����
				alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
				g2.setComposite(alphaComposite);
			}
		}

		// ��ֹ��� �׸���
		for (int i = 0; i < tacleList.size(); i++) {

			Tacle tempTacle = tacleList.get(i);

			if (tempTacle.getX() > -90 && tempTacle.getX() < 810) {

				buffg.drawImage(tempTacle.getImage(), tempTacle.getX(), tempTacle.getY(), tempTacle.getWidth(),
						tempTacle.getHeight(), null);
			}
		}

		if (c1.isInvincible()) { // ���������� ���
			// ��Ű�� alpha���� �޾ƿ´�
			alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) c1.getAlpha() / 255);
			g2.setComposite(alphaComposite);

			// ��Ű�� �׸���
			buffg.drawImage(c1.getImage(), c1.getX() - 110, c1.getY() - 170,
					cookieIc.getImage().getWidth(null) * 8 / 10, cookieIc.getImage().getHeight(null) * 8 / 10, null);

			// alpha���� �ǵ�����
			alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2.setComposite(alphaComposite);

		} else { // �������°� �ƴ� ���

			// ��Ű�� �׸���
			buffg.drawImage(c1.getImage(), c1.getX() - 110, c1.getY() - 170,
					cookieIc.getImage().getWidth(null) * 8 / 10, cookieIc.getImage().getHeight(null) * 8 / 10, null);
		}

		// �ǰݽ� ���� ȭ��
		if (redScreen) {

			alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 125 / 255);
			g2.setComposite(alphaComposite);

			buffg.drawImage(redBg.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);

			alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2.setComposite(alphaComposite);
		}

//		buffg.setFont(new Font("Arial", Font.BOLD, 30));
//		buffg.setColor(Color.WHITE);
//		buffg.drawString(Integer.toString(resultScore), 700, 85);

		// ������ �׸���
		Util.drawFancyString(g2, Integer.toString(resultScore), 600, 58, 30, Color.WHITE);

		// ü�°������� �׸���
		buffg.drawImage(lifeBar.getImage(), 20, 30, null);
		buffg.setColor(Color.BLACK);
		buffg.fillRect(84 + (int) (470 * ((double) c1.getHealth() / 1000)), 65,
				1 + 470 - (int) (470 * ((double) c1.getHealth() / 1000)), 21);

		// ��ư�� �׸���
		buffg.drawImage(jumpBtn, 0, 360, 132, 100, null);
		buffg.drawImage(slideBtn, 650, 360, 132, 100, null);

		if (escKeyOn) { // escŰ�� ������� ȭ���� �帮�� �����

			// alpha���� �������ϰ� �����
			alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 100 / 255);
			g2.setComposite(alphaComposite);

			buffg.setColor(Color.BLACK);

			buffg.fillRect(0, 0, 850, 550);

			// alpha���� �ǵ�����
			alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 255 / 255);
			g2.setComposite(alphaComposite);
		}

		// �����̹����� ȭ�鿡 ����Ѵ�
		g.drawImage(buffImage, 0, 0, this);

	}

	// �� ������Ʈ �̹������� ����
	private void makeMo() {

		mo1 = new MapObjectImg(new ImageIcon("img/Objectimg/map1img/bg1.png"),
				new ImageIcon("img/Objectimg/map1img/bg2.png"), new ImageIcon("img/Objectimg/map1img/jelly1.png"),
				new ImageIcon("img/Objectimg/map1img/jelly2.png"), new ImageIcon("img/Objectimg/map1img/jelly3.png"),
				new ImageIcon("img/Objectimg/map1img/life.png"), new ImageIcon("img/Objectimg/map1img/effectTest.png"),
				new ImageIcon("img/Objectimg/map1img/fieldIc1.png"),
				new ImageIcon("img/Objectimg/map1img/fieldIc2.png"), new ImageIcon("img/Objectimg/map1img/tacle1.gif"),
				new ImageIcon("img/Objectimg/map1img/tacle2.png"), new ImageIcon("img/Objectimg/map1img/tacle3.png"),
				new ImageIcon("img/Objectimg/map1img/tacle3.png"));

		mo2 = new MapObjectImg(new ImageIcon("img/Objectimg/map2img/back1.png"),
				new ImageIcon("img/Objectimg/map2img/back2.png"), new ImageIcon("img/Objectimg/map1img/jelly1.png"),
				new ImageIcon("img/Objectimg/map1img/jelly2.png"), new ImageIcon("img/Objectimg/map1img/jelly3.png"),
				new ImageIcon("img/Objectimg/map1img/life.png"), new ImageIcon("img/Objectimg/map1img/effectTest.png"),
				new ImageIcon("img/Objectimg/map2img/field1.png"), new ImageIcon("img/Objectimg/map2img/field2.png"),
				new ImageIcon("img/Objectimg/map2img/tacle1.png"), new ImageIcon("img/Objectimg/map2img/tacle2.png"),
				new ImageIcon("img/Objectimg/map2img/tacle3.png"), new ImageIcon("img/Objectimg/map2img/tacle3.png"));

		mo3 = new MapObjectImg(new ImageIcon("img/Objectimg/map3img/bg.png"),
				new ImageIcon("img/Objectimg/map3img/bg2.png"), new ImageIcon("img/Objectimg/map1img/jelly1.png"),
				new ImageIcon("img/Objectimg/map1img/jelly2.png"), new ImageIcon("img/Objectimg/map1img/jelly3.png"),
				new ImageIcon("img/Objectimg/map1img/life.png"), new ImageIcon("img/Objectimg/map1img/effectTest.png"),
				new ImageIcon("img/Objectimg/map3img/field.png"), new ImageIcon("img/Objectimg/map3img/field2.png"),
				new ImageIcon("img/Objectimg/map3img/tacle1.png"), new ImageIcon("img/Objectimg/map3img/tacle2.png"),
				new ImageIcon("img/Objectimg/map3img/tacle3.png"), new ImageIcon("img/Objectimg/map3img/tacle3.png"));

		mo4 = new MapObjectImg(new ImageIcon("img/Objectimg/map4img/bback.png"),
				new ImageIcon("img/Objectimg/map4img/bback2.png"), new ImageIcon("img/Objectimg/map1img/jelly1.png"),
				new ImageIcon("img/Objectimg/map1img/jelly2.png"), new ImageIcon("img/Objectimg/map1img/jelly3.png"),
				new ImageIcon("img/Objectimg/map1img/life.png"), new ImageIcon("img/Objectimg/map1img/effectTest.png"),
				new ImageIcon("img/Objectimg/map4img/ffootTest.png"),
				new ImageIcon("img/Objectimg/map4img/ffootTest2.png"),
				new ImageIcon("img/Objectimg/map4img/tacle1.png"), new ImageIcon("img/Objectimg/map4img/tacle2.png"),
				new ImageIcon("img/Objectimg/map4img/tacle2.png"), new ImageIcon("img/Objectimg/map4img/tacle2.png"));

	}

	// ���ο��� ���� ��Ű �̹��� �����ܵ��� �ν��Ͻ�ȭ
	private void initCookieImg(CookieImg ci) {
		// ��Ű �̹��� �����ܵ�
		cookieIc = ci.getCookieIc(); // �⺻���
		jumpIc = ci.getJumpIc(); // �������
		doubleJumpIc = ci.getDoubleJumpIc(); // �����������
		fallIc = ci.getFallIc(); // ���ϸ��(���� ���� ��)
		slideIc = ci.getSlideIc(); // �����̵� ���
		hitIc = ci.getHitIc(); // �ε����� ���
	}

	// ���� ���� ��ֹ� ���� �ν��Ͻ�ȭ
	private void initImageIcon(MapObjectImg mo) {

		// ���� �̹��� �����ܵ�
		jelly1Ic = mo.getJelly1Ic();
		jelly2Ic = mo.getJelly2Ic();
		jelly3Ic = mo.getJelly3Ic();
		jellyHPIc = mo.getJellyHPIc();

		jellyEffectIc = mo.getJellyEffectIc();

		// ���� �̹��� �����ܵ�
		field1Ic = mo.getField1Ic(); // ����
		field2Ic = mo.getField2Ic(); // ���߹���

		// ��ֹ� �̹��� �����ܵ�
		tacle10Ic = mo.getTacle10Ic(); // 1ĭ ��ֹ�
		tacle20Ic = mo.getTacle20Ic(); // 2ĭ ��ֹ�
		tacle30Ic = mo.getTacle30Ic(); // 3ĭ ��ֹ�
		tacle40Ic = mo.getTacle40Ic(); // 4ĭ ��ֹ�
	}

	// ���� ������ �׸��� �̹����� �޾Ƽ� ����
	private void initMap(int num, int mapLength) {

		String tempMap = null;
		int tempMapLength = 0;

		if (num == 1) {
			tempMap = "img/map/map1.png";
		} else if (num == 2) {
			tempMap = "img/map/map2.png";
		} else if (num == 3) {
			tempMap = "img/map/map3.png";
		} else if (num == 4) {
			tempMap = "img/map/map4.png";
		}

		// �� ���� �ҷ�����
		try {
			sizeArr = Util.getSize(tempMap); // �� ����� �迭�� ����
			colorArr = Util.getPic(tempMap); // �� �ȼ����� �迭�� ����
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		tempMapLength = sizeArr[0];
		int maxX = sizeArr[0]; // ���� ����
		int maxY = sizeArr[1]; // ���� ����

		for (int i = 0; i < maxX; i += 1) { // ������ 1ĭ�� �����ϱ� ������ 1,1������� �ݺ����� ������.
			for (int j = 0; j < maxY; j += 1) {
				if (colorArr[i][j] == 16776960) { // ������ 16776960�� ��� �⺻���� ����
					// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 30���� �Ѵ�.
					jellyList.add(new Jelly(jelly1Ic.getImage(), i * 40 + mapLength * 40, j * 40, 30, 30, 255, 1234));

				} else if (colorArr[i][j] == 13158400) { // ������ 13158400�� ��� ������� ����
					// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 30���� �Ѵ�.
					jellyList.add(new Jelly(jelly2Ic.getImage(), i * 40 + mapLength * 40, j * 40, 30, 30, 255, 2345));

				} else if (colorArr[i][j] == 9868800) { // ������ 9868800�� ��� ������� ����
					// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 30���� �Ѵ�.
					jellyList.add(new Jelly(jelly3Ic.getImage(), i * 40 + mapLength * 40, j * 40, 30, 30, 255, 3456));

				} else if (colorArr[i][j] == 16737280) { // ������ 16737280�� ��� �� ���� ����
					// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 30���� �Ѵ�.
					jellyList.add(new Jelly(jellyHPIc.getImage(), i * 40 + mapLength * 40, j * 40, 30, 30, 255, 4567));
				}
			}
		}

		for (int i = 0; i < maxX; i += 2) { // ������ 4ĭ�� �����ϴ� �����̱� ������ 2,2������� �ݺ����� ������.
			for (int j = 0; j < maxY; j += 2) {
				if (colorArr[i][j] == 0) { // ������ 0 �ϰ�� (������)
					// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 80���� �Ѵ�.
					fieldList.add(new Field(field1Ic.getImage(), i * 40 + mapLength * 40, j * 40, 80, 80));

				} else if (colorArr[i][j] == 6579300) { // ������ 6579300 �ϰ�� (ȸ��)
					// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 80���� �Ѵ�.
					fieldList.add(new Field(field2Ic.getImage(), i * 40 + mapLength * 40, j * 40, 80, 80));
				}
			}
		}

		for (int i = 0; i < maxX; i += 2) { // ��ֹ��� 4ĭ �̻��� �����Ѵ�. ���� ����
			for (int j = 0; j < maxY; j += 2) {
				if (colorArr[i][j] == 16711680) { // ������ 16711680�� ��� (������) 1ĭ
					// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 80���� �Ѵ�.
					tacleList.add(new Tacle(tacle10Ic.getImage(), i * 40 + mapLength * 40, j * 40, 80, 80, 0));

				} else if (colorArr[i][j] == 16711830) { // ������ 16711830�� ��� (��ȫ) 2ĭ
					// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 160���� �Ѵ�.
					tacleList.add(new Tacle(tacle20Ic.getImage(), i * 40 + mapLength * 40, j * 40, 80, 160, 0));

				} else if (colorArr[i][j] == 16711935) { // ������ 16711830�� ��� (����ũ) 3ĭ
					// ��ǥ�� 40�� ���ϰ�, ���̿� ���̴� 240���� �Ѵ�.
					tacleList.add(new Tacle(tacle30Ic.getImage(), i * 40 + mapLength * 40, j * 40, 80, 240, 0));
				}
			}
		}

		this.mapLength = this.mapLength + tempMapLength;
	}

	// makeMo, initImageIcon, imitMap �޼��带 �̿��ؼ� ��ü ����
	private void initObject() {

		// ��������� �̹���������
		lifeBar = new ImageIcon("img/Objectimg/lifebar/lifeBar1.png");

		// �ǰ� ���� �̹���
		redBg = new ImageIcon("img/Objectimg/lifebar/redBg.png");

		// ������ư
		jumpButtonIconUp = new ImageIcon("img/Objectimg/lifebar/jumpno.png");
		jumpButtonIconDown = new ImageIcon("img/Objectimg/lifebar/jumpdim.png");

		// �����̵� ��ư
		slideIconUp = new ImageIcon("img/Objectimg/lifebar/slideno.png");
		slideIconDown = new ImageIcon("img/Objectimg/lifebar/slidedim.png");

		jumpBtn = jumpButtonIconUp.getImage();
		slideBtn = slideIconUp.getImage();

		jellyList = new ArrayList<>(); // ���� ����Ʈ

		fieldList = new ArrayList<>(); // ���� ����Ʈ

		tacleList = new ArrayList<>(); // ��ֹ� ����Ʈ

		mapLengthList = new ArrayList<>(); // ���� ���� ���������� Ȯ���ϱ����� �迭

		// �� �ν��Ͻ����� ����

		makeMo();

		initImageIcon(mo1);
		initMap(1, mapLength);
		mapLengthList.add(mapLength);

		initImageIcon(mo2);
		initMap(2, mapLength);
		mapLengthList.add(mapLength);

		initImageIcon(mo3);
		initMap(3, mapLength);
		mapLengthList.add(mapLength);

		initImageIcon(mo4);
		initMap(4, mapLength);

		// ����̹��� ������
		backIc = mo1.getBackIc();
		secondBackIc = mo1.getSecondBackIc();

		backIc2 = mo2.getBackIc();
		secondBackIc2 = mo2.getSecondBackIc();

		backIc3 = mo3.getBackIc();
		secondBackIc3 = mo3.getSecondBackIc();

		backIc4 = mo4.getBackIc();
		secondBackIc4 = mo4.getSecondBackIc();

		// ��Ű �ν��Ͻ� ���� / �⺻ �ڷ�� Ŭ�����ȿ� ���� �Ǿ� �ֱ� ������ �̹����� �־���.
		c1 = new Cookie(cookieIc.getImage());

		// ��Ű�� ���� ��ġ / ��Ű�� x���� ���̸� ���� ��
		face = c1.getX() + c1.getWidth();

		// ��Ű�� �߹� ��ġ / ��Ű�� y���� ���̸� ���� ��
		foot = c1.getY() + c1.getHeight();

		// ���1-1 �ν��Ͻ� ����
		b11 = new Back(backIc.getImage(), 0, 0, backIc.getImage().getWidth(null), backIc.getImage().getHeight(null));

		// ���1-2 �ν��Ͻ� ����
		b12 = new Back(backIc.getImage(), backIc.getImage().getWidth(null), 0, // y �� (���� �ʿ�)
				backIc.getImage().getWidth(null), backIc.getImage().getHeight(null));

		// ���2-1 �ν��Ͻ� ����
		b21 = new Back(secondBackIc.getImage(), 0, 0, secondBackIc.getImage().getWidth(null),
				secondBackIc.getImage().getHeight(null));

		// ���2-2 �ν��Ͻ� ����
		b22 = new Back(secondBackIc.getImage(), secondBackIc.getImage().getWidth(null), 0, // y �� (���� �ʿ�)
				secondBackIc.getImage().getWidth(null), secondBackIc.getImage().getHeight(null));

		backFade = new Color(0, 0, 0, 0);

	}

	// ������ �߰� �޼���
	private void initListener() {
		addKeyListener(new KeyAdapter() { // Ű ������ �߰�

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // escŰ�� ������ ��
					if (!escKeyOn) {
						escKeyOn = true;
						add(escButton);
						repaint(); // ȭ���� ��Ӱ� �ϱ����� ������Ʈ
					} else {
						remove(escButton);
						escKeyOn = false;
					}
				}

				if (!escKeyOn) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {// �����̽� Ű�� ������ ���������� 2�� �ƴҶ�
						jumpBtn = jumpButtonIconDown.getImage();
						if (c1.getCountJump() < 2) {
							jump(); // ���� �޼��� ����
						}
					}
					if (e.getKeyCode() == KeyEvent.VK_DOWN) { // �ٿ�Ű�� ������ ��
						slideBtn = slideIconDown.getImage();
						downKeyOn = true; // downKeyOn ������ true��

						if (c1.getImage() != slideIc.getImage() // ��Ű�̹����� �����̵� �̹����� �ƴϰ�
								&& !c1.isJump() // ���� ���� �ƴϸ�
								&& !c1.isFall()) { // ���� �ߵ� �ƴ� ��

							c1.setImage(slideIc.getImage()); // �̹����� �����̵��̹����� ����

						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_DOWN) { // �ٿ�Ű�� ���� ��
					slideBtn = slideIconUp.getImage();
					downKeyOn = false; // downKeyOn ������ false��

					if (c1.getImage() != cookieIc.getImage() // ��Ű�̹����� �⺻�̹����� �ƴϰ�
							&& !c1.isJump() // ���� ���� �ƴϸ�
							&& !c1.isFall()) { // ���� �ߵ� �ƴ� ��

						c1.setImage(cookieIc.getImage()); // �̹����� �⺻�̹����� ����
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					jumpBtn = jumpButtonIconUp.getImage();
				}
			}
		});
	}

	// ������Ʈ ���� ������ �߰� �޼���
	private void runRepaint() {
		// ������Ʈ ���� ������
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					repaint();

					if (escKeyOn) { // esc Ű�� ������� ������Ʈ�� �����
						while (escKeyOn) {
							try {
								Thread.sleep(10);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

					try {
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	// ȭ���� �����̰� ������ �԰ų�, ��ֹ��� �ε����� ���� �̺�Ʈ�� �߻���Ű�� �޼���
	private void mapMove() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					if (runPage > 800) { // 800�ȼ� �̵� ���� ü���� 10�� �����Ѵ� (���� �ʱ��̿� ���߾� ���ҷ� ����)
						c1.setHealth(c1.getHealth() - 10);
						runPage = 0;
					}

					runPage += gameSpeed; // ȭ���� �̵��ϸ� runPage�� �̵��� ��ŭ ����ȴ�.

					foot = c1.getY() + c1.getHeight(); // ĳ���� �� ��ġ �罺ĵ
					if (foot > 1999 || c1.getHealth() < 1) {
						main.getEndPanel().setResultScore(resultScore);
						cl.show(superFrame.getContentPane(), "end");
						main.setGamePanel(new GamePanel(superFrame, cl, main));
						superFrame.requestFocus();
						escKeyOn = true;
					}

					// ��� �̹��� ����
					if (fadeOn == false) { // ���̵�ƿ��� ���°� �ƴҶ�
						if (mapLength > mapLengthList.get(2) * 40 + 800 && b11.getImage() != backIc4.getImage()) {
							fadeOn = true;

							new Thread(new Runnable() {

								@Override
								public void run() {

									backFadeOut();

									b11 = new Back(backIc4.getImage(), 0, 0, backIc4.getImage().getWidth(null),
											backIc4.getImage().getHeight(null));

									b12 = new Back(backIc4.getImage(), backIc4.getImage().getWidth(null), 0,
											backIc4.getImage().getWidth(null), backIc4.getImage().getHeight(null));

									b21 = new Back(secondBackIc4.getImage(), 0, 0,
											secondBackIc4.getImage().getWidth(null),
											secondBackIc4.getImage().getHeight(null));

									b22 = new Back(secondBackIc4.getImage(), secondBackIc4.getImage().getWidth(null), 0,
											secondBackIc4.getImage().getWidth(null),
											secondBackIc4.getImage().getHeight(null));

									backFadeIn();
									fadeOn = false;
								}
							}).start();

						} else if (mapLength > mapLengthList.get(1) * 40 + 800
								&& mapLength < mapLengthList.get(2) * 40 + 800
								&& b11.getImage() != backIc3.getImage()) {
							fadeOn = true;

							new Thread(new Runnable() {

								@Override
								public void run() {

									backFadeOut();

									b11 = new Back(backIc3.getImage(), 0, 0, backIc3.getImage().getWidth(null),
											backIc3.getImage().getHeight(null));

									b12 = new Back(backIc3.getImage(), backIc3.getImage().getWidth(null), 0,
											backIc3.getImage().getWidth(null), backIc3.getImage().getHeight(null));

									b21 = new Back(secondBackIc3.getImage(), 0, 0,
											secondBackIc3.getImage().getWidth(null),
											secondBackIc3.getImage().getHeight(null));

									b22 = new Back(secondBackIc3.getImage(), secondBackIc3.getImage().getWidth(null), 0,
											secondBackIc3.getImage().getWidth(null),
											secondBackIc3.getImage().getHeight(null));

									backFadeIn();
									fadeOn = false;
								}
							}).start();

						} else if (mapLength > mapLengthList.get(0) * 40 + 800
								&& mapLength < mapLengthList.get(1) * 40 + 800
								&& b11.getImage() != backIc2.getImage()) {
							fadeOn = true;

							new Thread(new Runnable() {

								@Override
								public void run() {

									backFadeOut();

									b11 = new Back(backIc2.getImage(), 0, 0, backIc2.getImage().getWidth(null),
											backIc2.getImage().getHeight(null));

									b12 = new Back(backIc2.getImage(), backIc2.getImage().getWidth(null), 0,
											backIc2.getImage().getWidth(null), backIc2.getImage().getHeight(null));

									b21 = new Back(secondBackIc2.getImage(), 0, 0,
											secondBackIc2.getImage().getWidth(null),
											secondBackIc2.getImage().getHeight(null));

									b22 = new Back(secondBackIc2.getImage(), secondBackIc2.getImage().getWidth(null), 0,
											secondBackIc2.getImage().getWidth(null),
											secondBackIc2.getImage().getHeight(null));

									backFadeIn();
									fadeOn = false;
								}
							}).start();
						}
					}

					// ����̹��� ������ ���� ���̵� ���� ����
					mapLength += gameSpeed;

					if (b11.getX() < -(b11.getWidth() - 1)) { // ���1-1 �� -(������)���� ������, �� ȭ������� ��γ����� ��� 1-2�ڿ� ����
						b11.setX(b11.getWidth());
					}
					if (b12.getX() < -(b12.getWidth() - 1)) { // ���1-2 �� -(������)���� ������, �� ȭ������� ��γ����� ��� 1-1�ڿ� ����
						b12.setX(b12.getWidth());
					}

					if (b21.getX() < -(b21.getWidth() - 1)) { // ���1-1 �� -(������)���� ������, �� ȭ������� ��γ����� ��� 1-2�ڿ� ����
						b21.setX(b21.getWidth());
					}
					if (b22.getX() < -(b22.getWidth() - 1)) { // ���1-2 �� -(������)���� ������, �� ȭ������� ��γ����� ��� 1-1�ڿ� ����
						b22.setX(b22.getWidth());
					}

					// ����� x��ǥ�� -1 ���ش� (�������� �帣�� ȿ��)
					b11.setX(b11.getX() - gameSpeed / 3);
					b12.setX(b12.getX() - gameSpeed / 3);

					b21.setX(b21.getX() - gameSpeed * 2 / 3);
					b22.setX(b22.getX() - gameSpeed * 2 / 3);

					// ������ġ�� -3 �� ���ش�. (�������� �帣�� ȿ��)
					for (int i = 0; i < fieldList.size(); i++) {

						Field tempField = fieldList.get(i); // �ӽ� ������ ����Ʈ �ȿ� �ִ� ���� ������ �ҷ�����

						if (tempField.getX() < -90) { // ������ x��ǥ�� -90 �̸��̸� �ش� ������ �����Ѵ�.(����ȭ)

							fieldList.remove(tempField);

						} else {

							tempField.setX(tempField.getX() - gameSpeed); // �� ���ǿ� �ش��� �ȵǸ� x��ǥ�� ������

						}
					}

					// ������ġ�� -4 �� ���ش�.
					for (int i = 0; i < jellyList.size(); i++) {

						Jelly tempJelly = jellyList.get(i); // �ӽ� ������ ����Ʈ �ȿ� �ִ� ���� ������ �ҷ�����

						if (tempJelly.getX() < -90) { // ������ x ��ǥ�� -90 �̸��̸� �ش� ������ �����Ѵ�.(����ȭ)

							fieldList.remove(tempJelly);

						} else {

							tempJelly.setX(tempJelly.getX() - gameSpeed); // �� ���ǿ� �ش��� �ȵǸ� x��ǥ�� ������
							if (tempJelly.getImage() == jellyEffectIc.getImage() && tempJelly.getAlpha() > 4) {
								tempJelly.setAlpha(tempJelly.getAlpha() - 5);
							}

							foot = c1.getY() + c1.getHeight(); // ĳ���� �� ��ġ �罺ĵ

							if ( // ĳ������ ���� �ȿ� ������ ������ �������� �Դ´�.
							c1.getImage() != slideIc.getImage()
									&& tempJelly.getX() + tempJelly.getWidth() * 20 / 100 >= c1.getX()
									&& tempJelly.getX() + tempJelly.getWidth() * 80 / 100 <= face
									&& tempJelly.getY() + tempJelly.getWidth() * 20 / 100 >= c1.getY()
									&& tempJelly.getY() + tempJelly.getWidth() * 80 / 100 <= foot
									&& tempJelly.getImage() != jellyEffectIc.getImage()) {

								if (tempJelly.getImage() == jellyHPIc.getImage()) {
									if ((c1.getHealth() + 100) > 1000) {
										c1.setHealth(1000);
									} else {
										c1.setHealth(c1.getHealth() + 100);
									}
								}
								tempJelly.setImage(jellyEffectIc.getImage()); // ������ �̹����� ����Ʈ�� �ٲ۴�
								resultScore = resultScore + tempJelly.getScore(); // �������� ���� ������ ���Ѵ�

							} else if ( // �����̵� �ϴ� ĳ������ ���� �ȿ� ������ ������ �������� �Դ´�.
							c1.getImage() == slideIc.getImage()
									&& tempJelly.getX() + tempJelly.getWidth() * 20 / 100 >= c1.getX()
									&& tempJelly.getX() + tempJelly.getWidth() * 80 / 100 <= face
									&& tempJelly.getY() + tempJelly.getWidth() * 20 / 100 >= c1.getY()
											+ c1.getHeight() * 1 / 3
									&& tempJelly.getY() + tempJelly.getWidth() * 80 / 100 <= foot
									&& tempJelly.getImage() != jellyEffectIc.getImage()) {

								if (tempJelly.getImage() == jellyHPIc.getImage()) {
									if ((c1.getHealth() + 100) > 1000) {
										c1.setHealth(1000);
									} else {
										c1.setHealth(c1.getHealth() + 100);
									}
								}
								tempJelly.setImage(jellyEffectIc.getImage()); // ������ �̹����� ����Ʈ�� �ٲ۴�
								resultScore = resultScore + tempJelly.getScore(); // �������� ���� ������ ���Ѵ�

							}
						}
					}

					// ��ֹ���ġ�� - 4 �� ���ش�.
					for (int i = 0; i < tacleList.size(); i++) {

						Tacle tempTacle = tacleList.get(i); // �ӽ� ������ ����Ʈ �ȿ� �ִ� ���� ��ֹ��� �ҷ�����

						if (tempTacle.getX() < -90) {

							fieldList.remove(tempTacle); // ��ֹ��� x ��ǥ�� -90 �̸��̸� �ش� ������ �����Ѵ�.(����ȭ)

						} else {

							tempTacle.setX(tempTacle.getX() - gameSpeed); // �� ���ǿ� �ش��� �ȵǸ� x��ǥ�� ������

							face = c1.getX() + c1.getWidth(); // ĳ���� ���� ��ġ �罺ĵ
							foot = c1.getY() + c1.getHeight(); // ĳ���� �� ��ġ �罺ĵ

							if ( // �������°� �ƴϰ� �����̵� ���� �ƴϸ� ĳ������ ���� �ȿ� ��ֹ��� ������ �ε�����
							!c1.isInvincible() && c1.getImage() != slideIc.getImage()
									&& tempTacle.getX() + tempTacle.getWidth() / 2 >= c1.getX()
									&& tempTacle.getX() + tempTacle.getWidth() / 2 <= face
									&& tempTacle.getY() + tempTacle.getHeight() / 2 >= c1.getY()
									&& tempTacle.getY() + tempTacle.getHeight() / 2 <= foot) {

								hit(); // �ǰ� + ���� ������ �޼���

							} else if ( // �����̵� �ƴҽ� ������ֹ�
							!c1.isInvincible() && c1.getImage() != slideIc.getImage()
									&& tempTacle.getX() + tempTacle.getWidth() / 2 >= c1.getX()
									&& tempTacle.getX() + tempTacle.getWidth() / 2 <= face
									&& tempTacle.getY() <= c1.getY()
									&& tempTacle.getY() + tempTacle.getHeight() * 95 / 100 > c1.getY()) {

								hit(); // �ǰ� + ���� ������ �޼���

							} else if ( // �������°� �ƴϰ� �����̵� ���̸� ĳ������ ���� �ȿ� ��ֹ��� ������ �ε�����
							!c1.isInvincible() && c1.getImage() == slideIc.getImage()
									&& tempTacle.getX() + tempTacle.getWidth() / 2 >= c1.getX()
									&& tempTacle.getX() + tempTacle.getWidth() / 2 <= face
									&& tempTacle.getY() + tempTacle.getHeight() / 2 >= c1.getY()
											+ c1.getHeight() * 2 / 3
									&& tempTacle.getY() + tempTacle.getHeight() / 2 <= foot) {

								hit(); // �ǰ� + ���� ������ �޼���

							} else if ( // �����̵��� ������ֹ�
							!c1.isInvincible() && c1.getImage() == slideIc.getImage()
									&& tempTacle.getX() + tempTacle.getWidth() / 2 >= c1.getX()
									&& tempTacle.getX() + tempTacle.getWidth() / 2 <= face
									&& tempTacle.getY() < c1.getY() && tempTacle.getY()
											+ tempTacle.getHeight() * 95 / 100 > c1.getY() + c1.getHeight() * 2 / 3) {

								hit(); // �ǰ� + ���� ������ �޼���
							}
						}
					}

					// ��Ű�� ���� ������ ����ϴ� �ڵ�
					int tempField; // ������ġ�� ��� ��ĵ�ϴ� ��������
					int tempNowField; // ĳ���Ϳ� ������ ���̿� ���� ����Ǵ� ��������, ����� nowField�� �����Ѵ�

					// ��Ű�� �������¶�� ���� ���� �ʱ� ������ 400���� ���� / ������ �ƴ϶�� 2000(��������);
					if (c1.isInvincible()) {
						tempNowField = 400;
					} else {
						tempNowField = 2000;
					}

					for (int i = 0; i < fieldList.size(); i++) { // ������ ������ŭ �ݺ�

						int tempX = fieldList.get(i).getX(); // ������ x��

						if (tempX > c1.getX() - 60 && tempX <= face) { // ������ ĳ�� ���� ���̶��

							tempField = fieldList.get(i).getY(); // ������ y���� tempField�� �����Ѵ�

							foot = c1.getY() + c1.getHeight(); // ĳ���� �� ��ġ �罺ĵ

							// ������ġ�� tempNowField���� ����, �߹ٴ� ���� �Ʒ� �ִٸ�
							// ��, ĳ���� �� �Ʒ��� ���� ���� �ִ� �����̶�� tempNowField�� �����Ѵ�.
							if (tempField < tempNowField && tempField >= foot) {

								tempNowField = tempField;

							}
						}
					}

					nowField = tempNowField; // ����� nowField�� ������Ʈ �Ѵ�.

					if (escKeyOn) { // escŰ�� ������ ������ �����
						while (escKeyOn) {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	// �ε����� �� �Ͼ�� ���¸� ����ϴ� �޼���
	private void hit() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				c1.setInvincible(true); // ��Ű�� �������·� ��ȯ

				System.out.println("�ǰݹ�������");

				redScreen = true; // �ǰ� ���� ����Ʈ ����

				c1.setHealth(c1.getHealth() - 100); // ��Ű�� ü���� 100 ��´�

				c1.setImage(hitIc.getImage()); // ��Ű�� �ε��� ������� ����

				c1.setAlpha(80); // ��Ű�� ������ 80���� ����

				try { // 0.5�� ���
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				redScreen = false; // �ǰ� ���� ����Ʈ ����

				try { // 0.5�� ���
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (c1.getImage() == hitIc.getImage()) { // 0.5�� ���� �̹����� �ٲ��� �ʾҴٸ� �⺻�̹����� ����

					c1.setImage(cookieIc.getImage());

				}

				for (int j = 0; j < 11; j++) { // 2.5�ʰ� ĳ���Ͱ� �����δ�. (�ǰ��� ���� ���¸� �ν�)

					if (c1.getAlpha() == 80) { // �̹����� ���İ��� 80�̸� 160����

						c1.setAlpha(160);

					} else { // �ƴϸ� 80����

						c1.setAlpha(80);

					}
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				c1.setAlpha(255); // ��Ű�� ������ �������� ����

				c1.setInvincible(false);
				System.out.println("�ǰݹ�������");
			}
		}).start();
	}

	// ���� �޼���
	private void fall() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {

					foot = c1.getY() + c1.getHeight(); // ĳ���� �� ��ġ �罺ĵ

					// �߹ٴ��� ���Ǻ��� ���� ������ �۵�
					if (!escKeyOn // �Ͻ������� �ߵ� �ȵ��� ��
							&& foot < nowField // ���߿� ������
							&& !c1.isJump() // ���� ���� �ƴϸ�
							&& !c1.isFall()) { // �������� ���� �ƴ� ��

						c1.setFall(true); // �������� ������ ��ȯ
						System.out.println("����");

						if (c1.getCountJump() == 2) { // ���������� ������ ��� ���� �̹����� ����
							c1.setImage(fallIc.getImage());
						}

						long t1 = Util.getTime(); // ����ð��� �����´�
						long t2;
						int set = 1; // ó�� ���Ϸ� (0~10) ���� �׽�Ʈ�غ���

						while (foot < nowField) { // ���� ���ǿ� ��� ������ �ݺ�

							t2 = Util.getTime() - t1; // ���� �ð����� t1�� ����

							int fallY = set + (int) ((t2) / 40); // ���Ϸ��� �ø���.

							foot = c1.getY() + c1.getHeight(); // ĳ���� �� ��ġ �罺ĵ

							if (foot + fallY >= nowField) { // �߹ٴ�+���Ϸ� ��ġ�� ���Ǻ��� ���ٸ� ���Ϸ��� �����Ѵ�.
								fallY = nowField - foot;
							}

							c1.setY(c1.getY() + fallY); // Y��ǥ�� ���Ϸ��� ���Ѵ�

							if (c1.isJump()) { // �������ٰ� ������ �ϸ� ��������
								break;
							}

							if (escKeyOn) {
								long tempT1 = Util.getTime();
								long tempT2 = 0;
								while (escKeyOn) {
									try {
										Thread.sleep(10);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
								tempT2 = Util.getTime() - tempT1;
								t1 = t1 + tempT2;
							}

							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
						c1.setFall(false);

						if (downKeyOn // �ٿ�Ű�� �������°�
								&& !c1.isJump() // ���� ���°� �ƴϰ�
								&& !c1.isFall() // ���� ���°� �ƴϰ�
								&& c1.getImage() != slideIc.getImage()) { // ��Ű �̹����� �����̵� �̹����� �ƴ� ���

							c1.setImage(slideIc.getImage()); // ��Ű �̹����� �����̵�� ����

						} else if (!downKeyOn // �ٿ�Ű�� �������°� �ƴϰ�
								&& !c1.isJump() // ���� ���°� �ƴϰ�
								&& !c1.isFall() // ���� ���°� �ƴϰ�
								&& c1.getImage() != cookieIc.getImage()) { // ��Ű �̹����� �⺻ �̹����� �ƴ� ���

							c1.setImage(cookieIc.getImage());
						}

						if (!c1.isJump()) { // ���� ���� ��� ���� ���� �ƴ� �� �������� ī��Ʈ�� 0���� ����
							c1.setCountJump(0);
						}
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	// ���� �޼���
	private void jump() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				c1.setCountJump(c1.getCountJump() + 1); // ���� Ƚ�� ����

				int nowJump = c1.getCountJump(); // �̹������� �������� ������������ ����

				c1.setJump(true); // ���������� ����

				if (c1.getCountJump() == 1) { // ���� Ƚ���� 1�̶��

					System.out.println("����");
					c1.setImage(jumpIc.getImage());

				} else if (c1.getCountJump() == 2) { // ���� Ƚ���� 2���

					System.out.println("��������");
					c1.setImage(doubleJumpIc.getImage());

				}

				long t1 = Util.getTime(); // ����ð��� �����´�
				long t2;
				int set = 8; // ���� ��� ����(0~20) ������ �ٲ㺸��
				int jumpY = 1; // 1�̻����θ� �����ϸ� �ȴ�.(while�� ���� ����)

				while (jumpY >= 0) { // ��� ���̰� 0�϶����� �ݺ�

					t2 = Util.getTime() - t1; // ���� �ð����� t1�� ����

					jumpY = set - (int) ((t2) / 40); // jumpY �� �����Ѵ�.

					c1.setY(c1.getY() - jumpY); // Y���� �����Ѵ�.

					if (nowJump != c1.getCountJump()) { // ������ �ѹ� ���Ǹ� ù��° ������ �����.
						break;
					}

					if (escKeyOn) {
						long tempT1 = Util.getTime();
						long tempT2 = 0;
						while (escKeyOn) {
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						tempT2 = Util.getTime() - tempT1;
						t1 = t1 + tempT2;
					}

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (nowJump == c1.getCountJump()) { // ������ ��¥ ������ ���� Ȯ��
					c1.setJump(false); // �������¸� false�� ����
				}

			}
		}).start();
	}

	private void backFadeOut() {
		for (int i = 0; i < 256; i += 2) {
			backFade = new Color(0, 0, 0, i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void backFadeIn() {
		for (int i = 255; i >= 0; i -= 2) {
			backFade = new Color(0, 0, 0, i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
