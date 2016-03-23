package com.tshevchuk.prayer.presentation.about_app;

import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.presentation.base.BasePresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by taras on 18.03.16.
 */
public class AboutAppPresenter extends BasePresenter {
    private final DataManager dataManager;

    public AboutAppPresenter(DataManager dataManager) {
        this.dataManager = dataManager;

        StringBuilder sb = new StringBuilder();
        sb.append("Коротка довідка: https://github.com/tshevchuk/prayerbook/wiki/Довідка-програми-Молитовник \n\n");
        sb.append("Автор: Тарас Шевчук taras.shevchuk@gmail.com\n\n");

        sb.append("Подяки:\nІван Дутка - за поради і зауваження, надані деякі тексти;\n");
        sb.append("Степан Сус - за поширення програми в Facebook\n\n");

        sb.append("Історія змін: https://github.com/tshevchuk/prayerbook/wiki/Історія-змін \n\n");

        sb.append("Допомогти проекту можна наступними способами: https://github.com/tshevchuk/prayerbook/wiki/Як-допомогти-проекту%3F \n\n");

        sb.append("Джерела текстів:\n");
        List<String> srcs = new ArrayList<>(dataManager.getAllPrayersReferences());
        Collections.sort(srcs);
        for (String src : srcs) {
            sb.append(" • ").append(src).append("\n");
        }

    }

    public interface AboutView extends BaseView {
        void setAboutInfo(String info);
    }
}
