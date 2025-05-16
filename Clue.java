import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;

public class Clue{
    private Color borderColor,clueColor,bgColor;
    private Font textFont;
    private Rectangle2D.Double clueIcon, xBox;
    private int x, y;
    private Image clue1, clue2, clue3, clue4;
	private boolean drawClue1, drawClue2, drawClue3, drawClue4;

    public Clue(){
        borderColor = new Color(79,29,20);
        clueColor = new Color(255,187,0);
        bgColor = Color.BLACK;
		clue1 = new ImageIcon("Clues/Bookshelf.png").getImage();
        clue2 = new ImageIcon("Clues/MazeMaps.png").getImage();
		clue3 = new ImageIcon("Clues/Potions.png").getImage();
		clue4 = new ImageIcon("Clues/Blackboard.png").getImage();
   
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/PixelifySans-Bold.ttf"));
        } catch (IOException ex) {
            System.out.println("IOException from Status Font.");
        } catch (FontFormatException ex) {
            System.out.println("FontFormatException from Status Font.");
        }
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
        removeClues();
        switch (clueObjIndex) {
            case 0: drawClue1 = true; break;
            case 1: drawClue2 = true; break;
            case 2: drawClue3 = true; break;
            case 3: drawClue4 = true; break;
        }
        
		Rectangle2D.Double clueBox = new Rectangle2D.Double(112,188,800,450);
        g2d.setColor(bgColor);
        g2d.fill(clueBox);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(11));
        g2d.draw(clueBox);

        g2d.setColor(Color.RED);
        g2d.drawString("clue!", 470, 250);
		
		if (drawClue1){
			g2d.drawImage(clue1, 170, 278, 683, 328, null);
		}
		if (drawClue2){
			g2d.drawImage(clue2, 146, 301, 732, 285, null);
		}
		if (drawClue3){
			g2d.drawImage(clue3, 170, 278, 683, 328, null);
		}
		if (drawClue4){
			g2d.drawImage(clue4, 179, 301, 668, 307, null);
		}
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

	public void removeClues(){
		drawClue1 = false;
		drawClue2 = false;
		drawClue3 = false;
		drawClue4 = false;
	}
}