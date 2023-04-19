package model.entities;

import model.components.Area2D;
import model.components.Sprite2D;
import UI.GamePanel;
import UI.KeyHandler;
import model.Entity;
import utils.Const;
import utils.enumerations.EntityDirection;

public class Player extends Entity{
    private final KeyHandler keyHandler;
    private int cooldown = 3000; // millis
    private int bombRadius = 1;
    private long lastShot = 0;

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
        if (currTime - lastShot > cooldown) {
            lastShot = currTime;
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

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    @Override
    protected void update() {

        if (keyHandler.spacePressed) {
            setBomb();
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

    @Override
    public void onAreaEntered(Area2D area) {
        super.onAreaEntered(area);
        System.out.println("area entered!!");
    }
}
