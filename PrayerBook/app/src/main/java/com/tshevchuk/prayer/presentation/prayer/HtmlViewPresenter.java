package com.tshevchuk.prayer.presentation.prayer;

import android.text.TextUtils;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.Navigator;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by taras on 18.03.16.
 */
public class HtmlViewPresenter extends BasePresenter<HtmlViewView> {
    private final static int PROGRESS_ID_WEB_VIEW_LOADING = 0;

    private final List<MenuItemPrayer> prayers = new ArrayList<>();
    private final DataManager dataManager;
    private final Navigator navigator;
    private boolean webViewCreated;

    public HtmlViewPresenter(DataManager dataManager, Navigator navigator,
                             AnalyticsManager analyticsManager) {
        super(analyticsManager, navigator);
        this.dataManager = dataManager;
        this.navigator = navigator;
    }

    @Override
    public void attachView(HtmlViewView mvpView) {
        super.attachView(mvpView);

        getMvpView().setFontSizeSp(dataManager.getTextFontSizeSp());
        if (!prayers.isEmpty()) {
            getMvpView().setScreenTitle(prayers.get(prayers.size() - 1).getName());
        }
        if (webViewCreated) {
            loadPrayer(prayers.get(prayers.size() - 1));
            onLoadingProgresChanged(0);
            webViewCreated = false;
        }
    }

    public void setArgPrayerId(int id) {
        if (prayers.isEmpty()) {
            prayers.add((MenuItemPrayer) dataManager.getMenuItem(id));
        }
    }

    @DebugLog
    private void loadPrayer(MenuItemPrayer p) {
        String url = "file:///android_asset/" + p.getFileName();
        if (p.getHtmlLinkAnchor() != null && !p.getHtmlLinkAnchor().isEmpty()) {
            url += "#" + p.getHtmlLinkAnchor();
        }
        getMvpView().loadUrl(url);
    }

    public void onWebViewCreated() {
        webViewCreated = true;
    }

    public void onGoBack() {
        if (!prayers.isEmpty()) {
            prayers.remove(prayers.size() - 1);
        }
    }

    public void onLoadingProgresChanged(int progressPercent) {
        if (progressPercent < 100) {
            showProgress(PROGRESS_ID_WEB_VIEW_LOADING);
        } else {
            hideProgress(PROGRESS_ID_WEB_VIEW_LOADING);
        }
    }

    public void onLinkClick(String[] params) {
        int id = Integer.valueOf(params[0]);
        MenuItemBase mi = dataManager.getMenuItem(id);
        if (mi instanceof MenuItemPrayer) {
            if (params.length == 2 && !TextUtils.isEmpty(params[1])) {
                ((MenuItemPrayer) mi).setHtmlLinkAnchor(params[1]);
            }
        }
        if (mi instanceof MenuItemPrayer && ((MenuItemPrayer) mi).getType() == MenuItemPrayer.Type.HtmlInWebView) {
            prayers.add((MenuItemPrayer) mi);
            loadPrayer((MenuItemPrayer) mi);
        } else {
            navigator.showMenuItem(this, dataManager.getMenuListItem(mi.getId()));
            dataManager.updateRecentlyUsedBecauseItemOpened(mi.getId());
        }
    }

    public void onPageLoadFinished(String url) {
        for (int i = prayers.size() - 1; i >= 0; --i) {
            MenuItemPrayer p = prayers.get(i);
            String u = HtmlViewView.URL_ROOT_ASSET_FOLDER + p.getFileName();
            if (url.equals(u) || url.startsWith(u + "#")) {
                while (prayers.size() > i + 1) {
                    prayers.remove(prayers.size() - 1);
                }
                break;
            }
        }

        if (!prayers.isEmpty()) {
            getMvpView().setScreenTitle(prayers.get(prayers.size() - 1).getName());
            getMvpView().scrollToUrlAnchor(url);
        }
    }

    public void onCreateShortcutClick() {
        if (!prayers.isEmpty()) {
            MenuItemBase mi = prayers.get(prayers.size() - 1);
            handleCreateShortcutClick(mi);
        }
    }
}
