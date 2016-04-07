package com.tshevchuk.prayer.presentation.prayer;

import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.base.BaseView;

/**
 * Created by taras on 03.04.16.
 */
public interface TextViewView extends BaseView {
    void setMenuItem(MenuItemPrayer prayer);
    void setPrayerText(CharSequence param);
}
