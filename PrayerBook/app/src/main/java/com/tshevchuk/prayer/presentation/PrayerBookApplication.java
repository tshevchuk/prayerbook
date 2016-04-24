package com.tshevchuk.prayer.presentation;

import android.app.Application;

import com.tshevchuk.prayer.BuildConfig;
import com.tshevchuk.prayer.di.AppModule;
import com.tshevchuk.prayer.di.DaggerViewComponent;
import com.tshevchuk.prayer.di.ModelModule;
import com.tshevchuk.prayer.di.PresenterModule;
import com.tshevchuk.prayer.di.ViewComponent;

import timber.log.Timber;

public class PrayerBookApplication extends Application {
	public static Long startupTimeMeasuringStartTimestamp = System
			.currentTimeMillis();

	private ViewComponent viewComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		viewComponent = DaggerViewComponent.builder()
				.appModule(new AppModule(this))
				.modelModule(new ModelModule())
				.presenterModule(new PresenterModule())
				.build();
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
	}


	public ViewComponent getViewComponent() {
		return viewComponent;
	}


}
