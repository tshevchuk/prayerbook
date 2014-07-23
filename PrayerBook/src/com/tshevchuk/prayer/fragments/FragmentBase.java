package com.tshevchuk.prayer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tshevchuk.prayer.HomeActivity;
import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.MenuItemBase;

public class FragmentBase extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				if (canCreateShortcut()) {
					setHasOptionsMenu(true);
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		HomeActivity activity = (HomeActivity) getActivity();
		activity.getSupportActionBar().show();
		activity.setNavigationDrawerEnabled(isNavigationDrawerEnabled());
		Tracker t = PrayerBookApplication.getInstance().getTracker();
		t.setScreenName(getClass().getSimpleName());
		t.send(new HitBuilders.AppViewBuilder().build());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		MenuItemBase menuItem = getMenuItem();
		if (menuItem != null && menuItem.getId() != 0) {
			inflater.inflate(R.menu.actionbar_create_shortcut, menu);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mi_create_shortcut:
			MenuItemBase mi = getMenuItem();
			createShortcut(mi);
			((HomeActivity) getActivity()).sendAnalyticsOptionsMenuEvent(
					item.getTitle(),
					String.format("#%d %s", mi.getId(), mi.getName()));
			return true;

		case android.R.id.home:
			getActivity().getSupportFragmentManager().popBackStack();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean isSameScreen(Fragment f) {
		return getClass().equals(f.getClass());
	}

	public boolean goBack() {
		return false;
	}

	protected MenuItemBase getMenuItem() {
		return null;
	}

	private boolean canCreateShortcut() {
		MenuItemBase menuItem = getMenuItem();
		return menuItem != null && menuItem.getId() != 0;
	}

	private void createShortcut(MenuItemBase mi) {
		PrayerBookApplication app = PrayerBookApplication.getInstance();
		Intent shortcutIntent = new Intent(app, HomeActivity.class);
		shortcutIntent.putExtra(HomeActivity.PARAM_SCREEN_ID, mi.getId());
		shortcutIntent.setAction(Intent.ACTION_MAIN);

		Intent addIntent = new Intent();
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, mi.getName());
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(app,
						R.drawable.ic_launcher));

		addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		app.sendBroadcast(addIntent);
	}

	protected boolean isNavigationDrawerEnabled() {
		return false;
	}
}
