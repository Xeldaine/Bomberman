package tilesets;

import components.SpriteManager;
import graphics.GamePanel;
import utils.enumerations.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    int x, y;
    TileType type;
    SpriteManager spriteManager;

    public Tile(int x, int y, TileType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        int tileSize = GamePanel.originalTileSize;
        spriteManager = new SpriteManager(tileSize, tileSize, 1, type.getPath());
    }

    public void draw(Graphics2D graphics2D) {
        if (spriteManager != null) {
            int tileSize = GamePanel.tileSize;
            BufferedImage image = spriteManager.getFrameAt(0);
            graphics2D.drawImage(image, x, y, tileSize, tileSize, null);
        }
    }
}
