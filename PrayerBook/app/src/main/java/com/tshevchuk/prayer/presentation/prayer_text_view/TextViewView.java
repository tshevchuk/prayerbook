package com.tshevchuk.prayer.presentation.prayer_text_view;

import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.common.BaseView;

/**
 * Created by taras on 03.04.16.
 */
public interface TextViewView extends BaseView {
    void setMenuItem(MenuItemPrayer prayer);
    void setPrayerText(CharSequence param);

    void setFontSizeSp(int textFontSizeSp);

    void setKeepScreenOn(boolean keepScreenOn);
}
