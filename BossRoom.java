/**
This is the BossRoom class. It's responsible for drawing the Boss Room which appears in the third level of the game.
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
import javax.swing.*;

public class BossRoom implements Room{
    private ArrayList<GameObject> bossRoomObjs;
    private ArrayList<Shape> objectBounds;
    private Image bg, door;
    private GameObject doorObj;
    private Rectangle doorBounds;

    /**
     * This Constructor initializes the bossRoomObjs arraylist, the bg and door images, and the doorBounds.
     * These are used to construct the overall look of the room and the objects that players can interact with.
     */
    public BossRoom(){
        bossRoomObjs = new ArrayList<>();

        // Images from Stardew Valley Planner
        // URL: https://stardewplanner.com/
        // Accessed: May 16, 2025
        // Note: Used for design and map layout inspiration only; no code copied
        bg = new ImageIcon("MazeRoom/MazeRoomBG.png").getImage();
        door = new ImageIcon("MazeRoom/Door.png").getImage();

        doorBounds = new Rectangle(461, 46, 104, 146);

        createObjs();
        createObjectBounds();
    }

    /**
     * overrides the createObjs() method from the Room interface
     * and compiles the interactive objects in an arraylist
     */
    @Override
    public void createObjs(){
        doorObj = new GameObject(door, 461, 46, 104, 146);

        bossRoomObjs.add(doorObj);  
    }

    /**
     * overrides the draw(Graphics g) method from the Room interface
     * and draws all the objects the make up the room
     */
    @Override
    public void draw(Graphics g){
        // Source: Oracle, "Drawing an Image", The Java™ Tutorials
        // URL: https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
        // Accessed: May 16, 2025
        // Note: Adapted from official Oracle Java 2D tutorial
        g.drawImage(bg, 0, 0, 1024, 768, null);        
        g.drawImage(door, 461, 46, 104, 146, null);

        for(GameObject obj:bossRoomObjs){
            obj.draw(g);
        }
    }

    /**
     * overrides the createObjectBounds() method from the Room interface
     * and creates an arraylist of shapes to define the bounds of the objects
     */
    @Override
    public void createObjectBounds(){
        objectBounds = new ArrayList<>();
        objectBounds.add(new Rectangle(0,0,1024,192));
        objectBounds.add(new Rectangle(0,182,34,589));
        objectBounds.add(new Rectangle(33,734,966,33));
        objectBounds.add(new Rectangle(995,182,29,589));
		
        objectBounds.add(doorBounds);
    }

    /**
     * overrides the getObjectBounds() method from the Room interface
     * and returns the arraylist of object bounds
     */
    @Override
    public ArrayList<Shape> getObjectBounds() {
        return objectBounds;
    }

    /**
     * overrides the getOthrObjBounds() method from the Room interface
     * and returns the arraylist of other object bounds
     */
    @Override
	public ArrayList<Shape> getOthrObjBounds() {
		return new ArrayList<>();
	}
}