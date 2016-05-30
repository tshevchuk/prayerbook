package com.tshevchuk.calendar_validation.utils;

import com.tshevchuk.prayer.data.church_calendar.CalendarDate;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by taras on 10.05.16.
 */
public class DateUtils {
    public static List<String> getDatesStrOfYear(int year) {
        List<String> dates = new ArrayList<>();
        for (Date date : getDatesOfYear(year)) {
            dates.add(dateToDateStr(date));
        }
        return dates;
    }

    public static List<Date> getDatesOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        List<Date> dates = new ArrayList<>();
        for (cal.set(year, 0, 1); cal.get(Calendar.YEAR) == year; cal.add(Calendar.DAY_OF_MONTH, 1)) {
            dates.add(cal.getTime());
        }
        return dates;
    }

    public static String dateToDateStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return dateToDateStr(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }

    public static String dateToDateStr(int year, int month, int day) {
        return String.format("%04d%02d%02d", year, month, day);
    }

    public static Date dateStrToDate(String dateStr) {
        return DateTime.parse(dateStr, DateTimeFormat.forPattern("yyyyMMdd")).toDate();
    }

    public static Date calendarDateToDate(CalendarDate date) {
        return new DateTime().withYear(date.getYear())
                .withMonthOfYear(date.getMonth()).withDayOfMonth(date.getDay()).toDate();
    }

    public static Date julianDateToGregorianDate(Date julianDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(julianDate);
        cal.add(Calendar.DAY_OF_MONTH, 13);
        return cal.getTime();
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static String getDayOfWeekStr(Date date){
        DateFormat format = new SimpleDateFormat("EEE");
        return format.format(date);
    }

    public static int getDaysBetween(Date start, Date end) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        int startDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        cal.setTime(end);
        int endDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        return endDayOfYear - startDayOfYear;
    }
}
