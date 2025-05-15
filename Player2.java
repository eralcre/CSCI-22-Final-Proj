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
        character = new ImageIcon("P2/Player2F.png").getImage();
    }

    @Override
    public void appearance(String position){
        switch (position) {
            case "F":
                character = new ImageIcon("P2/Player2F.png").getImage();
                break;
            case "B":
                character = new ImageIcon("P2/Player2B.png").getImage();
                break;
            case "L":
                character = new ImageIcon("P2/Player2L.png").getImage();
                break;
            case "R":
                character = new ImageIcon("P2/Player2R.png").getImage();
                break;
            default:
                character = new ImageIcon("P2/Player2B.png").getImage();
        }
    }

    @Override
    public Image characterImage(){
        return character;
    }
	
	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
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