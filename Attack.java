/**
This is the Attack class. It handles the creation and rendering of player attacks.
@author Edward Francis S. Ibera (242267) & Erin Clare G. Maranan (242916)
@version 19 May 2025
We have not discussed the Java language code in our program
with anyone other than our instructor or the teaching assistants
assigned to this course.
We have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in our program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of our program.
**/
import java.awt.*;
import java.awt.geom.*;

public class Attack {
    private Color color1, color2;
    private int playerX, playerY, playerW, size1, size2, size3;
    private int attackX, attackY;
    private Ellipse2D.Double attack1, attack2, attack3;

    /**
     * This Constructor creates an Attack object based on the player's current position
     * @param x x-coordinate of the player
     * @param y y-coordinate of the player
     * @param w width of the player
     */
    public Attack(int x, int y, int w){
        color1 = new Color(255,146,50);
        color2 = new Color(255,146,50,64);
        size1 = 20;
        size2 = 30;
        size3 = 40;

        playerX = x;
        playerY = y;
        playerW = w;

        attackX = playerX + (playerW / 2) - (size1 / 2);
        attackY = playerY - 40;

        updateAttack();
    }

    /**
     * draws the attack on the screen
	 * calls the updateAttack() method first to ensure that the attack drawn is the current position
     */
    public void drawAttack(Graphics2D g2d){
        updateAttack();

        g2d.setColor(color2);
        g2d.fill(attack3);
        g2d.fill(attack2);
        g2d.setColor(color1);
        g2d.fill(attack1);
    }

    /**
     * updates the position of the attack
     */
    private void updateAttack() {
        attack1 = new Ellipse2D.Double(attackX, attackY, size1, size1);
        attack2 = new Ellipse2D.Double(attackX - 5, attackY - 5, size2, size2);
        attack3 = new Ellipse2D.Double(attackX - 10, attackY - 10, size3, size3);
    }

    /**
     * returns the hitbox of the attack (attack3 because it's the other circle of the attack)
     */
    public Ellipse2D.Double getAttackHitbox() {
        return attack3;
    }

    /**
     * moves the attack upward
     */
    public void moveAttack(){
        attackY -= 10;
        updateAttack();
    }

    /**
     * returns the current y-coordinate of the attack
     */
    public int getAttackY() {
        return attackY;
    }
}