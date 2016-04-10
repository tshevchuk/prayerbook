package com.tshevchuk.prayer.presentation.home;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

/**
 * Created by taras on 23.03.16.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private final Navigator navigator;
    private final DataManager dataManager;
    private boolean restoringInstanceState;
    private int paramScreenId;

    public HomePresenter(Navigator navigator, DataManager dataManager) {
        this.navigator = navigator;
        this.dataManager = dataManager;
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
}
