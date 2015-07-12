package com.tshevchuk.prayer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.adapters.SubMenuListAdapter;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemSubMenu;

public class SubMenuFragment extends FragmentBase {
	private MenuItemSubMenu subMenu;

	public static SubMenuFragment getInstance(MenuItemSubMenu subMenu) {
		SubMenuFragment f = new SubMenuFragment();
		Bundle b = new Bundle();
		b.putSerializable("subMenu", subMenu);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		subMenu = (MenuItemSubMenu) getArguments().getSerializable("subMenu");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_submenu, container, false);
		ListView lvItems = (ListView) v.findViewById(R.id.lvItems);
		lvItems.setAdapter(new SubMenuListAdapter(activity, subMenu.getSubItems()));
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				activity.displayMenuItem(subMenu.getSubItems().get(position));
			}
		});

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		activity.getActionBar().setTitle(subMenu.getName());
	}

	@Override
	public boolean isSameScreen(Fragment f) {
		if (getClass().equals(f.getClass())) {
			MenuItemSubMenu s1 = (MenuItemSubMenu) getArguments()
					.getSerializable("subMenu");
			MenuItemSubMenu s2 = (MenuItemSubMenu) f.getArguments()
					.getSerializable("subMenu");
			return s1.getName().equals(s2.getName());
		}
		return false;
	}

	@Override
	public MenuItemBase getMenuItem() {
		return subMenu;
	}

	@Override
	protected boolean isNavigationDrawerEnabled() {
		return true;
	}
}
