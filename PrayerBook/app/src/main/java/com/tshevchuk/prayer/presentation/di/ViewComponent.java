package com.tshevchuk.prayer.presentation.di;

import com.tshevchuk.prayer.presentation.activities.HomeActivity;
import com.tshevchuk.prayer.presentation.fragments.CerkovnyyCalendarFragment;
import com.tshevchuk.prayer.presentation.fragments.FragmentBase;
import com.tshevchuk.prayer.presentation.fragments.OftenUsedFragment;
import com.tshevchuk.prayer.presentation.fragments.SettingsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by taras on 18.03.16.
 */
@Singleton
@Component(modules = {AppModule.class, ViewModule.class})
public interface ViewComponent {
    void inject(HomeActivity activity);

    void inject(FragmentBase fragment);

    void inject(OftenUsedFragment fragment);

    void inject(CerkovnyyCalendarFragment fragment);

    void inject(SettingsFragment fragment);
}
