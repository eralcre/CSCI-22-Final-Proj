import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class GameWon{
    private Image gameOver;
    private Rectangle2D.Double gameOverScreen;
    private Color bgColor;

    public GameWon(){
        bgColor = new Color(0,0,0,191);
        gameOver = new ImageIcon("GameWon/gameWon.png").getImage();
    }

    public void drawGameOverScreen(Graphics2D g2d){ 
        gameOverScreen = new Rectangle2D.Double(0, 0, 1024, 768);
        g2d.setColor(bgColor);
        g2d.fill(gameOverScreen);      
        g2d.drawImage(gameOver, 239, 200, null);
    }
}