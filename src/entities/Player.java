package entities;

import components.Area2D;
import components.Camera2D;
import components.Collider2D;
import components.Sprite2D;
import graphics.GamePanel;
import graphics.KeyHandler;
import utils.Const;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private KeyHandler keyHandler;
    private Boolean canMove = true;

    public Player(int x, int y) {
        super(x, y);
        this.keyHandler = GamePanel.getInstance().getKeyHandler();
        setSprite2D(new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, 4, Const.bombermanPath));
        setArea2D(new Area2D(12 * GamePanel.scale, 18 * GamePanel.scale, 8 * GamePanel.scale, 13 * GamePanel.scale, this));
    }

    @Override
    public void update() {

        if (keyHandler.isNothingPressed() || !canMove) {
            state = EntityState.IDLE;
            sprite2D.resetFrameAnimationCount();

        } else {
            state = EntityState.MOVING;
            sprite2D.updateFrameAnimationCount();

            if (keyHandler.downPressed) {
                direction = EntityDirection.DOWN;
            }
            if (keyHandler.upPressed) {
                direction = EntityDirection.UP;
            }
            if (keyHandler.leftPressed) {
                direction = EntityDirection.LEFT;
            }
            if (keyHandler.rightPressed) {
                direction = EntityDirection.RIGHT;
            }

            Collider2D collider = GamePanel.getInstance().checkCollision(this, worldX, worldY, direction, speed);

            if (collider != null) {
                return;
            }

            switch (direction) {
                case DOWN -> worldY += speed;
                case UP -> worldY -= speed;
                case LEFT -> worldX -= speed;
                case RIGHT -> worldX += speed;
            }
        }
    }

    public void setCanMove(Boolean canMove) {
        this.canMove = canMove;
    }

    public Boolean canMove() {
        return canMove;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int tileSize = GamePanel.tileSize;
        if (sprite2D != null) {
            int section = direction.getSection();
            BufferedImage frame = sprite2D.getFrameBySection(section);
            int x = 0;
            int y = 0;

            if (Camera2D.getInstance().getEntity() == this) {
                x = Camera2D.getInstance().getScreenX();
                y = Camera2D.getInstance().getScreenY();
            } else {
                x = worldX + Camera2D.getInstance().getOffsetX();
                y = worldY + Camera2D.getInstance().getOffsetY();
            }
            graphics2D.drawImage(frame, x, y, tileSize, tileSize, null);
        }
    }

    @Override
    public void onCollisionEntered(Collider2D collider2D) {

    }
}
