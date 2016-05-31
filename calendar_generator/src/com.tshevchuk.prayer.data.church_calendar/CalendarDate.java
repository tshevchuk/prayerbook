package com.tshevchuk.prayer.data.church_calendar;

/**
 * Created by taras on 16.05.16.
 */
public class CalendarDate {
    private final int year;
    private final int month;
    private final int day;

    public CalendarDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "CalendarDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
