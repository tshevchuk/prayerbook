package com.tshevchuk.prayer;

import java.io.IOException;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;

public class PrayerLoader extends AsyncTaskLoader<CharSequence> {
	public static final String PARAM_ASSET_FILE_NAME = "asset_file_name";
	public static final String PARAM_IS_HTML = "is_html";

	private String assetFileName;
	private boolean isHtml;
	private CharSequence data;

	private Context context;

	public PrayerLoader(Context context, Bundle args) {
		super(context);
		assetFileName = args.getString(PARAM_ASSET_FILE_NAME);
		isHtml = args.getBoolean(PARAM_IS_HTML);
		this.context = context;
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
			parsedHtml = Html.fromHtml(html, new ImageGetter(), null);
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

	private class ImageGetter implements android.text.Html.ImageGetter {

		public Drawable getDrawable(String source) {
			int id;

			id = context.getResources().getIdentifier("content_" + source, "drawable",
					context.getPackageName());

			if (id == 0) {
				// the drawable resource wasn't found in our package, maybe it
				// is a stock android drawable?
				id = context.getResources().getIdentifier(source, "drawable",
						"android");
			}

			if (id == 0) {
				// prevent a crash if the resource still can't be found
				return null;
			} else {
				Drawable d = context.getResources().getDrawable(id);
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				return d;
			}
		}

	}

}
