package com.tjeannin.apprate;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.content.SharedPreferences;

public class ExceptionHandler implements UncaughtExceptionHandler {

	private UncaughtExceptionHandler defaultExceptionHandler;
	SharedPreferences preferences;

	// Constructor.
	public ExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler, Context context)
	{
		preferences = context.getSharedPreferences(PrefsContract.SHARED_PREFS_NAME, 0);
		defaultExceptionHandler = uncaughtExceptionHandler;
	}

	public void uncaughtException(Thread thread, Throwable throwable) {

		preferences.edit().putBoolean(PrefsContract.PREF_APP_HAS_CRASHED, true).commit();

		// Call the original handler.
		defaultExceptionHandler.uncaughtException(thread, throwable);
	}
}