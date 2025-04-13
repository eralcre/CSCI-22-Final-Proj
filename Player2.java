import java.awt.*;
import javax.swing.*;

public class Player2 extends Player {
    private int x, y, width, height;
    private Image character;

    public Player2(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }

    @Override
    public void appearance(String position){
        switch (position) {
            case "F":
                character = new ImageIcon("samplePlayer2F.png").getImage();
                break;
            case "B":
                character = new ImageIcon("samplePlayer2B.png").getImage();
                break;
            case "L":
                character = new ImageIcon("samplePlayer2L.png").getImage();
                break;
            case "R":
                character = new ImageIcon("samplePlayer2R.png").getImage();
                break;
            default:
                character = new ImageIcon("samplePlayer2F.png").getImage();
        }
    }

    @Override
    public Image characterImage(){
        return character;
    }

    @Override
    public int getX() {return x;}
    @Override
    public int getY() {return y;}
    @Override
    public int getWidth() {return width;}
    @Override
    public int getHeight() {return height;}

    public void moveU() {y -= 10;}
    public void moveD() {y += 10;}
    public void moveL() {x -= 10;}
    public void moveR() {x += 10;}
}