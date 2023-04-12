package model.entities;

import UI.GamePanel;
import model.Entity;
import model.components.Area2D;
import model.components.Sprite2D;
import utils.Const;

public class Bomb extends Entity {
    private final int detonationTime = 5000;
    private long start = 0;
    public Bomb(int x, int y) {
        super(x, y);
        int tileSize = GamePanel.originalTileSize;
        this.sprite2D = new Sprite2D(tileSize, tileSize, 2, Const.bombPath);
        this.sprite2D.setPriority(1);
        this.isCollisionEnabled = true;
        this.area2D = new Area2D(7 * GamePanel.scale, 11 * GamePanel.scale, 18 * GamePanel.scale, 18 * GamePanel.scale, this);
    }

    @Override
    public void update() {
        sprite2D.updateFrameCounter();
        if (start > 0 && System.currentTimeMillis() - start > detonationTime) {
            explode();
            start = 0;
        }
    }

    public void castExplosion() {
        start = System.currentTimeMillis();
    }

    private void explode() {
        Entity explosion = new Explosion(getWorldX(), getWorldY());
        GamePanel.getInstance().addEntity(explosion);
        GamePanel.getInstance().removeEntity(this);
    }
}
