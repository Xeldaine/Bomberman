package entities;

import components.SpriteManager;
import graphics.GamePanel;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

import java.awt.*;
import java.beans.PropertyChangeEvent;

public abstract class Entity {
    int worldX, worldY; // position of the entity (in pixels)
    int speed = 2 * GamePanel.scale; //default value for speed

    SpriteManager spriteManager;
    EntityState state = EntityState.IDLE;
    EntityDirection direction = EntityDirection.DOWN;

    public Entity(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public EntityState getState() {
        return state;
    }

    public EntityDirection getDirection() {
        return direction;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSpriteManager(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public abstract void update();

    public abstract void draw(Graphics2D graphics2D);
}
