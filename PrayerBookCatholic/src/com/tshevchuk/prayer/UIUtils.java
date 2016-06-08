package com.tshevchuk.prayer;

import android.util.DisplayMetrics;

public class UIUtils {
	public static int dpToPx(int dp) {
		DisplayMetrics displayMetrics = PrayerBookApplication.getInstance()
				.getResources().getDisplayMetrics();
		int px = Math.round(dp
				* (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}
}
