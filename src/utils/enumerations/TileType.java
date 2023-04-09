package utils.enumerations;

import utils.Const;

public enum TileType {
    GRASS, BRICK, WALL;

    public static TileType rawValue(int rawValue) {
        if (rawValue == 0) {
            return TileType.GRASS;

        } else if (rawValue == 1) {
            return TileType.BRICK;

        } else if (rawValue == 2) {
            return TileType.WALL;
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
