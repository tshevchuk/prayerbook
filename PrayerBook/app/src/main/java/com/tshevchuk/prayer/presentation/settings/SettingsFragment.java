package com.tshevchuk.prayer.presentation.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.MenuItem;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.about_app.AboutAppFragment;
import com.tshevchuk.prayer.presentation.home.HomeActivity;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragmentCompat implements
		OnSharedPreferenceChangeListener {
	@Inject
	AnalyticsManager analyticsManager;

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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((PrayerBookApplication) getActivity().getApplication())
				.getViewComponent().inject(this);
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
	public void onResume() {
		super.onResume();
		ActionBar actionBar = activity.getSupportActionBar();
		if (actionBar != null) {
			actionBar.setTitle("Налаштування");
		}
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

	private void sendAnalyticsSettingsChanged(String settingsName, String value) {
		analyticsManager.sendActionEvent(Analytics.CAT_SETTINGS_CHANGED,
				settingsName, value);
	}
}
