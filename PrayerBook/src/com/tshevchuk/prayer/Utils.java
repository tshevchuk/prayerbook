package com.tshevchuk.prayer;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class Utils {
	public static String getAssetAsString(String fileName) throws IOException {
		InputStream input = PrayerBookApplication.getInstance().getAssets()
				.open(fileName);
		java.util.Scanner s = new java.util.Scanner(input);
		try {
			s = s.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		} finally {
			s.close();
		}
	}
	
	public static boolean isDebuggable(){
		Context c = PrayerBookApplication.getInstance();
		return 0 != (c.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE);
	}
	
}
