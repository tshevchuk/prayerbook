package com.tshevchuk.calendar_validation.comparators.day_comparator;

import com.tshevchuk.calendar_validation.comparators.CompareResult;
import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;

/**
 * Created by taras on 09.05.16.
 */
public interface DayEntryComparator {
    CompareResult compare(DayInfo dayInfo, CalendarDateInfo calendarDay);
}
