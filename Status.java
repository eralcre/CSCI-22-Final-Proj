import java.awt.*;
import java.awt.geom.*;
import java.io.*;

public class Status{
    private int sWidth, sHeight;
    private Font textFont;
    private Color bgColor, textColor, borderColor;

    private int playerNum, level;
    private boolean lvlWin, clueFound;
    private String clueStat;

    public Status(String player) {
        sWidth = 260;
        sHeight = 145;

        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/PixelifySans-Bold.ttf"));
        } catch (IOException ex) {
            System.out.println("IOException from Status Font.");
        } catch (FontFormatException ex) {
            System.out.println("FontFormatException from Status Font.");
        }

        bgColor = new Color(0,0,0,191);
        textColor = Color.WHITE;
        borderColor = new Color(79,29,20);

        if (player.equals("Player 1")){
            playerNum = 1;
        }
        else if (player.equals("Player 2")){
            playerNum = 2;
        }

        level = 1;
        lvlWin = false;
        clueFound = false;
        clueStat = "not found";
    }

    public void draw(Graphics2D g2d){
        Rectangle2D.Double statusBoard = new Rectangle2D.Double(0,0,sWidth,sHeight);
        g2d.setColor(bgColor);
        g2d.fill(statusBoard);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(11));
        g2d.draw(statusBoard);

        g2d.setColor(textColor);
        textFont = textFont.deriveFont(32f);
        g2d.setFont(textFont);
        g2d.drawString("Player" + playerNum, 33, 54);

        textFont = textFont.deriveFont(24f);
        g2d.setFont(textFont);
        g2d.drawString("level:", 33, 105);
        g2d.drawString("clue:", 33, 127);
        g2d.drawString(Integer.toString(level), 112, 105);

        if(clueStat.equals("not found")){
            g2d.setColor(Color.RED);
        }
        else if(clueStat.equals("found")){
            g2d.setColor(Color.GREEN);
        }
        g2d.drawString(clueStat, 112, 127);
    }

    public int getLevel(){
        return level;
    }

    public void nextLevel(){
        level += 1;
    }

    // public boolean clueIsFound(){
        
    // }
}
