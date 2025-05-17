/**
This is the Status class. It draws status board on the top left of the canvas and presents the current level
and running timer for each player.
@author Edward Francis S. Ibera (242267) & Erin Clare G. Maranan (242916)
@version 19 May 2025
We have not discussed the Java language code in our program
with anyone other than our instructor or the teaching assistants
assigned to this course.
We have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in our program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of our program.
**/
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

    /**
     * This Constructor initializes the colors and font used in drawing the status.
     * It also initializes the level, timer, timer status for the players.
     * @param player Player 1 or Player 2
     */
    public Status(String player) {
        sWidth = 260;
        sHeight = 145;

        // Source: Oracle, Java SE 8 API Documentation – java.awt.Font
        // URL: https://docs.oracle.com/javase/8/docs/api/java/awt/Font.html
        // Accessed: May 16, 2025
        // Note: Referenced for creating and using custom fonts in Java GUI
        try {
            // Source: Google Fonts, "Pixelify Sans"
            // URL: https://fonts.google.com/specimen/Pixelify+Sans
            // Accessed: May 16, 2025
            // Note: Font downloaded from Google Fonts and used in Java GUI via custom font loading
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

    /**
     * draws all the shapes and text that make up the status
     */
    public void draw(Graphics2D g2d){
        Rectangle2D.Double statusBoard = new Rectangle2D.Double(0,0,sWidth,sHeight);
        g2d.setColor(bgColor);
        g2d.fill(statusBoard);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(11));
        g2d.draw(statusBoard);

        g2d.setColor(textColor);
        textFont = textFont.deriveFont(32f);
        // Source: TutorialsPoint, "Java Examples – Display Text in a Frame"
        // URL: https://www.tutorialspoint.com/javaexamples/gui_text.htm
        // Accessed: May 16, 2025
        // Note: Adapted from GUI example demonstrating how to display text in a JFrame
        g2d.setFont(textFont);
        g2d.drawString("Player" + playerNum, 33, 54);

        textFont = textFont.deriveFont(24f);
        // Source: TutorialsPoint, "Java Examples – Display Text in a Frame"
        // URL: https://www.tutorialspoint.com/javaexamples/gui_text.htm
        // Accessed: May 16, 2025
        // Note: Adapted from GUI example demonstrating how to display text in a JFrame
        g2d.setFont(textFont);
        g2d.drawString("level:", 33, 105);
        g2d.drawString("timer:", 33, 127);
        g2d.drawString(Integer.toString(level), 112, 105);

        g2d.setColor(Color.RED);
        g2d.drawString(timer, 112, 127);
    }

    /**
     * returns the level
     */
    public int getLevel(){
        return level;
    }

    /**
     * increments the level
     */
    public void nextLevel(){
        level += 1;
    }
	
    /**
     * sets the level based on the int passed through the parameter
     * @param level int that the level will be changed to
     */
	public void setLevel(int level){
		this.level = level;
	}

    /**
     * sets the timer values based on the ints passed through the parameters
     * @param m1 tens value of the minutes
     * @param m2 ones value of the minutes
     * @param s1 tens value of the seconds
     * @param s2 ones value of the seconds
     */
    public void setTimer(int m1, int m2, int s1, int s2){
        mins1 = m1;
        mins2 = m2;
        secs1 = s1;
        secs2 = s2;
    }

    /**
     * returns the tens value of the minutes
     */
    public int getMins1(){return mins1;}

    /**
     * returns the ones value of the minutes
     */
    public int getMins2(){return mins2;}

    /**
     * returns the tens value of the seconds
     */
    public int getSecs1(){return secs1;}

    /**
     * returns the ones value of the seconds
     */
    public int getSecs2(){return secs2;}

    /**
     * counts down the timer by 1 second, sets timerOver to true once the timer runs out, 
     * and updates the timer string
     */
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

    /**
     * returns timerOver which represents the status of the timer
     */
    public boolean timeIsOver(){
        return timeOver;
    }
}
