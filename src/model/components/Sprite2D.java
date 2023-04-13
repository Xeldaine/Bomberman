package model.components;

import model.interfaces.Sprite2DListener;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sprite2D {
    private ArrayList<BufferedImage> spriteSheet;
    private int frameWidth = 0, frameHeight = 0; // width and height for each frame
    private int frameNumber = 1; // number of frames for each section
    private int frameCounter = 0; // the current number of frame
    private int spriteNumber = 10; // number of updates between each frame
    private int spriteCounter = 0; // the current number of update
    private Sprite2DListener listener;
    private int priority = 0;

    public Sprite2D(int frameWidth, int frameHeight, int frameNumber, String filename) {
        if (frameWidth > 0 && frameHeight > 0) {
            this.frameWidth = frameWidth;
            this.frameHeight = frameHeight;
        }

        if (frameNumber > 0) {
            this.frameNumber = frameNumber;
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
        }
    }

    public void rotateFrameClockwise90() {
        List<BufferedImage> sheet = spriteSheet.stream().map( ImageUtils::rotateClockwise90).toList();
        spriteSheet.clear();
        spriteSheet.addAll(sheet);
    }

    public void rotateFrameAntiClockwise90() {
        List<BufferedImage> sheet = spriteSheet.stream().map( ImageUtils::rotateAntiClockwise90).toList();
        spriteSheet.clear();
        spriteSheet.addAll(sheet);
    }

    public void rotateFrame180(){
        List<BufferedImage> sheet = spriteSheet.stream().map( ImageUtils::rotateClockwise180).toList();
        spriteSheet.clear();
        spriteSheet.addAll(sheet);
    }

    public Sprite2DListener getListener() {
        return listener;
    }

    public void setListener(Sprite2DListener listener) {
        this.listener = listener;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void updateFrameCounter() {
        spriteCounter++;
        if (spriteCounter >= spriteNumber) {
            if (listener != null) {
                listener.didChangeFrame();
            }
            spriteCounter -= spriteNumber;
            frameCounter = (frameCounter + 1) % frameNumber;
            if(listener != null && frameCounter == 0) {
                listener.didEndAnimation();
            }
        }
    }

    public void resetFrameCounter() {
        frameCounter = 0;
    }

    public BufferedImage getFrameAt(int index) {
        if (index < spriteSheet.size()) {
            return spriteSheet.get(index);
        }

        return null;
    }

    public BufferedImage getFrameBySection(int sectionIndex){
        return getFrameAt((frameNumber * sectionIndex) + frameCounter);
    }
}
