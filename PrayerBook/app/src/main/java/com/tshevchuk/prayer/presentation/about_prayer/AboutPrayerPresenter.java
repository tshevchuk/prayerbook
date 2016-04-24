package com.tshevchuk.prayer.presentation.about_prayer;

import android.app.Application;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

/**
 * Created by taras on 22.03.16.
 */
public class AboutPrayerPresenter extends BasePresenter<AboutPrayerView> {

    private final Application application;
    private MenuItemPrayer menuItemPrayer;

    public AboutPrayerPresenter(Application application, AnalyticsManager analyticsManager,
                                Navigator navigator) {
        super(analyticsManager, navigator);
        this.application = application;
    }

    void setMenuItemPrayer(MenuItemPrayer menuItemPrayer){
        this.menuItemPrayer = menuItemPrayer;
    }

    @Override
    public void attachView(AboutPrayerView mvpView) {
        super.attachView(mvpView);

        getMvpView().setPrayerName(menuItemPrayer.getName());

        String about = menuItemPrayer.getAbout();
        if (menuItemPrayer.isOfficialUGCCText()) {
            about += "<br><br>" + application.getString(R.string.about_prayer__this_is_official_text_ugcc);
        }
        getMvpView().setAboutHtml(about);
    }
}
