package com.tshevchuk.prayer.presentation.common;

import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.presentation.Navigator;

import java.util.BitSet;
import java.util.Locale;

/**
 * Created by taras on 22.03.16.
 */
public abstract class BasePresenter<T extends BaseView> {
    protected final AnalyticsManager analyticsManager;
    protected final Navigator navigator;
    private T mvpView;
    private final BitSet progressStates = new BitSet();

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

    protected boolean isViewDetached() {
        return mvpView == null;
    }

    public T getMvpView() {
        return mvpView;
    }

    protected void checkViewAttached() {
        if (isViewDetached()) throw new MvpViewNotAttachedException();
    }

    protected void showProgress(int progressId) {
        progressStates.set(progressId, true);
        getMvpView().showProgress();
    }

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

    static class MvpViewNotAttachedException extends RuntimeException {
        MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
