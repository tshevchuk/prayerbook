package com.tshevchuk.calendar_validation.command_line_tasks;

import com.tshevchuk.prayer.data.church_calendar.ChurchCalendar;
import com.tshevchuk.calendar_validation.utils.JvmCalendarConfigReader;

import java.io.IOException;

/**
 * Created by taras on 16.05.16.
 */
public class PrintEasterDatesTask extends CommandLineTask{
    @Override
    public String getCommand() {
        return "--print-easter-dates";
    }

    @Override
    public String getDescription() {
        return "--print-easter-dates <from_year> <to_year>" +
                "\n   Вивести дату Великодня в старому стилі для вказаних років" ;
    }

    @Override
    public void execute(String[] args) throws IOException {
        int fromYear = Integer.valueOf(args[0]);
        int toYear = Integer.valueOf(args[1]);

        for (int year = fromYear; year <= toYear; ++year) {
            System.out.println(new ChurchCalendar(new JvmCalendarConfigReader()).getEasterDateJulian(year));
        }
    }
}
