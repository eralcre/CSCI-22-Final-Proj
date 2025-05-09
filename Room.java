import java.awt.*;
import java.util.ArrayList;

public interface Room {
    public abstract void draw(Graphics g);
    public abstract void createObjs();
    public abstract void createObjectBounds();
    public abstract ArrayList<Shape> getObjectBounds();
    public abstract ArrayList<Shape> getOthrObjBounds();
}