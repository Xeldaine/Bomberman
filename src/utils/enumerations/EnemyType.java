package utils.enumerations;

import utils.Const;

public enum EnemyType {
    FLAN_RED, FLAN_GREEN, FLAN_BLUE;

    public static EnemyType generateRandomType() {
        EnemyType[] values = EnemyType.values();
        return values[(int)(Math.random() * (values.length - 1))];
    }

    public String getPath() {
        switch (this) {
            case FLAN_RED -> {return Const.flanRedPath;}
            case FLAN_GREEN -> {return Const.flanGreenPath;}
            case FLAN_BLUE -> {return Const.flanBluePath;}
        }
        return null;
    }
}
