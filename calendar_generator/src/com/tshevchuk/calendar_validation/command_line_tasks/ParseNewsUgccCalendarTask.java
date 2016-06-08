package com.tshevchuk.calendar_validation.command_line_tasks;

import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.calendar_validation.file.NewsUgccJsonExport;
import com.tshevchuk.calendar_validation.parsers.NewsUgccParser;
import com.tshevchuk.calendar_validation.parsers.Parser;
import com.tshevchuk.calendar_validation.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras on 16.05.16.
 */
public class ParseNewsUgccCalendarTask extends CommandLineTask {
    @Override
    public String getCommand() {
        return "--parse-news-ugcc";
    }

    @Override
    public String getDescription() {
        return "--parse-news-ugcc <from_year> <to_year> " +
                "\n   Розпарсати календар http://news.ugcc.ua/calendar/ за вказані роки, зберегти в файли в форматі json";
    }

    @Override
    public void execute(String[] args) throws IOException {
        int fromYear = Integer.valueOf(args[0]);
        int toYear = Integer.valueOf(args[1]);

        Parser parser = new NewsUgccParser();
        for (int year = fromYear; year <= toYear; ++year) {
            List<DayInfo> days = new ArrayList<>();
            for (int i = 1; i <= 12; ++i) {
                days.addAll(parser.parse(year, i));
            }
            String json = new NewsUgccJsonExport().calendarToJson(days);
            FileUtils.writeFile("news_ugcc_calendar_" + year + ".json", json);
            System.out.println(json);
        }
    }
}
