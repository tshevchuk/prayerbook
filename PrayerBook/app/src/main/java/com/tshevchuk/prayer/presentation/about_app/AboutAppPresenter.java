package com.tshevchuk.prayer.presentation.about_app;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by taras on 18.03.16.
 */
public class AboutAppPresenter extends BasePresenter<AboutView> {
    private final DataManager dataManager;
    private final Utils utils;

    @Inject
    public AboutAppPresenter(DataManager dataManager, Utils utils) {
        this.dataManager = dataManager;
        this.utils = utils;
    }

    @Override
    public void attachView(AboutView mvpView) {
        super.attachView(mvpView);

        StringBuilder sb = new StringBuilder();
        List<String> srcs = new ArrayList<>(dataManager.getAllPrayersReferences());
        Collections.sort(srcs);
        for (String src : srcs) {
            sb.append(" • ").append(src).append("\n");
        }
        getMvpView().setTextSources(sb.toString());
        getMvpView().setScreenTitle();
        getMvpView().setAppNameAndVersion(utils.getApplicationNameAndVersion());
    }
}
