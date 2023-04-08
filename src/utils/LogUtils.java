package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtils {
    public static String getCurrentDateString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }
}
