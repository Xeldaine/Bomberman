package entities;


import entities.components.SpriteManager;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

import java.awt.*;

public abstract class Entity {
    int x, y; // position of the entity (in pixels)
    int speed = 4; //default value for speed

    SpriteManager spriteManager;
    EntityState state = EntityState.IDLE;
    EntityDirection direction = EntityDirection.DOWN;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public EntityState getState() {
        return state;
    }

    public EntityDirection getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
