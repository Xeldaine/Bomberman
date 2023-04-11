package entities;

import components.Area2D;
import components.Camera2D;
import components.Sprite2D;
import graphics.GamePanel;
import graphics.KeyHandler;
import utils.Const;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private int speed = 2 * GamePanel.scale; //default value for speed
    private KeyHandler keyHandler;

    public Player(int x, int y) {
        super(x, y);
        this.keyHandler = GamePanel.getInstance().getKeyHandler();
        setSprite2D(new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, 4, Const.bombermanPath));
        setArea2D(new Area2D(12 * GamePanel.scale, 18 * GamePanel.scale, 8 * GamePanel.scale, 13 * GamePanel.scale, this));
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void update() {
        if (keyHandler.isNothingPressed()) {
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

            Area2D area = GamePanel.getInstance().checkCollision(this);

            if (area != null && area.getEntity().isCollisionEnabled()) {
                //return;
            }

            switch (direction) {
                case DOWN -> y += speed;
                case UP -> y -= speed;
                case LEFT -> x -= speed;
                case RIGHT -> x += speed;
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int tileSize = GamePanel.tileSize;
        if (sprite2D != null) {
            int section = direction.getSection();
            BufferedImage frame = sprite2D.getFrameBySection(section);

            int screenX = this.getWorldX() + Camera2D.getInstance().getOffsetX();
            int screenY = this.getWorldY() + Camera2D.getInstance().getOffsetY();

            graphics2D.drawImage(frame, screenX, screenY, tileSize, tileSize, null);
            graphics2D.setColor(new Color(1, 0, 0, 0.5f));
            graphics2D.fillRect(screenX + area2D.x, screenY + area2D.y, area2D.width, area2D.height);
        }
    }
}
