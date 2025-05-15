import java.awt.*;
import javax.swing.*;

public class GameFrame {
    private GameCanvas gc;
    private JFrame frame;
    private String playerNumber;

    public GameFrame(String playerNumber) {
        this.playerNumber = playerNumber;
        int w = 1024;
        int h = 768;
        frame = new JFrame();
        gc = new GameCanvas(playerNumber);
        gc.setPreferredSize(new Dimension(w, h));
        frame.setTitle("Game Title");
        frame.add(gc);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

 	public int updateGameLevel(){
		return gc.getLevel();
	}

    public void setLevel(int level) {
        gc.setLevel(level);
    }

    public boolean isPlayerReady() {
        return gc.isPlayerReady();
    }

    public void startGameFromServer() {
        gc.startGame();
    }
	
    public int getPlayerX() {
        return gc.getPlayerX();
    }

    public int getPlayerY() {
        return gc.getPlayerY();
    }

    public void updateOtherPlayerPosition(int x, int y) {
        gc.updateOtherPlayerPosition(x, y);
    }

	public void spawnOtherPlayerAttack(int x, int y) {
		gc.spawnOtherPlayerAttack(x, y);
	}

	public boolean shouldSendAttack() {
		return gc.shouldSendAttack();
	}
}