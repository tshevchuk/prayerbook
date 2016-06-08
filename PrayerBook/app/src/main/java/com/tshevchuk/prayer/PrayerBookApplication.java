package com.tshevchuk.prayer;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class PrayerBookApplication extends Application {
    public static Long startupTimeMeasuringStartTimestamp = System
            .currentTimeMillis();
    private static PrayerBookApplication instance;
    private final Map<TrackerName, Tracker> trackers = new HashMap<>();

    public static PrayerBookApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        instance = this;

        if (BuildConfig.DEBUG) {
            GoogleAnalytics.getInstance(this).setDryRun(true);
        }
    }

    public synchronized Tracker getTracker() {
        if (!trackers.containsKey(TrackerName.APP_TRACKER)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(getString(R.string.ga_trackingId));
            trackers.put(TrackerName.APP_TRACKER, t);
        }
        return trackers.get(TrackerName.APP_TRACKER);
    }

    public enum TrackerName {
        APP_TRACKER,
    }
}
