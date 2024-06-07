/*
 * package minigame;
 * 
 * import panels.MiniGamePanel;
 * import javax.swing.*;
 * import java.awt.*;
 * import java.awt.event.KeyAdapter;
 * import java.awt.event.KeyEvent;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 * public class BidirectionalKeysGame extends MiniGames {
 * /*
 * private List<String> keySequence; // 키 시퀀스
 * private StringBuilder userInput; // 사용자 입력
 * 
 * private JPanel keyPanel;
 * 
 * public BidirectionalKeysGame(MiniGamePanel parentPanel) {
 * super(parentPanel);
 * setupGame();
 * }
 * 
 * private void setupGame() {
 * setLayout(null); // 절대 위치 사용
 * 
 * keyPanel = new JPanel();
 * keyPanel.setLayout(new FlowLayout());
 * keyPanel.setBounds(100, 100, 600, 100);
 * add(keyPanel);
 * 
 * setFocusable(true);
 * requestFocusInWindow(); // 포커스 요청
 * addKeyListener(new KeyAdapter() {
 * 
 * @Override
 * public void keyPressed(KeyEvent e) {
 * handleKeyPress(e);
 * }
 * });
 * 
 * startNewSequence();
 * }
 * 
 * private void startNewSequence() {
 * keySequence = generateKeySequence();
 * userInput = new StringBuilder(); // 새로운 시퀀스를 시작할 때마다 사용자 입력을 초기화
 * displayKeySequence();
 * requestFocusInWindow(); // 키 입력을 받을 수 있도록 포커스를 요청
 * }
 * 
 * private List<String> generateKeySequence() {
 * List<String> sequence = new ArrayList<>();
 * sequence.add("l");
 * sequence.add("r");
 * sequence.add("u");
 * sequence.add("d");
 * // 필요한 경우 추가 키 시퀀스를 생성하세요.
 * return sequence;
 * }
 * 
 * private void displayKeySequence() {
 * keyPanel.removeAll();
 * for (String key : keySequence) {
 * JLabel keyLabel = new JLabel(getArrowSymbol(key));
 * keyLabel.setFont(new Font("Serif", Font.BOLD, 48));
 * keyPanel.add(keyLabel);
 * }
 * keyPanel.revalidate();
 * keyPanel.repaint();
 * }
 * 
 * private String getArrowSymbol(String key) {
 * switch (key) {
 * case "l":
 * return "←";
 * case "r":
 * return "→";
 * case "u":
 * return "↑";
 * case "d":
 * return "↓";
 * default:
 * return "";
 * }
 * }
 * 
 * private void handleKeyPress(KeyEvent e) {
 * String keyPressed = getKeyName(e);
 * if (keyPressed != null) {
 * System.out.println("Pressed: " + keyPressed); // 입력된 키를 확인하기 위해 출력
 * userInput.append(keyPressed);
 * if (userInput.length() > keySequence.size()) {
 * gameFailure();
 * return;
 * }
 * 
 * // 입력된 문자열이 시퀀스의 앞부분과 일치하는지 확인
 * if (!keySequence.subList(0,
 * userInput.length()).equals(stringToList(userInput.toString()))) {
 * gameFailure();
 * return;
 * }
 * 
 * // 전체 문자열이 일치하는지 확인
 * if (userInput.length() == keySequence.size() &&
 * userInput.toString().equals(listToString(keySequence))) {
 * startNewSequence();
 * }
 * }
 * }
 * 
 * private String getKeyName(KeyEvent e) {
 * switch (e.getKeyCode()) {
 * case KeyEvent.VK_LEFT:
 * return "l";
 * case KeyEvent.VK_RIGHT:
 * return "r";
 * case KeyEvent.VK_UP:
 * return "u";
 * case KeyEvent.VK_DOWN:
 * return "d";
 * default:
 * return null;
 * }
 * }
 * 
 * private List<String> stringToList(String str) {
 * List<String> list = new ArrayList<>();
 * for (char c : str.toCharArray()) {
 * list.add(String.valueOf(c));
 * }
 * return list;
 * }
 * 
 * private String listToString(List<String> list) {
 * StringBuilder sb = new StringBuilder();
 * for (String s : list) {
 * sb.append(s);
 * }
 * return sb.toString();
 * }
 * 
 * @Override
 * public void startGame() {
 * removeAll(); // 모든 컴포넌트를 제거하여 초기 상태로 만듦
 * setupGame(); // 게임을 새로 설정
 * revalidate(); // 컴포넌트 트리를 다시 검증
 * repaint(); // 다시 그리기
 * requestFocusInWindow(); // 게임이 시작될 때 포커스를 요청
 * }
 * 
 * @Override
 * public void addNotify() {
 * super.addNotify();
 * requestFocusInWindow(); // 컴포넌트가 추가될 때 포커스를 요청
 * }
 * 
 * }
 */