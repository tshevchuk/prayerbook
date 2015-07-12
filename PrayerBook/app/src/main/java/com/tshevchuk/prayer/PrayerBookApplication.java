package com.tshevchuk.prayer;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.tshevchuk.prayer.data.Catalog;

public class PrayerBookApplication extends Application {
	public enum TrackerName {
		APP_TRACKER,
	}

	public static Long startupTimeMeasuringStartTimestamp = System
			.currentTimeMillis();

	private Catalog catalog;

	private static PrayerBookApplication instance;
	private Map<TrackerName, Tracker> trackers = new HashMap<TrackerName, Tracker>();

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		if (Utils.isDebuggable()) {
			GoogleAnalytics.getInstance(this).setDryRun(true);
		}

		catalog = new Catalog();
	}

	public static PrayerBookApplication getInstance() {
		return instance;
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
}
