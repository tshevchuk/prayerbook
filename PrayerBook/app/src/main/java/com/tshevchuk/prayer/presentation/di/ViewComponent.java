package com.tshevchuk.prayer.presentation.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by taras on 18.03.16.
 */
@Singleton
@Component(modules = {AppModule.class, ViewModule.class})
public interface ViewComponent {
}
