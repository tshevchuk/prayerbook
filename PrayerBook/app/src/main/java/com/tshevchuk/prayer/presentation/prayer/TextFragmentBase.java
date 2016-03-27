package com.tshevchuk.prayer.presentation.prayer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.about_prayer.AboutPrayerFragment;
import com.tshevchuk.prayer.presentation.base.FragmentBase;
import com.tshevchuk.prayer.presentation.home.HomeActivity;

public abstract class TextFragmentBase extends FragmentBase {
	@Override
	public abstract MenuItemPrayer getMenuItem();

	@Override
	protected String getScreenTitle() {
		return getMenuItem().getName();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		ActionBar actionBar = activity.getSupportActionBar();
		if (actionBar != null) {
			actionBar.setTitle(getMenuItem().getName());
		}
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
				a.displayMenuItem(catalog.getMenuItemById(parentId));
				//todo: add update of recently used
				return true;
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
