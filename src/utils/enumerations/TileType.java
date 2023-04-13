package utils.enumerations;

import utils.Const;

public enum TileType {
    GRASS, BRICK, WALL;

    public static TileType rawValue(int rawValue) {
        TileType[] values = TileType.values();
        if(rawValue >= 0 && rawValue < values.length) {
            return values[rawValue];
        }
        return null;
    }

    public String getPath(){
        String path = "";
        switch (this) {
            case GRASS -> path = Const.grassPath;
            case BRICK -> path = Const.brickPath;
            case WALL -> path = Const.wallPath;
        }

        return path;
    }
}
