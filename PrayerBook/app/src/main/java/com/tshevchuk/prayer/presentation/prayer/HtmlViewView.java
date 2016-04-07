package com.tshevchuk.prayer.presentation.prayer;

import com.tshevchuk.prayer.presentation.base.BaseView;

/**
 * Created by taras on 06.04.16.
 */
public interface HtmlViewView extends BaseView {
    void setFontSizeSp(int textFontSizeSp);

    void loadUrl(String url);

    void setScreenTitle(String name);
}
