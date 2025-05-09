import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;

public class Clue{
    private Color borderColor,clueColor,bgColor;
    private Font textFont;
    private Rectangle2D.Double clueIcon, xBox;
    private int x, y;
    private Image clue;

    public Clue(){
        borderColor = new Color(79,29,20);
        clueColor = new Color(255,187,0);
        bgColor = Color.BLACK;
    
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/PixelifySans-Bold.ttf"));
        } catch (IOException ex) {
            System.out.println("IOException from Status Font.");
        } catch (FontFormatException ex) {
            System.out.println("FontFormatException from Status Font.");
        }

        clue = new ImageIcon("Hints/sampleHint.png").getImage();
    }

    public void drawClueIcon(Graphics2D g2d, int x, int y){
        clueIcon = new Rectangle2D.Double(x+25, y-65, 50, 50);
        g2d.setColor(bgColor);
        g2d.fill(clueIcon);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(7));
        g2d.draw(clueIcon);

        g2d.setColor(clueColor);
        textFont = textFont.deriveFont(36f);
        g2d.setFont(textFont);
        g2d.drawString("?", x+25+15, y-65+33);
    }

    public Rectangle2D.Double clueIconArea(){
        return clueIcon;
    }

    public void drawClueBox(Graphics2D g2d, int clueObjIndex){
        Rectangle2D.Double clueBox = new Rectangle2D.Double(112,188,800,450);
        g2d.setColor(bgColor);
        g2d.fill(clueBox);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(11));
        g2d.draw(clueBox);

        g2d.setColor(Color.RED);
        g2d.drawString("clue!", 470, 290);

        switch (clueObjIndex) {//change the files to actual clue images per level later on
            case 0:
                clue = new ImageIcon("Hints/sampleHint.png").getImage();//bookshelf clue
                break;
            case 1:
                clue = new ImageIcon("Hints/sampleHint.png").getImage();//table clue
                break;
            case 2:
                clue = new ImageIcon("Hints/sampleHint.png").getImage();//potions clue
                break;
            case 3:
                clue = new ImageIcon("Hints/sampleHint.png").getImage();//dresser clue
                break;
            default:
                break;
        }

        g2d.drawImage(clue, 303, 324, 420, 120, null);
    }

    public void drawX(Graphics2D g2d){
        Line2D.Double lStroke = new Line2D.Double(869.06, 214.94, 885.06, 230.94);
        Line2D.Double rStroke = new Line2D.Double(884.94, 214.94, 869.06, 230.94);
        g2d.setColor(bgColor);
        xBox = new Rectangle2D.Double(862,208,30,30);
        g2d.fill(xBox);
        
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(3));
        g2d.draw(lStroke);
        g2d.draw(rStroke);

        
    }

    public Rectangle2D.Double xBoxArea(){
        return xBox;
    }
}