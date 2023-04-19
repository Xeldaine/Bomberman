package model;

import model.components.Area2D;
import model.components.Camera2D;
import model.components.Sprite2D;
import UI.GamePanel;
import utils.Config;
import utils.Const;
import utils.classes.PositionChangedBundle;
import utils.enumerations.EntityDirection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Entity implements PropertyChangeListener {
    private Entity parent;
    private final ConcurrentLinkedQueue<Entity> children = new ConcurrentLinkedQueue<>();
    protected int x, y; // position of the entity relative to parent (if parent == null, they represent world coordinates)
    protected int speed = Const.defaultSpeed;
    protected Sprite2D sprite2D;
    protected Area2D area2D;
    protected EntityDirection direction;
    public Boolean isCollisionEnabled = false;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
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

    private void setParent(Entity parent) {
        this.parent = parent;
    }

    public Entity getParent() {
        return parent;
    }

    public ConcurrentLinkedQueue<Entity> getChildren() {
        return children;
    }

    public void addChild(Entity child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(Entity child) {
        children.remove(child);
        child.setParent(null);
    }

    public void removeAllChildren() {
        for(Entity child: children) {
            removeChild(child);
        }
    }

    public ArrayList<Entity> getFamily() {
        ArrayList<Entity> family = new ArrayList<>(children);
        for (Entity child: children) {
            family.addAll(child.getFamily());
        }
        return family;
    }

    public final void updateData() {
        PositionChangedBundle oldValue = new PositionChangedBundle();
        oldValue.x = this.x;
        oldValue.y = this.y;
        oldValue.area2D = this.area2D;

        this.update();

        // sprite drawing
        if (sprite2D != null) {
            sprite2D.updateFrameCounter();
        }

        PositionChangedBundle newValue = new PositionChangedBundle();
        newValue.x = this.x;
        newValue.y = this.y;
        newValue.area2D = this.area2D;

        //collision check
        if (newValue.x != oldValue.x || newValue.y != oldValue.y) {
            GamePanel.getInstance().firePropertyChange(Const.pclKeyArea, oldValue, newValue);
        }

        for (Entity child: children) {
            child.updateData();
        }
    }

    public void destroy() {
        if (parent != null) {
            parent.removeChild(this);
        }
        for (Entity child: children) {
            this.removeChild(child);
        }
        GamePanel.getInstance().removeEntity(this);
    }

    protected abstract void update();

    public void draw(Graphics2D graphics2D) {
        // draws the parent
        if (sprite2D != null) {
            int tileSize = GamePanel.tileSize;
            BufferedImage frame = sprite2D.getCurrentFrame();

            // camera shift
            int screenX = this.getWorldX() + Camera2D.getInstance().getOffsetX();
            int screenY = this.getWorldY() + Camera2D.getInstance().getOffsetY();

            // opacity
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, sprite2D.getAlpha());
            graphics2D.setComposite(ac);

            // drawing
            graphics2D.drawImage(frame, screenX, screenY, tileSize, tileSize, null);

            // debug
            if (area2D != null && Config.visibleAreas) {
                graphics2D.setColor(Const.transparentRed);
                graphics2D.fillRect(screenX + area2D.x, screenY + area2D.y, area2D.width, area2D.height);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Const.pclKeyArea)) {
            PositionChangedBundle oldValue = (PositionChangedBundle) evt.getOldValue();
            PositionChangedBundle newValue = (PositionChangedBundle) evt.getNewValue();
            Area2D area = newValue.area2D;

            if (area != null && area2D != null && area.getEntity() != this) {
                if (isCollisionEnabled && oldValue != null) {
                    // handles collisions
                    int offsetX = newValue.x - oldValue.x;
                    int offsetY = newValue.y - oldValue.y;

                    if (area.intersectsWithOffset(area2D, offsetX, offsetY)) {
                        area.getEntity().x = oldValue.x;
                        area.getEntity().y = oldValue.y;
                        area.getEntity().onCollisionHit(area2D);
                    }

                } else if (area2D.intersectsWithOffset(area, 0, 0)) {
                    this.onAreaEntered(area);
                }
            }
        }

        // propagates the event to the children
        for (Entity child: children) {
            child.propertyChange(evt);
        }
    }

    public void onAreaEntered(Area2D area) {
        //void implementation
    }

    public void onCollisionHit(Area2D area) {
        //void implementation
    }
}
