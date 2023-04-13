package model.entities;

import UI.GamePanel;
import model.Entity;
import model.components.Area2D;
import model.components.Sprite2D;
import model.interfaces.Sprite2DListener;
import utils.Const;
import utils.enumerations.EntityDirection;

public class Explosion extends Entity implements Sprite2DListener {

    public Explosion(int x, int y, int radius, int level, EntityDirection direction) {
        super(x, y);
        this.area2D = new Area2D(0, 0, GamePanel.tileSize, GamePanel.tileSize, this);
        this.direction = direction;
        String path = this.getImagePathByLevel(level, radius);
        this.sprite2D = new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, 6, path);
        this.sprite2D.setListener(this);
        this.sprite2D.setPriority(3);
        this.propagateExplosion(level, radius);
    }

    private void propagateExplosion(int level, int radius) {
        int tileSize = GamePanel.tileSize;
        if (level >= radius) {
            // stop

        } else if (level == 0) {
            Explosion exUp = new Explosion(0, -tileSize, radius, level + 1, EntityDirection.UP);
            Explosion exDown = new Explosion(0, tileSize, radius, level + 1, EntityDirection.DOWN);
            Explosion exLeft = new Explosion(-tileSize, 0, radius, level + 1, EntityDirection.LEFT);
            Explosion exRight = new Explosion(tileSize, 0, radius, level + 1, EntityDirection.RIGHT);

            for (Explosion ex: new Explosion[] { exUp, exDown, exLeft, exRight} ) {
                this.addChild(ex);
            }

        } else {
            switch (direction) {
                case DOWN -> {
                    Explosion exDown = new Explosion(0, tileSize, radius, level + 1, EntityDirection.DOWN);
                    this.addChild(exDown);
                }
                case UP -> {
                    Explosion exUp = new Explosion(0, -tileSize, radius, level + 1, EntityDirection.UP);
                    this.addChild(exUp);
                }
                case LEFT -> {
                    Explosion exLeft = new Explosion(-tileSize, 0, radius, level + 1, EntityDirection.LEFT);
                    this.addChild(exLeft);
                }
                case RIGHT -> {
                    Explosion exRight = new Explosion(tileSize, 0, radius, level + 1, EntityDirection.RIGHT);
                    this.addChild(exRight);
                }
                default -> { }
            }
        }
    }

    private String getImagePathByLevel(int level, int radius) {
        if (level == 0) {
            return Const.explosionCenterPath;

        } else if (level == radius) {
            return Const.explosionEndPath;

        } else {
            return Const.explosionSidePath;
        }
    }

    @Override
    public void update() {
        GamePanel.getInstance().firePropertyChange(Const.pclKeyArea, null, area2D);
    }

    @Override
    public void didChangeFrame() {

    }

    @Override
    public void didEndAnimation() {
        GamePanel.getInstance().removeEntity(this);
    }


}
