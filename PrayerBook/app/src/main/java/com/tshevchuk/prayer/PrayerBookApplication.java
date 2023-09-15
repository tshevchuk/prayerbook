package com.tshevchuk.prayer;

import android.app.Application;
import android.content.Intent;

import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.gms.security.ProviderInstaller;
import com.tshevchuk.prayer.di.AppModule;
import com.tshevchuk.prayer.di.DaggerViewComponent;
import com.tshevchuk.prayer.di.ModelModule;
import com.tshevchuk.prayer.di.PresenterModule;
import com.tshevchuk.prayer.di.ViewComponent;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class PrayerBookApplication extends Application {
    public static Long startupTimeMeasuringStartTimestamp = System
            .currentTimeMillis();
    private static PrayerBookApplication instance;
    private ViewComponent viewComponent;
    private HttpProxyCacheServer proxy;


    public static PrayerBookApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        viewComponent = DaggerViewComponent.builder()
                .appModule(new AppModule(this))
                .modelModule(new ModelModule())
                .presenterModule(new PresenterModule())
                .build();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        ProviderInstaller.installIfNeededAsync(this,
                new com.tshevchuk.prayer.ProviderInstallerListener());
    }

    public synchronized HttpProxyCacheServer getAudioHttpProxy(){
        if(proxy == null){
            proxy = new HttpProxyCacheServer.Builder(this).build();
        }
        return proxy;
    }


    public enum TrackerName {
        APP_TRACKER,
    }

    public ViewComponent getViewComponent() {
        return viewComponent;
    }

}
