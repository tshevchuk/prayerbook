package com.tshevchuk.prayer.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.PreferenceManager;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.adapters.OftenUsedListAdapter;
import com.tshevchuk.prayer.data.CalendarDay;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.CerkovnyyCalendar;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemOftenUsed;

public class OftenUsedFragment extends FragmentBase {
	private MenuItemOftenUsed menuItem;
	private List<MenuItemBase> oftenUsedItems;
	private ListView lvItems;
	private TextView tvDay;
	private TextView tvDescription;
	private HomeActivity activity;
	private LinearLayout llToday;

	public static OftenUsedFragment getInstance(MenuItemOftenUsed menuItem) {
		OftenUsedFragment f = new OftenUsedFragment();
		Bundle b = new Bundle();
		b.putSerializable("menuItem", menuItem);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (HomeActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menuItem = (MenuItemOftenUsed) getArguments().getSerializable(
				"menuItem");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_often_used, container, false);
		lvItems = (ListView) v.findViewById(R.id.lvItems);

		View calendarToday = inflater.inflate(R.layout.i_calendar_for_today,
				lvItems, false);
		llToday = (LinearLayout) calendarToday.findViewById(R.id.ll_today);
		tvDay = (TextView) calendarToday.findViewById(R.id.tvDay);
		tvDescription = (TextView) calendarToday
				.findViewById(R.id.tvDescription);
		llToday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.displayMenuItem(PrayerBookApplication.getInstance()
						.getCatalog().getMenuItemById(Catalog.ID_CALENDAR));
			}
		});
		lvItems.addHeaderView(calendarToday);

		lvItems.setAdapter(new ArrayAdapter<MenuItemBase>(getActivity(),
				android.R.layout.simple_list_item_1, new MenuItemBase[0]));
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((HomeActivity) getActivity()).displayMenuItem(oftenUsedItems
						.get(position - 1));
			}
		});

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		activity.getSupportActionBar().setTitle(R.string.app_name);

		int[] recentIds = PreferenceManager.getInstance().getRecentMenuItems();
		oftenUsedItems = new ArrayList<MenuItemBase>(recentIds.length);
		Catalog cat = PrayerBookApplication.getInstance().getCatalog();
		for (int id : recentIds) {
			MenuItemBase mi = cat.getMenuItemById(id);
			if (mi != null) {
				oftenUsedItems.add(mi);
			}
		}
		lvItems.setAdapter(new OftenUsedListAdapter(activity, oftenUsedItems));

		llToday.setVisibility(View.VISIBLE);
		CerkovnyyCalendar cal = CerkovnyyCalendar.getInstance();
		CalendarDay day = cal.getCalendarDay(new Date());
		String d = new SimpleDateFormat("d EE", Utils.getUkrainianLocale())
				.format(day.getDay());
		if (day.isDateRed()) {
			d = "<font color=\"red\">" + d + "</font>";
		}
		tvDay.setText(Html.fromHtml(d));
		int fontSizeSp = PreferenceManager.getInstance().getFontSizeSp();
		tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
		tvDescription.setText(Html.fromHtml(day.getDescription().toString()));
		tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
	}

	@Override
	protected MenuItemBase getMenuItem() {
		return menuItem;
	}

	@Override
	protected boolean isNavigationDrawerEnabled() {
		return true;
	}
}
