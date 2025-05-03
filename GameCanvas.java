import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameCanvas extends JComponent {
	private int width, height;
	private String playerNumber;
    private Player player;
    private Room room;
    private Status gameStat;
    private Hint hint;
    private boolean hintIsClicked;
    private int hintClickCount;
	
    public GameCanvas(int w, int h, String playerNumber) {
		this.playerNumber = playerNumber;
		width = w;
		height = h;
        hintIsClicked = false;
        hintClickCount = 0;

        if (this.playerNumber.equals("Player 1")){
            player = new Player1(200, 200, 100, 100);
            room = new ClueRoom();
            gameStat = new Status(playerNumber);
            hint = new Hint();
            //call method here that will set listeners for when they click the objects
            
        } else if (this.playerNumber.equals("Player 2")) {
            player = new Player2(200, 200, 100, 100);
            //add bg image for this player here that should be conditioned depending on the level
            //call method that will set listeneners depending on the level puzzle
            gameStat = new Status(playerNumber);
            hint = new Hint();
        }

        setFocusable(true);
        setupKeyBindings();
        setUpHintMouseClicked();
    }

    private void setupKeyBindings() {
        InputMap im = getInputMap();
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "up");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "down");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right");

        am.put("up", new UpAction());
        am.put("down", new DownAction());
        am.put("left", new LeftAction());
        am.put("right", new RightAction());
    }

    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveU();
            player.appearance("B");
			repaint();
        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveD();
            player.appearance("F");
			repaint();
        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveL();
            player.appearance("L");
			repaint();
        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveR();
            player.appearance("R");
			repaint();
        }
    }

    private void setUpHintMouseClicked(){
        MouseAdapter ma = new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                Point p = e.getPoint();
                if(hint.hintIconArea().contains(p)){
                    hintClickCount++;
                    if (hintClickCount%2!=0){
                        hintIsClicked = true;
                    }
                    else {
                        hintIsClicked = false;
                    }
                    repaint();
                }
            }
        };
        this.addMouseListener(ma);
    }

    public int getLevel(){
        return gameStat.getLevel();
    }

    @Override
    protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);

        room.draw(g2d);
        player.draw(g2d);
        gameStat.draw(g2d);
        hint.drawHintIcon(g2d);
        if(hintIsClicked){
            hint.drawHintBox(g2d, getLevel(), playerNumber);
        }
    }
}