package com.tshevchuk.prayer.presentation.often_used;

import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.model.CalendarDay;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by taras on 18.03.16.
 */
public class OftenUsedPresenter extends BasePresenter<OftenUsedView> {
    private final Navigator navigator;
    private final DataManager dataManager;

    @Inject
    public OftenUsedPresenter(Navigator navigator, DataManager dataManager) {
        this.navigator = navigator;
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(OftenUsedView mvpView) {
        super.attachView(mvpView);

        loadMenuItems();
        loadCalendarDay();
    }

    public void onCalendarClick() {
        navigator.showCalendar(this);
        dataManager.updateRecentlyUsedBecauseItemOpened(Catalog.ID_CALENDAR);
    }

    public void onItemClick(MenuListItemOftenUsed item) {
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
        CalendarDay day = dataManager.getCalendarDay(new Date());
        int fontSizeSp = dataManager.getTextFontSizeSp();
        getMvpView().setCalendarDay(day, fontSizeSp);
    }
}
