package com.tshevchuk.calendar_validation.command_line_tasks;

import com.tshevchuk.calendar_validation.comparators.CalendarComparator;
import com.tshevchuk.calendar_validation.comparators.day_comparator.PostyComparator;
import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.calendar_validation.file.InvalidFormatException;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.calendar_validation.parsers.DyvensvitParser;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by taras on 23.05.16.
 */
public class CompareDyvensvitPostyTask extends CommandLineTask {
    @Override
    public String getCommand() {
        return "--compare-dyvensvit-posty";
    }

    @Override
    public String getDescription() {
        return "--compare-dyvensvit-posty <from_year> <to_year>" +
                "\n   Порівняти пости та загальниці у згенерованому календарі і в календарі Дивенсвіт, дані якого є в папці ./dyvensvit";
    }

    @Override
    public void execute(String[] args) throws IOException, InvalidFormatException {
        int fromYear = Integer.valueOf(args[0]);
        int toYear = Integer.valueOf(args[1]);

        Map<String, DayInfo> daysByDate = new TreeMap<>();
        for (int year = fromYear; year <= toYear; ++year) {
            for (int month = 1; month <= 12; ++month) {
                for (DayInfo day : new DyvensvitParser().parse(year, month)) {
                    daysByDate.put(day.getDate(), day);
                }
            }
        }

        Map<String, CalendarDateInfo> generatedCalendar = generateCalendar(fromYear, toYear);
        String res = new CalendarComparator(new PostyComparator())
                .compare(fromYear, toYear, daysByDate, generatedCalendar);
        System.out.println(res);
    }
}
