package model.entities;

import UI.GamePanel;
import model.Entity;
import model.components.Area2D;
import model.components.Sprite2D;
import model.interfaces.Sprite2DListener;
import utils.Const;
import utils.classes.PositionChangedBundle;
import utils.enumerations.EntityDirection;
import utils.enumerations.TileType;

public class Explosion extends Entity implements Sprite2DListener {
    private int radius;
    private int level;

    public Explosion(int x, int y, int radius, int level) {
        super(x, y);
        this.area2D = new Area2D(0, 0, GamePanel.tileSize, GamePanel.tileSize, this);
        this.radius = radius;
        this.level = level;
        String path = this.getImagePathByLevel(level, radius);
        this.sprite2D = new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, path);
        this.sprite2D.setListener(this);
        this.sprite2D.setPriority(3);
        this.sprite2D.setAnimationIndexRange(0, 5);
    }

    public void propagateExplosion() {
        this.propagateExplosion(null);
    }

    private void propagateExplosion(EntityDirection direction) {
        if (direction != null) {
            switch (direction) {
                case UP -> sprite2D.rotateFrameAntiClockwise90();
                case DOWN -> sprite2D.rotateFrameClockwise90();
                case LEFT -> sprite2D.rotateFrame180();
            }
        }

        int tileSize = GamePanel.tileSize;
        if (level >= radius) {
            // stop

        } else if (level == 0) {
            Explosion exUp = new Explosion(0, -tileSize, radius, level + 1);
            Explosion exDown = new Explosion(0, tileSize, radius, level + 1);
            Explosion exLeft = new Explosion(-tileSize, 0, radius, level + 1);
            Explosion exRight = new Explosion(tileSize, 0, radius, level + 1);

            checkPropagateExplosion(exUp, EntityDirection.UP);
            checkPropagateExplosion(exDown, EntityDirection.DOWN);
            checkPropagateExplosion(exLeft, EntityDirection.LEFT);
            checkPropagateExplosion(exRight, EntityDirection.RIGHT);

        } else {
            switch (direction) {
                case DOWN -> {
                    Explosion exDown = new Explosion(0, tileSize, radius, level + 1);
                    checkPropagateExplosion(exDown, direction);
                }
                case UP -> {
                    Explosion exUp = new Explosion(0, -tileSize, radius, level + 1);
                    checkPropagateExplosion(exUp, direction);
                }
                case LEFT -> {
                    Explosion exLeft = new Explosion(-tileSize, 0, radius, level + 1);
                    checkPropagateExplosion(exLeft, direction);
                }
                case RIGHT -> {
                    Explosion exRight = new Explosion(tileSize, 0, radius, level + 1);
                    checkPropagateExplosion(exRight, direction);
                }
                default -> { }
            }
        }
    }

    private void checkPropagateExplosion(Explosion ex, EntityDirection direction) {
        this.addChild(ex);
        Tile tile = GamePanel.getInstance().getCurrTileMap().getTileByWorldPosition(ex.getWorldX(), ex.getWorldY());
        if (tile == null) {
            this.removeChild(ex);
            return;

        } else if(tile.getType() == TileType.WALL) {
            this.removeChild(ex);
            return;

        } else if (tile.getType() == TileType.BRICK) {
            // replaces the brick with grass
            tile.setType(TileType.GRASS);
        }

        ex.propagateExplosion(direction);
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
    protected void update() {
        PositionChangedBundle bundle = new PositionChangedBundle();
        bundle.area2D = area2D;
        GamePanel.getInstance().firePropertyChange(Const.pclKeyArea, null, bundle);
    }

    @Override
    public void didChangeFrame() {

    }

    @Override
    public void didEndAnimation() {
        this.destroy();
    }
}
