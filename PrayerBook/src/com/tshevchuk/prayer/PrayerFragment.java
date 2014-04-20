package com.tshevchuk.prayer;

import java.util.List;

import org.json.JSONArray;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tshevchuk.prayer.data.Prayer;
import com.tshevchuk.prayer.data.PrayerPart;

public class PrayerFragment extends FragmentBase {
	private Prayer prayer;
	private LinearLayout llPrayer;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getActivity().getActionBar().setTitle("Щоденні молитви");
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.f_prayer, container, false);
		llPrayer = (LinearLayout) view.findViewById(R.id.ll_prayer);

		try {
			JSONArray json = new JSONArray(
					Utils.getAssetAsString("molytvy.json"));
			prayer = new Prayer(json.getJSONObject(0));
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (prayer != null) {
			getActivity().getActionBar().setTitle(prayer.getTitle());
			List<PrayerPart> parts = prayer.getParts();
			for (PrayerPart p : parts) {
				View v = inflater.inflate(R.layout.i_prayer_part, llPrayer,
						false);
				TextView tvSubtitle = (TextView) v
						.findViewById(R.id.tv_subtitle);
				tvSubtitle.setText(p.getSubTitle());
				if(TextUtils.isEmpty(p.getSubTitle()))
					tvSubtitle.setVisibility(View.GONE);
				((TextView) v.findViewById(R.id.tv_prayer_part_text)).setText(p
						.getText());
				llPrayer.addView(v);
			}
		}

		return view;
	}
}
