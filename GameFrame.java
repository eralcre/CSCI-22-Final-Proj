import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
    private GameCanvas gc;
	private JFrame frame;

    public GameFrame() {
		int w = 1024;
		int h = 768;
		JFrame frame = new JFrame();
		GameCanvas gc = new GameCanvas(w, h);
		gc.setPreferredSize(new Dimension(w, h));
		frame.setTitle("blablablablablblababoom");
		frame.add(gc);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
    }
}