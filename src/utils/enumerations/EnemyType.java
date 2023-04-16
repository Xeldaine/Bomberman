package utils.enumerations;

import utils.Const;
import utils.MathUtils;

public enum EnemyType {
    FLAN_RED, FLAN_GREEN, FLAN_BLUE;

    public static EnemyType generateRandomType() {
        EnemyType[] values = EnemyType.values();
        return values[MathUtils.getRandomValueBetween(0, values.length)];
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
