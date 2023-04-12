package model;

import model.components.Area2D;
import model.components.Camera2D;
import model.components.Sprite2D;
import UI.GamePanel;
import utils.Config;
import utils.Const;
import utils.enumerations.EntityDirection;
import utils.enumerations.EntityState;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Entity {
    protected Entity parent;
    protected ArrayList<Entity> children = new ArrayList<>();
    protected int x, y; // position of the entity relative to parent (if parent == null, they represent world coordinates)
    protected int speed = Const.defaultSpeed;
    protected Sprite2D sprite2D;
    protected Area2D area2D;
    protected Boolean isCollisionEnabled = false;
    protected EntityState state = EntityState.IDLE;
    protected EntityDirection direction = EntityDirection.DOWN;

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

    public ArrayList<Entity> getChildren() {
        return children;
    }

    public void addChild(Entity child) {
        children.add(child);
    }

    public void removeChild(Entity child) {
        children.remove(child);
    }

    public void removeAllChildren() {
        children.clear();
    }

    public abstract void update();

    public void draw(Graphics2D graphics2D) {
        // draws the parent
        if (sprite2D != null) {
            int tileSize = GamePanel.tileSize;
            int section = direction.getSection();
            BufferedImage frame = sprite2D.getFrameBySection(section);

            int screenX = this.getWorldX() + Camera2D.getInstance().getOffsetX();
            int screenY = this.getWorldY() + Camera2D.getInstance().getOffsetY();

            graphics2D.drawImage(frame, screenX, screenY, tileSize, tileSize, null);

            if (area2D != null && Config.showCollisions && isCollisionEnabled) {
                graphics2D.setColor(Const.transparentRed);
                graphics2D.fillRect(screenX + area2D.x, screenY + area2D.y, area2D.width, area2D.height);
            }
        }

        // draws the children
        for (Entity child: children) {
            child.draw(graphics2D);
        }
    }
}
