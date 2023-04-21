package model.entities;

import model.components.Area2D;
import model.components.Sprite2D;
import UI.GamePanel;
import UI.KeyHandler;
import model.Entity;
import utils.Const;
import utils.MathUtils;
import utils.enumerations.EntityDirection;

public class Player extends Entity{
    private final KeyHandler keyHandler;
    private int cooldown = 3000; // millis
    private int invincibilityFrame = 3000; // millis
    private int bombRadius = 1;
    private long lastSetBomb = 0;
    private long lastDamaged = 0;
    private int score = 0;
    private int lives = 3;

    private float deltaAlpha = -0.1f;

    public Player(int x, int y) {
        super(x, y);
        this.keyHandler = GamePanel.getInstance().getKeyHandler();
        setSprite2D(new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, Const.bombermanPath));
        this.sprite2D.setPriority(2);
        this.direction = EntityDirection.DOWN;
        setArea2D(new Area2D(10 * GamePanel.scale, 14 * GamePanel.scale, 12 * GamePanel.scale, 17 * GamePanel.scale, this));
    }

    private void setBomb() {
        long currTime = System.currentTimeMillis();
        if (currTime - lastSetBomb > cooldown) {
            lastSetBomb = currTime;
            int centerX = getWorldX() + GamePanel.tileSize / 2;
            int centerY = getWorldY() + GamePanel.tileSize / 2;
            Tile tile = GamePanel.getInstance().getCurrTileMap().getTileByWorldPosition(centerX, centerY);
            if (tile != null) {
                Bomb bomb = new Bomb(tile.getX(), tile.getY());
                GamePanel.getInstance().getCurrTileMap().addChild(bomb);
                bomb.startCountdown();
            }
        }
    }

    public void damage() {
        if (lastDamaged == 0) {
            lastDamaged = System.currentTimeMillis();
            lives--;
        }
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getInvincibilityFrame() {
        return invincibilityFrame;
    }

    public void setInvincibilityFrame(int invincibilityFrame) {
        this.invincibilityFrame = invincibilityFrame;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    @Override
    protected void update() {

        if (keyHandler.spacePressed) {
            setBomb();
        }

        if (lastDamaged > 0) {
            if (System.currentTimeMillis() - lastDamaged > invincibilityFrame) {
                lastDamaged = 0;
                sprite2D.setAlpha(1);

            } else {
                float alpha = sprite2D.getAlpha();
                if (alpha == 1) {
                    deltaAlpha = -0.05f;

                } else if(alpha <= 0.5f) {
                    deltaAlpha = 0.05f;
                }
                sprite2D.setAlpha(MathUtils.clamp(0.5f, 1, alpha + deltaAlpha));
            }
        }

        if (keyHandler.arrowNotPressed()) {
            switch (direction) {
                case DOWN -> sprite2D.setAnimationIndexRange(0,0);
                case UP -> sprite2D.setAnimationIndexRange(4,4);
                case RIGHT -> sprite2D.setAnimationIndexRange(8,8);
                case LEFT -> sprite2D.setAnimationIndexRange(12,12);
            }

        } else {
            EntityDirection newDirection = keyHandler.getDirectionByKeyPressed();
            if (newDirection != null) {
                direction = newDirection;
            }

            switch (direction) {
                case DOWN -> { y += speed; sprite2D.setAnimationIndexRange(0,3); }
                case UP -> { y -= speed; sprite2D.setAnimationIndexRange(4,7); }
                case RIGHT -> {  x += speed; sprite2D.setAnimationIndexRange(8,11); }
                case LEFT -> { x -= speed; sprite2D.setAnimationIndexRange(12,15); }
            }
        }
    }
}
