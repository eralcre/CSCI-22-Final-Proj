import java.awt.*;
import javax.swing.*;

public class Player1 extends Player {
    private int x, y, width, height;
    private Image character;

    public Player1(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        character = new ImageIcon("P1/Player1F.png").getImage();
    }

    @Override
    public void appearance(String position){
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