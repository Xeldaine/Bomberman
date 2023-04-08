package entities;

import java.awt.*;

public abstract class Entity {
    public int x, y; // position of the entity (in pixels)
    public int speed;

    public Entity(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public abstract void update();

    public abstract void draw(Graphics2D graphics2D);
}
