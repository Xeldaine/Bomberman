package entities;

import components.Area2D;
import components.Sprite2D;
import graphics.GamePanel;
import graphics.KeyHandler;
import utils.Const;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

public class Player extends Entity {
    private KeyHandler keyHandler;

    public Player(int x, int y) {
        super(x, y);
        this.keyHandler = GamePanel.getInstance().getKeyHandler();
        setSprite2D(new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, 4, Const.bombermanPath));
        setArea2D(new Area2D(12 * GamePanel.scale, 18 * GamePanel.scale, 8 * GamePanel.scale, 13 * GamePanel.scale, this));
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
                return;
            }

            switch (direction) {
                case DOWN -> y += speed;
                case UP -> y -= speed;
                case LEFT -> x -= speed;
                case RIGHT -> x += speed;
            }
        }
    }
}
