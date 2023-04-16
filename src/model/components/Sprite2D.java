package model.components;

import model.interfaces.Sprite2DListener;
import utils.ImageUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Sprite2D {
    private ArrayList<BufferedImage> spriteSheet;
    private int spriteNumber = 10; // number of updates between each frame
    private int spriteCounter = 0; // the current number of update
    private Sprite2DListener listener; // listener of events
    private int priority = 0; // priority which defines the order by each sprite is drawn
    private int animationIndexFrom = 0; // the index where the animation starts
    private int animationIndexTo = 0; // the index where the animation ends
    private int currentAnimationIndex = 0; // current index of the animation

    public Sprite2D(int frameWidth, int frameHeight, String filename) {

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
        List<BufferedImage> sheet = spriteSheet.stream().map(ImageUtils::rotateClockwise90).toList();
        spriteSheet.clear();
        spriteSheet.addAll(sheet);
    }

    public void rotateFrameAntiClockwise90() {
        List<BufferedImage> sheet = spriteSheet.stream().map(ImageUtils::rotateAntiClockwise90).toList();
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
        if (currentAnimationIndex < animationIndexFrom) {
            currentAnimationIndex = animationIndexFrom;
        }

        if (spriteCounter >= spriteNumber) {
            if (listener != null) {
                listener.didChangeFrame();
            }
            spriteCounter -= spriteNumber;

            currentAnimationIndex++;
            if(currentAnimationIndex > animationIndexTo) {
                currentAnimationIndex = animationIndexFrom;
            }

            if(listener != null && currentAnimationIndex == animationIndexFrom) {
                listener.didEndAnimation();
            }
        }
    }

    public void setAnimationIndexes(int animationIndexFrom, int animationIndexTo) {
        this.animationIndexFrom = animationIndexFrom;
        this.animationIndexTo = animationIndexTo;
    }

    public BufferedImage getFrameAt(int index) {
        if (index < spriteSheet.size()) {
            return spriteSheet.get(index);
        }

        return null;
    }

    public BufferedImage getCurrentFrame() {
        return getFrameAt(currentAnimationIndex);
    }
}
