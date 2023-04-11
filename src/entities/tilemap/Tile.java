package entities.tilemap;

import components.Area2D;
import components.Sprite2D;
import entities.Entity;
import graphics.GamePanel;
import utils.enumerations.TileType;

public class Tile extends Entity {
    private TileType type;

    public Tile(int x, int y, TileType type) {
        super(x, y);
        this.type = type;
        sprite2D = new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, 1, type.getPath());
        area2D = new Area2D(0, 0, GamePanel.tileSize, GamePanel.tileSize, this);
        if (type == TileType.WALL || type == TileType.BRICK) {
            setCollisionEnabled(true);
        }
    }

    @Override
    public void update() {
        // nothing to update
    }
}
