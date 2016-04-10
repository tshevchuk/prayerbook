package com.tshevchuk.prayer.presentation.navigation_drawer;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import java.util.ArrayList;

/**
 * Created by taras on 09.04.16.
 */
public class NavigationDrawerPresenter extends BasePresenter<NavigationDrawerView> {
    private final DataManager dataManager;
    private final Navigator navigator;

    public NavigationDrawerPresenter(DataManager dataManager, Navigator navigator) {
        this.dataManager = dataManager;
        this.navigator = navigator;
    }

    @Override
    public void attachView(NavigationDrawerView mvpView) {
        super.attachView(mvpView);

        ArrayList<MenuListItem> items = dataManager.getTopMenuListItems();
        getMvpView().setListItems(items);
    }

    public void onListItemClick(MenuListItem menuListItem) {
        navigator.clearBackStack(this);
        navigator.showMenuItem(this, menuListItem);
        dataManager.updateRecentlyUsedBecauseItemOpened(menuListItem.getId());
    }
}
