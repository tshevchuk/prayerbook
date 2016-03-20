package com.tshevchuk.prayer.presentation.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AboutAppFragment extends FragmentBase {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_about_app, container, false);
		((TextView) v.findViewById(R.id.tv_app_name)).setText(Utils
				.getApplicationNameAndVersion());
		StringBuilder sb = new StringBuilder();
		sb.append("Коротка довідка: https://github.com/tshevchuk/prayerbook/wiki/Довідка-програми-Молитовник \n\n");
		sb.append("Автор: Тарас Шевчук taras.shevchuk@gmail.com\n\n");
		
		sb.append("Подяки:\nІван Дутка - за поради і зауваження, надані деякі тексти;\n");
		sb.append("Степан Сус - за поширення програми в Facebook\n\n");

		sb.append("Історія змін: https://github.com/tshevchuk/prayerbook/wiki/Історія-змін \n\n");

		sb.append("Допомогти проекту можна наступними способами: https://github.com/tshevchuk/prayerbook/wiki/Як-допомогти-проекту%3F \n\n");
		
		sb.append("Джерела текстів:\n");
		List<String> srcs = new ArrayList<>(PrayerBookApplication
				.getInstance().getCatalog().getAllSources());
		Collections.sort(srcs);
		for (String src : srcs) {
			sb.append(" • ").append(src).append("\n");
		}
		((TextView) v.findViewById(R.id.tv_content)).setText(sb);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		ActionBar actionBar = activity.getSupportActionBar();
		if (actionBar != null) {
			actionBar.setTitle(R.string.about__about_prayerbook);
		}
	}
}
