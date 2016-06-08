package com.tshevchuk.prayer.presentation.cerkovnyy_calendar;

import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.presentation.AsyncTaskManager;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.common.BasePresenter;

import org.json.JSONException;
import org.parceler.Parcel;

import java.io.IOException;
import java.util.ArrayList;

import hugo.weaving.DebugLog;

/**
 * Created by taras on 18.03.16.
 */
public class CerkovnyyCalendarPresenter extends BasePresenter<CerkovnyyCalendarView> {
    private static final int PROGRESS_ID_LOAD_CALENDAR = 0;
    private final int currentYear;
    private final DataManager dataManager;
    private final AsyncTaskManager asyncTaskManager;
    public InstanceState instanceState = new InstanceState();

    public CerkovnyyCalendarPresenter(AnalyticsManager analyticsManager, DataManager dataManager,
                                      Navigator navigator, AsyncTaskManager asyncTaskManager) {
        super(analyticsManager, navigator);
        this.dataManager = dataManager;
        this.asyncTaskManager = asyncTaskManager;

        currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        if (instanceState.year == 0) {
            instanceState.year = currentYear;
        }
    }

    @Override
    public void attachView(CerkovnyyCalendarView mvpView) {
        super.attachView(mvpView);

        try {
            getMvpView().setYears(dataManager.getYears(), instanceState.year);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        asyncTaskManager.cancelAll();
    }

    public void onYearSelected(int year) {
        instanceState.year = year;
        setCalendar(year);
        analyticsManager.sendActionEvent(Analytics.CAT_CERKOVNYY_CALENDAR,
                "Вибрано рік", String.valueOf(year));
    }

    private void setCalendar(final int year) {
        showProgress(PROGRESS_ID_LOAD_CALENDAR);
        asyncTaskManager.executeTask(new AsyncTaskManager.BackgroundTask<Void>() {
            private ArrayList<CalendarDateInfo> resultCalendarDays;
            private boolean isVerifiedYear = false;

            @DebugLog
            @Override
            public Void doInBackground() throws Exception {
                resultCalendarDays = dataManager.getCalendarDays(year);
                int[] verifiedYears = dataManager.getVerifiedYears();
                for (int i = verifiedYears.length - 1; i >= 0; i--) {
                    if (verifiedYears[i] == year) {
                        isVerifiedYear = true;
                        break;
                    }
                }
                return null;
            }

            @Override
            public void postExecute(Void result) {
                int position = (year == currentYear) ? (java.util.Calendar
                        .getInstance().get(java.util.Calendar.DAY_OF_YEAR) - 1) : -1;
                getMvpView().showCalendarForYear(instanceState.year, resultCalendarDays, position,
                        dataManager.getTextFontSizeSp());
                hideProgress(PROGRESS_ID_LOAD_CALENDAR);
                if (!isVerifiedYear) {
                    getMvpView().showCalendarNotVerifiedWarning(year);
                }
            }

            @Override
            public void onError(Throwable tr) {
                hideProgress(PROGRESS_ID_LOAD_CALENDAR);
            }
        });
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

    public void onCreateShortcutClick() {
        MenuItemBase mi = dataManager.getMenuItem(Catalog.ID_CALENDAR);
        handleCreateShortcutClick(mi);
    }

    public boolean onUpButtonPress() {
        navigator.close(this);
        navigator.showMenuItem(this, dataManager.getMenuListItem(Catalog.ID_RECENT_SCREENS));
        return true;
    }

    public void onPostyZahalnytsiClick() {
        navigator.showMenuItem(this, dataManager.getMenuListItem(Catalog.ID_POSTY_ZAHALNYTSI));
    }

    public void onAboutCalendarClick(){
        navigator.openAboutCalendar(this);
    }

    @Parcel
    public static class InstanceState {
        int year;
    }
}
