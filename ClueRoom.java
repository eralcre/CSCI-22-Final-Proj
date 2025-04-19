import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ClueRoom implements Room{
    private ArrayList<GameObject> clueRoomObjs;
    private Image bg, bookshelf, shelf, chairL, chairR, dresser, flowers, magicBall, plant, table, potions;
    private GameObject bookshelfObj, tableObj, potionsObj, dresserObj;

    public ClueRoom(){
        clueRoomObjs = new ArrayList<>();
        bg = new ImageIcon("ClueRoom/ClueRoomBG.png").getImage();
        bookshelf = new ImageIcon("ClueRoom/Bookshelf.png").getImage();
        shelf = new ImageIcon("ClueRoom/shelf.png").getImage();
        chairL = new ImageIcon("ClueRoom/ChairL.png").getImage();
        chairR = new ImageIcon("ClueRoom/ChairR.png").getImage();
        dresser = new ImageIcon("ClueRoom/Dreser.png").getImage();
        flowers = new ImageIcon("ClueRoom/Flowers.png").getImage();
        magicBall = new ImageIcon("ClueRoom/MagicBall.png").getImage();
        plant = new ImageIcon("ClueRoom/Plant.png").getImage();
        table = new ImageIcon("ClueRoom/Table.png").getImage();
        potions = new ImageIcon("ClueRoom/Potions.png").getImage();
    }

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

    @Override
    public void draw(Graphics g){
        for(GameObject obj:clueRoomObjs){
            obj.draw(g);
        }
        g.drawImage(bg, 0, 0, 1024, 768, null);
        g.drawImage(shelf, 84, 41, 175, 234, null);
        g.drawImage(shelf, 239, 41, 175, 234, null);
        g.drawImage(plant, 652, 129, 79, 128, null);
        g.drawImage(chairR, 157, 382, 104, 164, null);
        g.drawImage(chairL, 371, 336, 95, 162, null);
        g.drawImage(chairL, 371, 438, 95, 162, null);
        g.drawImage(magicBall, 251, 419, 84, 79, null);
        g.drawImage(flowers, 293, 459, 73, 104, null);
        g.drawImage(plant, 44, 600, 79, 128, null);
        g.drawImage(dresser, 907, 357, 117, 214, null);
        g.drawImage(plant, 907, 600, 79, 128, null);
    }

    public ArrayList<GameObject> getClueRoomObjs(){
        return clueRoomObjs;
    }
}
