package com.tshevchuk.prayer.presentation.sub_menu;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by taras on 18.03.16.
 */
public class SubMenuPresenter extends BasePresenter<SubMenuView> {

    private final DataManager dataManager;
    private final Navigator navigator;
    private int subMenuId;

    @Inject
    public SubMenuPresenter(DataManager dataManager, Navigator navigator) {
        this.dataManager = dataManager;
        this.navigator = navigator;
    }

    @Override
    public void attachView(SubMenuView mvpView) {
        super.attachView(mvpView);
        loadMenuItems();
    }

    public void setId(int subMenuId) {

        this.subMenuId = subMenuId;
    }

    private void loadMenuItems() {
        List<MenuListItem> menuListItems = dataManager.getMenuListItems(subMenuId);
        boolean showOfficialUGCC = dataManager.isShowOfficialUGCC();
        if (!showOfficialUGCC) {
            for (MenuListItem item : menuListItems) {
                item.setOfficialUGCCText(false);
            }
        }
        getMvpView().setMenuItems(menuListItems);
    }

    public void onItemClick(MenuListItem menuListItem) {
        navigator.showMenuItem(this, menuListItem);
        dataManager.updateRecentlyUsedBecauseItemOpened(menuListItem.getId());
    }
}
