package com.tshevchuk.prayer.presentation.search;

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

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.SearchItem;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.base.FragmentBase;

import java.util.List;

import javax.inject.Inject;

public class SearchFragment extends FragmentBase {
	@Inject
	SearchPresenter presenter;
	private String searchPhrase;
	private List<SearchItem> items;
	private ListView lvItems;
	private TextView tvHeader;

	@Override
	protected String getScreenTitle() {
		return getString(R.string.search__search);
	}

	@Override
	protected BasePresenter getPresenter() {
		return presenter;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		((PrayerBookApplication) getActivity().getApplication())
				.getViewComponent().inject(this);
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
				//todo: implement
				//((HomeActivity) getActivity()).displayMenuItem(mi);
				//todo: add update of recently used
				analyticsManager.sendActionEvent(Analytics.CAT_SEARCH,
						"Вибрано елемент на фрагменті результатів пошуку",
						mi.getId() + " " + mi.getName());
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

			items = catalog.filter(this.searchPhrase);
			lvItems.setAdapter(new SearchListAdapter(getActivity(), items, catalog, preferenceManager));
			tvHeader.setText(String.format("Для «%s» знайдено:", searchPhrase));
		}
	}
}
