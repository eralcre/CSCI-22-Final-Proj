/**
This is the GameObject class. It draws the interactive game objects using images.
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

public class GameObject {
    private int x, y, width, height;
    private Image img;

    /**
     * This Constructor initializes the image and its x and y coordinates, width, and height.
     * @param img game object image
     * @param x x-coordinate when the object must be drawn
     * @param y y-coordinate when the object must be drawn
     * @param w width of the image
     * @param h height of the image
     */
    public GameObject(Image img, int x, int y, int w, int h) {
        this.img = img;
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }

    /**
     * draws the game object image
     */
    public void draw(Graphics g) {
        // Source: Oracle, "Drawing an Image", The Java™ Tutorials
        // URL: https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
        // Accessed: May 16, 2025
        // Note: Adapted from official Oracle Java 2D tutorial
        g.drawImage(img, x, y, width, height, null);
    }
}