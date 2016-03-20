package com.tshevchuk.prayer.presentation.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.Tracker;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.Analytics;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.SearchItem;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.activities.HomeActivity;
import com.tshevchuk.prayer.presentation.adapters.SearchListAdapter;

import java.util.List;

public class SearchFragment extends FragmentBase {
	private String searchPhrase;
	private List<SearchItem> items;
	private ListView lvItems;
	private TextView tvHeader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_search, container, false);
		lvItems = (ListView) v.findViewById(R.id.lvItems);
		tvHeader = (TextView) inflater.inflate(R.layout.f_search_header,
				lvItems, false);
		lvItems.addHeaderView(tvHeader, null, false);
		setItemsForSearchPhrase(searchPhrase);
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MenuItemBase mi = items.get(position - 1).getMenuItem();
				((HomeActivity) getActivity()).displayMenuItem(mi);

				Tracker t = PrayerBookApplication.getInstance().getTracker();
				EventBuilder event = new HitBuilders.EventBuilder()
						.setCategory(Analytics.CAT_SEARCH)
						.setAction(
								"Вибрано елемент на фрагменті результатів пошуку")
						.setLabel(mi.getId() + " " + mi.getName());
				t.send(event.build());
			}
		});

		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		activity.getSearchView().setIconified(false);
	}

	public void setItemsForSearchPhrase(String searchPhrase) {
		this.searchPhrase = TextUtils.isEmpty(searchPhrase) ? searchPhrase
				: searchPhrase.toLowerCase(Utils.getUkrainianLocale());
		if (lvItems != null) {

			items = PrayerBookApplication.getInstance().getCatalog()
					.filter(this.searchPhrase);
			lvItems.setAdapter(new SearchListAdapter(getActivity(), items));
			tvHeader.setText(String.format("Для «%s» знайдено:", searchPhrase));
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		ActionBar actionBar = activity.getSupportActionBar();
		if (actionBar != null) {
			actionBar.setTitle("Пошук");
		}
	}
}
