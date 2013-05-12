package com.tshevchuk.prayer_book;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
	public static String getAssetAsString(String fileName) throws IOException{
		InputStream input = PrayerBookApplication.getInstance().getAssets().open(fileName);
		java.util.Scanner s = new java.util.Scanner(input).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
