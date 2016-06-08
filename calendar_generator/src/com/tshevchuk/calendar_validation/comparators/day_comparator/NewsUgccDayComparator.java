package com.tshevchuk.calendar_validation.comparators.day_comparator;

import com.tshevchuk.calendar_validation.comparators.CompareResult;
import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.calendar_validation.utils.TextUtils;

import java.util.Objects;

/**
 * Created by taras on 10.05.16.
 */
public class NewsUgccDayComparator implements DayEntryComparator {
    @Override
    public CompareResult compare(DayInfo dayInfo, CalendarDateInfo calendarDay) {
        String parsedDay = TextUtils.simplifyText(dayInfo.getDay());
        if((parsedDay == null || parsedDay.isEmpty()) &&
                (calendarDay.getDay() == null || calendarDay.getDay().isEmpty())){
            return new CompareResult(true, null, null);
        }

        if(Objects.equals(parsedDay, calendarDay.getDay())) {
            return new CompareResult(true, null, null);
        } else{
            return new CompareResult(false, parsedDay, calendarDay.getDay());
        }
    }
}
