package com.tshevchuk.calendar_validation.command_line_tasks;

import com.tshevchuk.calendar_validation.utils.JvmCalendarConfigReader;
import com.tshevchuk.prayer.data.church_calendar.ChurchCalendar;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by taras on 06.06.16.
 */
public class PrintVerifiedYearsTask extends CommandLineTask {
    @Override
    public String getCommand() {
        return "--print-verified-years";
    }

    @Override
    public String getDescription() {
        return "--print-verified-years" +
                "\n   Вивести список перевірених років в згенерованому календарі";
    }

    @Override
    public void execute(String[] args) throws IOException {
        ChurchCalendar churchCalendar = new ChurchCalendar(new JvmCalendarConfigReader());
        int[] verifiedYears = churchCalendar.getVerifiedYears();
        System.out.println(Arrays.toString(verifiedYears));
    }
}
