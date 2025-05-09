import java.awt.*;
import java.awt.geom.*;
import java.io.*;

public class Status{
    private int sWidth, sHeight;
    private Font textFont;
    private Color bgColor, textColor, borderColor;

    private int playerNum, level, mins1, mins2, secs1, secs2;
    private String timer;
    private boolean timeOver;

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
        mins1 = 0;
        mins2 = 5;
        secs1 = 0;
        secs2 = 0;
        timer = mins1+mins2+":"+secs1+secs2;
        timeOver = false;
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
        g2d.drawString("timer:", 33, 127);
        g2d.drawString(Integer.toString(level), 112, 105);

        g2d.setColor(Color.RED);
        g2d.drawString(timer, 112, 127);
    }

    public int getLevel(){
        return level;
    }

    public void nextLevel(){
        level += 1;
    }
	
	public void setLevel(int level){
		this.level = level;
	}

    public void setTimer(int m1, int m2, int s1, int s2){
        mins1 = m1;
        mins2 = m2;
        secs1 = s1;
        secs2 = s2;
    }

    public int getMins1(){return mins1;}
    public int getMins2(){return mins2;}
    public int getSecs1(){return secs1;}
    public int getSecs2(){return secs2;}

    public void timerCountdown(){
        if (secs2 > 0) {
            secs2--;
        } 
        else {
            if (secs1 > 0) {
                secs1--;
                secs2 = 9;
            } 
            else {
                if (mins2 > 0) {
                    mins2--;
                    secs1 = 5;
                    secs2 = 9;
                } 
                else {
                    if (mins1 > 0) {
                        mins1--;
                        mins2 = 9;
                        secs1 = 5;
                        secs2 = 9;
                    } 
                    else {
                        timeOver = true;
                    }
                }
            }
        }
        timer = mins1+mins2+":"+secs1+secs2;
    }

    public boolean timeIsOver(){
        return timeOver;
    }
}
