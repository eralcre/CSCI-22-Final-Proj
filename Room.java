import java.awt.*;
import java.util.ArrayList;

public interface Room {
    public abstract void draw(Graphics g);
    public abstract void createObjs();
    public abstract void createObjectBounds();
    public abstract ArrayList<Rectangle> getObjectBounds();
    public abstract ArrayList<Rectangle> getOthrObjBounds();
}