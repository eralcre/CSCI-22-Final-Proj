/**
This is the Monster class. It handles the creation, movement, attacks, and rendering of the monster in the Boss Room.
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
import javax.swing.*;
import java.util.ArrayList;

public class Monster {
    private Color mainColor, highlight, shadow, eyeColor, eyeColor2, effectColor;
    private int x, y, currentHp, healthXChange, healthYChange;
    private Image sideR, sideL, healthbar, health;
    private Ellipse2D.Double mShadow, centerEye, smallEye1, smallEye2, smallEye3, monsterHitbox;
    private boolean mAttack;
    private Rectangle2D.Double attack1, attack2;
    private Ellipse2D.Double effect1, effect2, eyeEffect;
    private ArrayList<Shape> monsterAttackBounds;

    /**
     * This Constructor creates a new Monster object
     */
    public Monster() {
        x = 0;
        y = 0;

        mAttack = false;

        mainColor = new Color(22, 20, 20);
        highlight = new Color(240, 170, 235);
        shadow = new Color(49, 32, 32, 50);
        eyeColor = new Color(115, 63, 88);
        eyeColor2 = new Color(184, 152, 152);
        effectColor = new Color(217, 217, 217, 66);

        sideR = new ImageIcon("Monster/sideR.png").getImage();
        sideL = new ImageIcon("Monster/sideL.png").getImage();

        // Asset: Health Bar by DanoPix
        // URL: https://danopix.itch.io/health-bar-by-dano
        // Accessed: May 16, 2025
        // Note: Used for the images below
        healthbar = new ImageIcon("Monster/healthBar.png").getImage();
        health = new ImageIcon("Monster/100H.png").getImage();

        monsterAttackBounds = new ArrayList<>();
        monsterHitbox = new Ellipse2D.Double(x + 383, y + 114, 257, 186);
		currentHp = 100;
        healthXChange = 0;
        healthYChange = 0;
    }

    /**
     * draws the monster on the screen
     */
    public void drawMonster(Graphics2D g2d){
        mShadow = new Ellipse2D.Double(x+335, y+30, 356, 272);
        g2d.setColor(shadow);
        g2d.fill(mShadow);

        centerEye = new Ellipse2D.Double(x+468, y+177, 90, 90);
        g2d.setColor(eyeColor);
        g2d.fill(centerEye);
        g2d.drawImage(sideR, x+513, y+130, null);
        g2d.drawImage(sideL, x+388, y+130, null);

        smallEye1 = new Ellipse2D.Double(x+476, y+142, 24, 44);
        smallEye2 = new Ellipse2D.Double(x+499, y+130, 28, 50);
        smallEye3 = new Ellipse2D.Double(x+526, y+142, 24, 44);
        g2d.setColor(eyeColor2);
        g2d.fill(smallEye1);
        g2d.fill(smallEye2);
        g2d.fill(smallEye3);

        centerEye = new Ellipse2D.Double(x+497, y+182, 32, 80);
        smallEye1 = new Ellipse2D.Double(x+479, y+142, 18, 44);
        smallEye2 = new Ellipse2D.Double(x+503, y+130, 20, 50);
        smallEye3 = new Ellipse2D.Double(x+529, y+142, 18, 44);
        g2d.setColor(highlight);
        g2d.fill(centerEye);
        g2d.fill(smallEye1);
        g2d.fill(smallEye2);
        g2d.fill(smallEye3);

        centerEye = new Ellipse2D.Double(x+499, y+182, 28, 80);
        smallEye1 = new Ellipse2D.Double(x+485, y+142, 6, 44);
        smallEye2 = new Ellipse2D.Double(x+509, y+130, 7, 50);
        smallEye3 = new Ellipse2D.Double(x+535, y+142, 6, 44);
        g2d.setColor(mainColor);
        g2d.fill(centerEye);
        g2d.fill(smallEye1);
        g2d.fill(smallEye2);
        g2d.fill(smallEye3);

        int healthX = x + 355 + healthXChange;
        int healthY = y + 68 + healthYChange;

        switch (currentHp) {
            case 80:
                healthYChange =  59 - 68;
                break;
            case 60:
                healthXChange =  345 - 355;
                healthYChange =  49 - 68;
                break;
            case 40:
                healthXChange =  353 - 355;
                healthYChange =  61 - 68;
                break;
            case 20:
                healthXChange =  332 - 355;
                healthYChange =  54 - 68;
                break;
            case 0:
                healthXChange =  343 - 355;
                healthYChange =  49 - 68;
                break;
            default:
                break;
        }
        g2d.drawImage(health, healthX, healthY, null);
        g2d.drawImage(healthbar, x + 349, y + 49, null);

        g2d.setColor(new Color(240, 170, 235, 66));
        eyeEffect = new Ellipse2D.Double(x+407, y+218, 211, 7);
        g2d.fill(eyeEffect);
        eyeEffect = new Ellipse2D.Double(x+460, y+220, 106, 3);
        g2d.fill(eyeEffect);
        eyeEffect = new Ellipse2D.Double(x+507, y+186, 2, 54);
        g2d.fill(eyeEffect);
        eyeEffect = new Ellipse2D.Double(x+496, y+200, 21, 2);
        g2d.fill(eyeEffect);
        eyeEffect = new Ellipse2D.Double(x+501, y+205, 21, 2);
        g2d.fill(eyeEffect);

        attack1 = new Rectangle2D.Double(x+506, y+267, 12, 459);
        attack2 = new Rectangle2D.Double(x+497, y+267, 31, 459);
        effect1 = new Ellipse2D.Double(x+472, y+267, 79, 466);
        effect2 = new Ellipse2D.Double(x+450, y+267, 125, 466);

        if(mAttack){
            g2d.setColor(effectColor);
            g2d.fill(effect2);
            g2d.fill(effect1);
            g2d.setColor(new Color(240, 170, 235, 130));
            g2d.fill(attack2);
            g2d.setColor(highlight);
            g2d.fill(attack1);
        }
    }

    /**
     * updates the monster's health
	 * uses a different hp bar based on the current hp of the boss
     * @param hp the new health value
     */
    public void updateHealth(int hp){
        currentHp = hp;
        // Asset: Health Bar by DanoPix
        // URL: https://danopix.itch.io/health-bar-by-dano
        // Accessed: May 16, 2025
        // Note: Used for the images below
        switch (hp) {
            case 80:
                health = new ImageIcon("Monster/80H.png").getImage();
                break;
            case 60:
                health = new ImageIcon("Monster/60H.png").getImage();
                break;
            case 40:
                health = new ImageIcon("Monster/40H.png").getImage();
                break;
            case 20:
                health = new ImageIcon("Monster/20H.png").getImage();
                break;
            case 0:
                health = new ImageIcon("Monster/0H.png").getImage();
                break;
            default:
                break;
        }
    }

    /**
     * checks whether the monster is attacking
     * @param a true if attacking, false otherwise
     */
    public void monsterAttacks(boolean a) {
        mAttack = a;
    }

    /**
     * moves the monster towards a specified direction
     * @param direction the direction to move ("left" or "right")
     */
    public void moveMonster(String direction) {
        switch (direction) {
            case "left":
                x -= 5;
                break;
            case "right":
                x += 5;
                break;
            default:
                throw new AssertionError();
        }

        monsterHitbox.x = x + 383;
        monsterHitbox.y = y + 114;
    }

    /**
     * returns monster's attack hitboxes
     */
    public ArrayList<Shape> getMonsterAttackHitbox() {
        monsterAttackBounds.clear();

        if (mAttack) {
            monsterAttackBounds.add(attack1);
            monsterAttackBounds.add(attack2);
            monsterAttackBounds.add(effect1);
            monsterAttackBounds.add(effect2);
        }

        return monsterAttackBounds;
    }

    /**
     * returns the monster's body hitbox
     */
    public Ellipse2D.Double getMonsterHitbox() {
        return monsterHitbox;
    }

    /**
     * returns the monster's current health
     */
	public int getCurrentHp(){
		return currentHp;
	}
}