package com.tshevchuk.prayer.presentation.prayer;

import com.tshevchuk.prayer.presentation.base.BaseView;

/**
 * Created by taras on 06.04.16.
 */
public interface HtmlViewView extends BaseView {
    String URL_ROOT_ASSET_FOLDER = "file:///android_asset/";

    void setFontSizeSp(int textFontSizeSp);

    void loadUrl(String url);

    void setScreenTitle(String name);

    void scrollToUrlAnchor(String url);
}
