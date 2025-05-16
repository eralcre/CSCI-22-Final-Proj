import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;

public class Hint{
    private Font textFont;
    private Color bgColor, hintColor, borderColor;
    private Image hint;
    private Rectangle2D.Double hintIcon;

    public Hint() {
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/PixelifySans-Bold.ttf"));
        } catch (IOException ex) {
            System.out.println("IOException from Status Font.");
        } catch (FontFormatException ex) {
            System.out.println("FontFormatException from Status Font.");
        }

        bgColor = new Color(0,0,0,191);
        hintColor = new Color(255,187,0);
        borderColor = new Color(79,29,20);

        hintIcon = new Rectangle2D.Double(942,12,50,50);
    }

    public void drawHintIcon(Graphics2D g2d){
        g2d.setColor(bgColor);
        g2d.fill(hintIcon);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(7));
        g2d.draw(hintIcon);

        g2d.setColor(hintColor);
        textFont = textFont.deriveFont(36f);
        g2d.setFont(textFont);
        g2d.drawString("?", 957, 45);
    }

    public Rectangle2D.Double hintIconArea(){
        return hintIcon;
    }

    public void drawHintBox(Graphics2D g2d, int level, String player){
        Rectangle2D.Double hintBox = new Rectangle2D.Double(250,250,525,235);
        g2d.setColor(bgColor);
        g2d.fill(hintBox);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(11));
        g2d.draw(hintBox);

        g2d.setColor(Color.RED);
        g2d.drawString("hint!", 470, 290);

        if (player.equals("Player 2")){
            switch (level) {//change the files to actual hint images per level later on
                case 1:
                    hint = new ImageIcon("Hints/Hint.png").getImage();//hint1 clueroom img
                    break;
                case 2:
                    hint = new ImageIcon("Hints/Hint.png").getImage();//hint 2 puzzle img
                    break;
                case 3:
                    hint = new ImageIcon("Hints/Hint3.png").getImage();//battle instructions img
                    break;
                default:
                    break;
            }
        }
        else if (player.equals("Player 1")){
            switch (level) {//change the files to actual hint images per level later on
                case 1:
                    hint = new ImageIcon("Hints/Hint1.png").getImage();//hint1 puzzle img
                    break;
                case 2:
                    hint = new ImageIcon("Hints/Hint2.png").getImage();//hint 2 clue room img
                    break;
                case 3:
                    hint = new ImageIcon("Hints/Hint3.png").getImage();//battle instructions img
                    break;
                default:
                    break;
            }
        }

        g2d.drawImage(hint, 303, 324, 420, 120, null);
    }
}