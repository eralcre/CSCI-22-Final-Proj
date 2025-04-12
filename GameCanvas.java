import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameCanvas extends JPanel {
	private int width, height;
    private Player player;

    public GameCanvas(int w, int h) {
		width = w;
		height = h;
        setFocusable(true);
        player = new Player(200, 200, 40, 40, Color.GREEN);
        setupKeyBindings();
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
            player.move(player.getX(), player.getY() - 10);
			repaint();
        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.move(player.getX(), player.getY() + 10);
			repaint();
        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.move(player.getX() - 10, player.getY());
			repaint();
        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.move(player.getX() + 10, player.getY());
			repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, getWidth(), getHeight());

    RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHints(rh);

    player.draw(g2d);
    }
}