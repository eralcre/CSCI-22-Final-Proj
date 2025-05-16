/**
This is the Room interface. It consists of the differents method each room implements.
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
import java.util.ArrayList;

public interface Room {
    /**
     * draws the object
     */
    public abstract void draw(Graphics g);
    /**
     * creates the objects and add them in an arraylist
     */
    public abstract void createObjs();
    /**
     * creates the shapes that define object bounds and add them in an arraylist
     */
    public abstract void createObjectBounds();
    /**
     * returns the object bounds arraylist
     */
    public abstract ArrayList<Shape> getObjectBounds();
    /**
     * returns the bounds of other objects not previously included in the object bounds arra
     */
    public abstract ArrayList<Shape> getOthrObjBounds();
}