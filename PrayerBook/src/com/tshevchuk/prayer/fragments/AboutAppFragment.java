package com.tshevchuk.prayer.fragments;

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
		sb.append("Джерела текстів:\n");
		for (String src : PrayerBookApplication.getInstance().getCatalog()
				.getAllSources()) {
			sb.append(" • ").append(src).append("\n");
		}
		((TextView) v.findViewById(R.id.tv_content)).setText(sb);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle("Про Молитовник");
	}
}
