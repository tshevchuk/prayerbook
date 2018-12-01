package com.tshevchuk.prayer;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class Utils {
	private static Locale ukrainianLocale;
	private final Context context;

	public Utils(Context context) {
		this.context = context;
	}

	public static String getAssetAsString(Context context, String fileName) throws IOException {
		InputStream input = context.getAssets().open(fileName);
		java.util.Scanner s = new java.util.Scanner(input);
		//noinspection TryFinallyCanBeTryWithResources
		try {
			s.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		} finally {
			s.close();
		}
	}

	public static boolean isDebuggable(Context context) {
		return 0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE);
	}

	public static Locale getUkrainianLocale() {
		if (ukrainianLocale == null) {
			ukrainianLocale = new Locale("ukr", "UKR");
		}
		return ukrainianLocale;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public String getDeviceInfo() {
		DisplayMetrics metrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(metrics);

		return "OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")"
				+ "\nOS API Level: " + android.os.Build.VERSION.SDK_INT
				+ "\nDevice: " + android.os.Build.DEVICE
				+ "\nModel (and Product): " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")"
				+ "\nScreen size: " + metrics.widthPixels + "x" + metrics.heightPixels + " (" + metrics.densityDpi + ")";
	}

	public String getApplicationNameAndVersion() {
		PackageManager packageManager = context.getPackageManager();
		ApplicationInfo applicationInfo;
		String pkgNm = context.getPackageName();
		String ver = "";
		try {
			applicationInfo = packageManager.getApplicationInfo(pkgNm, 0);
			ver = context.getPackageManager().getPackageInfo(pkgNm, 0).versionName;
		} catch (final NameNotFoundException e) {
			applicationInfo = null;
		}
		return applicationInfo != null ? packageManager
				.getApplicationLabel(applicationInfo) + " " + ver : "(unknown)";
	}
}
