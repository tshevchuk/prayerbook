package com.tshevchuk.calendar_validation.comparators;

import com.tshevchuk.calendar_validation.comparators.day_comparator.DayEntryComparator;
import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.calendar_validation.utils.DateUtils;
import com.tshevchuk.calendar_validation.utils.TextUtils;

import java.util.Map;

/**
 * Created by taras on 09.05.16.
 */
public class CalendarComparator {
    private final DayEntryComparator dayEntryComparator;

    public CalendarComparator(DayEntryComparator dayEntryComparator) {
        this.dayEntryComparator = dayEntryComparator;
    }

    public String compare(int fromYear, int toYear, Map<String, DayInfo> daysInfo, Map<String,
            CalendarDateInfo> calendarDays) {
        final int yearsCount = toYear - fromYear + 1;
        StringBuilder[] resultsByDiffCount = new StringBuilder[yearsCount];

        int count = 0;
        for (int month = 12; month >= 1; --month) {
            for (int day = 31; day >= 1; --day) {
                StringBuilder sb = new StringBuilder();
                int countForThisYear = 0;
                for (int year = toYear; year >= fromYear; --year) {
                    String date = DateUtils.dateToDateStr(year, month, day);
                    DayInfo dayInfo = daysInfo.get(date);
                    CalendarDateInfo calendarDay = calendarDays.get(date);
                    if (dayInfo == null && calendarDay == null) {
                        continue;
                    }
                    CompareResult result = dayEntryComparator.compare(dayInfo, calendarDay);
                    if (!result.isEqual()) {
                        sb.append(date)
                                .append(" ")
                                .append(DateUtils.getDayOfWeekStr(calendarDay.getDate()))
                                .append("   (")
                                .append(DateUtils.dateToDateStr(calendarDay.getDateJulian()))
                                .append(", ")
                                .append(calendarDay.getShiftDaysFromEaster())
                                .append(")")
                                .append("    ")
                                .append(dayInfo == null ? "null" : TextUtils.simplifyText(dayInfo.getDay()))
                                .append(";    ")
                                .append(dayInfo == null ? "null" : TextUtils.simplifyText(dayInfo.getPerson()));
                        sb.append("\n     Expected: ").append(result.getExpected());
                        sb.append("\n     Actual:   ").append(result.getActual());
                        sb.append("\n");
                        count++;
                        countForThisYear++;
                    }
                }
                if (countForThisYear > 0) {
                    if (resultsByDiffCount[countForThisYear - 1] == null) {
                        resultsByDiffCount[countForThisYear - 1] = new StringBuilder();
                    }
                    resultsByDiffCount[countForThisYear - 1].append(sb.toString());
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (StringBuilder res : resultsByDiffCount) {
            sb.append(res).append("\n");
        }

        sb.append("\nНеобхідно виправити ").append(count).append(" елементів");
        return sb.toString();
    }
}
