package com.tshevchuk.prayer.presentation.common;

import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.presentation.Navigator;

import java.util.BitSet;
import java.util.Locale;

import hugo.weaving.DebugLog;

/**
 * Created by taras on 22.03.16.
 */
public abstract class BasePresenter<T extends BaseView> {
    protected final AnalyticsManager analyticsManager;
    protected final Navigator navigator;
    private T mvpView;
    private BitSet progressStates = new BitSet();

    protected BasePresenter(AnalyticsManager analyticsManager, Navigator navigator) {
        this.analyticsManager = analyticsManager;
        this.navigator = navigator;
    }


    public void attachView(T mvpView) {
        this.mvpView = mvpView;
    }

    public void detachView() {
        this.mvpView = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public T getMvpView() {
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    @DebugLog
    protected void showProgress(int progressId) {
        progressStates.set(progressId, true);
        getMvpView().showProgress();
    }

    @DebugLog
    protected void hideProgress(int progressId) {
        progressStates.set(progressId, false);
        if (progressStates.isEmpty()) {
            getMvpView().hideProgress();
        }
    }

    protected void handleCreateShortcutClick(MenuItemBase mi) {
        navigator.createShortcut(this, mi);
        analyticsManager.sendActionEvent("Create launcher shortcut",
                String.format(Locale.US, "#%d %s", mi.getId(), mi.getName()));
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
