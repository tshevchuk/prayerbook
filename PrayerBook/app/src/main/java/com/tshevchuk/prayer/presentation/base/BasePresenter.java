package com.tshevchuk.prayer.presentation.base;

import java.util.BitSet;

/**
 * Created by taras on 22.03.16.
 */
public abstract class BasePresenter<T extends BaseView> {

    private T mvpView;
    private BitSet progressStates = new BitSet();

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

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
