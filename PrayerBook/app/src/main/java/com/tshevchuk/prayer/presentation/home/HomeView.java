package com.tshevchuk.prayer.presentation.home;

import com.tshevchuk.prayer.presentation.base.BaseView;

import java.io.File;

/**
 * Created by taras on 10.04.16.
 */
public interface HomeView extends BaseView {
    boolean handleUpAction();
    boolean handleBackAction();
    void updateAppRater();

    byte[] createScreenshotJpeg();

    void sendErrorReport(String email, File imageFile);

    void setNightMode(boolean nightMode);
}
