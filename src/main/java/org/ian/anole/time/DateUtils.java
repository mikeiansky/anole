package org.ian.anole.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Ian
 * @date 2022/10/11
 * @desc date utils
 **/
public class DateUtils {

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


}
