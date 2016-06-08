package com.tshevchuk.prayer.di;

import com.tshevchuk.prayer.presentation.about_app.AboutAppFragment;
import com.tshevchuk.prayer.presentation.about_church_calendar.AboutChurchCalendarFragment;
import com.tshevchuk.prayer.presentation.about_prayer.AboutPrayerFragment;
import com.tshevchuk.prayer.presentation.cerkovnyy_calendar.CerkovnyyCalendarFragment;
import com.tshevchuk.prayer.presentation.common.FragmentBase;
import com.tshevchuk.prayer.presentation.home.HomeActivity;
import com.tshevchuk.prayer.presentation.navigation_drawer.NavigationDrawerFragment;
import com.tshevchuk.prayer.presentation.often_used.OftenUsedFragment;
import com.tshevchuk.prayer.presentation.prayer_html_view.HtmlViewFragment;
import com.tshevchuk.prayer.presentation.prayer_text_view.TextViewFragment;
import com.tshevchuk.prayer.presentation.search.SearchFragment;
import com.tshevchuk.prayer.presentation.settings.SettingsFragment;
import com.tshevchuk.prayer.presentation.sub_menu.SubMenuFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by taras on 18.03.16.
 */
@Singleton
@Component(modules = {AppModule.class, ModelModule.class, PresenterModule.class})
public interface ViewComponent {
    void inject(HomeActivity activity);
    void inject(FragmentBase fragment);
    void inject(OftenUsedFragment fragment);
    void inject(CerkovnyyCalendarFragment fragment);
    void inject(SettingsFragment fragment);
    void inject(AboutPrayerFragment fragment);
    void inject(AboutAppFragment fragment);
    void inject(TextViewFragment fragment);
    void inject(HtmlViewFragment fragment);
    void inject(SubMenuFragment fragment);
    void inject(SearchFragment fragment);
    void inject(NavigationDrawerFragment fragment);

    void inject(AboutChurchCalendarFragment fragment);
}
