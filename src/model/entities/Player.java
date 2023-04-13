package model.entities;

import model.components.Area2D;
import model.components.Sprite2D;
import UI.GamePanel;
import UI.KeyHandler;
import model.Entity;
import utils.Const;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

public class Player extends Entity{
    private KeyHandler keyHandler;
    private final int cooldown = 5000; // millis
    private long lastShot = 0;

    public Player(int x, int y) {
        super(x, y);
        this.keyHandler = GamePanel.getInstance().getKeyHandler();
        setSprite2D(new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, 4, Const.bombermanPath));
        this.sprite2D.setPriority(2);
        setArea2D(new Area2D(10 * GamePanel.scale, 14 * GamePanel.scale, 12 * GamePanel.scale, 17 * GamePanel.scale, this));
    }

    private void setBomb() {
        long currTime = System.currentTimeMillis();
        if (currTime - lastShot > cooldown) {
            lastShot = currTime;
            int centerX = getWorldX() + GamePanel.tileSize / 2;
            int centerY = getWorldY() + GamePanel.tileSize / 2;
            Bomb bomb = new Bomb(centerX - (centerX % GamePanel.tileSize), centerY - (centerY % GamePanel.tileSize));
            bomb.castExplosion();
            GamePanel.getInstance().addEntity(bomb);
        }
    }

    @Override
    public void update() {
        if (keyHandler.spacePressed) {
            setBomb();
        }

        if (keyHandler.arrowNotPressed()) {
            state = EntityState.IDLE;

        } else {
            state = EntityState.MOVING;

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

            GamePanel.getInstance().checkCollision(this);

            switch (direction) {
                case DOWN -> y += speed;
                case UP -> y -= speed;
                case LEFT -> x -= speed;
                case RIGHT -> x += speed;
            }
        }
    }

    @Override
    public void onAreaEntered(Area2D area) {
        super.onAreaEntered(area);
        System.out.println("area entered!!");
    }
}
