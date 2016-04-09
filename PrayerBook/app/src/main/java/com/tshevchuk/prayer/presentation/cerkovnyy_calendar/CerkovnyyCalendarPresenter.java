package com.tshevchuk.prayer.presentation.cerkovnyy_calendar;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.CalendarDay;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by taras on 18.03.16.
 */
public class CerkovnyyCalendarPresenter extends BasePresenter<CerkovnyyCalendarView> {
    private final int currentYear;
    private final DataManager dataManager;
    private final AnalyticsManager analyticsManager;
    public InstanceState instanceState = new InstanceState();

    public CerkovnyyCalendarPresenter(AnalyticsManager analyticsManager, DataManager dataManager) {
        this.analyticsManager = analyticsManager;
        this.dataManager = dataManager;

        currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        if (instanceState.year == 0) {
            instanceState.year = currentYear;
        }
    }

    @Override
    public void attachView(CerkovnyyCalendarView mvpView) {
        super.attachView(mvpView);

        getMvpView().setYears(dataManager.getYears(), instanceState.year);
    }

    public void onYearSelected(int year) {
        instanceState.year = year;
        setCalendar(year);
        analyticsManager.sendActionEvent(Analytics.CAT_CERKOVNYY_CALENDAR,
                "Вибрано рік", String.valueOf(year));
    }

    private void setCalendar(int year) {
        int daysCount = new GregorianCalendar().isLeapYear(year) ? 366 : 365;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);

        ArrayList<CalendarDay> calendarDays = new ArrayList<>(daysCount);

        for (int i = 0; i < daysCount; ++i) {
            calendar.set(Calendar.DAY_OF_YEAR, i + 1);
            calendarDays.add(dataManager.getCalendarDay(calendar.getTime()));
        }

        int position = (year == currentYear) ? (java.util.Calendar
                .getInstance().get(java.util.Calendar.DAY_OF_YEAR) - 1) : 0;
        getMvpView().showCalendarForYear(instanceState.year, calendarDays, position,
                dataManager.getTextFontSizeSp());
    }

    public void onVisibleDaysChanged(int firstVisibleDay, int lastVisibleDay) {
        if (!isViewAttached()) {
            return;
        }

        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        cal1.set(java.util.Calendar.YEAR, instanceState.year);
        cal1.set(java.util.Calendar.DAY_OF_YEAR, firstVisibleDay + 1);
        java.util.Calendar cal2 = java.util.Calendar.getInstance();
        cal2.set(java.util.Calendar.YEAR, instanceState.year);
        cal2.set(java.util.Calendar.DAY_OF_YEAR, lastVisibleDay + 1);

        getMvpView().setCurrentMonths(
                cal1.get(java.util.Calendar.MONTH), cal2.get(java.util.Calendar.MONTH), instanceState.year);
    }

    @Parcel
    public static class InstanceState {
        int year;
    }
}
