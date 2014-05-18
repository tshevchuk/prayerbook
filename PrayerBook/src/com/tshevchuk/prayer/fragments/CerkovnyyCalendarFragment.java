package com.tshevchuk.prayer.fragments;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.adapters.CerkovnyyCalendarListAdapter;
import com.tshevchuk.prayer.data.Catalog.Calendar;
import com.tshevchuk.prayer.data.CerkovnyyCalendar;

public class CerkovnyyCalendarFragment extends FragmentBase {
	private int year;
	private int currentYear;
	private CerkovnyyCalendar calendar = CerkovnyyCalendar.getInstance();
	private int prevFirstVisibleItem;
	private int[] years = calendar.getYears();
	private String[] formattedYears;

	private Activity activity;
	private ListView lvCalendar;
	private TextView tvMonth;
	private ActionBar actionBar;

	public static CerkovnyyCalendarFragment getInstance(Calendar cal) {
		CerkovnyyCalendarFragment f = new CerkovnyyCalendarFragment();
		return f;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getActivity().getActionBar();
		year = currentYear = java.util.Calendar.getInstance().get(
				java.util.Calendar.YEAR);
		formattedYears = new String[years.length];
		for (int i = 0; i < years.length; ++i) {
			formattedYears[i] = years[i] + " рік";
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_cerkovnyy_calendar, container,
				false);
		lvCalendar = (ListView) v.findViewById(R.id.lvCalendar);
		tvMonth = (TextView) v.findViewById(R.id.tvMonth);

		lvCalendar.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				updateMonth(firstVisibleItem, false);
			}
		});
		showCalendarForYear(year);

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		actionBar.setTitle("Церковний календар");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(new ArrayAdapter<String>(activity,
				android.R.layout.simple_spinner_dropdown_item, formattedYears),
				new OnNavigationListener() {
					@Override
					public boolean onNavigationItemSelected(int itemPosition,
							long itemId) {
						showCalendarForYear(years[itemPosition]);
						return true;
					}
				});
		for(int i = 0; i < years.length; ++i){
			if(years[i] == year){
				actionBar.setSelectedNavigationItem(i);
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

	private void showCalendarForYear(int year) {
		lvCalendar.setAdapter(new CerkovnyyCalendarListAdapter(activity, year));
		int position = 0;
		if (year == currentYear) {
			position = java.util.Calendar.getInstance().get(
					java.util.Calendar.DAY_OF_YEAR) - 1;
			lvCalendar.setSelection(position);
		} else {
			lvCalendar.setSelection(0);
		}
		this.year = year;
		updateMonth(position, true);
	}

	private void updateMonth(int firstVisibleItem, boolean force) {
		if (prevFirstVisibleItem != firstVisibleItem || force) {
			java.util.Calendar cal = java.util.Calendar.getInstance();
			cal.set(java.util.Calendar.YEAR, year);
			cal.set(java.util.Calendar.DAY_OF_YEAR, firstVisibleItem + 1);
			tvMonth.setText(CerkovnyyCalendarListAdapter.MONTHES[cal
					.get(java.util.Calendar.MONTH)] + " " + year + " року");
			prevFirstVisibleItem = firstVisibleItem;
		}
	}
}
