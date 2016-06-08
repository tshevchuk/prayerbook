package com.tshevchuk.prayer.presentation.about_church_calendar;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.common.BasePresenter;

import javax.inject.Inject;

/**
 * Created by taras on 18.03.16.
 */
public class AboutChurchCalendarPresenter extends BasePresenter<AboutChurchCalendarView> {
    private final DataManager dataManager;
    private final Utils utils;

    @Inject
    public AboutChurchCalendarPresenter(DataManager dataManager, Utils utils, AnalyticsManager analyticsManager,
                                        Navigator navigator) {
        super(analyticsManager, navigator);
        this.dataManager = dataManager;
        this.utils = utils;
    }
}
