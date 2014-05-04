package com.tshevchuk.prayer.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.Catalog.MenuItemBase;
import com.tshevchuk.prayer.data.Catalog.SubMenu;

public class SubMenuFragment extends FragmentBase {
	private SubMenu subMenu;

	public static SubMenuFragment getInstance(SubMenu subMenu) {
		SubMenuFragment f = new SubMenuFragment();
		Bundle b = new Bundle();
		b.putSerializable("subMenu", subMenu);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		subMenu = (SubMenu) getArguments().getSerializable("subMenu");
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_submenu, container, false);
		ListView lvItems = (ListView) v.findViewById(R.id.lvItems);
		lvItems.setAdapter(new ArrayAdapter<MenuItemBase>(getActivity(),
				android.R.layout.simple_list_item_1, subMenu.getSubItems()));
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				((HomeActivity) getActivity()).displayMenuItem(subMenu
						.getSubItems().get(position));
			}
		});

		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getActionBar().setTitle(subMenu.getName());
	}

	@Override
	public boolean isSameScreen(Fragment f) {
		if (getClass().equals(f.getClass())) {
			SubMenu s1 = (SubMenu) getArguments().getSerializable("subMenu");
			SubMenu s2 = (SubMenu) f.getArguments().getSerializable("subMenu");
			return s1.getName().equals(s2.getName());
		}
		return false;
	}
}
