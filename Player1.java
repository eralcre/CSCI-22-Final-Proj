/**
This is the Player1 class, which extends the abstract Player class. It sets the appearance of Player 1 and
allows the changing of its position on the canvas.
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

public class Player1 extends Player {
    private int x, y, width, height;
    private Image character;

    /**
     * This Constructor initializes the x and y coordinates, width, height, and image of the player.
     * @param x x-coordinate
     * @param y y-coordinate
     * @param w width
     * @param h height
     */
    public Player1(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        character = new ImageIcon("P1/Player1F.png").getImage();
    }

    /**
     * overrides the abstract method from the abstract Player class
     * sets the player's appearance based on a given position
     * @param position player position
     */
    @Override
    public void appearance(String position){
        // Images from Stardew Valley Dress Up Tool by Lybell
        // URL: https://lybell-art.github.io/stardew-dressup/
        // Accessed: May 16, 2025
        // Note: Only used as images; no code was copied
        switch (position) {
            case "F":
                character = new ImageIcon("P1/Player1F.png").getImage();
                break;
            case "B":
                character = new ImageIcon("P1/Player1B.png").getImage();
                break;
            case "L":
                character = new ImageIcon("P1/Player1L.png").getImage();
                break;
            case "R":
                character = new ImageIcon("P1/Player1R.png").getImage();
                break;
            default:
                character = new ImageIcon("P1/Player1B.png").getImage();
        }
    }

    /**
     * overrides the abstract method from the abstract Player class
     * returns the image used to represent the player
     */
    @Override
    public Image characterImage(){
        return character;
    }
	
    /**
     * overrides the abstract method from the abstract Player class
     * changes the x and y coordinates of the player image
     * @param x x-coordinate
     * @param y y-coordinate
     */
	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

    /**
     * overrides the abstract method from the abstract Player class
     * gets the x-coordinate of the player image
     */
    @Override
    public int getX() {return x;}

    /**
     * overrides the abstract method from the abstract Player class
     * gets the y-coordinate of the player image
     */
    @Override
    public int getY() {return y;}

    /**
     * overrides the abstract method from the abstract Player class
     * gets the width of the player image
     */
    @Override
    public int getWidth() {return width;}

    /**
     * overrides the abstract method from the abstract Player class
     * gets the height of the player image
     */
    @Override
    public int getHeight() {return height;}

    /**
     * overrides the abstract method from the abstract Player class
     * moves the player image up
     */
    @Override
    public void moveU() {y -= 10;}

    /**
     * overrides the abstract method from the abstract Player class
     * moves the player image down
     */
    @Override
    public void moveD() {y += 10;}

    /**
     * overrides the abstract method from the abstract Player class
     * moves the player image left
     */
    @Override
    public void moveL() {x -= 10;}

    /**
     * overrides the abstract method from the abstract Player class
     * moves the player image right
     */
    @Override
    public void moveR() {x += 10;}
}