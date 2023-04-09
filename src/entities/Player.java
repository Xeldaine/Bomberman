package entities;

import components.Camera2D;
import components.SpriteManager;
import graphics.GamePanel;
import graphics.KeyHandler;
import utils.Const;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyHandler keyHandler;

    public Player(int x, int y) {
        super(x, y);
        this.keyHandler = GamePanel.getInstance().getKeyHandler();
        setSpriteManager(new SpriteManager(GamePanel.originalTileSize, GamePanel.originalTileSize, 4, Const.bombermanPath));
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
                worldY += speed;
                direction = EntityDirection.DOWN;
            }
            if (keyHandler.upPressed) {
                worldY -= speed;
                direction = EntityDirection.UP;
            }
            if (keyHandler.leftPressed) {
                worldX -= speed;
                direction = EntityDirection.LEFT;
            }
            if (keyHandler.rightPressed) {
                worldX += speed;
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
            int x = worldX;
            int y = worldY;

            if (Camera2D.getInstance().getEntity() == this) {
                x = Camera2D.getInstance().getScreenX();
                y = Camera2D.getInstance().getScreenY();
            }
            graphics2D.drawImage(frame, x, y, tileSize, tileSize, null);
        }
    }
}
