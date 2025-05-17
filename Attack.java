import java.awt.*;
import java.awt.geom.*;

public class Attack{
    private Color color1, color2;
    private int playerX, playerY, playerW, size1, size2, size3;
    private int attackX, attackY;
    private Ellipse2D.Double attack1, attack2, attack3;

    public Attack(int x, int y, int w){
        color1 = new Color(255,146,50);
        color2 = new Color(255,146,50,64);
        size1 = 20;
        size2 = 30;
        size3 = 40;

        playerX = x;
        playerY = y;
        playerW = w;

        attackX = playerX+(playerW/2)-(size1/2);
        attackY = playerY-40;
    }

	public void drawAttack(Graphics2D g2d){
		attack1 = new Ellipse2D.Double(attackX,attackY,size1,size1);
		attack2 = new Ellipse2D.Double(attackX-5,attackY-5,size2,size2);
		attack3 = new Ellipse2D.Double(attackX-10,attackY-10,size3,size3);
		
		g2d.setColor(color2);
		g2d.fill(attack3);
		g2d.fill(attack2);
		g2d.setColor(color1);
		g2d.fill(attack1);
	}

    public void moveAttack(){
        attackY -= 10;
    }
	
	public int getAttackY() {
		return attackY;
	}
}