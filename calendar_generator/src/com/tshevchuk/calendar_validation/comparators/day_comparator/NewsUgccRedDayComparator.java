package com.tshevchuk.calendar_validation.comparators.day_comparator;

import com.tshevchuk.calendar_validation.comparators.CompareResult;
import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;

/**
 * Created by taras on 10.05.16.
 */
public class NewsUgccRedDayComparator implements DayEntryComparator {
    @Override
    public CompareResult compare(DayInfo dayInfo, CalendarDateInfo calendarDay) {
        if(dayInfo.isRedDay() == calendarDay.isDateRed()) {
            return new CompareResult(true, null, null);
        } else{
            return new CompareResult(false, String.valueOf(dayInfo.isRedDay()), String.valueOf(calendarDay.isDateRed()));
        }
    }
}
