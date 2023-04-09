package entities;


import entities.components.SpriteManager;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

import java.awt.*;

public abstract class Entity {
    int x, y; // position of the entity (in pixels)
    int speed;
    SpriteManager spriteManager;
    EntityState state = EntityState.IDLE;
    EntityDirection direction = EntityDirection.DOWN;

    public Entity(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public EntityState getState() {
        return state;
    }

    public EntityDirection getDirection() {
        return direction;
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
