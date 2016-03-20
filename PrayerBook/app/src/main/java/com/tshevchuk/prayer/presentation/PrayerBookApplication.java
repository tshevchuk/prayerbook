package com.tshevchuk.prayer.presentation;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.presentation.di.AppModule;
import com.tshevchuk.prayer.presentation.di.DaggerViewComponent;
import com.tshevchuk.prayer.presentation.di.ViewComponent;
import com.tshevchuk.prayer.presentation.di.ViewModule;

import java.util.HashMap;
import java.util.Map;

public class PrayerBookApplication extends Application {
	public static Long startupTimeMeasuringStartTimestamp = System
			.currentTimeMillis();
	private static PrayerBookApplication instance;
	private final Map<TrackerName, Tracker> trackers = new HashMap<>();
	private Catalog catalog;
	private ViewComponent viewComponent;

	public static PrayerBookApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		if (Utils.isDebuggable()) {
			GoogleAnalytics.getInstance(this).setDryRun(true);
		}

		viewComponent = DaggerViewComponent.builder()
				.appModule(new AppModule(this))
				.viewModule(new ViewModule())
				.build();


		catalog = new Catalog();
	}

	public synchronized Tracker getTracker() {
		if (!trackers.containsKey(TrackerName.APP_TRACKER)) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			Tracker t = analytics.newTracker(getString(R.string.ga_trackingId));
			trackers.put(TrackerName.APP_TRACKER, t);
		}
		return trackers.get(TrackerName.APP_TRACKER);
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public ViewComponent getViewComponent() {
		return viewComponent;
	}

	public enum TrackerName {
		APP_TRACKER,
	}
}
