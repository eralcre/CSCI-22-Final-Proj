/**
This is the ClueRoom class. It draws the Clue Room and allows for some of the objects in the room to be
interactive by creating shapes to represent object bounds.
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

public class ClueRoom implements Room{
    private ArrayList<GameObject> clueRoomObjs;
    private ArrayList<Shape> objectBounds, clueObjBounds;
    private Image bg, bookshelf, shelf, chairL, chairR, dresser, flowers, magicBall, plant, table, potions;
    private GameObject bookshelfObj, tableObj, potionsObj, dresserObj;
    private Rectangle bookshelfBounds, tableBounds, potionsBounds, dresserBounds;

    /**
     * This Constructor initializes the arraylist of clueRoomObjs, the images of objects in the clue room, and
     * calls the method that creates the clue objects and the shapes that define object bounds.
     */
    public ClueRoom(){
        clueRoomObjs = new ArrayList<>();

        // Images from Stardew Valley Planner
        // URL: https://stardewplanner.com/
        // Accessed: May 16, 2025
        // Note: Used for design and map layout inspiration only; no code copied
        bg = new ImageIcon("ClueRoom/ClueRoomBG.png").getImage();
        bookshelf = new ImageIcon("ClueRoom/Bookshelf.png").getImage();
        shelf = new ImageIcon("ClueRoom/shelf.png").getImage();
        chairL = new ImageIcon("ClueRoom/ChairL.png").getImage();
        chairR = new ImageIcon("ClueRoom/ChairR.png").getImage();
        dresser = new ImageIcon("ClueRoom/Dresser.png").getImage();
        flowers = new ImageIcon("ClueRoom/Flowers.png").getImage();
        magicBall = new ImageIcon("ClueRoom/MagicBall.png").getImage();
        plant = new ImageIcon("ClueRoom/Plant.png").getImage();
        table = new ImageIcon("ClueRoom/Table.png").getImage();
        potions = new ImageIcon("ClueRoom/Potions.png").getImage();

        bookshelfBounds = new Rectangle(419,208,207,60);
        tableBounds = new Rectangle(261,363,101,251);
        potionsBounds = new Rectangle(579,571,211,157);
        dresserBounds = new Rectangle(922,262,70,309);

        createObjs();
        createObjectBounds();
    }

    /**
     * overrides the createObjs() method from the Room interface
     * and compiles the interactive clue objects in an arraylist
     */
    @Override
    public void createObjs(){
        bookshelfObj = new GameObject(bookshelf, 414, 64, 212, 204);
        tableObj = new GameObject(table, 251, 363, 111, 251);
        potionsObj = new GameObject(potions, 550, 546, 253, 182);
        dresserObj = new GameObject(dresser, 907, 256, 117, 214);

        clueRoomObjs.add(bookshelfObj);
        clueRoomObjs.add(tableObj);
        clueRoomObjs.add(potionsObj);
        clueRoomObjs.add(dresserObj);   
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
        g.drawImage(shelf, 84, 41, 175, 234, null);
        g.drawImage(shelf, 239, 41, 175, 234, null);
        g.drawImage(plant, 652, 129, 79, 128, null);
        g.drawImage(chairR, 157, 382, 104, 164, null);
        g.drawImage(chairL, 371, 336, 95, 162, null);
        g.drawImage(chairL, 371, 438, 95, 162, null);

        for(GameObject obj:clueRoomObjs){
            obj.draw(g);
        }
        
        g.drawImage(magicBall, 251, 419, 84, 79, null);
        g.drawImage(flowers, 293, 459, 73, 104, null);
        g.drawImage(plant, 44, 600, 79, 128, null);
        g.drawImage(dresser, 907, 357, 117, 214, null);
        g.drawImage(plant, 907, 600, 79, 128, null);
    }

    /**
     * overrides the createObjectBounds() method from the Room interface
     * and creates an arraylist of shapes to define the bounds of the objects
     */
    @Override
    public void createObjectBounds(){
        objectBounds = new ArrayList<>();
        objectBounds.add(new Rectangle(0,0,1024,208));
        objectBounds.add(new Rectangle(0,208,32,560));
        objectBounds.add(new Rectangle(0,600,113,168));
        objectBounds.add(new Rectangle(165,728,879,40));
        objectBounds.add(new Rectangle(102,208,293,54));
        objectBounds.add(new Rectangle(662,208,64,48));
        objectBounds.add(new Rectangle(165,395,86,143));
        objectBounds.add(new Rectangle(371,345,86,233));
        objectBounds.add(new Rectangle(907,571,85,157));
        objectBounds.add(new Rectangle(992,208,32,560));

        objectBounds.add(bookshelfBounds);
        objectBounds.add(tableBounds);
        objectBounds.add(potionsBounds);
        objectBounds.add(dresserBounds);

        clueObjBounds = new ArrayList<>();
        clueObjBounds.add(bookshelfBounds);
        clueObjBounds.add(tableBounds);
        clueObjBounds.add(potionsBounds);
        clueObjBounds.add(dresserBounds);
    }

    /**
     * overrides the getObjectBounds() method from the Room interface
     * and returns the arraylist of object bounds
     */
    @Override
    public ArrayList<Shape> getObjectBounds(){
        return objectBounds;
    }

    /**
     * overrides the getOthrObjBounds() method from the Room interface
     * and returns the arraylist of clue object bounds
     */
    @Override
    public ArrayList<Shape> getOthrObjBounds(){
        return clueObjBounds;
    }
}
