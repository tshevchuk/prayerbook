package com.tshevchuk.prayer.di;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.presentation.about_app.AboutAppPresenter;

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
}
