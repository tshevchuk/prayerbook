package com.tshevchuk.prayer.domain.analytics;

public class AnalyticsStub implements AnalyticsManager{
    @Override
    public void sendTimingEvent(String category, String label, String variable, long value) {
        // do nothing
    }

    @Override
    public void sendActionEvent(String category, String action, String label) {
        // do nothing
    }

    @Override
    public void sendActionEvent(String category, String action) {
        // do nothing
    }

    @Override
    public void sendScreenEvent(String screenName) {
        // do nothing
    }
}
