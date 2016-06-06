package com.tshevchuk.prayer.data.repositories;

import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.data.church_calendar.ChurchCalendar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by taras on 31.03.16.
 */
public class ChurchCalendarRepository {
    private final ChurchCalendar churchCalendar;

    public ChurchCalendarRepository(ChurchCalendar churchCalendar) {
        this.churchCalendar = churchCalendar;
    }

    public CalendarDateInfo getCalendarDay(Date date) throws IOException, JSONException {
        return churchCalendar.getCalendarDay(date);
    }

    public ArrayList<CalendarDateInfo> getCalendarDays(int year) throws IOException, JSONException {
        return churchCalendar.getCalendarDays(year);
    }

    public int[] getYears() throws IOException, JSONException {
        return churchCalendar.getCalendarYears();
    }
}
