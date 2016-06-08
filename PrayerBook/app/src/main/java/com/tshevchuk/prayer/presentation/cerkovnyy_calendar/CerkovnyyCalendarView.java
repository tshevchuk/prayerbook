package com.tshevchuk.prayer.presentation.cerkovnyy_calendar;

import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.presentation.common.BaseView;

import java.util.ArrayList;

/**
 * Created by taras on 26.03.16.
 */
public interface CerkovnyyCalendarView extends BaseView {
    void showCalendarForYear(int year, ArrayList<CalendarDateInfo> calendarDays,
                             int positionOfToday, int fontSizeSp);

    void setCurrentMonths(int monthFrom, int monthTo, int year);

    void setYears(int[] years, int currentYear);

    void showCalendarNotVerifiedWarning(int year);
}
