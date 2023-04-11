package entities;

import components.Area2D;
import components.Sprite2D;
import graphics.GamePanel;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;

import java.awt.*;

public abstract class Entity {
    protected int x, y; // position of the entity relative to parent (if parent == null, they represents world coordinates)
    protected int screenX, screenY; // position of the entity in the screen
    protected int speed = 2 * GamePanel.scale; //default value for speed
    protected Sprite2D sprite2D;
    protected Area2D area2D;
    protected Boolean isCollisionEnabled = false;
    protected EntityState state = EntityState.IDLE;
    protected EntityDirection direction = EntityDirection.DOWN;
    protected Entity parent;

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

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getWorldX() {
        int parentX = parent != null ? parent.getWorldX() : 0;
        return parentX + this.x;
    }

    public int getWorldY() {
        int parentY = parent != null ? parent.getWorldY() : 0;
        return parentY + this.y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Boolean isCollisionEnabled() {
        return isCollisionEnabled;
    }

    public void setCollisionEnabled(Boolean collisionEnabled) {
        isCollisionEnabled = collisionEnabled;
    }

    public void setSprite2D(Sprite2D sprite2D) {
        this.sprite2D = sprite2D;
    }

    public Sprite2D getSprite2D() {
        return sprite2D;
    }

    public Area2D getArea2D() {
        return area2D;
    }

    public void setArea2D(Area2D area2D) {
        this.area2D = area2D;
    }

    public void setParent(Entity parent) {
        this.parent = parent;
    }

    public Entity getParent() {
        return parent;
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

    public abstract void update();

    public abstract void draw(Graphics2D graphics2D);
}
