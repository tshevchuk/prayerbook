package com.tshevchuk.prayer.presentation.home;

import android.content.Context;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.common.BasePresenter;

import java.io.File;

/**
 * Created by taras on 23.03.16.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private final Navigator navigator;
    private final DataManager dataManager;
    private final AnalyticsManager analyticsManager;
    private final Utils utils;
    private final Context context;
    private boolean restoringInstanceState;
    private int paramScreenId;

    public HomePresenter(Navigator navigator, DataManager dataManager,
                         AnalyticsManager analyticsManager, Utils utils, Context context) {
        super(analyticsManager, navigator);
        this.navigator = navigator;
        this.dataManager = dataManager;
        this.analyticsManager = analyticsManager;
        this.utils = utils;
        this.context = context;
    }

    @Override
    public void attachView(HomeView mvpView) {
        super.attachView(mvpView);

        if (!restoringInstanceState) {
            int id = paramScreenId;
            if (id == 0) {
                id = dataManager.getDefaultScreenMenuItemId();
            }
            navigator.showMenuItem(this, dataManager.getMenuListItem(id));

            getMvpView().updateAppRater();
        }

        getMvpView().enableToolbarHidingOnScroll(dataManager.isHideToolbarOnScrolling());
    }

    public boolean isNightModeEnabled() {
        return dataManager.isNightMode();
    }

    public void onUpPressed() {
        navigator.handleUpAction(this);
    }

    public void setParamScreenId(int id) {
        paramScreenId = id;
    }

    public void setRestoringInstanceState(boolean restoring) {
        restoringInstanceState = restoring;
    }

    public void onSettingsClick() {
        navigator.showSettings(this);
        analyticsManager.sendActionEvent(Analytics.CAT_OPTIONS_MENU, "Налаштування");
    }

    public void onReportMistakeClick() {
        byte[] screenshot = getMvpView().createScreenshotJpeg();
        File imageFile = null;
        if (screenshot != null) {
            imageFile = dataManager.storeErrorReportScreenshot(screenshot);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\nApplication: ").append(utils.getApplicationNameAndVersion())
                .append(' ').append(context.getPackageName());
        sb.append("\n").append(getMvpView().getCurrentScreenInfoForErrorReport());
        sb.append("\n").append(utils.getDeviceInfo());

        File deviceInfoFile = dataManager.storeErrorReportDeviceInfoAttachment(sb.toString());

        getMvpView().sendErrorReport("taras.shevchuk@gmail.com", imageFile, deviceInfoFile);

        analyticsManager.sendActionEvent(Analytics.CAT_OPTIONS_MENU, "Повідомити про помилку");
    }
}
