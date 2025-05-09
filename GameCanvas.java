import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameCanvas extends JComponent {
    private int width, height;
    private String playerNumber;
    private Player player;
    private Start startScreen;
    private Room room;
    private Status gameStat;
    private Hint hint;
    private boolean levelFailed;
    private boolean gameStarted, hintIsClicked, clueFound, clueIsClicked, timerIsRunning;
    private int hintClickCount;
    private Rectangle nextPlayerBounds, doorBounds;
    private Clue clue;
    private int clueBoundsIndex;
    private Timer levelTimer;

    public GameCanvas(int w, int h, String playerNumber) {
        this.playerNumber = playerNumber;
        width = w;
        height = h;
        gameStarted = false;
        timerIsRunning = false;
        levelFailed = false;
        hintIsClicked = false;
        hintClickCount = 0;
        clueFound = false;
        clueIsClicked = false;

        startScreen = new Start();
        gameStart();

        setFocusable(true);
        setupKeyBindings();
        setUpTimers();
        setUpStartMouseClicked();
        setUpHintMouseClicked();
        setUpClueMouseClicked();
        
    }

    public void gameStart(){
        if (playerNumber.equals("Player 1")) {
            player = new Player1(600, 300, 100, 100);
            room = new ClueRoom();
            gameStat = new Status(playerNumber);
            hint = new Hint();
            clue = new Clue();
        } else if (playerNumber.equals("Player 2")) {
            player = new Player2(472, 639, 100, 100);
            room = new MazeRoom();
            gameStat = new Status(playerNumber);
            hint = new Hint();
            clue = new Clue();
        }
    }

	private void checkForLevelCompletion() {
		Rectangle playerBounds = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
		doorBounds = new Rectangle(461, 46, 104, 146);

		if (playerBounds.intersects(doorBounds)) {
			gameStat.nextLevel();
			int newLevel = gameStat.getLevel();
			switch (newLevel) {
				case 2:
					if (this.playerNumber.equals("Player 1")) {
						room = new ClueRoom();
					} else if (this.playerNumber.equals("Player 2")) {
						room = new MatchingRoom();
						player.setPosition(472, 639);
					}
					break;
				case 3:
					break;
				default:
					break;
			}

			repaint();
		}
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
            if (canMoveUp()) {
                player.moveU();
                player.appearance("B");
                clueFound = false;
				checkForLevelCompletion();
                repaint();
            } else if (isNearInteractiveObj("up")) {
                clueFound = true;
                repaint();
            }
        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (canMoveDown()) {
                player.moveD();
                player.appearance("F");
                clueFound = false;
				checkForLevelCompletion();
                repaint();
            } else if (isNearInteractiveObj("down")) {
                clueFound = true;
                repaint();
            }
        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (canMoveLeft()) {
                player.moveL();
                player.appearance("L");
                clueFound = false;
				checkForLevelCompletion();
                repaint();
            } else if (isNearInteractiveObj("left")) {
                clueFound = true;
                repaint();
            }
        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (canMoveRight()) {
                player.moveR();
                player.appearance("R");
                clueFound = false;
				checkForLevelCompletion();
                repaint();
            } else if (isNearInteractiveObj("right")) {
                clueFound = true;
                repaint();
            }
        }
    }

    public boolean canMoveUp() {
		int newWidth = player.getWidth() / 2;
		int xOffset = (player.getWidth() - newWidth) / 2;
		nextPlayerBounds = new Rectangle(player.getX() + xOffset, player.getY() + 45, newWidth, 45);
		for (Shape x : room.getObjectBounds()) {
			if (x.intersects(nextPlayerBounds)) {
				return false;
			}
		}
		return true;
    }

    public boolean canMoveDown() {
		int newWidth = player.getWidth() / 2;
		int xOffset = (player.getWidth() - newWidth) / 2;
		nextPlayerBounds = new Rectangle(player.getX() + xOffset, player.getY() + 65, newWidth, 45);
		for (Shape x : room.getObjectBounds()) {
			if (x.intersects(nextPlayerBounds)) {
				return false;
			}
		}
		return true;
    }

    public boolean canMoveLeft() {
		int newWidth = player.getWidth() / 2;
		int xOffset = (player.getWidth() - newWidth) / 2;
		nextPlayerBounds = new Rectangle(player.getX() - 10 + xOffset, player.getY() + 55, newWidth, 45);
		for (Shape x : room.getObjectBounds()) {
			if (x.intersects(nextPlayerBounds)) {
				return false;
			}
		}
		return true;
    }

    public boolean canMoveRight() {
		int newWidth = player.getWidth() / 2;
		int xOffset = (player.getWidth() - newWidth) / 2;
		nextPlayerBounds = new Rectangle(player.getX() + 10 + xOffset, player.getY() + 55, newWidth, 45);
		for (Shape x : room.getObjectBounds()) {
			if (x.intersects(nextPlayerBounds)) {
				return false;
			}
		}
		return true;
    }

    public boolean isNearInteractiveObj(String direction) {
		int newWidth = player.getWidth() / 2;
		int xOffset = (player.getWidth() - newWidth) / 2;
		
		switch (direction) {
			case "up":
				nextPlayerBounds = new Rectangle(player.getX() + xOffset, player.getY() + 45, newWidth, 45);
				break;
			case "down":
				nextPlayerBounds = new Rectangle(player.getX() + xOffset, player.getY() + 65, newWidth, 45);
				break;
			case "left":
				nextPlayerBounds = new Rectangle(player.getX() - 10 + xOffset, player.getY() + 55, newWidth, 45);
				break;
			case "right":
				nextPlayerBounds = new Rectangle(player.getX() + 10 + xOffset, player.getY() + 55, newWidth, 45);
				break;
		}

		for (Shape obj : room.getOthrObjBounds()) {
			if (obj.intersects(nextPlayerBounds)) {
				clueBoundsIndex = room.getObjectBounds().indexOf(obj);
				return true;
			}
		}
		return false;
    }

    public void setUpTimers(){
        ActionListener levelTimerListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                gameStat.timerCountdown();
                if (gameStat.timeIsOver()){
                    levelTimer.stop();
                    levelFailed = true;
                    gameStart();
                }
                repaint();
            }
        };

        levelTimer = new Timer(1000, levelTimerListener);
    }

    public void startGame(){
        gameStarted = true;
        repaint();
    }

    private void setUpStartMouseClicked(){
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (startScreen.startButtonArea().contains(p)) {
                    startGame();
                    timerIsRunning = true;
                    levelTimer.start();
                }
            }
        };
        this.addMouseListener(ma);
    }

    private void setUpHintMouseClicked() {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (hint.hintIconArea().contains(p)) {
                    hintClickCount++;
                    if (hintClickCount%2 != 0){
                        hintIsClicked = true;
                    }
                    else{
                        hintIsClicked = false;
                    }
                    repaint();
                }
            }
        };
        this.addMouseListener(ma);
    }

    private void setUpClueMouseClicked() {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if ((clue.clueIconArea()!=null) && (clue.clueIconArea().contains(p))) {
                    clueIsClicked = true;
                }
                else if ((clue.xBoxArea()!=null)&&(clue.xBoxArea().contains(p))){
                    clueIsClicked = false;
                }
                repaint();
            }
        };
        this.addMouseListener(ma);
    }

    public int getLevel() {
        return gameStat.getLevel();
    }
	
	public void setLevel(int level) {
		gameStat.setLevel(level);
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
        
        if(gameStarted==false){
            startScreen.drawStartScreen(g2d);
            startScreen.drawStartButton(g2d);
        }
    
        if (hintIsClicked) {
            hint.drawHintBox(g2d, getLevel(), playerNumber);
        }
        if (clueFound) {
            clue.drawClueIcon(g2d, player.getX(), player.getY());
        }
        if (clueIsClicked) {
            clue.drawClueBox(g2d, clueBoundsIndex);
            clue.drawX(g2d);
        }
    }
}