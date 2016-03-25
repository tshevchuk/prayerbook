package com.tshevchuk.prayer.presentation.about_prayer;

import com.tshevchuk.prayer.presentation.base.BaseView;

/**
 * Created by taras on 25.03.16.
 */
public interface AboutPrayerView extends BaseView {
    void setPrayerName(String name);

    void setAboutHtml(String about);
}
