/**
This is the Start class. It draws start screen and button that waits for both players to begin that game before
the game starts at level 1.
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

public class Start{
    private Color bgColor;
    private Font textFont;
    private Rectangle2D.Double startButton, startScreen;
    private Image title;

    /**
     * This Constructor initializes the colors, images, and font used in drawing the start screen.
     */
    public Start(){
        bgColor = new Color(0,0,0,191);

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
            System.out.println("IOException from start Font.");
        } catch (FontFormatException ex) {
            System.out.println("FontFormatException from start Font.");
        }

        title = new ImageIcon("GameTitle/title.png").getImage();
    }

    /**
     * draws all the shapes and images that make up the start screen
     */
    public void drawStartScreen(Graphics2D g2d){
        startScreen = new Rectangle2D.Double(0, 0, 1024, 768);
        g2d.setColor(bgColor);
        g2d.fill(startScreen);

        // Source: Oracle, "Drawing an Image", The Java™ Tutorials
        // URL: https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
        // Accessed: May 16, 2025
        // Note: Adapted from official Oracle Java 2D tutorial       
        g2d.drawImage(title, 213, 90, null);
    }

    /**
     * draws all the shapes and text that make up the start button
     * @param clicked changes the status of the button
     */
    public void drawStartButton(Graphics2D g2d, boolean clicked){
		startButton = new Rectangle2D.Double(390, 420, 244, 98);
		g2d.setColor(Color.BLACK);
		g2d.fill(startButton);

		textFont = textFont.deriveFont(36f);
        // Source: TutorialsPoint, "Java Examples – Display Text in a Frame"
        // URL: https://www.tutorialspoint.com/javaexamples/gui_text.htm
        // Accessed: May 16, 2025
        // Note: Adapted from GUI example demonstrating how to display text in a JFrame
		g2d.setFont(textFont);
		g2d.setColor(Color.RED);

		if (clicked) {
			g2d.drawString("Waiting", 435, 477);
		} else {
			g2d.drawString("Start", 453, 477);
		}
    }

    /**
     * returns the startButton shape
     */
    public Rectangle2D.Double startButtonArea(){
        return startButton;
    }
}