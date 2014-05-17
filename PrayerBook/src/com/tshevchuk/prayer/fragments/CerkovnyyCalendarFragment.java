package com.tshevchuk.prayer.fragments;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;

import com.tshevchuk.prayer.CerkovnyyCalendarListAdapter;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.UIUtils;
import com.tshevchuk.prayer.data.Catalog.Calendar;
import com.tshevchuk.prayer.data.CerkovnyyCalendar;

public class CerkovnyyCalendarFragment extends FragmentBase {
	private int year;
	private int currentYear;
	private CerkovnyyCalendar calendar = CerkovnyyCalendar.getInstance();
	private int prevFirstVisibleItem;

	private Activity activity;
	private ListView lvCalendar;
	private TextView tvMonth;

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
		year = currentYear = java.util.Calendar.getInstance().get(
				java.util.Calendar.YEAR);
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
				updateMonth(firstVisibleItem);
			}
		});
		showCalendarForYear(year);

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setTitle("Церковний календар");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.removeAllTabs();
		for (int i = 0; i < calendar.getYearsCount(); ++i) {
			int year = calendar.getYearByIndex(i);
			ActionBar.Tab tab = actionBar.newTab().setText(year + " рік")
					.setTabListener(new TabListener<Fragment>(year));
			actionBar.addTab(tab, year == this.year);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private void showCalendarForYear(int year) {
		lvCalendar.setAdapter(new CerkovnyyCalendarListAdapter(activity, year));
		int position = 0;
		if (year == currentYear) {
			position = java.util.Calendar.getInstance().get(
					java.util.Calendar.DAY_OF_YEAR) - 1;
			lvCalendar.setSelectionFromTop(position, UIUtils.dpToPx(40));
		} else {
			lvCalendar.setSelection(0);
		}
		updateMonth(position);
		this.year = year;
	}

	private void updateMonth(int firstVisibleItem) {
		if (prevFirstVisibleItem != firstVisibleItem) {
			java.util.Calendar cal = java.util.Calendar.getInstance();
			cal.set(java.util.Calendar.YEAR, year);
			cal.set(java.util.Calendar.DAY_OF_YEAR, firstVisibleItem + 1);
			tvMonth.setText(CerkovnyyCalendarListAdapter.MONTHES[cal
					.get(java.util.Calendar.MONTH)] + " " + year + " року");
			prevFirstVisibleItem = firstVisibleItem;
		}
	}

	private class TabListener<T extends Fragment> implements
			ActionBar.TabListener {
		private int year;

		public TabListener(int year) {
			this.year = year;
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			showCalendarForYear(year);
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// empty
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// empty
		}
	}
}
