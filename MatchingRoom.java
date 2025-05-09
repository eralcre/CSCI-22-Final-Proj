import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class MatchingRoom implements Room{
    private ArrayList<GameObject> matchingRoomObjs;
    private ArrayList<Shape> objectBounds;
    private Image bg, door;
    private GameObject doorObj;
    private Rectangle doorBounds;

    public MatchingRoom(){
        matchingRoomObjs = new ArrayList<>();
        bg = new ImageIcon("MatchingRoom/MatchingRoomBG.png").getImage();
        door = new ImageIcon("MazeRoom/Door.png").getImage();

        doorBounds = new Rectangle(461, 46, 104, 146);

        createObjs();
        createObjectBounds();
    }

    @Override
    public void createObjs(){
        doorObj = new GameObject(door, 461, 46, 104, 146);

        matchingRoomObjs.add(doorObj);  
    }

    @Override
    public void draw(Graphics g){
        g.drawImage(bg, 0, 0, 1024, 768, null);        
        g.drawImage(door, 461, 46, 104, 146, null);

        for(GameObject obj:matchingRoomObjs){
            obj.draw(g);
        }
    }

    public ArrayList<GameObject> getMatchingRoomObjs(){
        return matchingRoomObjs;
    }

    @Override
    public void createObjectBounds(){
        objectBounds = new ArrayList<>();
        objectBounds.add(new Rectangle(0,0,1024,192));
        objectBounds.add(new Rectangle(0,182,34,589));
        objectBounds.add(new Rectangle(33,734,966,33));
        objectBounds.add(new Rectangle(995,182,29,589));
		
        objectBounds.add(doorBounds);
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