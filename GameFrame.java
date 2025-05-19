/**
This is the GameFrame class. It creates the game window and manages the communication between the game canvas and server.
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
**/
import java.awt.*;
import javax.swing.*;

public class GameFrame {
    private GameCanvas gc;
    private JFrame frame;
    private String playerNumber;

    /**
     * This Constructor creates a new GameFrame based on the player's number
     * @param playerNumber identifies the player number ("Player 1" or "Player 2")
     */
    public GameFrame(String playerNumber) {
        this.playerNumber = playerNumber;
        int w = 1024;
        int h = 768;
        frame = new JFrame();
        gc = new GameCanvas(playerNumber);
        gc.setPreferredSize(new Dimension(w, h));
        frame.setTitle("The Wizard's Escape");
        frame.add(gc);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * returns the current game level
     */
 	public int updateGameLevel(){
		return gc.getLevel();
	}

    /**
     * sets the current game level
     * @param level the level to set
     */
    public void setLevel(int level) {
        gc.setLevel(level);
    }

    /**
     * returns the boolean state of playerReady in GameCanvas
     */
    public boolean isPlayerReady() {
        return gc.isPlayerReady();
    }

    /**
     * starts the game when triggered by the server
     */
    public void startGameFromServer() {
        gc.startGame();
    }
	
    /**
     * returns the player's X-coordinate
     */
    public int getPlayerX() {
        return gc.getPlayerX();
    }

    /**
     * returns the player's Y-coordinate
     */
    public int getPlayerY() {
        return gc.getPlayerY();
    }

    /**
     * updates the position of the other player
     * @param x x-coordinate of other player
     * @param y y-coordinate of other player
     */
    public void updateOtherPlayerPosition(int x, int y) {
        gc.updateOtherPlayerPosition(x, y);
    }
	
    /**
     * returns the boss's current health
     */
	public int getBossHp(){
		return gc.getBossHp();
	}

    /**
     * updates the boss's health
     * @param hp new health value
     */
	public void updateBossHp(int hp){
		gc.updateBossHp(hp);
	}

    /**
     * returns the boolean state of gameOver
     */
    public boolean isGameOver() {
        return gc.isGameOver();
    }

    /**
     * updates the game over state
     * @param a new game over state
     */
	public void updateGameOver(boolean a){
		gc.updateGameOver(a);
	}
}