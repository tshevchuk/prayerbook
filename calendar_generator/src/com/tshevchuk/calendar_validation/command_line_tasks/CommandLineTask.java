package com.tshevchuk.calendar_validation.command_line_tasks;

import com.tshevchuk.calendar_validation.file.InvalidFormatException;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.data.church_calendar.ChurchCalendar;
import com.tshevchuk.calendar_validation.utils.DateUtils;
import com.tshevchuk.calendar_validation.utils.JvmCalendarConfigReader;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by taras on 23.05.16.
 */
public abstract class CommandLineTask {
    public abstract String getCommand();

    public abstract String getDescription();

    public abstract void execute(String[] args) throws IOException, InvalidFormatException;

    protected Map<String, CalendarDateInfo> generateCalendar(int fromYear, int toYear) throws IOException {
        ChurchCalendar cc = new ChurchCalendar(new JvmCalendarConfigReader());
        Map<String, CalendarDateInfo> days = new TreeMap<>();
        for (int year = fromYear; year <= toYear; year++) {
            for (Date date : DateUtils.getDatesOfYear(year)) {
                days.put(DateUtils.dateToDateStr(date), cc.getCalendarDay(date));
            }
        }
        return days;
    }

}
