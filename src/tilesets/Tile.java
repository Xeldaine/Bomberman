package tilesets;

import components.Collider2D;
import components.Sprite2D;
import graphics.GamePanel;
import utils.enumerations.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private int localX, localY;
    private int worldX = 0, worldY = 0;
    private int screenX = 0, screenY = 0;

    private Collider2D collider2D;

    private TileType type;
    private Sprite2D sprite2D;

    public Tile(int localX, int localY, TileType type) {
        this.localX = localX;
        this.localY = localY;
        this.type = type;
        sprite2D = new Sprite2D(GamePanel.originalTileSize, GamePanel.originalTileSize, 1, type.getPath());

        if (type == TileType.WALL || type == TileType.BRICK) {
            collider2D = new Collider2D(0, 0, GamePanel.tileSize, GamePanel.tileSize, this);
        }
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

    public Collider2D getCollider2D() {
        return collider2D;
    }

    public void draw(Graphics2D graphics2D) {
        if (sprite2D != null) {
            int tileSize = GamePanel.tileSize;
            BufferedImage image = sprite2D.getFrameAt(0);
            graphics2D.drawImage(image, screenX + worldX + localX, screenY + worldY + localY, tileSize, tileSize, null);
        }
    }
}
