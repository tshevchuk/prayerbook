package com.tshevchuk.calendar_validation.comparators.day_comparator;

import com.tshevchuk.calendar_validation.comparators.CompareResult;
import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.calendar_validation.utils.TextUtils;

import java.util.Objects;

/**
 * Created by taras on 10.05.16.
 */
public class NewsUgccPersonComparator implements DayEntryComparator {
    @Override
    public CompareResult compare(DayInfo dayInfo, CalendarDateInfo calendarDay) {
        String parsedPerson = TextUtils.simplifyText(dayInfo.getPerson());
        if((parsedPerson == null || parsedPerson.isEmpty()) &&
                (calendarDay.getPerson() == null || calendarDay.getPerson().isEmpty())){
            return new CompareResult(true, null, null);
        }

        if(Objects.equals(parsedPerson, calendarDay.getPerson())) {
            return new CompareResult(true, null, null);
        } else{
            return new CompareResult(false, parsedPerson, calendarDay.getPerson());
        }
    }
}
