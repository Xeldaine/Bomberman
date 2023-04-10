package components;

import java.awt.*;

public class Collider2D extends Rectangle {
    public Boolean isTrigger = false;
    //if isTrigger is true, then the collision is not blocking
    // and the object can pass through the area
    private Object parent;

    public Collider2D(int x, int y, int width, int height, Object parent) {
        super(x, y, width, height);
        this.parent = parent;
    }

    public Object getParent() {
        return parent;
    }
}
