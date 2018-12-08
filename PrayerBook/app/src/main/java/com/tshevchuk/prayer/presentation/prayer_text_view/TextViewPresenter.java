package com.tshevchuk.prayer.presentation.prayer_text_view;

import android.content.Intent;
import android.net.Uri;

import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.AsyncTaskManager;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.common.BasePresenter;

import java.util.Locale;

/**
 * Created by taras on 18.03.16.
 */
public class TextViewPresenter extends BasePresenter<TextViewView> {
    private final static int PROGRESS_ID_TEXT_VIEW_LOADING = 0;

    private final DataManager dataManager;
    private final AsyncTaskManager asyncTaskManager;
    private int id;
    private MenuItemPrayer menuItemPrayer;

    public TextViewPresenter(AnalyticsManager analyticsManager, Navigator navigator,
                             DataManager dataManager, AsyncTaskManager asyncTaskManager) {
        super(analyticsManager, navigator);
        this.dataManager = dataManager;
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
        getMvpView().setFontSizeSp(dataManager.getTextFontSizeSp());
        getMvpView().setKeepScreenOn(dataManager.isKeepScreenOn());
        loadPrayer();
    }

    @Override
    public void detachView() {
        super.detachView();
        asyncTaskManager.cancelAll();
    }

    private void loadPrayer() {
        showProgress(PROGRESS_ID_TEXT_VIEW_LOADING);
        asyncTaskManager.executeTask(new AsyncTaskManager.BackgroundTask<CharSequence>() {
            @Override
            public CharSequence doInBackground() {
                return dataManager.loadText(menuItemPrayer);
            }

            @Override
            public void postExecute(CharSequence result) {
                getMvpView().setPrayerText(result);
                hideProgress(PROGRESS_ID_TEXT_VIEW_LOADING);
            }

            @Override
            public void onError(Throwable tr) {
                hideProgress(PROGRESS_ID_TEXT_VIEW_LOADING);
                getMvpView().showError(tr.getLocalizedMessage());
            }
        });
    }

    public void onCreateShortcutClick() {
        handleCreateShortcutClick(menuItemPrayer);
    }

    @SuppressWarnings("SameReturnValue")
    boolean onUpButtonPress() {
        navigator.close(this);
        int parentId = menuItemPrayer.getParentItemId();
        if (parentId > 0) {
            navigator.showSubMenu(this, parentId, dataManager.getMenuItem(parentId).getName());
        } else {
            navigator.showMenuItem(this, dataManager.getMenuListItem(Catalog.ID_RECENT_SCREENS));
        }
        return true;
    }

    void onOpenAboutClick() {
        navigator.showAboutPrayer(this, menuItemPrayer);
        analyticsManager.sendActionEvent(Analytics.CAT_OPTIONS_MENU, "Опис",
                String.format(Locale.US, "#%d %s", menuItemPrayer.getId(), menuItemPrayer.getName()));
    }
}
