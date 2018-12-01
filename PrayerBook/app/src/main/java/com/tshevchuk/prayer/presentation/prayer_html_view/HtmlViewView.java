package com.tshevchuk.prayer.presentation.prayer_html_view;

import com.tshevchuk.prayer.presentation.common.BaseView;

/**
 * Created by taras on 06.04.16.
 */
interface HtmlViewView extends BaseView {
    String URL_ROOT_ASSET_FOLDER = "file:///android_asset/";

    void setFontSizeSp(int textFontSizeSp);

    void loadUrl(String url);

    void setScreenTitle(String name);

    void scrollToUrlAnchor(String url);

    void setKeepScreenOn(boolean keepScreenOn);
}
