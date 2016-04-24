package com.tshevchuk.prayer.data.repositories;

import com.tshevchuk.prayer.data.CerkovnyyCalendar;
import com.tshevchuk.prayer.domain.model.CalendarDay;

import java.util.Date;

/**
 * Created by taras on 31.03.16.
 */
public class CerkovnyyCalendarRepository {
    private final CerkovnyyCalendar cerkovnyyCalendar;

    public CerkovnyyCalendarRepository(CerkovnyyCalendar cerkovnyyCalendar) {
        this.cerkovnyyCalendar = cerkovnyyCalendar;
    }

    public CalendarDay getCalendarDay(Date date) {
        return cerkovnyyCalendar.getCalendarDay(date);
    }

    public int[] getYears() {
        return cerkovnyyCalendar.getYears();
    }
}
