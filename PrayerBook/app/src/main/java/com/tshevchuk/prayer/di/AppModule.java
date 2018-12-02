package com.tshevchuk.prayer.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by taras on 18.03.16.
 */
@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }


    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

}