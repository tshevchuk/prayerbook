package com.tshevchuk.prayer.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemPrayer;

public class AboutPrayerFragment extends FragmentBase {
	private MenuItemPrayer prayer;

	public static AboutPrayerFragment getInstance(MenuItemPrayer prayer) {
		AboutPrayerFragment f = new AboutPrayerFragment();
		Bundle b = new Bundle();
		b.putSerializable("prayer", prayer);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prayer = (MenuItemPrayer) getArguments().getSerializable("prayer");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_about_prayer, container, false);
		((TextView) v.findViewById(R.id.tv_name)).setText(prayer.getFullName());
		TextView tvAbout = (TextView) v.findViewById(R.id.tv_about);
		String about = prayer.getAbout();
		if (prayer.isOfficialUGCCText()) {
			about += "<br><br>Цей текст/молитва належить до офіційних текстів УГКЦ";
		}
		tvAbout.setText(Html.fromHtml(about));
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		activity.getActionBar().setTitle("Опис");
	}

	@Override
	public MenuItemBase getMenuItem() {
		return prayer;
	}
}
