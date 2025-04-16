import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {
	private int width, height;
	private String playerNumber;
	
    public GameCanvas(int w, int h, String playerNumber) {
		this.playerNumber = playerNumber;
		width = w;
		height = h;
		
        setLayout(new BorderLayout());

        if (playerNumber.equals("Player 1")){
            add(new P1Canvas(width, height), BorderLayout.CENTER);
        } else if (playerNumber.equals("Player 2")) {
            add(new P2Canvas(width, height), BorderLayout.CENTER);
        }
    }
}