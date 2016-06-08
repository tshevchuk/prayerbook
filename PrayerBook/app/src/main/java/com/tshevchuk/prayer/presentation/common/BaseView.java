package com.tshevchuk.prayer.presentation.common;

/**
 * Created by taras on 24.03.16.
 */
public interface BaseView {
    void showProgress();
    void hideProgress();
    void showError(String msg);
}
