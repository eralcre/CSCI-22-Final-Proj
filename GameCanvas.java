import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JComponent {
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

    @Override
    protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);

        
    }
}