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
    private boolean hintIsClicked, clueFound, clueIsClicked;
    private int hintClickCount, clueClickCount;
    private Rectangle nextPlayerBounds;
    private Clue clue;
    private int clueBoundsIndex;
	
    public GameCanvas(int w, int h, String playerNumber) {
		this.playerNumber = playerNumber;
		width = w;
		height = h;
        hintIsClicked = false;
        hintClickCount = 0;
        clueFound = false;
        clueIsClicked = false;
        clueClickCount = 0;

        if (this.playerNumber.equals("Player 1")){
            player = new Player1(600, 300, 100, 100);
            room = new ClueRoom();
            gameStat = new Status(playerNumber);
            hint = new Hint();
            clue = new Clue();
            //call method here that will set listeners for when they click the objects
            
        } else if (this.playerNumber.equals("Player 2")) {
            player = new Player2(200, 300, 100, 100);
            //add bg image for this player here that should be conditioned depending on the level
            //call method that will set listeneners depending on the level puzzle
            gameStat = new Status(playerNumber);
            hint = new Hint();
            clue = new Clue();
        }

        setFocusable(true);
        setupKeyBindings();
        setUpHintMouseClicked();
        setUpClueMouseClicked();
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
            if(canMoveUp()){
                player.moveU();
                player.appearance("B");
                clueFound = false;
                repaint();
            }
            else if(isNearInteractiveObj("up")){
                clueFound = true;
                repaint();
            }
        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(canMoveDown()){
                player.moveD();
                player.appearance("F");
                clueFound = false;
                repaint();
            }
            else if(isNearInteractiveObj("down")){
                clueFound = true;
                repaint();
            }
        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(canMoveLeft()){
                player.moveL();
                player.appearance("L");
                clueFound = false;
                repaint();
            }
            else if(isNearInteractiveObj("left")){
                clueFound = true;
                repaint();
            }
        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(canMoveRight()){
                player.moveR();
                player.appearance("R");
                clueFound = false;
                repaint();
            }
            else if(isNearInteractiveObj("right")){
                clueFound = true;
                repaint();
            }
        }
    }

    public boolean canMoveUp(){
        nextPlayerBounds = new Rectangle(player.getX(),player.getY()+55-10,player.getWidth(),45);
        for(Rectangle x: room.getObjectBounds()){
            if(nextPlayerBounds.intersects(x)){
                return false;
            }
        }
        return true;
    }

    public boolean canMoveDown(){
        nextPlayerBounds = new Rectangle(player.getX(),player.getY()+55+10,player.getWidth(),45);
        for(Rectangle x: room.getObjectBounds()){
            if(nextPlayerBounds.intersects(x)){
                return false;
            }
        }
        return true;
    }

    public boolean canMoveLeft(){
        nextPlayerBounds = new Rectangle(player.getX()-10,player.getY()+55,player.getWidth(),45);
        for(Rectangle x: room.getObjectBounds()){
            if(nextPlayerBounds.intersects(x)){
                return false;
            }
        }
        return true;
    }

    public boolean canMoveRight(){
        nextPlayerBounds = new Rectangle(player.getX()+10,player.getY()+55,player.getWidth(),45);
        for(Rectangle x: room.getObjectBounds()){
            if(nextPlayerBounds.intersects(x)){
                return false;
            }
        }
        return true;
    }

    public boolean isNearInteractiveObj(String direction){
        switch (direction) {
            case "up":
                nextPlayerBounds = new Rectangle(player.getX(),player.getY()+55-10,player.getWidth(),45);
                break;
            case "down":
                nextPlayerBounds = new Rectangle(player.getX(),player.getY()+55+10,player.getWidth(),45);
                break;
            case "left":
                nextPlayerBounds = new Rectangle(player.getX()-10,player.getY()+55,player.getWidth(),45);
                break;
            case "right":
                nextPlayerBounds = new Rectangle(player.getX()+10,player.getY()+55,player.getWidth(),45);
                break;
            default:
                break;
        }

        for(Rectangle obj:room.getOthrObjBounds()){
            if(nextPlayerBounds.intersects(obj)){
                clueBoundsIndex = room.getObjectBounds().indexOf(obj);
                return true;
            }
        }
        return false;
    }

    private void setUpHintMouseClicked(){
        MouseAdapter ma = new MouseAdapter(){
            @Override
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

    private void setUpClueMouseClicked(){
        MouseAdapter ma = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Point p = e.getPoint();
                if(clue.clueIconArea().contains(p)){
                    clueClickCount++;
                    if (clueClickCount%2!=0){
                        clueIsClicked = true;
                    }
                    else {
                        clueIsClicked = false;
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
        if(clueFound){
            clue.drawClueIcon(g2d, player.getX(), player.getY());
        }
        if(clueIsClicked){
            clue.drawClueBox(g2d, clueBoundsIndex);
        }
    }
}