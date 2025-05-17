/**
This is the Hint class. It draws the hint button at the top right of the screen and allows players to view the hints
to the right clues for each puzzle.
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
import java.io.*;
import javax.swing.*;

public class Hint{
    private Font textFont;
    private Color bgColor, hintColor, borderColor;
    private Image hint;
    private Rectangle2D.Double hintIcon;

    /**
     * This Constructor initializes the colors, images, and font used in drawing the hint.
     */
    public Hint() {
        // Source: Oracle, Java SE 8 API Documentation – java.awt.Font
        // URL: https://docs.oracle.com/javase/8/docs/api/java/awt/Font.html
        // Accessed: May 16, 2025
        // Note: Referenced for creating and using custom fonts in Java GUI
        try {
            // Source: Google Fonts, "Pixelify Sans"
            // URL: https://fonts.google.com/specimen/Pixelify+Sans
            // Accessed: May 16, 2025
            // Note: Font downloaded from Google Fonts and used in Java GUI via custom font loading
            textFont = Font.createFont(Font.TRUETYPE_FONT, new File("Font/PixelifySans-Bold.ttf"));
        } catch (IOException ex) {
            System.out.println("IOException from hint Font.");
        } catch (FontFormatException ex) {
            System.out.println("FontFormatException from hint Font.");
        }

        bgColor = new Color(0,0,0,191);
        hintColor = new Color(255,187,0);
        borderColor = new Color(79,29,20);

        hintIcon = new Rectangle2D.Double(942,12,50,50);
    }

    /**
     * draws all the shapes and text the make up the hint button
     */
    public void drawHintIcon(Graphics2D g2d){
        g2d.setColor(bgColor);
        g2d.fill(hintIcon);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(7));
        g2d.draw(hintIcon);

        g2d.setColor(hintColor);
        textFont = textFont.deriveFont(36f);
        // Source: TutorialsPoint, "Java Examples – Display Text in a Frame"
        // URL: https://www.tutorialspoint.com/javaexamples/gui_text.htm
        // Accessed: May 16, 2025
        // Note: Adapted from GUI example demonstrating how to display text in a JFrame
        g2d.setFont(textFont);
        g2d.drawString("?", 957, 45);
    }

    /**
     * returns the hintIcon shape
     */
    public Rectangle2D.Double hintIconArea(){
        return hintIcon;
    }

    /**
     * draws all the shapes and text the make up the hint popup box
     * and the hint images depending on the level and the player
     * @param level current level of the players
     * @param player Player 1 or Player 2
     */
    public void drawHintBox(Graphics2D g2d, int level, String player){
        Rectangle2D.Double hintBox = new Rectangle2D.Double(250,250,525,235);
        g2d.setColor(bgColor);
        g2d.fill(hintBox);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(11));
        g2d.draw(hintBox);

        g2d.setColor(Color.RED);
        g2d.drawString("hint!", 470, 290);

        if (player.equals("Player 2")){
            switch (level) {
                case 1:
                    hint = new ImageIcon("Hints/Hint.png").getImage();
                    break;
                case 2:
                    hint = new ImageIcon("Hints/Hint.png").getImage();
                    break;
                case 3:
                    hint = new ImageIcon("Hints/Hint3.png").getImage();//battle instructions img
                    break;
                default:
                    break;
            }
        }
        else if (player.equals("Player 1")){
            switch (level) {//change the files to actual hint images per level later on
                case 1:
                    hint = new ImageIcon("Hints/Hint1.png").getImage();
                    break;
                case 2:
                    hint = new ImageIcon("Hints/Hint2.png").getImage();
                    break;
                case 3:
                    hint = new ImageIcon("Hints/Hint3.png").getImage();//battle instructions img
                    break;
                default:
                    break;
            }
        }
        // Source: Oracle, "Drawing an Image", The Java™ Tutorials
        // URL: https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
        // Accessed: May 16, 2025
        // Note: Adapted from official Oracle Java 2D tutorial
        g2d.drawImage(hint, 303, 324, 420, 120, null);
    }
}