/**
This is the GameWon class. It draws Game Won screen when players successfully defeat the boss and finish level 3.
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
import javax.swing.*;

public class GameWon{
    private Image gameOver;
    private Rectangle2D.Double gameOverScreen;
    private Color bgColor;

    /**
     * This Constructor initializes the color and image that make up the game won screen.
     */
    public GameWon(){
        bgColor = new Color(0,0,0,191);
        gameOver = new ImageIcon("GameWon/gameWon.png").getImage();
    }

    /**
     * draws the game won screen
     */
    public void drawGameWonScreen(Graphics2D g2d){ 
        gameOverScreen = new Rectangle2D.Double(0, 0, 1024, 768);
        g2d.setColor(bgColor);
        g2d.fill(gameOverScreen);      
        // Source: Oracle, "Drawing an Image", The Java™ Tutorials
        // URL: https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
        // Accessed: May 16, 2025
        // Note: Adapted from official Oracle Java 2D tutorial 
        g2d.drawImage(gameOver, 239, 200, null);
    }
             
}