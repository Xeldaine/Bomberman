package model.entities;

import model.components.Area2D;
import model.components.Sprite2D;
import UI.GamePanel;
import UI.KeyHandler;
import model.Entity;
import utils.Const;

public class Player extends Entity{
    private final KeyHandler keyHandler;
    private int cooldown = 3000; // millis
    private int bombRadius = 1;
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

    public int getSection() {
        if (this.direction == null) {
            return 0;
        }

        int section = 0;
        switch (this.direction) {
            case DOWN -> section = 0;
            case UP ->  section = 1;
            case RIGHT -> section = 2;
            case LEFT -> section = 3;
        }

        return section;
    }

    @Override
    protected void update() {

        if (keyHandler.spacePressed) {
            setBomb();
        }

        if (keyHandler.arrowNotPressed()) {
            sprite2D.resetFrameCounter();

        } else {
            direction = keyHandler.getDirectionByKeyPressed();
            sprite2D.setSectionIndex(getSection());

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
