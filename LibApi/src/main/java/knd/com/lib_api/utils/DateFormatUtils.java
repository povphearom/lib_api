package knd.com.lib_api.utils;

import android.text.format.DateUtils;

import com.afinos.api.application.MyApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Copy Right 2012-2016, Afinos, Inc., or its affiliates
 * @Author: Afinos Team
 **/
public class DateFormatUtils {
    public static String formatDate(long millisecond) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM hh:mm:ss a");
            return format.format(new Date(millisecond));
        } catch (Exception e) {
            return "Unknown date";
        }
    }
    public static String formatDates(long millisecond) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM");
            return format.format(new Date(millisecond));
        } catch (Exception e) {
            return "Unknown date";
        }
    }

    public static boolean compare(String startDate, String endDate) {
        boolean before = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        try {
            before = sdf.parse(startDate).after(sdf.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
            before = false;
        }
        return before;
    }

    public static long convertCurrentDate(long groupActive) {
        long currentMil = groupActive - MyApp.init().getTimeOffSet();
        return currentMil;
    }

    public static String convertDate(long groupActive) {
        long now = System.currentTimeMillis();
        long nows = now - groupActive;
        if (nows < 60000) {
            return "now";
        }
        return DateUtils.getRelativeTimeSpanString(groupActive, now, DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
    }

    public static String bytesIntoHumanReadable(long bytes) {
        long kilobyte = 1024;
        long megabyte = kilobyte * 1024;
        long gigabyte = megabyte * 1024;
        long terabyte = gigabyte * 1024;

        if ((bytes >= 0) && (bytes < kilobyte)) {
            return bytes + " B";

        } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
            return (bytes / kilobyte) + " KB";

        } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
            return (bytes / megabyte) + " MB";

        } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
            return (bytes / gigabyte) + " GB";

        } else if (bytes >= terabyte) {
            return (bytes / terabyte) + " TB";

        } else {
            return bytes + " Bytes";
        }
    }

    public static String convertDuration(long duration) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);

        if (minutes >= 1) {
            return String.format("%2d:%02d", 1, 0);
        }
        return String.format("%2d:%02d", minutes, seconds % 60);
    }

    public static long getMillis(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        try {
            Date date = df.parse(formattedDate);
            long millisecond = date.getTime();
            return millisecond;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
