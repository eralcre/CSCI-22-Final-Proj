import java.awt.*;

public abstract class Player {
    private int x, y, width, height;

    public abstract void appearance(String position);
    public abstract Image characterImage();

    public void draw(Graphics g){
        g.drawImage(characterImage(), getX(), getY(), getWidth(), getHeight(), null);
    }

    public abstract void moveU();
    public abstract void moveD();
    public abstract void moveL();
    public abstract void moveR();

    public abstract int getX();
    public abstract int getY();
    public abstract int getWidth();
    public abstract int getHeight();
}