package com.tshevchuk.prayer.presentation.prayer;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.Navigator;

/**
 * Created by taras on 18.03.16.
 */
public class TextViewPresenter extends TextBasePresenter<TextViewView> {
    private int id;

    public TextViewPresenter(AnalyticsManager analyticsManager, Navigator navigator,
                             DataManager dataManager) {
        super(analyticsManager, navigator, dataManager);
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void attachView(TextViewView mvpView) {
        super.attachView(mvpView);
        menuItemPrayer = (MenuItemPrayer) dataManager.getMenuItem(id);
        getMvpView().setMenuItem(menuItemPrayer);
    }
}
