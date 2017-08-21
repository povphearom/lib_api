package knd.com.lib_api.utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by itphe on 12/27/2016.
 */

public abstract class DurationUtils {

    /*Static method*/
    public static String timeToString(long timeMs) {
        long s = TimeUnit.MILLISECONDS.toSeconds(timeMs);
        long hours = TimeUnit.MILLISECONDS.toHours(timeMs);

        if (hours > 0) {
            return String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
        } else {
            return String.format("%02d:%02d", (s % 3600) / 60, (s % 60));
        }
    }
}
