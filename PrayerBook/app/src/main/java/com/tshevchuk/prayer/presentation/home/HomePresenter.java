package com.tshevchuk.prayer.presentation.home;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import java.io.File;

/**
 * Created by taras on 23.03.16.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private final Navigator navigator;
    private final DataManager dataManager;
    private final AnalyticsManager analyticsManager;
    private boolean restoringInstanceState;
    private int paramScreenId;

    public HomePresenter(Navigator navigator, DataManager dataManager, AnalyticsManager analyticsManager) {
        this.navigator = navigator;
        this.dataManager = dataManager;
        this.analyticsManager = analyticsManager;
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
    }

    public void onBackPressed() {
        if (!getMvpView().handleUpAction()) {
            getMvpView().handleBackAction();
        }
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

        getMvpView().sendErrorReport("taras.shevchuk@gmail.com", imageFile);

        analyticsManager.sendActionEvent(Analytics.CAT_OPTIONS_MENU, "Повідомити про помилку");
    }
}
