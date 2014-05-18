package com.tshevchuk.prayer.fragments;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.text.TextUtils;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.Tracker;
import com.tshevchuk.prayer.Analytics;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.PreferenceManager;
import com.tshevchuk.prayer.R;

public class SettingsFragment extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	public void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(PreferenceManager.PREF_NIGHT_MODE)) {
			sendAnalyticsSettingsChanged(key,
					String.valueOf(sharedPreferences.getBoolean(key, false)));
			getActivity().recreate();
		} else if (key.equals(PreferenceManager.PREF_TEXT_FONT_SIZE)) {
			sendAnalyticsSettingsChanged(key,
					sharedPreferences.getString(key, ""));
		}
	}

	public void sendAnalyticsSettingsChanged(CharSequence settingsName,
			CharSequence value) {
		Tracker t = PrayerBookApplication.getInstance().getTracker();
		EventBuilder event = new HitBuilders.EventBuilder().setCategory(
				Analytics.CAT_SETTINGS_CHANGED).setAction(
				settingsName.toString());
		if (!TextUtils.isEmpty(value)) {
			event.setLabel(value.toString());
		}
		t.send(event.build());
	}
}
