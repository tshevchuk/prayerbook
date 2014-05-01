package com.tshevchuk.prayer.fragments;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.Catalog.Prayer;

public class AboutPrayerFragment extends FragmentBase {
	private Prayer prayer;

	public static AboutPrayerFragment getInstance(Prayer prayer) {
		AboutPrayerFragment f = new AboutPrayerFragment();
		Bundle b = new Bundle();
		b.putSerializable("prayer", prayer);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prayer = (Prayer) getArguments().getSerializable("prayer");
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_about_prayer, container, false);
		((TextView) v.findViewById(R.id.tv_name)).setText(prayer.getFullName());
		TextView tvAbout = (TextView) v.findViewById(R.id.tv_about);
		tvAbout.setText(Html.fromHtml(prayer.getAbout()));
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle("Опис");
	}
}
