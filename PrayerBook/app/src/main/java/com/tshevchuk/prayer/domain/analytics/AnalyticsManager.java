package com.tshevchuk.prayer.domain.analytics;

/**
 * Created by taras on 21.03.16.
 */
public interface AnalyticsManager {
    void sendTimingEvent(String category, String label, String variable, long value);

    void sendActionEvent(String category, String action, String label);

    void sendActionEvent(String category, String action);

    void sendScreenEvent(String screenName);
}
