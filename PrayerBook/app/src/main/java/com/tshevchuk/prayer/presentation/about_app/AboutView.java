package com.tshevchuk.prayer.presentation.about_app;

import com.tshevchuk.prayer.presentation.common.BaseView;

/**
 * Created by taras on 24.03.16.
 */
public interface AboutView extends BaseView {
    void setTextSources(String textSources);

    void setAppNameAndVersion(String appNameAndVersion);
}
