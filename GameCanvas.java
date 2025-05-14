import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class GameCanvas extends JComponent {
    private String playerNumber;
    private Player player;
	private Player otherPlayer;
    private boolean showOtherPlayer = false;
    private Start startScreen;
    private GameOver GOScreen;
    private Room room;
    private Status gameStat;
    private Hint hint;
    private boolean gameOver;
    private boolean gameStarted, hintIsClicked, clueFound, clueIsClicked;
    private int hintClickCount;
    private Rectangle nextPlayerBounds, doorBounds;
    private Clue clue;
    private int clueBoundsIndex;
    private Timer animationTimer, levelTimer;
	private boolean playerIsReady;
	private boolean startButtonClicked;
	private boolean bossDefeated = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean pPressed = false;

    public GameCanvas(String playerNumber) {
        this.playerNumber = playerNumber;
        
        hintIsClicked = false;
        hintClickCount = 0;
        clueFound = false;
        clueIsClicked = false;
        startScreen = new Start();
        GOScreen = new GameOver();
        gameStart();

        setFocusable(true);
        setUpStartMouseClicked();
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
        gameStarted = false;
        gameOver = false;
        playerIsReady = false;
        startButtonClicked = false;
        setUpTimers();
    }

	private void checkForLevelCompletion() {
		Rectangle playerBounds = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
		doorBounds = new Rectangle(461, 46, 104, 146);

		if (playerNumber.equals("Player 1")) {
			if (gameStat.getLevel() == 3) {
				room = new BossRoom();
				player.setPosition(300, 639);
				gameStat.setTimer(0, 2, 0, 0);
				repaint();
			}
		}

		if (playerNumber.equals("Player 2")) {
			if (playerBounds.intersects(doorBounds)) {
				gameStat.nextLevel();
				int newLevel = gameStat.getLevel();
				switch (newLevel) {
					case 2:
						room = new MatchingRoom();
						player.setPosition(472, 639);
						break;
					case 3:
						room = new BossRoom();
						player.setPosition(600, 639);
						gameStat.setTimer(0, 2, 0, 0);
						repaint();
						break;
					default:
						break;
				}
				repaint();
			}
		}
	}
	
	public void updateOtherPlayerPosition(int x, int y) {
		if (otherPlayer == null) {
			if (playerNumber.equals("Player 1")) {
				otherPlayer = new Player2(x, y, 100, 100);
			} else {
				otherPlayer = new Player1(x, y, 100, 100);
			}
		} else {
			otherPlayer.setPosition(x, y);
		}
		showOtherPlayer = true;
		repaint();
	}

	private void setupKeyBindings() {
		InputMap im = getInputMap(WHEN_FOCUSED);
		ActionMap am = getActionMap();

		im.put(KeyStroke.getKeyStroke("pressed W"), "pressed up");
		im.put(KeyStroke.getKeyStroke("pressed S"), "pressed down");
		im.put(KeyStroke.getKeyStroke("pressed A"), "pressed left");
		im.put(KeyStroke.getKeyStroke("pressed D"), "pressed right");

		im.put(KeyStroke.getKeyStroke("released W"), "released up");
		im.put(KeyStroke.getKeyStroke("released S"), "released down");
		im.put(KeyStroke.getKeyStroke("released A"), "released left");
		im.put(KeyStroke.getKeyStroke("released D"), "released right");
		
		im.put(KeyStroke.getKeyStroke("pressed P"), "pressed P");
		im.put(KeyStroke.getKeyStroke("released P"), "released P");

		am.put("pressed up", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { upPressed = true; }
		});
		am.put("pressed down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { downPressed = true; }
		});
		am.put("pressed left", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { leftPressed = true; }
		});
		am.put("pressed right", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { rightPressed = true; }
		});

		am.put("released up", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { upPressed = false; }
		});
		am.put("released down", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { downPressed = false; }
		});
		am.put("released left", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { leftPressed = false; }
		});
		am.put("released right", new AbstractAction() {
			public void actionPerformed(ActionEvent e) { rightPressed = false; }
		});

		am.put("pressed P", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameStat.getLevel() == 3) {
					 pPressed = true; 
				}
			}
		});
		am.put("released P", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameStat.getLevel() == 3) {
					 pPressed = false; 
				}
			}
		});
	}

	public void setUpActions() {
		animationTimer = new Timer(16, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean moved = false;

				if (upPressed) {
					if (canMoveUp()) {
						player.moveU();
						player.appearance("B");
						clueFound = false;
						checkForLevelCompletion();
						moved = true;
					} else if (isNearInteractiveObj("up")) {
						clueFound = true;
						moved = true;
					}
				}

				if (downPressed) {
					if (canMoveDown()) {
						player.moveD();
						if (gameStat.getLevel() == 3 && !bossDefeated) {
							player.appearance("B");
						} else {
							player.appearance("F");
						}
						clueFound = false;
						checkForLevelCompletion();
						moved = true;
					} else if (isNearInteractiveObj("down")) {
						clueFound = true;
						moved = true;
					}
				}

				if (leftPressed) {
					if (canMoveLeft()) {
						player.moveL();
						if (gameStat.getLevel() == 3 && !bossDefeated) {
							player.appearance("B");
						} else {
							player.appearance("L");
						}
						clueFound = false;
						checkForLevelCompletion();
						moved = true;
					} else if (isNearInteractiveObj("left")) {
						clueFound = true;
						moved = true;
					}
				}

				if (rightPressed) {
					if (canMoveRight()) {
						player.moveR();
						if (gameStat.getLevel() == 3 && !bossDefeated) {
							player.appearance("B");
						} else {
							player.appearance("R");
						}
						clueFound = false;
						checkForLevelCompletion();
						moved = true;
					} else if (isNearInteractiveObj("right")) {
						clueFound = true;
						moved = true;
					}
				}

				if (moved) {
					repaint();
				}
			}
		});
		animationTimer.start();
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
				clueBoundsIndex = room.getOthrObjBounds().indexOf(obj);
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
                    gameOver = true;
                }
                repaint();
            }
        };

        levelTimer = new Timer(1000, levelTimerListener);
    }

	private void setUpStartMouseClicked(){
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (gameStarted || startButtonClicked) return;

				Point p = e.getPoint();
				if (startScreen.startButtonArea().contains(p)) {
					startButtonClicked = true;
					playerIsReady = true;
					repaint();
				}
			}
		};
		this.addMouseListener(ma);
	}

    private void setUpGameOverMouseClicked(){
        MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = e.getPoint();
				if ((GOScreen.restartButtonArea()!=null) && (GOScreen.restartButtonArea().contains(p))) {
                    gameStart();
					repaint();
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
	
	private void setUpPatternMouseClicked() {
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (gameStat.getLevel() == 2  && playerNumber.equals("Player 2") && gameStarted) {
					MatchingRoom matchingRoom = (MatchingRoom) room;
					Point p = e.getPoint();

					for (int i = 0; i < 8; i++) {
						if (matchingRoom.getPatternBounds()[i].contains(p)) {
							matchingRoom.patternClicked(i);
							repaint();
							break;
						}
					}
				}
			}
		};
		this.addMouseListener(ma);
	}

    public int getLevel() {
        return gameStat.getLevel();
    }

	public void setLevel(int level) {
		gameStat.setLevel(level);
		repaint();
	}

	public boolean isPlayerReady() {
		return playerIsReady;
	}
	
    public void startGame(){
        gameStarted = true;
        levelTimer.start();
		setupKeyBindings();
		setUpActions();
		setUpGameOverMouseClicked();
        setUpHintMouseClicked();
        setUpClueMouseClicked();
		setUpPatternMouseClicked();
		repaint();
    }

    public boolean gameIsOver(){
        return gameOver;
    }

	public int getPlayerX() {
		return player.getX();
	}

	public int getPlayerY() {
		return player.getY();
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
		if (showOtherPlayer && otherPlayer != null && gameStat.getLevel() == 3) {
            otherPlayer.draw(g);
        }
        gameStat.draw(g2d);
        hint.drawHintIcon(g2d);

        if (gameStarted==false){
            startScreen.drawStartScreen(g2d);
            startScreen.drawStartButton(g2d, startButtonClicked);
        }

        if (gameOver){
            GOScreen.drawGameOverScreen(g2d);
            GOScreen.drawRestartButton(g2d);
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