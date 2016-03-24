package com.tshevchuk.prayer.di;

import com.tshevchuk.prayer.presentation.about_app.AboutAppFragment;
import com.tshevchuk.prayer.presentation.base.FragmentBase;
import com.tshevchuk.prayer.presentation.cerkovnyy_calendar.CerkovnyyCalendarFragment;
import com.tshevchuk.prayer.presentation.home.HomeActivity;
import com.tshevchuk.prayer.presentation.often_used.OftenUsedFragment;
import com.tshevchuk.prayer.presentation.settings.SettingsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by taras on 18.03.16.
 */
@Singleton
@Component(modules = {AppModule.class, ViewModule.class, PresenterModule.class})
public interface ViewComponent {
    void inject(HomeActivity activity);
    void inject(FragmentBase fragment);
    void inject(OftenUsedFragment fragment);
    void inject(CerkovnyyCalendarFragment fragment);
    void inject(SettingsFragment fragment);

    void inject(AboutAppFragment fragment);
}
