import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameCanvas extends JComponent {
    private int width, height;
    private String playerNumber;
    private Player player;
    private Room room;

    public GameCanvas(int w, int h, String playerNumber) {
        this.playerNumber = playerNumber;
        width = w;
        height = h;

        if (playerNumber.equals("Player 1")) {
            player = new Player1(200, 200, 100, 100);
            room = new ClueRoom();

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    // Currently does nothing (I'll continue it tomorrow, I'm kinda ded but I know what I'll do already)
                    // gameObjectClick(e.getX(), e.getY());
                }
            });
        } else if (playerNumber.equals("Player 2")) {
            player = new Player2(200, 200, 100, 100);
            room = new ClueRoom();
        }

        setFocusable(true);
        setupKeyBindings();
    }

    // Method to handle object clicks (currently does nothing)
    private void gameObjectClick(int mouseX, int mouseY) {
        // ClueRoom clueRoom = (ClueRoom) room;
        // for (GameObject obj : clueRoom.getClueRoomObjs()) {
        //     if (obj.isClicked(mouseX, mouseY)) {
        //         break;
        //     }
        // }
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
            player.moveU();
            player.appearance("B");
            repaint();
        }
    }

    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveD();
            player.appearance("F");
            repaint();
        }
    }

    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveL();
            player.appearance("L");
            repaint();
        }
    }

    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.moveR();
            player.appearance("R");
            repaint();
        }
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
    }
}