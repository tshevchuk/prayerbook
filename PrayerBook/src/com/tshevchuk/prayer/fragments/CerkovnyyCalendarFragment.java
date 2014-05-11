package com.tshevchuk.prayer.fragments;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.Catalog.Calendar;
import com.tshevchuk.prayer.data.CerkovnyyCalendar;

public class CerkovnyyCalendarFragment extends FragmentBase {

	public static CerkovnyyCalendarFragment getInstance(Calendar cal) {
		CerkovnyyCalendarFragment f = new CerkovnyyCalendarFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_cerkovnyy_calendar, container,
				false);
		TextView tvCalendar = (TextView) v.findViewById(R.id.tv_calendar);
		StringBuilder sb = new StringBuilder();
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(2014, 6, 1);
		CerkovnyyCalendar cc = CerkovnyyCalendar.getInstance();
		while(cal.get(java.util.Calendar.YEAR) < 2015){
			sb.append(cc.getCalendarDay(cal.getTime())).append("\n\n");
			cal.add(java.util.Calendar.DAY_OF_MONTH, 1);
		}
		tvCalendar.setText(Html.fromHtml(sb.toString().replaceAll("\n", "<br>")));
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle("Церковний календар");
	}
}
