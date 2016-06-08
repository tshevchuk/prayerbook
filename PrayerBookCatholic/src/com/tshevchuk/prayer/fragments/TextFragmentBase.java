package com.tshevchuk.prayer.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.catholic.R;
import com.tshevchuk.prayer.data.MenuItemPrayer;

public abstract class TextFragmentBase extends FragmentBase {
	@Override
	public abstract MenuItemPrayer getMenuItem();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		activity.getActionBar().setTitle(getMenuItem().getFullName());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.actionbar_textviewfragment, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mi_about_prayer:
			activity.displayFragment(
					AboutPrayerFragment.getInstance(getMenuItem()), 0, null);
			activity.sendAnalyticsOptionsMenuEvent("Опис", String.format(
					"#%d %s", getMenuItem().getId(), getMenuItem().getName()));
			return true;

		case android.R.id.home:
			int parentId = getMenuItem().getParentItemId();
			if (parentId > 0) {
				HomeActivity a = activity;
				a.getFragmentManager().popBackStackImmediate();
				a.displayMenuItem(PrayerBookApplication.getInstance()
						.getCatalog().getMenuItemById(parentId));
				return true;
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
