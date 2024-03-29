package model.entities;

import UI.GamePanel;
import model.Entity;
import model.components.Area2D;
import model.components.Sprite2D;
import utils.Const;
import utils.classes.PositionChangedBundle;

import java.beans.PropertyChangeEvent;

public class Bomb extends Entity {
    private int countdown = 3000;
    private long start = 0;

    public Bomb(int x, int y) {
        super(x, y);
        this.isCollisionEnabled = true;
        int tileSize = GamePanel.originalTileSize;
        this.sprite2D = new Sprite2D(tileSize, tileSize, Const.bombPath);
        this.sprite2D.setPriority(2);
        this.sprite2D.setAnimationIndexRange(0, 1);
        this.area2D = new Area2D(7 * GamePanel.scale, 11 * GamePanel.scale, 18 * GamePanel.scale, 18 * GamePanel.scale, this);
    }

    @Override
    protected void update() {
        if (start > 0 && System.currentTimeMillis() - start > countdown) {
            explode();
        }
    }

    public void startCountdown() {
        start = System.currentTimeMillis();
    }

    public void explode() {
        int radius = GamePanel.getInstance().getCurrPlayer().getBombRadius();
        start = 0;
        Explosion explosion = new Explosion(getWorldX(), getWorldY(), radius, 0);
        explosion.propagateExplosion();
        GamePanel.getInstance().addEntity(explosion);
        this.destroy();
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Const.pclKeyArea)) {
            PositionChangedBundle newValue = (PositionChangedBundle) evt.getNewValue();
            if(newValue.area2D != null && !(newValue.area2D.getEntity() instanceof Player)) {
                super.propertyChange(evt);
            }
        }
    }
}
