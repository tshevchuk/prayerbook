package com.tshevchuk.prayer.di;

import android.app.Application;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.presentation.about_app.AboutAppPresenter;
import com.tshevchuk.prayer.presentation.about_prayer.AboutPrayerPresenter;
import com.tshevchuk.prayer.presentation.cerkovnyy_calendar.CerkovnyyCalendarPresenter;
import com.tshevchuk.prayer.presentation.often_used.OftenUsedPresenter;
import com.tshevchuk.prayer.presentation.prayer.HtmlViewPresenter;
import com.tshevchuk.prayer.presentation.prayer.TextViewPresenter;
import com.tshevchuk.prayer.presentation.search.SearchPresenter;
import com.tshevchuk.prayer.presentation.sub_menu.SubMenuPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by taras on 24.03.16.
 */
@Module
public class PresenterModule {
    @Provides
    AboutAppPresenter getAboutAppPresenter(DataManager dataManager, Utils utils) {
        return new AboutAppPresenter(dataManager, utils);
    }

    @Provides
    AboutPrayerPresenter getAboutPrayerPresenter(Application application){
        return new AboutPrayerPresenter(application);
    }

    @Provides
    OftenUsedPresenter getOftenUsedPresenter(){
        return new OftenUsedPresenter();
    }

    @Provides
    CerkovnyyCalendarPresenter getCerkovnyyCalendarPresenter(){
        return new CerkovnyyCalendarPresenter();
    }

    @Provides
    SubMenuPresenter getSubMenuPresenter(){
        return new SubMenuPresenter();
    }

    @Provides
    HtmlViewPresenter getHtmlViewPresenter(){
        return new HtmlViewPresenter();
    }

    @Provides
    TextViewPresenter getTextViewPresenter(){
        return new TextViewPresenter();
    }

    @Provides
    SearchPresenter getSearchPresenter(){
        return new SearchPresenter();
    }
}
