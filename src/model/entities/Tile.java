package model.entities;

import model.components.Area2D;
import model.components.Sprite2D;
import model.Entity;
import UI.GamePanel;
import utils.enumerations.TileType;

public class Tile extends Entity {
    private TileType type;
    public Boolean isCollisionEnabled = false;

    public Tile(int x, int y, TileType type) {
        super(x, y);
        this.type = type;
        sprite2D = new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, 1, type.getPath());
        sprite2D.setPriority(0);
        area2D = new Area2D(0, 0, GamePanel.tileSize, GamePanel.tileSize, this);
        if (type == TileType.WALL || type == TileType.BRICK) {
            setCollisionEnabled(true);
        }
    }

    public Boolean isCollisionEnabled() {
        return isCollisionEnabled;
    }

    public void setCollisionEnabled(Boolean collisionEnabled) {
        isCollisionEnabled = collisionEnabled;
    }

    @Override
    public void update() {
        // nothing to update
    }

    @Override
    public void onAreaEntered(Area2D area) {
        super.onAreaEntered(area);
    }
}
