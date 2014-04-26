package com.tshevchuk.prayer;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class HomeActivity extends Activity {
	private String[] menuItems;
	private String[] menuHtmlPages;

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PreferenceManager pm = PreferenceManager.getInstance();
		setTheme(pm.isNightModeEnabled() ? R.style.PrayerBook_ThemeDark
				: R.style.PrayerBook_ThemeLight);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_home);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		menuItems = getResources().getStringArray(R.array.home_menu);
		menuHtmlPages = getResources().getStringArray(
				R.array.home_menu_html_pages);

		drawerList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, menuItems));
		drawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				displayFragment(position);
			}
		});

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(R.attr.pb_navigationDrawerIconDrawable,
				typedValue, true);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				typedValue.resourceId, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			displayFragment(0);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	@Override
	protected void onDestroy() {
		GoogleAnalytics.getInstance(PrayerBookApplication.getInstance())
				.dispatchLocalHits();
		super.onDestroy();

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		PreferenceManager pm = PreferenceManager.getInstance();
		menu.findItem(R.id.mi_night_mode_dark).setVisible(
				!pm.isNightModeEnabled());
		menu.findItem(R.id.mi_night_mode_light).setVisible(
				pm.isNightModeEnabled());
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		PreferenceManager pm = PreferenceManager.getInstance();
		switch (item.getItemId()) {
		case R.id.mi_night_mode_dark:
			pm.setNightModeEnabled(true);
			recreate();
			invalidateOptionsMenu();
			sendAnalyticsMenuEvent(item.getTitle());
			return true;
		case R.id.mi_night_mode_light:
			pm.setNightModeEnabled(false);
			recreate();
			invalidateOptionsMenu();
			sendAnalyticsMenuEvent(item.getTitle());
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void sendAnalyticsMenuEvent(CharSequence menuItemName) {
		Tracker t = PrayerBookApplication.getInstance().getTracker();
		t.send(new HitBuilders.EventBuilder()
				.setCategory(Analytics.CAT_OPTIONS_MENU)
				.setAction(menuItemName.toString()).build());
	}

	private void displayFragment(int position) {
		FragmentBase f = null;
		if (position < menuHtmlPages.length) {
			f = HtmlViewFragment.getInstance(menuItems[position],
					menuHtmlPages[position]);
		}

		FragmentBase curFragment = (FragmentBase) getFragmentManager()
				.findFragmentById(R.id.content_frame);
		if (curFragment != null && curFragment.isSameScreen(f)) {
			return;
		}

		if (f != null) {
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.content_frame, f);
			if (curFragment != null)
				transaction.addToBackStack(null);
			transaction.commit();
			drawerList.setItemChecked(position, true);
			drawerList.setSelection(position);
			setTitle(menuItems[position]);
			drawerLayout.closeDrawer(drawerList);
		}

		PrayerBookApplication
				.getInstance()
				.getTracker()
				.send(new HitBuilders.EventBuilder()
						.setCategory("Fragment Opened")
						.setAction(menuItems[position]).build());
	}
}
