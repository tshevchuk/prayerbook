package com.tshevchuk.calendar_validation.command_line_tasks;

import com.google.gson.stream.JsonWriter;
import com.tshevchuk.calendar_validation.utils.DateUtils;
import com.tshevchuk.calendar_validation.utils.FileUtils;
import com.tshevchuk.calendar_validation.utils.JvmCalendarConfigReader;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.data.church_calendar.ChurchCalendar;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by taras on 16.05.16.
 */
public class CompareCalendarGenerationByDayAndByDaysTask extends CommandLineTask {
    @Override
    public String getCommand() {
        return "--compare-calendars-generated-by-day-and-by-days";
    }

    @Override
    public String getDescription() {
        return "--compare-calendars-generated-by-day-and-by-days" +
                "\n   Порівняти календарі згереровані за допомогою методу getCalendarDays(year) і методу getCalendarDay(date)";
    }

    @Override
    public void execute(String[] args) throws IOException {
        ChurchCalendar cc = new ChurchCalendar(new JvmCalendarConfigReader());
        for (int year : cc.getCalendarYears()) {

            List<Date> datesOfYear = DateUtils.getDatesOfYear(year);
            List<CalendarDateInfo> days = cc.getCalendarDays(year);

            if(datesOfYear.size() != days.size()){
                throw new IllegalStateException("Wrong sizes for year " + year);
            }

            for(int i = 0; i < datesOfYear.size(); ++i){
                CalendarDateInfo d1 = days.get(i);
                CalendarDateInfo d2 = cc.getCalendarDay(datesOfYear.get(i));
                if(!d1.equals(d2)){
                    throw new IllegalStateException("Wrong values for date " + datesOfYear.get(i));
                }
            }

            System.out.println("Checked for year " + year);
        }
    }
}
