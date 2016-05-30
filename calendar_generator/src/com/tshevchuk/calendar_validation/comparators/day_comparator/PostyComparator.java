package com.tshevchuk.calendar_validation.comparators.day_comparator;

import com.tshevchuk.calendar_validation.comparators.CompareResult;
import com.tshevchuk.calendar_validation.domain.DayInfo;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.calendar_validation.utils.TextUtils;

/**
 * Created by taras on 10.05.16.
 */
public class PostyComparator implements DayEntryComparator {
    @Override
    public CompareResult compare(DayInfo dayInfo, CalendarDateInfo calendarDay) {
        if(dayInfo.getPist() == DayInfo.PIST_NONE && TextUtils.isEmpty(calendarDay.getPistType())
                || dayInfo.getPist() == DayInfo.PIST_PIST && CalendarDateInfo.PIST_PIST.equals(calendarDay.getPistType())
                || dayInfo.getPist() == DayInfo.PIST_STROHYY_PIST && CalendarDateInfo.PIST_STROHYY.equals(calendarDay.getPistType())
                || dayInfo.getPist() == DayInfo.PIST_ZAHALNYTSYA && CalendarDateInfo.PIST_ZAHALNYTSYA.equals(calendarDay.getPistType())) {
            return new CompareResult(true, null, null);
        } else{
            return new CompareResult(false, dayInfo.getPistStr(), calendarDay.getPistType());
        }
    }
}
