package com.tshevchuk.prayer.presentation.prayer;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.AsyncTaskManager;
import com.tshevchuk.prayer.presentation.Navigator;

/**
 * Created by taras on 18.03.16.
 */
public class TextViewPresenter extends TextBasePresenter<TextViewView> {
    private final AsyncTaskManager asyncTaskManager;
    private int id;

    public TextViewPresenter(AnalyticsManager analyticsManager, Navigator navigator,
                             DataManager dataManager, AsyncTaskManager asyncTaskManager) {
        super(analyticsManager, navigator, dataManager);
        this.asyncTaskManager = asyncTaskManager;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void attachView(TextViewView mvpView) {
        super.attachView(mvpView);
        menuItemPrayer = (MenuItemPrayer) dataManager.getMenuItem(id);
        getMvpView().setMenuItem(menuItemPrayer);
        loadPrayer();
    }

    @Override
    public void detachView() {
        super.detachView();
        asyncTaskManager.cancelAll();
    }

    private void loadPrayer() {
        getMvpView().showProgress();
        asyncTaskManager.executeTask(new AsyncTaskManager.BackgroundTask<CharSequence>() {
            @Override
            public CharSequence doInBackground() {
                return dataManager.loadText(menuItemPrayer);
            }

            @Override
            public void postExecute(CharSequence result) {
                getMvpView().setPrayerText(result);
                getMvpView().hideProgress();
            }

            @Override
            public void onError(Throwable tr) {
                getMvpView().hideProgress();
                getMvpView().showError(tr.getLocalizedMessage());
            }
        });
    }

    public void onCreateShortcutClick() {
        handleCreateShortcutClick(menuItemPrayer);
    }
}
