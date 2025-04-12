import java.awt.*;

public class Player {
    private int x, y, width, height;
    private Color color;

    public Player(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
		this.width = width;
		this.height = height;
        this.color = color;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}