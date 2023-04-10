package components;

import entities.Entity;
import graphics.GamePanel;

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