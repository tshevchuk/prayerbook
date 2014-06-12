package com.tshevchuk.prayer;

import android.content.SharedPreferences;
import android.text.TextUtils;

public class PreferenceManager {
	public static final String PREF_TEXT_FONT_SIZE = "pref_textFontSize";
	public static final String PREF_NIGHT_MODE = "pref_nightMode";
	public static final String PREF_RECENT_MENU_ITEMS = "pref_recentMenuItems";
	public static final String PREF_ABOUT_APP = "pref_aboutApp";

	private static final int MAX_RECENT_ITEMS_COUNT = 30;

	private static PreferenceManager instance = new PreferenceManager();

	private SharedPreferences sharedPrefs;
	private int[] recentMenuItemsId;
	private float[] recentMenuItemsShowCount;

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
			String[] items = TextUtils.split(s, "\\|");
			int[] ids = new int[items.length / 2];
			float[] showCount = new float[ids.length];
			for (int i = 0; i < ids.length; i++) {
				ids[i] = Integer.parseInt(items[i * 2]);
				showCount[i] = Float.parseFloat(items[i * 2 + 1]);
			}
			recentMenuItemsId = ids;
			recentMenuItemsShowCount = showCount;
		}
		return recentMenuItemsId;
	}

	public synchronized void markMenuItemAsOpened(int menuItemId) {
		int[] recentIds = getRecentMenuItems();
		float[] recentShowCount = recentMenuItemsShowCount;

		int index = -1;
		float curShowCount = 1;
		for (int i = 0; i < recentIds.length; ++i) {
			if (recentIds[i] == menuItemId) {
				index = i;
				curShowCount = recentShowCount[i] + 1;
			} else {
				recentShowCount[i] *= .99f;
			}
		}

		int newIndex = index == -1 ? recentIds.length : index;
		for (int i = newIndex - 1; i >= 0; --i) {
			if (recentShowCount[i] < curShowCount) {
				newIndex = i;
			} else {
				break;
			}
		}

		int newSize = index == -1 ? Math.min(MAX_RECENT_ITEMS_COUNT,
				recentIds.length + 1) : recentIds.length;

		int[] newRecentIds = new int[newSize];
		float[] newShowCount = new float[newSize];
		for (int i = 0; i < newRecentIds.length; ++i) {
			if (i == newIndex) {
				newRecentIds[i] = menuItemId;
				newShowCount[i] = curShowCount;
			} else if (i < newIndex
					|| (i > newIndex && index != -1 && i > index)) {
				newRecentIds[i] = recentIds[i];
				newShowCount[i] = recentShowCount[i];
			} else {
				newRecentIds[i] = recentIds[i - 1];
				newShowCount[i] = recentShowCount[i - 1];
			}
		}
		StringBuilder sb = new StringBuilder(10000);
		for (int i = 0; i < newRecentIds.length; ++i) {
			if (i > 0) {
				sb.append('|');
			}
			sb.append(newRecentIds[i]).append('|').append(newShowCount[i]);
		}
		recentMenuItemsId = newRecentIds;
		recentMenuItemsShowCount = newShowCount;
		sharedPrefs.edit().putString(PREF_RECENT_MENU_ITEMS, sb.toString())
				.apply();
	}
}
