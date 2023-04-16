package utils;

public abstract class MathUtils {

    public static int getRandomValueBetween(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min) + min);
    }

    public static int clamp(int min, int max, int val) {
        return Math.max(min, Math.min(max, val));
    }
}
