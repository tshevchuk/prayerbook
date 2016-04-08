package com.tshevchuk.prayer.presentation.prayer;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.base.BaseView;

/**
 * Created by taras on 03.04.16.
 */
public class TextBasePresenter<T extends BaseView> extends BasePresenter<T> {
    protected final AnalyticsManager analyticsManager;
    protected final Navigator navigator;
    protected final DataManager dataManager;
    protected MenuItemPrayer menuItemPrayer;

    public TextBasePresenter(AnalyticsManager analyticsManager,
                             Navigator navigator, DataManager dataManager) {
        this.analyticsManager = analyticsManager;
        this.navigator = navigator;
        this.dataManager = dataManager;
    }

    public void onOpenAboutClick() {
        navigator.showAboutPrayer(this, menuItemPrayer);
        analyticsManager.sendActionEvent(Analytics.CAT_OPTIONS_MENU, "Опис",
                String.format("#%d %s", menuItemPrayer.getId(), menuItemPrayer.getName()));
    }

    public boolean onUpClick() {
        int parentId = menuItemPrayer.getParentItemId();
        if (parentId > 0) {
            navigator.close(this);
            navigator.showSubMenu(this, parentId, dataManager.getMenuItem(parentId).getName());
            return true;
        }
        return false;
    }
}
