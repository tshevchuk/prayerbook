package com.tshevchuk.prayer.presentation;

import android.util.DisplayMetrics;

public class UIUtils {
	public static int dpToPx(int dp) {
		DisplayMetrics displayMetrics = PrayerBookApplication.getInstance()
				.getResources().getDisplayMetrics();
		return Math.round(dp
				* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}
}
