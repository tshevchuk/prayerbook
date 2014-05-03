package com.tshevchuk.prayer;

import java.io.IOException;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

public class PrayerLoader extends AsyncTaskLoader<Spanned> {
	public static final String PARAM_ASSET_FILE_NAME = "asset_file_name";

	private String assetFileName;
	private Spanned data;

	public PrayerLoader(Context context, Bundle args) {
		super(context);
		assetFileName = args.getString(PARAM_ASSET_FILE_NAME);
	}

	@Override
	public Spanned loadInBackground() {
		try {
			String html = Utils.getAssetAsString(assetFileName);
			Spanned parsedHtml = Html.fromHtml(html);
			return parsedHtml;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Html.fromHtml("");
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
