package com.tshevchuk.calendar_validation.command_line_tasks;

import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.calendar_validation.file.InvalidFormatException;
import com.tshevchuk.calendar_validation.file.JsonFileUtils;
import com.tshevchuk.prayer.data.church_calendar.ChurchCalendar;
import com.tshevchuk.calendar_validation.utils.DateUtils;
import com.tshevchuk.calendar_validation.utils.JvmCalendarConfigReader;
import com.tshevchuk.calendar_validation.utils.TextUtils;

import java.io.IOException;
import java.util.*;

/**
 * Created by taras on 16.05.16.
 */
public class PrintDaysNewsUgccTask extends CommandLineTask {
    @Override
    public String getCommand() {
        return "--print-days-news-ugcc";
    }

    @Override
    public String getDescription() {
        return "--print-days-news-ugcc <from_year> <to_year>" +
                "\n   Вивести дні із календаря http://news.ugcc.ua/calendar/ за вказані роки";
    }

    @Override
    public void execute(String[] args) throws IOException, InvalidFormatException {
        int fromYear = Integer.valueOf(args[0]);
        int toYear = Integer.valueOf(args[1]);

        StringBuilder sb = new StringBuilder();

        printDaysForEachYear(fromYear, toYear, sb);
        printRepeatedForMonthAndDay(fromYear, toYear, sb);
        printRepeatedWhichAreShiftedFromEasterDate(fromYear, toYear, sb);


        System.out.println(sb);
    }

    private void printDaysForEachYear(int fromYear, int toYear, StringBuilder sb) throws IOException, InvalidFormatException {
        for (int year = fromYear; year <= toYear; ++year) {
            for (DayInfo di : JsonFileUtils.readDayInfosFromJson(year)) {
                String day = TextUtils.simplifyText(di.getDay());
                if (TextUtils.isEmpty(day)) {
                    continue;
                }
                sb.append(di.getDate()).append("  ").append(day).append('\n');
            }
            sb.append('\n');
        }
    }

    private void printRepeatedForMonthAndDay(int fromYear, int toYear, StringBuilder sb) throws IOException, InvalidFormatException {
        Map<String, List<String>> daysByMonthAndDayOfWeek = new TreeMap<>();
        for (int year = fromYear; year <= toYear; ++year) {
            for (DayInfo di : JsonFileUtils.readDayInfosFromJson(year)) {
                String day = TextUtils.simplifyText(di.getDay());
                if (TextUtils.isEmpty(day)) {
                    continue;
                }

                String monthAndDayOfMonth = di.getDate().substring(4);
                List<String> days = daysByMonthAndDayOfWeek.get(monthAndDayOfMonth);
                if (days == null) {
                    days = new ArrayList<>();
                    daysByMonthAndDayOfWeek.put(monthAndDayOfMonth, days);
                }
                days.add(day);
                daysByMonthAndDayOfWeek.put(monthAndDayOfMonth, days);
            }
            sb.append('\n');
        }

        sb.append("Повторювані в той самий місяць і день:\n");
        for (int occurrences = toYear - fromYear + 1; occurrences > 1; occurrences--) {
            for (int month = 1; month <= 12; ++month) {
                for (int dayOfMonth = 1; dayOfMonth <= 31; ++dayOfMonth) {
                    String monthAndDayOfWeek = String.format("%02d%02d", month, dayOfMonth);
                    List<String> days = daysByMonthAndDayOfWeek.get(monthAndDayOfWeek);
                    if (days == null) {
                        continue;
                    }
                    Set<String> checked = new HashSet<>();
                    for (String day : days) {
                        if (checked.contains(day)) {
                            continue;
                        }

                        if (Collections.frequency(days, day) == occurrences) {
                            sb.append(occurrences)
                                    .append("   ")
                                    .append(monthAndDayOfWeek)
                                    .append("   ")
                                    .append(day)
                                    .append("\n");
                        }

                        checked.add(day);
                    }
                }
            }
        }
    }

    private void printRepeatedWhichAreShiftedFromEasterDate(int fromYear, int toYear, StringBuilder sb) throws IOException, InvalidFormatException {
        Map<Integer, List<String>> daysByDaysDiff = new TreeMap<>();
        for (int year = fromYear; year <= toYear; ++year) {
            Date easterJulianDate = DateUtils.calendarDateToDate(new ChurchCalendar(new JvmCalendarConfigReader()).getEasterDateJulian(year));
            Date easterDate = DateUtils.julianDateToGregorianDate(easterJulianDate);

            for (DayInfo di : JsonFileUtils.readDayInfosFromJson(year)) {
                String day = TextUtils.simplifyText(di.getDay());
                if (TextUtils.isEmpty(day)) {
                    continue;
                }

                Date date = DateUtils.dateStrToDate(di.getDate());
                int daysDiff = DateUtils.getDaysBetween(easterDate, date);
                List<String> days = daysByDaysDiff.get(daysDiff);
                if (days == null) {
                    days = new ArrayList<>();
                    daysByDaysDiff.put(daysDiff, days);
                }
                days.add(day);
                daysByDaysDiff.put(daysDiff, days);
            }
            sb.append('\n');
        }

        sb.append("\nПовторювані в дні, зміщені на ту саму кількість днів від дати Великодня:\n");
        for (int occurrences = toYear - fromYear + 1; occurrences > 1; occurrences--) {
            for (int daysDiff = -365; daysDiff <= 365; ++daysDiff) {
                List<String> days = daysByDaysDiff.get(daysDiff);
                if (days == null) {
                    continue;
                }
                Set<String> checked = new HashSet<>();
                for (String day : days) {
                    if (checked.contains(day)) {
                        continue;
                    }

                    if (Collections.frequency(days, day) == occurrences) {
                        sb.append(occurrences)
                                .append("   ")
                                .append(daysDiff)
                                .append("   ")
                                .append(day)
                                .append("\n");
                    }

                    checked.add(day);
                }
            }
        }
    }
}
