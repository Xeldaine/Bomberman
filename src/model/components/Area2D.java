package model.components;

import model.Entity;
import java.awt.*;

public class Area2D extends Rectangle {
    private Entity entity;

    public Area2D(int x, int y, int width, int height, Entity entity) {
        super(x, y, width, height);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean intersect(Area2D area2D) {
        Rectangle rect1 = new Rectangle(entity.getWorldX() + x, entity.getWorldY() + y, width, height);
        Rectangle rect2 = new Rectangle(area2D.getEntity().getWorldX() + area2D.x, area2D.getEntity().getWorldY() + area2D.y, area2D.width, area2D.height);

        return rect1.intersects(rect2);
    }
}
