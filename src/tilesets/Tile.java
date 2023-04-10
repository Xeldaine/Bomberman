package tilesets;

import components.SpriteManager;
import graphics.GamePanel;
import utils.enumerations.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private int localX, localY;
    private int worldX = 0, worldY = 0;
    private int screenX = 0, screenY = 0;

    TileType type;
    SpriteManager spriteManager;

    public Tile(int localX, int localY, TileType type) {
        this.localX = localX;
        this.localY = localY;
        this.type = type;
        int tileSize = GamePanel.originalTileSize;
        spriteManager = new SpriteManager(tileSize, tileSize, 1, type.getPath());
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public int getLocalX() {
        return localX;
    }

    public void setLocalX(int localX) {
        this.localX = localX;
    }

    public int getLocalY() {
        return localY;
    }

    public void setLocalY(int localY) {
        this.localY = localY;
    }

    public void draw(Graphics2D graphics2D) {
        if (spriteManager != null) {
            int tileSize = GamePanel.tileSize;
            BufferedImage image = spriteManager.getFrameAt(0);
            graphics2D.drawImage(image, screenX + worldX + localX, screenY + worldY + localY, tileSize, tileSize, null);
        }
    }
}
