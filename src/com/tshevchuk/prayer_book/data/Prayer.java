package com.tshevchuk.prayer_book.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Prayer {
	private String title;
	private List<PrayerPart> parts;

	public Prayer(JSONObject json) throws JSONException {
		title = json.getString("title");
		JSONArray jsonParts = json.getJSONArray("content");
		parts = new ArrayList<PrayerPart>();
		for (int i = 0; i < jsonParts.length(); ++i) {
			parts.add(new PrayerPart(jsonParts.getJSONObject(i)));
		}
	}

	public String getTitle() {
		return title;
	}

	public List<PrayerPart> getParts() {
		return parts;
	}
}
