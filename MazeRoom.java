import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.*;
import javax.swing.*;

public class MazeRoom implements Room{
    private ArrayList<GameObject> mazeRoomObjs;
    private ArrayList<Shape> objectBounds;
    private Image bg, door;
    private GameObject doorObj;
    private Rectangle doorBounds;

    public MazeRoom(){
        mazeRoomObjs = new ArrayList<>();
        bg = new ImageIcon("MazeRoom/MazeRoomBG.png").getImage();
        door = new ImageIcon("MazeRoom/Door.png").getImage();

        doorBounds = new Rectangle(461, 46, 104, 146);

        createObjs();
        createObjectBounds();
    }

    @Override
    public void createObjs(){
        doorObj = new GameObject(door, 461, 46, 104, 146);

        mazeRoomObjs.add(doorObj);  
    }

    @Override
    public void draw(Graphics g){
        g.drawImage(bg, 0, 0, 1024, 768, null);        
        g.drawImage(door, 461, 46, 104, 146, null);

        for(GameObject obj:mazeRoomObjs){
            obj.draw(g);
        }
    }

    public ArrayList<GameObject> getMazeRoomObjs(){
        return mazeRoomObjs;
    }

    @Override
    public void createObjectBounds(){
        objectBounds = new ArrayList<>();
        objectBounds.add(new Rectangle(0,0,1024,192));
        objectBounds.add(new Rectangle(0,182,34,589));
        objectBounds.add(new Rectangle(33,734,966,33));
        objectBounds.add(new Rectangle(995,182,29,589));
		
        objectBounds.add(doorBounds);
		
		objectBounds.add(new Line2D.Double(351, 626.5, 566, 626.5));         
		objectBounds.add(new Line2D.Double(351.5, 629, 350.5, 734));        
		objectBounds.add(new Line2D.Double(458.5, 519, 457.5, 624));     
		objectBounds.add(new Line2D.Double(566.5, 519, 565.5, 624));
		objectBounds.add(new Line2D.Double(457.5, 175, 456.5, 304));
		objectBounds.add(new Line2D.Double(139.5, 309, 140.5, 414));
		objectBounds.add(new Line2D.Double(244.5, 519, 243.5, 624));
		objectBounds.add(new Line2D.Double(140.5, 414, 139.5, 519));
		objectBounds.add(new Line2D.Double(564, 413.5, 669, 413.5));
		objectBounds.add(new Line2D.Double(140, 413.5, 251, 413.5));
		objectBounds.add(new Line2D.Double(251, 413.5, 356, 413.5));
		objectBounds.add(new Line2D.Double(34, 628.5, 139, 628.5));
		objectBounds.add(new Line2D.Double(667, 518.5, 772, 518.5));
		objectBounds.add(new Line2D.Double(884, 518.5, 989, 518.5));
		objectBounds.add(new Line2D.Double(356, 413.5, 461, 413.5));
		objectBounds.add(new Line2D.Double(461, 413.5, 566, 413.5));
		objectBounds.add(new Line2D.Double(570, 194, 570, 306.5));
		objectBounds.add(new Line2D.Double(565.5, 309, 564.5, 414));
		objectBounds.add(new Line2D.Double(669.5, 309, 668.5, 414));
		objectBounds.add(new Line2D.Double(668.5, 414, 667.5, 519));
		objectBounds.add(new Line2D.Double(884.5, 309, 884.5, 414));
		objectBounds.add(new Line2D.Double(777.5, 199, 777.5, 304));
		objectBounds.add(new Line2D.Double(777.5, 304, 777.5, 409));
		objectBounds.add(new Line2D.Double(884.5, 414, 883.5, 519));
		objectBounds.add(new Line2D.Double(669.5, 629, 668.5, 734));
		objectBounds.add(new Line2D.Double(669, 628.5, 884, 628.5));
		objectBounds.add(new Line2D.Double(244, 303.5, 459, 303.5));
		objectBounds.add(new Line2D.Double(140, 518.5, 355, 518.5));
		objectBounds.add(new Line2D.Double(250.5, 196, 249.5, 301));
		objectBounds.add(new Line2D.Double(675.5, 196, 674.5, 301));
    }

    @Override
    public ArrayList<Shape> getObjectBounds() {
        return objectBounds;
    }

    @Override
	public ArrayList<Shape> getOthrObjBounds() {
		return new ArrayList<>();
	}
}