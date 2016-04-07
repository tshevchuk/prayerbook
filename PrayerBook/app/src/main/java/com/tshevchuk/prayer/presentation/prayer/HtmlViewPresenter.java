package com.tshevchuk.prayer.presentation.prayer;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras on 18.03.16.
 */
public class HtmlViewPresenter extends BasePresenter<HtmlViewView> {
    private final List<MenuItemPrayer> prayers = new ArrayList<>();

    private final DataManager dataManager;

    public HtmlViewPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(HtmlViewView mvpView) {
        super.attachView(mvpView);

        getMvpView().setFontSizeSp(dataManager.getTextFontSizeSp());
        if (!prayers.isEmpty()) {
            getMvpView().setScreenTitle(prayers.get(prayers.size() - 1).getName());
        }
    }

    public void setArgPrayerId(int id) {
        if (prayers.isEmpty()) {
            prayers.add((MenuItemPrayer) dataManager.getMenuItem(id));
        }
    }

    private void loadPrayer(MenuItemPrayer p) {
        String url = "file:///android_asset/" + p.getFileName();
        if (p.getHtmlLinkAnchor() != null && !p.getHtmlLinkAnchor().isEmpty()) {
            url += "#" + p.getHtmlLinkAnchor();
        }
        getMvpView().loadUrl(url);
    }

    public void onWebViewCreated() {
        loadPrayer(prayers.get(prayers.size() - 1));
    }

    public void onGoBack() {
        if (!prayers.isEmpty()) {
            prayers.remove(prayers.size() - 1);
        }
    }
}
