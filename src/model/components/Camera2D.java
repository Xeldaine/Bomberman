package model.components;

import model.Entity;
import UI.GamePanel;

public class Camera2D {
    private static Camera2D instance;
    private Entity entity;
    private final int screenX; // position in the screen
    private final int screenY; // position in the screen

    private Camera2D() {
        screenX = (GamePanel.screenWidth - GamePanel.tileSize) / 2;
        screenY = (GamePanel.screenHeight - GamePanel.tileSize) / 2;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public int getOffsetX() {
        int x = 0;
        if (entity != null) {
            x = entity.getWorldX();
        }

        return screenX - x;
    }

    public int getOffsetY() {
        int y = 0;
        if (entity != null) {
            y = entity.getWorldY();
        }

        return screenY - y;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public static Camera2D getInstance() {
        if (instance == null) {
            instance = new Camera2D();
        }
        return instance;
    }
}
