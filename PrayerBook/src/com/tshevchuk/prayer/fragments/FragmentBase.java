package com.tshevchuk.prayer.fragments;

import android.app.Fragment;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tshevchuk.prayer.PrayerBookApplication;

public class FragmentBase extends Fragment {

	@Override
	public void onResume() {
		super.onResume();
		Tracker t = PrayerBookApplication.getInstance().getTracker();
		t.setScreenName(getClass().getSimpleName());
		t.send(new HitBuilders.AppViewBuilder().build());
	}

	public boolean isSameScreen(FragmentBase f) {
		return getClass().equals(f.getClass());
	}
}
