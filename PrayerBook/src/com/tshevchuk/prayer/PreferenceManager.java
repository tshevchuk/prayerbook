package com.tshevchuk.prayer;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
	private static PreferenceManager instance = new PreferenceManager();

	private SharedPreferences sharedPrefs;

	private PreferenceManager() {
		sharedPrefs = PrayerBookApplication.getInstance().getSharedPreferences(
				"prefs", Context.MODE_PRIVATE);
	}

	public static PreferenceManager getInstance() {
		return instance;
	}

	public boolean isNightModeEnabled() {
		return sharedPrefs.getBoolean("night_mode", false);
	}

	public void setNightModeEnabled(boolean enabled) {
		sharedPrefs.edit().putBoolean("night_mode", enabled).apply();
	}
}
