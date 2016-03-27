package com.tshevchuk.prayer.presentation.often_used;

import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.CerkovnyyCalendar;
import com.tshevchuk.prayer.domain.model.CalendarDay;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuItemOftenUsed;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.base.FragmentBase;
import com.tshevchuk.prayer.presentation.home.HomeActivity;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class OftenUsedFragment extends FragmentBase {
	@Inject
	CerkovnyyCalendar cerkovnyyCalendar;
	@Inject
	OftenUsedPresenter presenter;
	private MenuItemOftenUsed menuItem;
	private List<MenuItemBase> oftenUsedItems;
	private ListView lvItems;
	private TextView tvDay;
	private TextView tvDescription;
	private LinearLayout llToday;

	public static OftenUsedFragment getInstance(MenuItemOftenUsed menuItem) {
		OftenUsedFragment f = new OftenUsedFragment();
		Bundle b = new Bundle();
		b.putParcelable("menuItem", Parcels.wrap(menuItem));
		f.setArguments(b);
		return f;
	}

	@Override
	protected String getScreenTitle() {
		return getString(R.string.app_name);
	}

	@Override
	protected BasePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected boolean isNavigationDrawerEnabled() {
		return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((PrayerBookApplication) getActivity().getApplication())
				.getViewComponent().inject(this);
		menuItem = Parcels.unwrap(getArguments().getParcelable(
				"menuItem"));
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
				activity.displayMenuItem(catalog.getMenuItemById(Catalog.ID_CALENDAR));
				//todo: add update of recently used
			}
		});
		lvItems.addHeaderView(calendarToday);

		lvItems.setAdapter(new ArrayAdapter<>(getActivity(),
				android.R.layout.simple_list_item_1, new MenuItemBase[0]));
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((HomeActivity) getActivity()).displayMenuItem(oftenUsedItems
						.get(position - 1));
				//todo: add update of recently used
			}
		});

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();

		int[] recentIds = preferenceManager.getRecentMenuItems();
		oftenUsedItems = new ArrayList<>(recentIds.length);
		for (int id : recentIds) {
			MenuItemBase mi = catalog.getMenuItemById(id);
			if (mi != null) {
				oftenUsedItems.add(mi);
			}
		}
		lvItems.setAdapter(new OftenUsedListAdapter(activity, oftenUsedItems, catalog, preferenceManager));

		llToday.setVisibility(View.VISIBLE);
		CalendarDay day = cerkovnyyCalendar.getCalendarDay(new Date());
		String d = new SimpleDateFormat("d EE", Utils.getUkrainianLocale())
				.format(day.getDay());
		if (day.isDateRed()) {
			d = "<font color=\"red\">" + d + "</font>";
		}
		tvDay.setText(Html.fromHtml(d));
		int fontSizeSp = preferenceManager.getFontSizeSp();
		tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
		tvDescription.setText(Html.fromHtml(day.getDescription().toString()));
		tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
	}

	@Override
	public MenuItemBase getMenuItem() {
		return menuItem;
	}
}
