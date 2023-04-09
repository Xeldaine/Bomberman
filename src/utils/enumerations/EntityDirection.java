package utils.enumerations;

public enum EntityDirection {
    DOWN, UP, RIGHT, LEFT;

    public int getSection() {
        int section = -1;
        switch (this) {
            case DOWN -> section = 0;
            case UP ->  section = 1;
            case RIGHT -> section = 2;
            case LEFT -> section = 3;
        }

        return section;
    }
}
