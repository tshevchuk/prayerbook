package com.tshevchuk.prayer.presentation.often_used;

import android.text.TextUtils;

import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.presentation.AsyncTaskManager;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.common.BasePresenter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by taras on 18.03.16.
 */
public class OftenUsedPresenter extends BasePresenter<OftenUsedView> {
    private final Navigator navigator;
    private final DataManager dataManager;
    private final AnalyticsManager analyticsManager;
    private final AsyncTaskManager asyncTaskManager;

    @Inject
    public OftenUsedPresenter(Navigator navigator, DataManager dataManager, AnalyticsManager analyticsManager, AsyncTaskManager asyncTaskManager) {
        super(analyticsManager, navigator);
        this.navigator = navigator;
        this.dataManager = dataManager;
        this.analyticsManager = analyticsManager;
        this.asyncTaskManager = asyncTaskManager;
    }

    @Override
    public void attachView(OftenUsedView mvpView) {
        super.attachView(mvpView);

        loadMenuItems();
        loadCalendarDay();
    }

    @Override
    public void detachView() {
        super.detachView();
        asyncTaskManager.cancelAll();
    }

    void onCalendarClick() {
        navigator.showCalendar(this);
        dataManager.updateRecentlyUsedBecauseItemOpened(Catalog.ID_CALENDAR);
    }

    void onItemClick(MenuListItemOftenUsed item) {
        navigator.showMenuItem(this, item);
        dataManager.updateRecentlyUsedBecauseItemOpened(item.getId());
    }

    private void loadMenuItems() {
        ArrayList<MenuListItemOftenUsed> menuListItems = dataManager.getOftenUsedMenuItems();
        boolean showOfficialUGCC = dataManager.isShowOfficialUGCC();
        if (!showOfficialUGCC) {
            for (MenuListItemOftenUsed item : menuListItems) {
                item.setOfficialUGCCText(false);
            }
        }
        getMvpView().setMenuItems(menuListItems);
    }

    private void loadCalendarDay() {
        getMvpView().showCalendarProgressBar(true);
        asyncTaskManager.executeTask(new AsyncTaskManager.BackgroundTask<CalendarDateInfo>() {
            @Override
            public CalendarDateInfo doInBackground() throws IOException, JSONException {
                return dataManager.getCalendarDay(new Date());
            }

            @Override
            public void postExecute(CalendarDateInfo result) {
                int fontSizeSp = dataManager.getTextFontSizeSp();
                getMvpView().setCalendarDay(result, fontSizeSp);
                getMvpView().showCalendarProgressBar(false);
            }

            @Override
            public void onError(Throwable tr) {
                getMvpView().showCalendarProgressBar(false);
            }
        });
    }

    void onSearchSubmit(String query) {
        navigator.showSearchScreen(this, query);
        analyticsManager.sendActionEvent(Analytics.CAT_SEARCH, "Підтверджено пошукову фразу", query);
    }

    void onSearchQueryTextChange(String newText) {
        getMvpView().showSearchSuggestions(dataManager.searchMenuItems(newText));
        if (!TextUtils.isEmpty(newText)) {
            analyticsManager.sendActionEvent(Analytics.CAT_SEARCH,
                    "Пошук із випадаючим списком підказок", newText);
        }
    }

    void onSearchSuggestionClick(MenuListItemSearch mi) {
        navigator.showMenuItem(this, mi);
        dataManager.updateRecentlyUsedBecauseItemOpened(mi.getId());
        analyticsManager.sendActionEvent(Analytics.CAT_SEARCH,
                "Вибрано випадаючу підказку", mi.getId() + " " + mi.getName());
    }

    public void onCreateShortcutClick() {
        MenuItemBase mi = dataManager.getMenuItem(Catalog.ID_RECENT_SCREENS);
        handleCreateShortcutClick(mi);
    }
}
