package entities;

import components.Area2D;
import components.Collider2D;
import components.Sprite2D;
import graphics.GamePanel;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

import java.awt.*;

public abstract class Entity {
    protected int worldX, worldY; // position of the entity (in pixels)
    protected int speed = 2 * GamePanel.scale; //default value for speed
    protected Sprite2D sprite2D;
    protected Area2D area2D;
    protected Collider2D collider2D;
    protected EntityState state = EntityState.IDLE;
    protected EntityDirection direction = EntityDirection.DOWN;

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

    public void setSprite2D(Sprite2D sprite2D) {
        this.sprite2D = sprite2D;
    }

    public Sprite2D getSprite2D() {
        return sprite2D;
    }

    public Collider2D getCollider2D() {
        return collider2D;
    }

    public void setCollider2D(Collider2D collider2D) {
        this.collider2D = collider2D;
    }

    public Area2D getArea2D() {
        return area2D;
    }

    public void setArea2D(Area2D area2D) {
        this.area2D = area2D;
    }

    public abstract void update();

    public abstract void draw(Graphics2D graphics2D);

    public abstract void onCollisionEntered(Collider2D collider2D);
}
