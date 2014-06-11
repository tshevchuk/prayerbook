package com.tshevchuk.prayer.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemSubMenu;

public class SearchFragment extends FragmentBase {
	private String searchPhrase;
	private List<MenuItemBase> items;
	private ListView lvItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_submenu, container, false);
		lvItems = (ListView) v.findViewById(R.id.lvItems);
		setItemsForSearchPhrase(searchPhrase);
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((HomeActivity) getActivity()).displayMenuItem(items
						.get(position));
			}
		});

		return v;
	}

	public void setItemsForSearchPhrase(String searchPhrase) {
		this.searchPhrase = TextUtils.isEmpty(searchPhrase) ? searchPhrase
				: searchPhrase.toLowerCase(Utils.getUkrainianLocale());
		if (lvItems != null) {
			lvItems.setAdapter(new ArrayAdapter<MenuItemBase>(getActivity(),
					android.R.layout.simple_list_item_1,
					filter(this.searchPhrase)));
		}
	}

	private List<MenuItemBase> filter(String searchPhrase) {
		Catalog cat = PrayerBookApplication.getInstance().getCatalog();
		List<MenuItemBase> filtered = new ArrayList<MenuItemBase>();

		if (TextUtils.isEmpty(searchPhrase)) {
			return filtered;
		}

		for (MenuItemBase mi : cat.getTopMenuItems()) {
			filter(searchPhrase, filtered, mi);
		}
		items = filtered;
		return filtered;
	}

	private List<MenuItemBase> filter(String searchPhrase,
			List<MenuItemBase> filtered, MenuItemBase mi) {
		String name = mi.getName().toLowerCase(Utils.getUkrainianLocale());
		if (name.indexOf(searchPhrase) != -1) {
			filtered.add(mi);
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
		getActivity().getActionBar().setTitle("Пошук");
	}

}
