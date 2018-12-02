package com.tshevchuk.prayer.presentation;

import android.app.Application;
import android.text.TextUtils;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by taras on 21.03.16.
 */
public class AnalyticsManagerImpl implements AnalyticsManager {
    private final Map<TrackerName, Tracker> trackers = new HashMap<>();
    private final Application application;

    @Inject
    public AnalyticsManagerImpl(Application application) {
        this.application = application;


        if (Utils.isDebuggable(application)) {
            GoogleAnalytics.getInstance(application).setDryRun(true);
        }
    }

    @Override
    public void sendTimingEvent(String category, String label, String variable, long value) {
        getTracker()
                .send(new HitBuilders.TimingBuilder()
                        .setLabel(label)
                        .setCategory(category)
                        .setVariable(variable).setValue(value).build());
    }

    @Override
    public void sendActionEvent(String category, String action, String label) {
        HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder()
                .setCategory(category).setAction(action);
        if (!TextUtils.isEmpty(label)) {
            eventBuilder.setLabel(label);
        }
        getTracker().send(eventBuilder.build());
    }

    @Override
    public void sendActionEvent(String category, String action) {
        sendActionEvent(category, action, null);
    }

    @Override
    public void sendScreenEvent(String screenName) {
        Tracker t = getTracker();
        t.setScreenName(screenName);
        t.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private synchronized Tracker getTracker() {
        if (!trackers.containsKey(TrackerName.APP_TRACKER)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(application);
            Tracker t = analytics.newTracker(application.getString(R.string.ga_trackingId));
            trackers.put(TrackerName.APP_TRACKER, t);
        }
        return trackers.get(TrackerName.APP_TRACKER);
    }

    public enum TrackerName {
        APP_TRACKER,
    }
}
