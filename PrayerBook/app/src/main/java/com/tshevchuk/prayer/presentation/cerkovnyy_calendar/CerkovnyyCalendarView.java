package com.tshevchuk.prayer.presentation.cerkovnyy_calendar;

import com.tshevchuk.prayer.domain.model.CalendarDay;
import com.tshevchuk.prayer.presentation.base.BaseView;

import java.util.ArrayList;

/**
 * Created by taras on 26.03.16.
 */
public interface CerkovnyyCalendarView extends BaseView {
    void showCalendarForYear(int year, ArrayList<CalendarDay> calendarDays,
                             int positionOfToday, int fontSizeSp);

    void setCurrentMonths(int monthFrom, int monthTo, int year);

    void setYears(int[] years, int currentYear);
}
