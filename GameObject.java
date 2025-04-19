import java.awt.*;

public class GameObject {
    private int x, y, width, height;
    private Image img;

    public GameObject(Image img, int x, int y, int w, int h) {
        this.img = img;
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    public boolean isClicked(int mouseX, int mouseY) {
        return (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height);
    }
}