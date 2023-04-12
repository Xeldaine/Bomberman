package model.entities;

import UI.GamePanel;
import model.Entity;
import model.components.Area2D;
import model.components.Sprite2D;
import model.interfaces.Sprite2DListener;
import utils.Const;

public class Explosion extends Entity implements Sprite2DListener {
    public Explosion(int x, int y) {
        super(x, y);
        this.area2D = new Area2D(0, 0, GamePanel.tileSize, GamePanel.tileSize, this);
        this.sprite2D = new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, 6, Const.explosionCenterPath);
        this.sprite2D.setListener(this);
        this.sprite2D.setPriority(3);
    }

    @Override
    public void update() {
        sprite2D.updateFrameCounter();
    }

    @Override
    public void didChangeFrame() {

    }

    @Override
    public void didEndAnimation() {
        GamePanel.getInstance().removeEntity(this);
    }
}
