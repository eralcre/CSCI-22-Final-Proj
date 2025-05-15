import java.awt.*;
import java.awt.geom.*;
import java.io.*;

public class StatusBoard{
    private int sWidth, sHeight;
    private Font textFont;
    private Color bgColor, textColor, borderColor;

    private int level;
    private boolean lvlWin, gameWin, clueFound;
    private String clueStat;

    public StatusBoard() {
        sWidth = 260;
        sHeight = 145;

        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/PixelifySans-Bold.ttf"));
        } catch (IOException ex) {
            System.out.println("IOException from Font.");
        } catch (FontFormatException ex) {
            System.out.println("FontFormatException from Font.");
        }

        bgColor = new Color(0,0,0,191);
        textColor = Color.WHITE;
        borderColor = new Color(79,29,20);
    }

    public void draw(Graphics2D g2d, int player){
        Rectangle2D.Double statusBoard = new Rectangle2D.Double(0,0,sWidth,sHeight);
        g2d.setColor(bgColor);
        g2d.fill(statusBoard);
        g2d.setColor(borderColor);
        g2d.draw(statusBoard);

        g2d.setColor(textColor);
        textFont.deriveFont(32);
        g2d.setFont(textFont);
        g2d.drawString("Player" + player, 33, 24);

        textFont.deriveFont(24);
        g2d.setFont(textFont);
        g2d.drawString("level:", 33, 75);
        g2d.drawString("clue:", 33, 97);
        g2d.drawString(Integer.toString(level), 112, 75);
        if(clueStat.equals("not found")){
            g2d.setColor(Color.RED);
        }
        else if(clueStat.equals("found")){
            g2d.setColor(Color.GREEN);
        }
        g2d.drawString(clueStat, 112, 97);
    }
}
