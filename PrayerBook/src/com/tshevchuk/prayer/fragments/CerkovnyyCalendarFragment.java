package com.tshevchuk.prayer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tshevchuk.prayer.CerkovnyyCalendarListAdapter;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.Catalog.Calendar;

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
		ListView lvCalendar = (ListView) v.findViewById(R.id.lvCalendar);
		lvCalendar.setAdapter(new CerkovnyyCalendarListAdapter(getActivity()));

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle("Церковний календар");
	}
}
