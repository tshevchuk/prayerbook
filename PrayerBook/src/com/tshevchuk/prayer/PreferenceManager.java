package com.tshevchuk.prayer;

import android.content.SharedPreferences;

public class PreferenceManager {
	public static final String PREF_TEXT_FONT_SIZE = "pref_textFontSize";
	public static final String PREF_NIGHT_MODE = "pref_nightMode";

	private static PreferenceManager instance = new PreferenceManager();

	private SharedPreferences sharedPrefs;

	private PreferenceManager() {
		sharedPrefs = android.preference.PreferenceManager
				.getDefaultSharedPreferences(PrayerBookApplication
						.getInstance());
	}

	public static PreferenceManager getInstance() {
		return instance;
	}

	public boolean isNightModeEnabled() {
		return sharedPrefs.getBoolean(PREF_NIGHT_MODE, false);
	}

	public int getFontSizeSp() {
		return Integer.parseInt(sharedPrefs
				.getString(PREF_TEXT_FONT_SIZE, "18"));
	}
}
