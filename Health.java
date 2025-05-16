import java.awt.*;
import javax.swing.*;

public class Health{
    private Image life1, life2, life3;
    private int lifeCount;

    public Health(){
        lifeCount = 3;
        life1 = new ImageIcon("BossRoom/Heart1.png").getImage();
        life2 = new ImageIcon("BossRoom/Heart1.png").getImage();
        life3 = new ImageIcon("BossRoom/Heart1.png").getImage();
    }	

    public void draw(Graphics2D g2d){
        g2d.drawImage(life1, 752, 12, null);        
        g2d.drawImage(life2, 810, 12, null);
        g2d.drawImage(life3, 869, 12, null);
    }

    public void lossLife(){
        lifeCount--;
        if(lifeCount == 2){
            life3 = new ImageIcon("BossRoom/Heart2.png").getImage();
        }
        else if(lifeCount == 1){
            life2 = new ImageIcon("BossRoom/Heart2.png").getImage();
        }
        else if(lifeCount == 0){
            life1 = new ImageIcon("BossRoom/Heart2.png").getImage();
        }
    }
	
	public int getLifeCount(){
		return lifeCount;
	}
}