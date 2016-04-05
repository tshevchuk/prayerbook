package com.tshevchuk.prayer.data;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

public class PrayerLoader extends AsyncTaskLoader<CharSequence> {
	public static final String PARAM_ASSET_FILE_NAME = "asset_file_name";
	public static final String PARAM_IS_HTML = "is_html";

    private final String assetFileName;
    private final boolean isHtml;

    private final Context context;

	public PrayerLoader(Context context, Bundle args) {
		super(context);
		assetFileName = args.getString(PARAM_ASSET_FILE_NAME);
		isHtml = args.getBoolean(PARAM_IS_HTML);
		this.context = context;
	}

	@Override
	public CharSequence loadInBackground() {

		return parsedHtml;
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
        forceLoad();
    }



}
