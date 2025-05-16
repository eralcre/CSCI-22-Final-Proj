import java.awt.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class MatchingRoom extends JComponent implements Room {
    private ArrayList<GameObject> matchingRoomObjs;
    private ArrayList<Shape> objectBounds;
    private Image bg, door, fence;
    private GameObject doorObj;
    private Rectangle doorBounds;
    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;
    private boolean puzzleSolved = false;
    private Image[] eyeImages = new Image[3];
    private Image[] patternImages = new Image[8];
    private Image[] patternClickImages = new Image[8];
    private Rectangle[] patternBounds = new Rectangle[8];
    private int[] selectedPatterns = {-1, -1};
    private int selectedCount = 0;
    private int[] correctPatterns = new int[2];
    private int leftEyeColor, rightEyeColor;

    public MatchingRoom(){
        matchingRoomObjs = new ArrayList<>();
        bg = new ImageIcon("MatchingRoom/MatchingRoomBG.png").getImage();
        door = new ImageIcon("MatchingRoom/Door.png").getImage();
		fence = new ImageIcon("MatchingRoom/Fence.png").getImage();

        eyeImages[0] = new ImageIcon("MatchingRoom/RedEye.png").getImage();
        eyeImages[1] = new ImageIcon("MatchingRoom/GreenEye.png").getImage();
        eyeImages[2] = new ImageIcon("MatchingRoom/BlueEye.png").getImage();

        patternImages[0] = new ImageIcon("MatchingRoom/Pattern1.png").getImage();
        patternImages[1] = new ImageIcon("MatchingRoom/Pattern2.png").getImage();
        patternImages[2] = new ImageIcon("MatchingRoom/Pattern3.png").getImage();
        patternImages[3] = new ImageIcon("MatchingRoom/Pattern4.png").getImage();
        patternImages[4] = new ImageIcon("MatchingRoom/Pattern5.png").getImage();
        patternImages[5] = new ImageIcon("MatchingRoom/Pattern6.png").getImage();
        patternImages[6] = new ImageIcon("MatchingRoom/Pattern7.png").getImage();
        patternImages[7] = new ImageIcon("MatchingRoom/Pattern8.png").getImage();

        patternClickImages[0] = new ImageIcon("MatchingRoom/Pattern1Click.png").getImage();
        patternClickImages[1] = new ImageIcon("MatchingRoom/Pattern2Click.png").getImage();
        patternClickImages[2] = new ImageIcon("MatchingRoom/Pattern3Click.png").getImage();
        patternClickImages[3] = new ImageIcon("MatchingRoom/Pattern4Click.png").getImage();
        patternClickImages[4] = new ImageIcon("MatchingRoom/Pattern5Click.png").getImage();
        patternClickImages[5] = new ImageIcon("MatchingRoom/Pattern6Click.png").getImage();
        patternClickImages[6] = new ImageIcon("MatchingRoom/Pattern7Click.png").getImage();
        patternClickImages[7] = new ImageIcon("MatchingRoom/Pattern8Click.png").getImage();
		
        patternBounds[0] = new Rectangle(156, 317, 84, 81);
        patternBounds[1] = new Rectangle(365, 320, 84, 78);
        patternBounds[2] = new Rectangle(576, 315, 88, 87);
        patternBounds[3] = new Rectangle(786, 310, 99, 92);
        patternBounds[4] = new Rectangle(156, 530, 83, 82);
        patternBounds[5] = new Rectangle(367, 530, 87, 84);
        patternBounds[6] = new Rectangle(573, 523, 95, 92);
        patternBounds[7] = new Rectangle(786, 524, 93, 91);

        doorBounds = new Rectangle(461, 46, 104, 146);

        createObjs();
        createObjectBounds();
		setUpColorCombinations();
    }

    @Override
    public void createObjs(){
        doorObj = new GameObject(door, 461, 46, 104, 146);

        matchingRoomObjs.add(doorObj);  
    }

    @Override
    public void draw(Graphics g){
        g.drawImage(bg, 0, 0, 1024, 768, null);        
        g.drawImage(door, 461, 46, 104, 146, null);

       switch(leftEyeColor) {
            case RED:
                g.drawImage(eyeImages[RED], 388, 43, 61, 34, null);
                break;
            case GREEN:
                g.drawImage(eyeImages[GREEN], 385, 39, 64, 41, null);
                break;
            case BLUE:
                g.drawImage(eyeImages[BLUE], 379, 32, 76, 56, null);
                break;
        }

        switch(rightEyeColor) {
            case RED:
                g.drawImage(eyeImages[RED], 573, 43, 61, 34, null);
                break;
            case GREEN:
                g.drawImage(eyeImages[GREEN], 574, 39, 64, 41, null);
                break;
            case BLUE:
                g.drawImage(eyeImages[BLUE], 568, 32, 76, 56, null);
                break;
        }

		if (puzzleSolved == false){
			g.drawImage(fence, 27, 193, 972, 108, null);	
		}

        for(GameObject obj:matchingRoomObjs){
            obj.draw(g);
        }
		
        for (int i = 0; i < 8; i++) {
            if (isPatternSelected(i)) {
                g.drawImage(patternClickImages[i], 
                          patternBounds[i].x, patternBounds[i].y,
                          patternBounds[i].width, patternBounds[i].height, null);
            } else {
                g.drawImage(patternImages[i], 
                          patternBounds[i].x, patternBounds[i].y,
                          patternBounds[i].width, patternBounds[i].height, null);
            }
        }		
    }

    public ArrayList<GameObject> getMatchingRoomObjs(){
        return matchingRoomObjs;
    }

    @Override
    public void createObjectBounds(){
        objectBounds = new ArrayList<>();
        objectBounds.add(new Rectangle(0,0,1024,192));
        objectBounds.add(new Rectangle(0,182,34,589));
        objectBounds.add(new Rectangle(33,734,966,33));
        objectBounds.add(new Rectangle(995,182,29,589));
		
        objectBounds.add(doorBounds);
		
		if (puzzleSolved == false) {
			objectBounds.add(new Rectangle(27, 193, 972, 108));
		}
    }

    @Override
    public ArrayList<Shape> getObjectBounds() {
        return objectBounds;
    }

    @Override
	public ArrayList<Shape> getOthrObjBounds() {
		return new ArrayList<>();
	}

	public void puzzleSolved(boolean solved) {
		this.puzzleSolved = solved;
		createObjectBounds();
		repaint();
	}

    private void setUpColorCombinations() {
        Random rand = new Random();
        leftEyeColor = rand.nextInt(3);
        rightEyeColor = rand.nextInt(3);

        if (leftEyeColor == RED && rightEyeColor == RED) correctPatterns = new int[]{5, 7};
        else if (leftEyeColor == RED && rightEyeColor == GREEN) correctPatterns = new int[]{4, 2};
        else if (leftEyeColor == RED && rightEyeColor == BLUE) correctPatterns = new int[]{2, 6};
        else if (leftEyeColor == GREEN && rightEyeColor == GREEN) correctPatterns = new int[]{4, 5};
        else if (leftEyeColor == GREEN && rightEyeColor == RED) correctPatterns = new int[]{3, 7};
        else if (leftEyeColor == GREEN && rightEyeColor == BLUE) correctPatterns = new int[]{2, 5};
        else if (leftEyeColor == BLUE && rightEyeColor == BLUE) correctPatterns = new int[]{0, 6};
        else if (leftEyeColor == BLUE && rightEyeColor == RED) correctPatterns = new int[]{6, 3};
        else if (leftEyeColor == BLUE && rightEyeColor == GREEN) correctPatterns = new int[]{7, 4};
    }
	
	public void patternClicked(int patternIndex) {
		for (int i = 0; i < selectedCount; i++) {
			if (selectedPatterns[i] == patternIndex) {
				for (int j = i; j < selectedCount - 1; j++) {
					selectedPatterns[j] = selectedPatterns[j + 1];
				}
				selectedPatterns[selectedCount - 1] = -1;
				selectedCount--;
				repaint();
				return;
			}
		}

		if (selectedCount >= 2) return;

		selectedPatterns[selectedCount++] = patternIndex;

		if (selectedCount == 2) {
			boolean isCorrect = (selectedPatterns[0] == correctPatterns[0] && selectedPatterns[1] == correctPatterns[1]) ||
								(selectedPatterns[0] == correctPatterns[1] && selectedPatterns[1] == correctPatterns[0]);

			if (isCorrect) {
				puzzleSolved = true;
				createObjectBounds();
			} else {
				Timer timer = new Timer(1000, e -> {
					selectedPatterns[0] = -1;
					selectedPatterns[1] = -1;
					selectedCount = 0;
					repaint();
				});
				timer.setRepeats(false);
				timer.start();
			}
		}
	}
	
	private boolean isPatternSelected(int index) {
		for (int i = 0; i < selectedCount; i++) {
			if (selectedPatterns[i] == index) {
				return true;
			}
		}
		return false;
	}
    public Rectangle[] getPatternBounds() { 
		return patternBounds; 
	}
	
    public boolean isPuzzleSolved() { 
		return puzzleSolved; 
	}
}