package com.tshevchuk.prayer.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.PreferenceManager;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemOftenUsed;

public class OftenUsedFragment extends FragmentBase {
	private MenuItemOftenUsed menuItem;
	private List<MenuItemBase> oftenUsedItems;
	private ListView lvItems;

	public static OftenUsedFragment getInstance(MenuItemOftenUsed menuItem) {
		OftenUsedFragment f = new OftenUsedFragment();
		Bundle b = new Bundle();
		b.putSerializable("menuItem", menuItem);
		f.setArguments(b);
		return f;
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
		lvItems.setAdapter(new ArrayAdapter<MenuItemBase>(getActivity(),
				android.R.layout.simple_list_item_1, new MenuItemBase[0]));
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((HomeActivity) getActivity()).displayMenuItem(oftenUsedItems
						.get(position));
			}
		});

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(menuItem.getName());

		int[] recentIds = PreferenceManager.getInstance().getRecentMenuItems();
		oftenUsedItems = new ArrayList<MenuItemBase>(recentIds.length);
		Catalog cat = PrayerBookApplication.getInstance().getCatalog();
		for (int id : recentIds) {
			MenuItemBase mi = cat.getMenuItemById(id);
			if (mi != null) {
				oftenUsedItems.add(mi);
			}
		}
		lvItems.setAdapter(new ArrayAdapter<MenuItemBase>(getActivity(),
				android.R.layout.simple_list_item_1, oftenUsedItems));
	}

	@Override
	protected MenuItemBase getMenuItem() {
		return menuItem;
	}
}
