package org.ian.anole.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Ian
 * @date 2022/10/11
 * @desc date utils
 **/
public class DateUtils {

    public static String Y4 = "yyyy";

    public static String YMD = "yyyy-MM-dd";

    public static String YMDH = "yyyy-MM-dd-HH";

    public static String YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static String now() {
        return nowStr(YMDHMS);
    }

    public static String nowStr(String formatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.format(new Date());
    }

    public static String getTimeOffsetStr(String target, TimeUnit timeUnit, int offset, String targetFormatter, String resultFormatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(targetFormatter);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(target));
            switch (timeUnit) {
                case MICROSECONDS:
                    calendar.add(Calendar.MILLISECOND, offset);
                    break;
                case SECONDS:
                    calendar.add(Calendar.SECOND, offset);
                    break;
                case MINUTES:
                    calendar.add(Calendar.MINUTE, offset);
                    break;
                case HOURS:
                    calendar.add(Calendar.HOUR_OF_DAY, offset);
                    break;
                case DAYS:
                    calendar.add(Calendar.DAY_OF_MONTH, offset);
                    break;
            }
        } catch (ParseException e) {

        }
        SimpleDateFormat resSdf = new SimpleDateFormat(resultFormatter);
        return resSdf.format(calendar.getTime());
    }

    public static String getMinuteOffsetStr(int minuteOffset, String formatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minuteOffset);
        return sdf.format(calendar.getTime());
    }

    public static String getHourOffsetStr(int hourOffset, String formatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hourOffset);
        return sdf.format(calendar.getTime());
    }

    public static String getDayOffsetStr(int dayOffset, String formatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, dayOffset);
        return sdf.format(calendar.getTime());
    }

    public static String convertToStr(String from, String fromFormat, String toFormat) {
        SimpleDateFormat fromSdf = new SimpleDateFormat(fromFormat);
        SimpleDateFormat toSdf = new SimpleDateFormat(toFormat);
        try {
            return toSdf.format(fromSdf.parse(from));
        } catch (ParseException e) {
            return null;
        }
    }

    public static long computeDistance(String from, String to, String formatter, TimeUnit timeUnit) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        try {
            Date dateFrom = sdf.parse(from);
            Date dateTo = sdf.parse(to);
            long time = dateTo.getTime() - dateFrom.getTime();
            switch (timeUnit) {
                case SECONDS:
                    return time / 1000;
                case MINUTES:
                    return Math.round(Math.ceil(Math.abs(time / (1000 * 60.0))));
                case HOURS:
                    return Math.round(Math.ceil(Math.abs(time / (1000 * 60 * 60.0))));
                case DAYS:
                    return Math.round(Math.ceil(Math.abs(time / (1000 * 60 * 60 * 24.0))));
                default:
                    return time;
            }
        } catch (ParseException e) {
            return 0;
        }
    }

    public static boolean before(String time1, String time2, String formatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        try {
            Date d1 = sdf.parse(time1);
            Date d2 = sdf.parse(time2);
            return d1.before(d2);
        } catch (ParseException e) {

        }
        return false;
    }

    public static int compareASC(String time1, String time2, String formatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        try {
            Date d1 = sdf.parse(time1);
            Date d2 = sdf.parse(time2);
            long offset = d1.getTime() - d2.getTime();
            if (offset == 0) {
                return 0;
            }
            if (offset < 0) {
                return -1;
            }
            return 1;
        } catch (ParseException e) {
        }
        return 0;
    }

    public static int compareDESC(String time1, String time2, String formatter) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        try {
            Date d1 = sdf.parse(time1);
            Date d2 = sdf.parse(time2);
            long offset = d1.getTime() - d2.getTime();
            if (offset == 0) {
                return 0;
            }
            if (offset < 0) {
                return 1;
            }
            return -1;
        } catch (ParseException e) {
        }
        return 0;
    }

}
