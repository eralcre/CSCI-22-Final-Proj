import java.awt.*;
import javax.swing.*;

public class GameFrame{
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
		frame.setTitle("blablablablablblababoom");
		frame.add(gc);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
    }

	public int updateGameLevel(){
		return gc.getLevel();
	}

	public void setLevel(int level){
		gc.setLevel(level);
	}
	
	public boolean isPlayerReady(){
		return gc.isPlayerReady();
	}

	public void startGameFromServer(){
		gc.startGame();
	}

	public boolean gameOver(){
		return gc.gameIsOver();
	}
}