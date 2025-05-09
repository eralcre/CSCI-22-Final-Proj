import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;

public class Start{
    private Color bgColor;
    private Font textFont;
    private Rectangle2D.Double startButton, startScreen;
    private Image title;

    public Start(){
        bgColor = new Color(0,0,0,191);
    
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/PixelifySans-Bold.ttf"));
        } catch (IOException ex) {
            System.out.println("IOException from Status Font.");
        } catch (FontFormatException ex) {
            System.out.println("FontFormatException from Status Font.");
        }

        title = new ImageIcon("Hints/sampleHint.png").getImage();
    }

    public void drawStartScreen(Graphics2D g2d){
        startScreen = new Rectangle2D.Double(0, 0, 1024, 768);
        g2d.setColor(bgColor);
        g2d.fill(startScreen);
       
        g2d.drawImage(title, 76, 87, 871, 308, null);
    }

    public void drawStartButton(Graphics2D g2d){
        startButton = new Rectangle2D.Double(390, 420, 244, 98);
        g2d.setColor(Color.BLACK);
        g2d.fill(startButton);

        textFont = textFont.deriveFont(36f);
        g2d.setFont(textFont);
        g2d.setColor(Color.RED);
        g2d.drawString("Start", 453, 477);
    }

    public Rectangle2D.Double startButtonArea(){
        return startButton;
    }
}