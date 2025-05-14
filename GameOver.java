import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;

public class GameOver{
    private Font textFont;
    private Rectangle2D.Double restartButton;
    private Image restart;

    public GameOver(){
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/PixelifySans-Bold.ttf"));
        } catch (IOException ex) {
            System.out.println("IOException from Status Font.");
        } catch (FontFormatException ex) {
            System.out.println("FontFormatException from Status Font.");
        }

        restart = new ImageIcon("GameOver/restart.png").getImage();
    }

    public void drawGameOverScreen(Graphics2D g2d){       
        g2d.drawImage(restart, 252, 102, 519, 220, null);
    }

    public void drawRestartButton(Graphics2D g2d){
		restartButton = new Rectangle2D.Double(390, 420, 244, 98);
		g2d.setColor(Color.BLACK);
		g2d.fill(restartButton);

		textFont = textFont.deriveFont(36f);
		g2d.setFont(textFont);
		g2d.setColor(Color.RED);

		g2d.drawString("Restart", 430, 477);
    }

    public Rectangle2D.Double restartButtonArea(){
        return restartButton;
    }
}