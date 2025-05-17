/**
This is the GameCanvas class. It handles the main game logic, player movement, collision detection,
level progression, and rendering for both Player 1 and Player 2. It also manages timers, attacks,
and interactions with game objects.
@author Edward Francis S. Ibera (242267) & Erin Clare G. Maranan (242916)
@version 19 May 2025
We have not discussed the Java language code in our program
with anyone other than our instructor or the teaching assistants
assigned to this course.
We have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in our program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of our program.
*/
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class GameCanvas extends JComponent {
    private String playerNumber;
    private Player player;
    private Player otherPlayer;
    private boolean showOtherPlayer = false;
    private Start startScreen;
    private GameOver GOScreen;
    private GameWon GWScreen;
    private Room room;
    private Status gameStat;
    private Hint hint;
    private boolean gameOver;
    private boolean gameStarted, hintIsClicked, clueFound, clueIsClicked;
    private int hintClickCount;
    private Rectangle nextPlayerBounds, doorBounds;
    private Clue clue;
    private int clueBoundsIndex;

    private Timer animationTimer, levelTimer, attackTimer, monsterTimerL, monsterTimerR, monsterTimerA, gracePeriodTimer;
    private boolean playerIsReady;
    private boolean startButtonClicked;

    private boolean bossRoomEntered = false;
    private boolean bossIsHit = false;
    private boolean bossDefeated = false;
    private int bossHp = 100;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
	
    private ArrayList<TimedAttack> activeAttacks = new ArrayList<>();
    private Health playerLife = new Health();
    private Monster monster;
    private boolean monsterAttacks;
    private boolean canTakeDamage = true;
	
    /**
     * This Constructor initializes the game canvas for a specific player with all necessary components.
     * @param playerNumber identifies whether this is Player 1 or Player 2
     */
    public GameCanvas(String playerNumber) {
        this.playerNumber = playerNumber;

        hintIsClicked = false;
        hintClickCount = 0;
        clueFound = false;
        clueIsClicked = false;
        monsterAttacks = false;
        startScreen = new Start();
        GOScreen = new GameOver();
        GWScreen = new GameWon();
        gameStart();

        setFocusable(true);
        setUpStartMouseClicked();
    }

    /**
     * initializes each player's character, starting room, game status, and other game elements at the start of the game
     */
    public void gameStart() {
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

    /**
     * checks if the player has completed the current level and progresses to the next level if applicable.
	 * assigns the room for each of players based on their level and player number
     */
    private void checkForLevelCompletion() {
        Rectangle playerBounds = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        doorBounds = new Rectangle(461, 46, 104, 146);

        if (playerNumber.equals("Player 1")) {
            if (gameStat.getLevel() == 3 && !bossRoomEntered) {
                room = new BossRoom();
                player.setPosition(300, 639);
                bossRoomEntered = true;
                monster = new Monster();
                monsterTimerA.start();
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
                        bossRoomEntered = true;
                        monster = new Monster();
                        monsterTimerA.start();
                        repaint();
                        break;
                    default:
                        break;
                }
                repaint();
            }
        }
    }

    /**
     * updates the position of the other player in the Boss Room.
     * @param x X-coordinate of other player
     * @param y Y-coordinate of other player
     */
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

    /**
     * sets up key bindings for player movement and actions.
     */
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
                    spawnAttack();
                    repaint();
                }
            }
        });
    }

    /**
     * sets up the timer for player movement and animation updates.
     */
    public void setUpMoveActions() {
        animationTimer = new javax.swing.Timer(32, new ActionListener() {
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

    /**
     * checks if the player can move up without colliding with objects
     */
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

    /**
     * checks if the player can move down without colliding with objects
     */
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

    /**
     * checks if the player can move left without colliding with objects
     */
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

    /**
     * checks if the player can move right without colliding with objects
     */
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

    /**
     * checks if the player is near an interactive object in the specified direction
     * @param direction the direction to check ("up", "down", "left", or "right")
     */
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

    /**
     * sets up game timers including level timer and monster movement timers
     */
    public void setUpTimers() {
        ActionListener levelTimerListener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                gameStat.timerCountdown();
                if (gameStat.timeIsOver()) {
                    levelTimer.stop();
                    gameOver = true;
                }
                repaint();
            }
        };
        levelTimer = new Timer(1000, levelTimerListener);

        ActionListener monsterTimerListenerL = new ActionListener() {
            int count = 0;
            public void actionPerformed(ActionEvent ae) {
                monster.moveMonster("left");
                if (count == 20) {
                    monster.monsterAttacks(true);
                }
                if (count == 35) {
                    monster.monsterAttacks(false);
                    count = 0;
                }
                repaint();
                count++;
            }
        };
        monsterTimerL = new Timer(200, monsterTimerListenerL);

        ActionListener monsterTimerListenerR = new ActionListener() {
            int count = 0;
            public void actionPerformed(ActionEvent ae) {
                monster.moveMonster("right");
                if (count == 20) {
                    monster.monsterAttacks(true);
                }
                if (count == 35) {
                    monster.monsterAttacks(false);
                    count = 0;
                }
                repaint();
                count++;
            }
        };
        monsterTimerR = new Timer(200, monsterTimerListenerR);

        ActionListener monsterTimerListenerA = new ActionListener() {
            int count = 0;
            int triggerL = 5;
            int triggerR = 20;
            int limit1 = 15;
            int limit2 = 40;
            boolean mtLisMoving = false;
            boolean mtRisMoving = false;
            public void actionPerformed(ActionEvent ae) {
                if (count == triggerL) {
                    monsterTimerL.start();
                    mtLisMoving = true;
                } else if (count == triggerR) {
                    monsterTimerR.start();
                    mtRisMoving = true;
                }

                if (count == limit1 && mtLisMoving) {
                    monsterTimerL.stop();
                    mtLisMoving = false;
                } else if (count == limit2 && mtRisMoving) {
                    monsterTimerR.stop();
                    mtRisMoving = false;
                    if (limit2 == 40) {
                        limit1 = 25;
                        triggerR = 25;
                        limit2 = 50;
                    }
                    count = 0;
                }
                count++;
            }
        };
        monsterTimerA = new Timer(1000, monsterTimerListenerA);
    }
	
    /**
     * sets up mouse listener for the start button
     */
    private void setUpStartMouseClicked() {
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

    /**
     * sets up mouse listener for the hint button
     */
    private void setUpHintMouseClicked() {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (hint.hintIconArea().contains(p)) {
                    hintClickCount++;
                    if (hintClickCount % 2 != 0) {
                        hintIsClicked = true;
                    } else {
                        hintIsClicked = false;
                    }
                    repaint();
                }
            }
        };
        this.addMouseListener(ma);
    }

    /**
     * sets up mouse listener for clue interactions
     */
    private void setUpClueMouseClicked() {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if ((clue.clueIconArea() != null) && (clue.clueIconArea().contains(p))) {
                    clueIsClicked = true;
                } else if ((clue.xBoxArea() != null) && (clue.xBoxArea().contains(p))) {
                    clueIsClicked = false;
                }
                repaint();
            }
        };
        this.addMouseListener(ma);
    }

    /**
     * sets up mouse listener for pattern matching in the MatchingRoom
     */
    private void setUpPatternMouseClicked() {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameStat.getLevel() == 2 && playerNumber.equals("Player 2") && gameStarted) {
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

    /**
     * starts the game by initializing all necessary systems and timers
     */
    public void startGame() {
        gameStarted = true;
        levelTimer.start();
        setupKeyBindings();
        setUpMoveActions();
        setUpHintMouseClicked();
        setUpClueMouseClicked();
        setUpPatternMouseClicked();
        repaint();
    }

    /**
     * inner class representing a timed attack with its associated timer
     */
    private class TimedAttack {
        Attack attack;
        Timer timer;

        TimedAttack(Attack attack, Timer timer) {
            this.attack = attack;
            this.timer = timer;
        }
    }

    /**
     * spawns a new attack from the player's current position
     */
    private void spawnAttack() {
        if (gameStat.getLevel() != 3) return;

        Attack newAttack = new Attack(player.getX(), player.getY(), player.getWidth());

        Timer attackTimer = new Timer(5, e -> {
            if (newAttack.getAttackY() > 0) {
                newAttack.moveAttack();
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
                removeAttack(newAttack);
            }
        });

        activeAttacks.add(new TimedAttack(newAttack, attackTimer));
        attackTimer.start();
    }

    /**
     * removes an attack from the active attacks list
     * @param attack the attack to remove
     */
    private void removeAttack(Attack attack) {
        for (int i = 0; i < activeAttacks.size(); i++) {
            if (activeAttacks.get(i).attack == attack) {
                activeAttacks.remove(i);
                break;
            }
        }
    }

    /**
     * returns the boolean state of gameOver
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * updates the gameOver state
	 * @param a new gameOver state
     */
	public void updateGameOver(boolean a){
		gameOver = a;
		repaint();
	}

    /**
     * returns the boolean state of bossDefeated
     */
    public boolean gameIsWon() {
        return bossDefeated;
    }

    /**
     * returns the player's X-coordinate
     */
    public int getPlayerX() {
        return player.getX();
    }

    /**
     * returns the player's Y-coordinate
     */
    public int getPlayerY() {
        return player.getY();
    }

    /**
     * returns the player's current level
     */
    public int getLevel() {
        return gameStat.getLevel();
    }

    /**
     * sets the current level and initializes the boss room if level 3 is entered
     * @param level the level to set
     */
    public void setLevel(int level) {
        gameStat.setLevel(level);
        if (level == 3 && !bossRoomEntered) {
            room = new BossRoom();
            if (playerNumber.equals("Player 1")) {
                player.setPosition(300, 639);
            } else {
                player.setPosition(600, 639);
            }
            bossRoomEntered = true;
            monster = new Monster();
            monsterTimerA.start();
        }
        repaint();
    }

    /**
     * returns the boolean state of playerIsReady
     */
    public boolean isPlayerReady() {
        return playerIsReady;
    }

    /**
     * returns the boss's current health
     */
    public int getBossHp() {
        return bossHp;
    }

    /**
     * updates the boss's health and repaints the game
     * @param hp the new health of the boss
     */
    public void updateBossHp(int hp) {
        bossHp = hp;
        monster.updateHealth(bossHp);
        repaint();
    }

    /**
     * draws all game components including room, player, monster for BossRoom, attacks, UI elements, and game state screens
	 * overrides the paintComponent of JComponent
     */
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

        for (TimedAttack ta : activeAttacks) {
            ta.attack.drawAttack(g2d);
            if (gameStat.getLevel() == 3 && bossRoomEntered && !bossDefeated) {
                Ellipse2D.Double bossHitbox = monster.getMonsterHitbox();
                if (ta.attack.getAttackHitbox().getBounds2D().intersects(bossHitbox.getBounds2D())) {
                    ta.timer.stop();
                    activeAttacks.remove(ta);
                    bossHp -= 2;
                    monster.updateHealth(bossHp);
                    if (bossHp <= 0) {
                        bossDefeated = true;
                    }
                    repaint();
                    break;
                }
            }
        }

        if (gameStat.getLevel() == 3 && bossRoomEntered && !bossDefeated) {
            playerLife.draw(g2d);
            if (!(monster.getCurrentHp() == 0)) monster.drawMonster(g2d);

            Rectangle playerBounds = new Rectangle(
                player.getX(), player.getY(),
                player.getWidth(), player.getHeight()
            );

            if (playerLife.getLifeCount() > 0 && canTakeDamage) {
                for (Shape attack : monster.getMonsterAttackHitbox()) {
                    if (attack != null && playerBounds.intersects(attack.getBounds2D())) {
                        playerLife.lossLife();

                        if (playerLife.getLifeCount() <= 0) {
                            gameOver = true;
                        }

                        canTakeDamage = false;

                        gracePeriodTimer = new Timer(5000, e -> {
                            canTakeDamage = true;
                            gracePeriodTimer.stop();
                        });
                        gracePeriodTimer.setRepeats(false);
                        gracePeriodTimer.start();

                        repaint();
                        break;
                    }
                }
            }
        }
        
        hint.drawHintIcon(g2d);

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
        
        gameStat.draw(g2d);
        

        if (gameStarted == false) {
            startScreen.drawStartScreen(g2d);
            startScreen.drawStartButton(g2d, startButtonClicked);
        }

        if (gameOver) {
            GOScreen.drawGameOverScreen(g2d);
        }

        if (gameStat.getLevel() == 3) {
            if (bossDefeated || (monster != null && monster.getCurrentHp() <= 0)) {
                GWScreen.drawGameWonScreen(g2d);
                levelTimer.stop();
            }
        }
    }
}