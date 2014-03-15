package com.tshevchuk.prayer;

import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tshevchuk.prayer.data.Prayer;
import com.tshevchuk.prayer.data.PrayerPart;
import com.tshevchuk.prayer.R;

public class PrayerActivity extends Activity {
	private Prayer prayer;
	private LinearLayout llPrayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_prayer);

		llPrayer = (LinearLayout) findViewById(R.id.ll_prayer);

		try {
			JSONArray json = new JSONArray(
					Utils.getAssetAsString("molytvy.json"));
			prayer = new Prayer(json.getJSONObject(0));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (prayer != null) {
			setTitle(prayer.getTitle());
			List<PrayerPart> parts = prayer.getParts();
			for (PrayerPart p : parts) {
				View v = getLayoutInflater().inflate(R.layout.i_prayer_part,
						llPrayer, false);
				((TextView) v.findViewById(R.id.tv_subtitle)).setText(p
						.getSubTitle());
				((TextView) v.findViewById(R.id.tv_prayer_part_text)).setText(p
						.getText());
				llPrayer.addView(v);
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// empty
	}
}
