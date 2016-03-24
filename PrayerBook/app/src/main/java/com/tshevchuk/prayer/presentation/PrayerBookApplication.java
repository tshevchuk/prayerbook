package com.tshevchuk.prayer.presentation;

import android.app.Application;

import com.tshevchuk.prayer.di.AppModule;
import com.tshevchuk.prayer.di.DaggerViewComponent;
import com.tshevchuk.prayer.di.PresenterModule;
import com.tshevchuk.prayer.di.ViewComponent;
import com.tshevchuk.prayer.di.ViewModule;

public class PrayerBookApplication extends Application {
	public static Long startupTimeMeasuringStartTimestamp = System
			.currentTimeMillis();

	private ViewComponent viewComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		viewComponent = DaggerViewComponent.builder()
				.appModule(new AppModule(this))
				.viewModule(new ViewModule())
				.presenterModule(new PresenterModule())
				.build();
	}


	public ViewComponent getViewComponent() {
		return viewComponent;
	}


}
