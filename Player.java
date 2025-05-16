/**
This is the abstract Player class. It consists of methods essential for the Player1 and Player2 classes.
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

public abstract class Player {
    /**
     * sets the player's appearance based on a given position
     * @param position player position
     */
    public abstract void appearance(String position);

    /**
     * returns the image used to represent the player
     */
    public abstract Image characterImage();

    /**
     * draws the player image at the current position and size using the Graphics object
     */
    public void draw(Graphics g){
        // Source: Oracle, "Drawing an Image", The Java™ Tutorials
        // URL: https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
        // Accessed: May 16, 2025
        // Note: Adapted from official Oracle Java 2D tutorial
        g.drawImage(characterImage(), getX(), getY(), getWidth(), getHeight(), null);
    }

    /**
     * changes the x and y coordinates of the player image
     * @param x x-coordinate
     * @param y y-coordinate
     */
	public abstract void setPosition(int x, int y);
   
    /**
     * moves the player image up
     */
    public abstract void moveU();
    
    /**
     * moves the player image down
     */
    public abstract void moveD();
    
    /**
     * moves the player image left
     */
    public abstract void moveL();
    
    /**
     * moves the player image right
     */
    public abstract void moveR();

    /**
     * gets the x-coordinate of the player image
     */
    public abstract int getX();
    
    /**
     * gets the y-coordinate of the player image
     */
    public abstract int getY();
    
    /**
     * gets the width of the player image
     */
    public abstract int getWidth();
    
    /**
     * gets the height of the player image
     */
    public abstract int getHeight();
}