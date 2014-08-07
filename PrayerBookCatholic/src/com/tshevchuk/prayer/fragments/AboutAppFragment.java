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
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.catholic.R;

public class AboutAppFragment extends FragmentBase {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_about_app, container, false);
		((TextView) v.findViewById(R.id.tv_app_name)).setText(Utils
				.getApplicationNameAndVersion());
		StringBuilder sb = new StringBuilder();
		sb.append("Розробник: Тарас Шевчук taras.shevchuk@gmail.com\n\n");

		sb.append("Микола Мишовський - ідея програми, надав тексти\n\n");

		sb.append("Допомогти проекту можна наступними способами: https://code.google.com/p/prayerbook/wiki/HowToContribute\n\n");

		List<String> srcs = new ArrayList<String>(PrayerBookApplication
				.getInstance().getCatalog().getAllSources());
		if (srcs.size() > 0) {
			sb.append("Джерела текстів:\n");
			Collections.sort(srcs);
			for (String src : srcs) {
				sb.append(" • ").append(src).append("\n");
			}
		}
		((TextView) v.findViewById(R.id.tv_content)).setText(sb);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		activity.getActionBar().setTitle("Про " + getString(R.string.app_name));
	}
}
