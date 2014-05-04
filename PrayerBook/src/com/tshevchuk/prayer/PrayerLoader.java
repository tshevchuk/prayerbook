package com.tshevchuk.prayer;

import java.io.IOException;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;

public class PrayerLoader extends AsyncTaskLoader<CharSequence> {
	public static final String PARAM_ASSET_FILE_NAME = "asset_file_name";
	public static final String PARAM_IS_HTML = "is_html";

	private String assetFileName;
	private boolean isHtml;
	private CharSequence data;

	public PrayerLoader(Context context, Bundle args) {
		super(context);
		assetFileName = args.getString(PARAM_ASSET_FILE_NAME);
		isHtml = args.getBoolean(PARAM_IS_HTML);
	}

	@Override
	public CharSequence loadInBackground() {
		String html = "";
		try {
			html = Utils.getAssetAsString(assetFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		CharSequence parsedHtml = null;
		if (isHtml) {
			parsedHtml = Html.fromHtml(html);
		} else {
			parsedHtml = html;
		}
		return parsedHtml;
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		if (TextUtils.isEmpty(data))
			forceLoad();
		else
			deliverResult(data);
	}

}
