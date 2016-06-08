package com.tshevchuk.calendar_validation.command_line_tasks;

import com.tshevchuk.calendar_validation.comparators.CalendarComparator;
import com.tshevchuk.calendar_validation.comparators.day_comparator.PostyComparator;
import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.calendar_validation.file.InvalidFormatException;
import com.tshevchuk.calendar_validation.file.JsonFileUtils;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by taras on 23.05.16.
 */
public class CompareNewsUgccPostyTask extends CommandLineTask {
    @Override
    public String getCommand() {
        return "--compare-news-ugcc-posty";
    }

    @Override
    public String getDescription() {
        return "--compare-news-ugcc-posty <from_year> <to_year>" +
                "\n   Порівняти пости та загальниці у згенерованому календарі і в календарі http://news.ugcc.ua/calendar/ , збереженому в json";
    }

    @Override
    public void execute(String[] args) throws IOException, InvalidFormatException {
        int fromYear = Integer.valueOf(args[0]);
        int toYear = Integer.valueOf(args[1]);

        Map<String, DayInfo> daysByDate = new TreeMap<>();
        for (int year = fromYear; year <= toYear; ++year) {
            for (DayInfo day : JsonFileUtils.readDayInfosFromJson(year)) {
                daysByDate.put(day.getDate(), day);
            }
        }

        Map<String, CalendarDateInfo> generatedCalendar = generateCalendar(fromYear, toYear);
        String res = new CalendarComparator(new PostyComparator())
                .compare(fromYear, toYear, daysByDate, generatedCalendar);
        System.out.println(res);
    }
}
