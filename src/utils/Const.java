package utils;

import UI.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public abstract class Const {
    public static final String gameName = "Bomberman";

    // sprites
    public static final String baseSpritePath = "res/sprites";
    public static final String bombermanPath = baseSpritePath + "/bomberman.png";
    public static final String grassPath = baseSpritePath + "/grass.png";
    public static final String brickPath = baseSpritePath + "/brick.png";
    public static final String wallPath = baseSpritePath + "/wall.png";
    public static final String bombPath = baseSpritePath + "/bomb.png";
    public static final String explosionCenterPath = baseSpritePath + "/explosion_center.png";
    public static final String explosionSidePath = baseSpritePath + "/explosion_side.png";
    public static final String explosionEndPath = baseSpritePath + "/explosion_end.png";
    public static final String flanRedPath = baseSpritePath + "/flanRed.png";
    public static final String flanGreenPath = baseSpritePath + "/flanGreen.png";
    public static final String flanBluePath = baseSpritePath + "/flanBlue.png";

    // maps
    public static final String baseMapsPath = "res/maps";
    public static final String map01Path = baseMapsPath + "/map01.txt";

    // colors
    public static final Color transparentRed = new Color(1, 0, 0, 0.5f);
    public static final Color backgroundGrey = new Color(132/255f, 126/255f, 135/255f);

    // fonts
    public static final String baseFontsPath = "/fonts";
    public static String emulogicFontPath = baseFontsPath + "/Emulogic-zrEw.ttf";

    public static Font getFont(String fontName, int style, int size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Const.class.getResourceAsStream(fontName)));
            return font.deriveFont(style, size);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // values
    public static final int defaultSpeed = 2 * GamePanel.scale;

    //keys
    public static final String pclKeyArea = "area";
}
