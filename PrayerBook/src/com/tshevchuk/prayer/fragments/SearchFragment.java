package com.tshevchuk.prayer.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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

import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.adapters.SearchListAdapter;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemSubMenu;
import com.tshevchuk.prayer.data.SearchItem;

public class SearchFragment extends FragmentBase {
	private String searchPhrase;
	private List<SearchItem> items;
	private ListView lvItems;
	private TextView tvHeader;
	private HomeActivity activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (HomeActivity) activity;
	}

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
				((HomeActivity) getActivity()).displayMenuItem(items.get(
						position - 1).getMenuItem());
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
			lvItems.setAdapter(new SearchListAdapter(getActivity(),
					filter(this.searchPhrase)));
		}
	}

	private List<SearchItem> filter(String searchPhrase) {
		Catalog cat = PrayerBookApplication.getInstance().getCatalog();

		tvHeader.setText(String.format("Для «%s» знайдено:", searchPhrase));

		List<SearchItem> filtered = new ArrayList<SearchItem>();

		if (TextUtils.isEmpty(searchPhrase)) {
			return filtered;
		}

		for (MenuItemBase mi : cat.getTopMenuItems()) {
			filter(searchPhrase, filtered, mi);
		}
		items = filtered;
		return filtered;
	}

	private List<SearchItem> filter(String searchPhrase,
			List<SearchItem> filtered, MenuItemBase mi) {
		String name = mi.getName().toLowerCase(Utils.getUkrainianLocale())
				.replace('’', '\'');
		int searchPhraseStartPos = name.indexOf(searchPhrase);
		if (searchPhraseStartPos != -1) {
			StringBuilder sb = new StringBuilder(mi.getName());
			sb.insert(searchPhraseStartPos + searchPhrase.length(), "</b>");
			sb.insert(searchPhraseStartPos, "<b>");
			filtered.add(new SearchItem(mi, sb.toString()));
		}

		if (mi instanceof MenuItemSubMenu) {
			for (MenuItemBase si : ((MenuItemSubMenu) mi).getSubItems()) {
				filter(searchPhrase, filtered, si);
			}
		}
		return filtered;
	}

	@Override
	public void onResume() {
		super.onResume();
		activity.getActionBar().setTitle("Пошук");
	}
}
