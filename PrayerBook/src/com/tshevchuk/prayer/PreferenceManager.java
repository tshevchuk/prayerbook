package com.tshevchuk.prayer;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

public class PreferenceManager {
	public static final String PREF_TEXT_FONT_SIZE = "pref_textFontSize";
	public static final String PREF_NIGHT_MODE = "pref_nightMode";
	public static final String PREF_RECENT_MENU_ITEMS = "pref_recentMenuItems";

	private static final int MAX_RECENT_ITEMS_COUNT = 30;

	private static PreferenceManager instance = new PreferenceManager();

	private SharedPreferences sharedPrefs;
	private int[] recentMenuItemsId;

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

	public synchronized int[] getRecentMenuItems() {
		if (recentMenuItemsId == null) {
			String s = sharedPrefs.getString(PREF_RECENT_MENU_ITEMS, "");
			String[] idsStr = TextUtils.split(s, "\\|");
			int[] ids = new int[idsStr.length];
			for (int i = 0; i < idsStr.length; ++i) {
				ids[i] = Integer.parseInt(idsStr[i]);
			}
			recentMenuItemsId = ids;
		}
		return recentMenuItemsId;
	}

	public synchronized void markMenuItemAsOpened(int menuItemId) {
		int[] recentIds = getRecentMenuItems();
		int index = -1;
		for (int i = 0; i < recentIds.length; ++i) {
			if (recentIds[i] == menuItemId) {
				index = i;
				break;
			}
		}
		int midIndex = MAX_RECENT_ITEMS_COUNT / 2;
		int newIndex;
		if (index >= 0 && index <= midIndex) {
			newIndex = Math.max(0, index - 1);
		} else {
			newIndex = Math.min(midIndex, recentIds.length);
		}
		int newSize = index == -1 ? Math.min(MAX_RECENT_ITEMS_COUNT,
				recentIds.length + 1) : recentIds.length;

		int[] newRecentIds = new int[newSize];
		for (int i = 0; i < newRecentIds.length; ++i) {
			if (i < newIndex) {
				newRecentIds[i] = recentIds[i];
			} else if (i == newIndex) {
				newRecentIds[i] = menuItemId;
			} else { // i > newIndex
				if (index == -1 || (i <= index)) {
					newRecentIds[i] = recentIds[i - 1];
				} else {
					newRecentIds[i] = recentIds[i];
				}
			}
		}
		StringBuilder sb = new StringBuilder(1000);
		sb.append(newRecentIds[0]);
		for (int i = 1; i < newRecentIds.length; ++i) {
			sb.append('|').append(newRecentIds[i]);
		}
		recentMenuItemsId = newRecentIds;
		sharedPrefs.edit().putString(PREF_RECENT_MENU_ITEMS, sb.toString())
				.apply();
	}
}
