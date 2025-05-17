/**
This is the Health class. It draws the remaining lives of the players and updates it whenever they lose a life.
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
import javax.swing.*;

public class Health{
    private Image life1, life2, life3;
    private int lifeCount;

    /**
     * This Constructor initializes the number of lives each player has and the images for each life remaining.
     */
    public Health(){
        lifeCount = 3;
        // Asset: Free Pixel Art Health Hearts 16x16 by Redreeh
        // URL: https://redreeh.itch.io/pixelhearts-16x16
        // Accessed: May 16, 2025
        // Note: Used for the images below
        life1 = new ImageIcon("BossRoom/Heart1.png").getImage();
        life2 = new ImageIcon("BossRoom/Heart1.png").getImage();
        life3 = new ImageIcon("BossRoom/Heart1.png").getImage();
    }	

    /**
     * draws all the images of the lives remaining
     */
    public void draw(Graphics2D g2d){
        // Source: Oracle, "Drawing an Image", The Java™ Tutorials
        // URL: https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
        // Accessed: May 16, 2025
        // Note: Adapted from official Oracle Java 2D tutorial
        g2d.drawImage(life1, 752, 12, null);        
        g2d.drawImage(life2, 810, 12, null);
        g2d.drawImage(life3, 869, 12, null);
    }

    /**
     * changes the images for each life lost
     */
    public void lossLife(){
        lifeCount--;
        if(lifeCount == 2){
            life3 = new ImageIcon("BossRoom/Heart2.png").getImage();
        }
        else if(lifeCount == 1){
            life2 = new ImageIcon("BossRoom/Heart2.png").getImage();
        }
        else if(lifeCount == 0){
            life1 = new ImageIcon("BossRoom/Heart2.png").getImage();
        }
    }
	
    /**
     * returns the lifeCount, which represents the remaining lives of the player
     */
	public int getLifeCount(){
		return lifeCount;
	}
}