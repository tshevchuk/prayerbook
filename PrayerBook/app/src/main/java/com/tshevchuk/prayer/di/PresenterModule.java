package com.tshevchuk.prayer.di;

import android.app.Application;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.AsyncTaskManager;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.about_app.AboutAppPresenter;
import com.tshevchuk.prayer.presentation.about_prayer.AboutPrayerPresenter;
import com.tshevchuk.prayer.presentation.cerkovnyy_calendar.CerkovnyyCalendarPresenter;
import com.tshevchuk.prayer.presentation.often_used.OftenUsedPresenter;
import com.tshevchuk.prayer.presentation.prayer.HtmlViewPresenter;
import com.tshevchuk.prayer.presentation.prayer.TextViewPresenter;
import com.tshevchuk.prayer.presentation.search.SearchPresenter;
import com.tshevchuk.prayer.presentation.settings.SettingsPresenter;
import com.tshevchuk.prayer.presentation.sub_menu.SubMenuPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by taras on 24.03.16.
 */
@Module
public class PresenterModule {
    @Provides
    AboutAppPresenter provideAboutAppPresenter(DataManager dataManager, Utils utils) {
        return new AboutAppPresenter(dataManager, utils);
    }

    @Provides
    AboutPrayerPresenter provideAboutPrayerPresenter(Application application) {
        return new AboutPrayerPresenter(application);
    }

    @Provides
    OftenUsedPresenter provideOftenUsedPresenter(Navigator navigator, DataManager dataManager) {
        return new OftenUsedPresenter(navigator, dataManager);
    }

    @Provides
    CerkovnyyCalendarPresenter provideCerkovnyyCalendarPresenter(AnalyticsManager analyticsManager,
                                                                 DataManager dataManager) {
        return new CerkovnyyCalendarPresenter(analyticsManager, dataManager);
    }

    @Provides
    SubMenuPresenter provideSubMenuPresenter(DataManager dataManager, Navigator navigator) {
        return new SubMenuPresenter(dataManager, navigator);
    }

    @Provides
    HtmlViewPresenter provideHtmlViewPresenter() {
        return new HtmlViewPresenter(dataManager);
    }

    @Provides
    TextViewPresenter provideTextViewPresenter(AnalyticsManager analyticsManager,
                                               Navigator navigator, DataManager dataManager,
                                               AsyncTaskManager asyncTaskManager) {
        return new TextViewPresenter(analyticsManager, navigator, dataManager, asyncTaskManager);
    }

    @Provides
    SearchPresenter provideSearchPresenter(Navigator navigator, DataManager dataManager,
                                           AnalyticsManager analyticsManager) {
        return new SearchPresenter(navigator, dataManager, analyticsManager);
    }

    @Provides
    SettingsPresenter provideSettingsPresenter(AnalyticsManager analyticsManager, Navigator navigator) {
        return new SettingsPresenter(analyticsManager, navigator);
    }
}
