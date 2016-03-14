package com.tshevchuk.prayer.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.Tracker;
import com.tshevchuk.prayer.Analytics;
import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.PreferenceManager;
import com.tshevchuk.prayer.R;

public class SettingsFragment extends PreferenceFragmentCompat implements
		OnSharedPreferenceChangeListener {
	private HomeActivity activity;

	@Override
	public void onAttach(Context context) { 
		super.onAttach(context);
		this.activity = (HomeActivity) getActivity();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		activity = null;
	}

	@Override
	public void onCreatePreferences(Bundle bundle, String s) {
		setHasOptionsMenu(true);
		addPreferencesFromResource(R.xml.preferences);
		Preference aboutApp = findPreference(PreferenceManager.PREF_ABOUT_APP);
		aboutApp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference arg0) {
				activity.displayFragment(new AboutAppFragment(), 0, null);
				return true;
			}
		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		TypedValue tv = new TypedValue();
		int abHeight = 0;
		if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize,
				tv, true)) {
			abHeight = TypedValue.complexToDimensionPixelSize(tv.data,
					getResources().getDisplayMetrics());
		}
		v.setPadding(0, abHeight, 0, 0);
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		activity.getSupportActionBar().setTitle("Налаштування");
		activity.setNavigationDrawerEnabled(false);

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
		switch (key) {
			case PreferenceManager.PREF_NIGHT_MODE:
				sendAnalyticsSettingsChanged(key,
						String.valueOf(sharedPreferences.getBoolean(key, false)));
				getActivity().recreate();
				break;
			case PreferenceManager.PREF_SHOW_OFFICIAL_UGCC:
				sendAnalyticsSettingsChanged(key,
						String.valueOf(sharedPreferences.getBoolean(key, false)));
				getActivity().recreate();
				break;
			case PreferenceManager.PREF_TEXT_FONT_SIZE:
				sendAnalyticsSettingsChanged(key,
						sharedPreferences.getString(key, ""));
				break;
			case PreferenceManager.PREF_DEFAULT_SCREENS:
				sendAnalyticsSettingsChanged(key,
						sharedPreferences.getString(key, ""));
				break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			activity.getFragmentManager().popBackStack();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void sendAnalyticsSettingsChanged(CharSequence settingsName,
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
