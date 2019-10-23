package com.mms.datacalculator.util;

import java.time.LocalDateTime;
import java.util.Calendar;

public class DateUtil {

    public static long convertDateToMilliSeconds(Calendar calendar){

        int minute = LocalDateTime.now().getMinute() - 1;
        int hour = LocalDateTime.now().getHour();

        long date = calendar.getTimeInMillis();
        long minuteInMillis = minute * 60 * 1000;
        long hourInMillis = hour * 60 * 60 * 1000;

        return date - (minuteInMillis + hourInMillis);
    }

    public static int calculateFreeMB (long mBytes, float todaysDataVolume){

        return (int) (todaysDataVolume - mBytes);
    }

}
