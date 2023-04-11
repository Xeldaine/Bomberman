package entities.tilemap;

import components.Area2D;
import components.Sprite2D;
import entities.Entity;
import graphics.GamePanel;
import utils.enumerations.TileType;
import java.awt.*;
import java.awt.image.BufferedImage;

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

    @Override
    public void draw(Graphics2D graphics2D) {
        if (sprite2D != null) {
            int tileSize = GamePanel.tileSize;
            BufferedImage image = sprite2D.getFrameAt(0);
            int screenX = parent != null ? parent.getScreenX() : 0;
            int screenY = parent != null ? parent.getScreenY() : 0;

            graphics2D.drawImage(image, screenX + getWorldX(), screenY + getWorldY(), tileSize, tileSize, null);
            if (isCollisionEnabled()) {
                graphics2D.setColor(new Color(1, 0, 0, 0.5f));
                graphics2D.fillRect(screenX + getWorldX() + area2D.x, screenY + getWorldY() + area2D.y, area2D.width, area2D.height);
            }
        }
    }
}
