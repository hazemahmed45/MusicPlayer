package com.example.hazem.musicplayer.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by mohamedelkhateeb on 12/11/16.
 */

public class DateUtil {

    public static String getActivityIntervalRange(long dateFrom, long dateTo) {
        int hrFrom, hrTo;

        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.setTimeInMillis(dateFrom * 1000);
        hrFrom = calendarFrom.get(Calendar.HOUR);
        int durFrom = calendarFrom.get(Calendar.AM_PM);

        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTimeInMillis(dateTo * 1000);
        hrTo = calendarTo.get(Calendar.HOUR);
        int durTo = calendarFrom.get(Calendar.AM_PM);

        return String.valueOf(hrFrom) + getDayDuration(durFrom) + " - " + String.valueOf(hrTo) + getDayDuration(durTo);
    }

    private static String getDayDuration(int dayDur) {
        if (dayDur == Calendar.AM) {
            return "ص";
        } else {
            return "م";
        }
    }

    public static String getDate(long milliseconds) /* This is your topStory.getTime()*1000 */ {
        long timestamp = milliseconds * 1000L;
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(calendar.getTime());
    }

    public static String getTime(long milliseconds) {
        long timestamp = milliseconds * 1000L;
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(calendar.getTime());
    }
}
