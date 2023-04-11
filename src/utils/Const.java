package utils;

import java.awt.*;

public abstract class Const {
    public static final String baseSpritePath = "res/sprites";
    public static final String baseMapsPath = "res/maps";

    // sprites
    public static final String bombermanPath = baseSpritePath + "/bomberman.png";
    public static final String grassPath = baseSpritePath + "/grass.png";
    public static final String brickPath = baseSpritePath + "/brick.png";
    public static final String wallPath = baseSpritePath + "/wall.png";

    // maps
    public static final String map01Path = baseMapsPath + "/map01.txt";

    // colors
    public static final Color transparentRed = new Color(1, 0, 0, 0.5f);
}
