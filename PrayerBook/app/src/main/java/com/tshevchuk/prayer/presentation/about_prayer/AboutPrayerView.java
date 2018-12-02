package com.tshevchuk.prayer.presentation.about_prayer;

import com.tshevchuk.prayer.presentation.common.BaseView;

/**
 * Created by taras on 25.03.16.
 */
interface AboutPrayerView extends BaseView {
    void setPrayerName(String name);

    void setAboutHtml(String about);
}
