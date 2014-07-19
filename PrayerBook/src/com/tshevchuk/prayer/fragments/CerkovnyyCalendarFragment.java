package com.tshevchuk.prayer.fragments;

import java.util.Calendar;

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

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tshevchuk.prayer.Analytics;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.adapters.CerkovnyyCalendarListAdapter;
import com.tshevchuk.prayer.data.CerkovnyyCalendar;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemCalendar;

public class CerkovnyyCalendarFragment extends FragmentBase {
	private int year;
	private int currentYear;
	private CerkovnyyCalendar calendar = CerkovnyyCalendar.getInstance();
	private int prevFirstVisibleItem;
	private int[] years = calendar.getYears();
	private String[] formattedYears;
	private MenuItemCalendar menuItem;
	private Integer initPosition;

	private Activity activity;
	private ListView lvCalendar;
	private TextView tvMonth;
	private ActionBar actionBar;

	public static CerkovnyyCalendarFragment getInstance(MenuItemCalendar cal) {
		CerkovnyyCalendarFragment f = new CerkovnyyCalendarFragment();
		Bundle args = new Bundle();
		args.putSerializable("menu_item", cal);
		f.setArguments(args);
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

		year = currentYear = java.util.Calendar.getInstance().get(
				java.util.Calendar.YEAR);
		menuItem = (MenuItemCalendar) getArguments().getSerializable(
				"menu_item");
		actionBar = getActivity().getActionBar();
		formattedYears = new String[years.length];
		for (int i = 0; i < years.length; ++i) {
			formattedYears[i] = years[i] + " рік";
		}

		if (savedInstanceState != null) {
			year = savedInstanceState.getInt("year");
		}

		if (savedInstanceState != null) {
			initPosition = savedInstanceState.getInt("firstVisiblePosition");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

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
		showCalendarForYear(year, initPosition);

		return v;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		initPosition = lvCalendar.getFirstVisiblePosition();
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
						if (years[itemPosition] != year) {
							showCalendarForYear(years[itemPosition], null);
							Tracker t = PrayerBookApplication.getInstance()
									.getTracker();
							t.send(new HitBuilders.EventBuilder()
									.setCategory(
											Analytics.CAT_CERKOVNYY_CALENDAR)
									.setAction("Вибрано рік")
									.setLabel(formattedYears[itemPosition])
									.build());
						}
						return true;
					}
				});
		for (int i = 0; i < years.length; ++i) {
			if (years[i] == year) {
				actionBar.setSelectedNavigationItem(i);
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("year", year);
		outState.putInt("firstVisiblePosition",
				lvCalendar.getFirstVisiblePosition());
	}

	private void showCalendarForYear(int year, Integer position) {
		lvCalendar.setAdapter(new CerkovnyyCalendarListAdapter(activity, year));

		if (position == null) {
			position = (year == currentYear) ? (java.util.Calendar
					.getInstance().get(java.util.Calendar.DAY_OF_YEAR) - 1) : 0;
		}

		this.year = year;

		lvCalendar.setSelection(position);

		final int pos = position;
		lvCalendar.post(new Runnable() {
			@Override
			public void run() {
				updateMonth(pos, true);
			}
		});
	}

	private void updateMonth(int firstVisibleItem, boolean force) {
		if (prevFirstVisibleItem != firstVisibleItem || force) {
			java.util.Calendar cal1 = java.util.Calendar.getInstance();
			cal1.set(java.util.Calendar.YEAR, year);
			cal1.set(java.util.Calendar.DAY_OF_YEAR, firstVisibleItem + 1);
			java.util.Calendar cal2 = java.util.Calendar.getInstance();
			cal2.set(java.util.Calendar.YEAR, year);
			cal2.set(java.util.Calendar.DAY_OF_YEAR,
					lvCalendar.getLastVisiblePosition() + 1);
			StringBuilder sb = new StringBuilder();
			sb.append(CerkovnyyCalendarListAdapter.MONTHES[cal1
					.get(java.util.Calendar.MONTH)]);
			if (cal2.get(Calendar.MONTH) != cal1.get(Calendar.MONTH)) {
				sb.append('-').append(
						CerkovnyyCalendarListAdapter.MONTHES[cal2
								.get(java.util.Calendar.MONTH)]);
			}
			sb.append(' ').append(year).append(" року");
			tvMonth.setText(sb);
			prevFirstVisibleItem = firstVisibleItem;
		}
	}

	@Override
	protected MenuItemBase getMenuItem() {
		return menuItem;
	}
}
