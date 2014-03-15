package com.tshevchuk.prayer.data;

import org.json.JSONException;
import org.json.JSONObject;

public class PrayerPart {
	private String subTitle;
	private String text;

	public PrayerPart(JSONObject json) throws JSONException {
		subTitle = json.getString("subtitle");
		text = json.getString("text");
	}

	public String getText() {
		return text;
	}

	public String getSubTitle() {
		return subTitle;
	}
}
