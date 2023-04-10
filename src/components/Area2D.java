package components;

import java.awt.*;

public class Area2D extends Rectangle {
    private Object parent;
    public Area2D(int x, int y, int width, int height, Object parent) {
        super(x, y, width, height);
        this.parent = parent;
    }

    public Object getParent() {
        return parent;
    }
}
