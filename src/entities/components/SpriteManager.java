package entities.components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class SpriteManager {
    ArrayList<BufferedImage> spriteSheet;
    int frameWidth, frameHeight; // width and height for each frame
    int framePerSection = 1; // number of frames for each section
    int frameAnimationCount = 0; // goes from 0 to (framePerSection - 1)
    int animationUpdateDelta = 10; // number of updates between each frame
    int currAnimationUpdate = 0; // the current number of update

    public SpriteManager(int frameWidth, int frameHeight, int framePerSection, String filename) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        if (framePerSection > 0) {
            this.framePerSection = framePerSection;
        }

        spriteSheet = new ArrayList<>();
        File file = new File(filename);

        try {
            BufferedImage fullImage = ImageIO.read(file);
            int width = fullImage.getWidth();
            int height = fullImage.getHeight();
            for (int j = 0; j < height; j+= frameHeight) {
                for (int i = 0; i < width; i+= frameWidth) {
                    spriteSheet.add(fullImage.getSubimage(i, j, frameWidth, frameHeight));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateFrameAnimationCount() {
        currAnimationUpdate++;
        if (currAnimationUpdate >= animationUpdateDelta) {
            currAnimationUpdate -= animationUpdateDelta;
            frameAnimationCount = (frameAnimationCount + 1) % framePerSection;
        }
    }

    public void resetFrameAnimationCount() {
        frameAnimationCount = 0;
    }

    public BufferedImage getFrameAt(int index) {
        return spriteSheet.get(index);
    }

    public BufferedImage getFrameBySection(int sectionIndex){
        return getFrameAt((framePerSection * sectionIndex) + frameAnimationCount);
    }
}
