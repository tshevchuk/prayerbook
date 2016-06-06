package com.tshevchuk.prayer.presentation.settings;

import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.common.BasePresenter;

/**
 * Created by taras on 22.03.16.
 */
public class SettingsPresenter extends BasePresenter<SettingsView> {

    private final AnalyticsManager analyticsManager;
    private final Navigator navigator;

    public SettingsPresenter(AnalyticsManager analyticsManager, Navigator navigator) {
        super(analyticsManager, navigator);
        this.analyticsManager = analyticsManager;
        this.navigator = navigator;
    }

    public void onSettingsChanged(String key, String value) {
        analyticsManager.sendActionEvent(Analytics.CAT_SETTINGS_CHANGED, key, value);
    }

    public void onOpenAboutAppClick() {
        navigator.openAboutApp(this);
    }
}
