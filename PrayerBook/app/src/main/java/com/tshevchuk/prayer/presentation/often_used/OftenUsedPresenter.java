package com.tshevchuk.prayer.presentation.often_used;

import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import java.util.List;

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
    }

    public void onCalendarClick() {
        navigator.showCalendar(this);
        dataManager.updateRecentlyUsedBecauseItemOpened(Catalog.ID_CALENDAR);
    }

    public void onItemClick(MenuListItemOftenUsed item) {
        //todo: implement
        //((HomeActivity) getActivity()).displayMenuItem(oftenUsedItems.get(position - 1));
        //todo: add update of recently used
    }

    private void loadMenuItems() {
        List<MenuListItemOftenUsed> menuListItems = dataManager.getOftenUsedMenuItems();
        boolean showOfficialUGCC = dataManager.isShowOfficialUGCC();
        if (!showOfficialUGCC) {
            for (MenuListItemOftenUsed item : menuListItems) {
                item.setOfficialUGCCText(false);
            }
        }
        getMvpView().setMenuItems(menuListItems);
    }
}
