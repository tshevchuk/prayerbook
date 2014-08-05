package com.tshevchuk.prayer.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;

public class AboutAppFragment extends FragmentBase {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_about_app, container, false);
		((TextView) v.findViewById(R.id.tv_app_name)).setText(Utils
				.getApplicationNameAndVersion());
		StringBuilder sb = new StringBuilder();
		sb.append("Автор: Тарас Шевчук taras.shevchuk@gmail.com\n\n");
		
		sb.append("Подяки:\nІван Дутка - за поради і зауваження, надані деякі тексти;\n");
		sb.append("Степан Сус - за поширення програми в Facebook\n\n");
		
		sb.append("Історія змін: https://code.google.com/p/prayerbook/wiki/ReleaseNotes\n\n");
		
		sb.append("Допомогти проекту можна наступними способами: https://code.google.com/p/prayerbook/wiki/HowToContribute\n\n");
		
		sb.append("Джерела текстів:\n");
		List<String> srcs = new ArrayList<String>(PrayerBookApplication
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
		activity.getActionBar().setTitle("Про Молитовник");
	}
}
