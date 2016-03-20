package com.tshevchuk.prayer.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuItemSubMenu;
import com.tshevchuk.prayer.presentation.adapters.SubMenuListAdapter;

import org.parceler.Parcels;

public class SubMenuFragment extends FragmentBase {
	private MenuItemSubMenu subMenu;

	public static SubMenuFragment getInstance(MenuItemSubMenu subMenu) {
		SubMenuFragment f = new SubMenuFragment();
		Bundle b = new Bundle();
		b.putParcelable("subMenu", Parcels.wrap(subMenu));
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		subMenu = Parcels.unwrap(getArguments().getParcelable("subMenu"));
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
		ActionBar actionBar = activity.getSupportActionBar();
		if (actionBar != null) {
			actionBar.setTitle(subMenu.getName());
		}
	}

	@Override
	public boolean isSameScreen(Fragment f) {
		if (getClass().equals(f.getClass())) {
			MenuItemSubMenu s1 = (MenuItemSubMenu) getArguments()
					.getSerializable("subMenu");
			MenuItemSubMenu s2 = (MenuItemSubMenu) f.getArguments()
					.getSerializable("subMenu");
			return s1 != null && s2 != null && s1.getName().equals(s2.getName());
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
