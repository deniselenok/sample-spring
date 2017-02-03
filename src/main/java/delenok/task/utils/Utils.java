package delenok.task.utils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class Utils {

    public static long getCurrentTimeInMs() {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        return Date.from(utc.toInstant()).getTime();
    }
}
