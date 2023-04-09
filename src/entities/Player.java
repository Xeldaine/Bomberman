package entities;

import entities.components.SpriteManager;
import graphics.GamePanel;
import graphics.KeyHandler;
import utils.Const;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    int framePerSection = 4;
    KeyHandler keyHandler = null;

    public Player(int x, int y, int speed) {
        super(x, y, speed);
        this.keyHandler = GamePanel.getInstance().getKeyHandler();
        setSpriteManager(new SpriteManager(GamePanel.originalTileSize, GamePanel.originalTileSize, framePerSection, Const.bombermanPath));
    }

    @Override
    public void update() {

        if (keyHandler.isNothingPressed()) {
            state = EntityState.IDLE;
            spriteManager.resetFrameAnimationCount();

        } else {
            state = EntityState.MOVING;
            spriteManager.updateFrameAnimationCount();

            if (keyHandler.downPressed) {
                y += speed;
                direction = EntityDirection.DOWN;
            }
            if (keyHandler.upPressed) {
                y -= speed;
                direction = EntityDirection.UP;
            }
            if (keyHandler.leftPressed) {
                x -= speed;
                direction = EntityDirection.LEFT;
            }
            if (keyHandler.rightPressed) {
                x += speed;
                direction = EntityDirection.RIGHT;
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int tileSize = GamePanel.tileSize;
        if (spriteManager != null) {
            int section = direction.getSection();
            BufferedImage frame = spriteManager.getFrameBySection(section);
            graphics2D.drawImage(frame, x, y, tileSize, tileSize, null);
        }
    }
}
