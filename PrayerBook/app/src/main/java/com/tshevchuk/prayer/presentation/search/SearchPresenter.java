package com.tshevchuk.prayer.presentation.search;

import android.text.TextUtils;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.common.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by taras on 18.03.16.
 */
public class SearchPresenter extends BasePresenter<SearchView> {
    private final Navigator navigator;
    private final DataManager dataManager;
    private final AnalyticsManager analyticsManager;

    @Inject
    public SearchPresenter(Navigator navigator, DataManager dataManager, AnalyticsManager analyticsManager) {
        super(analyticsManager, navigator);
        this.navigator = navigator;
        this.dataManager = dataManager;
        this.analyticsManager = analyticsManager;
    }

    @Override
    public void attachView(SearchView mvpView) {
        super.attachView(mvpView);
    }

    public void setSearchPhrase(String searchPhrase) {
        checkViewAttached();
        onSearch(searchPhrase);
    }

    public void onItemClick(MenuListItemSearch mi) {
        navigator.showMenuItem(this, mi);
        dataManager.updateRecentlyUsedBecauseItemOpened(mi.getId());
        analyticsManager.sendActionEvent(Analytics.CAT_SEARCH,
                "Вибрано елемент на фрагменті результатів пошуку",
                mi.getId() + " " + mi.getName());

    }

    private void onSearch(String searchPhrase) {
        String searchPhraseLowerCase = TextUtils.isEmpty(searchPhrase) ? searchPhrase
                : searchPhrase.toLowerCase(Utils.getUkrainianLocale());

        ArrayList<MenuListItemSearch> items = dataManager.searchMenuItems(searchPhraseLowerCase);
        boolean showOfficialUGCC = dataManager.isShowOfficialUGCC();
        if (!showOfficialUGCC) {
            for (int i = items.size() - 1; i >= 0; i--) {
                items.get(i).setOfficialUGCCText(false);
            }
        }
        getMvpView().setSearchResults(searchPhraseLowerCase, items);
    }

    public void onSearchQueryTextChange(String newText) {
        onSearch(newText);
    }
}
