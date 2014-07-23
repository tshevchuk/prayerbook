package com.tshevchuk.prayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.tjeannin.apprate.AppRate;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemCalendar;
import com.tshevchuk.prayer.data.MenuItemOftenUsed;
import com.tshevchuk.prayer.data.MenuItemPrayer;
import com.tshevchuk.prayer.data.MenuItemPrayer.Type;
import com.tshevchuk.prayer.data.MenuItemSubMenu;
import com.tshevchuk.prayer.fragments.CerkovnyyCalendarFragment;
import com.tshevchuk.prayer.fragments.FragmentBase;
import com.tshevchuk.prayer.fragments.HtmlViewFragment;
import com.tshevchuk.prayer.fragments.OftenUsedFragment;
import com.tshevchuk.prayer.fragments.SettingsFragment;
import com.tshevchuk.prayer.fragments.SubMenuFragment;
import com.tshevchuk.prayer.fragments.TextViewFragment;

public class HomeActivity extends ActionBarActivity {
	public final static String PARAM_SCREEN_ID = "screen_id";

	private Catalog catalog;

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		PreferenceManager pm = PreferenceManager.getInstance();
		setTheme(pm.isNightModeEnabled() ? R.style.PrayerBook_ThemeDark
				: R.style.PrayerBook_ThemeLight);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.a_home);
		setProgressBarIndeterminateVisibility(false);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);

		catalog = PrayerBookApplication.getInstance().getCatalog();

		drawerList
				.setAdapter(new ArrayAdapter<MenuItemBase>(this,
						android.R.layout.simple_list_item_1, catalog
								.getTopMenuItems()));
		drawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				displayMenuItem(catalog.getTopMenuItems().get(position));
			}
		});

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(R.attr.pb_navigationDrawerIconDrawable,
				typedValue, true);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				typedValue.resourceId, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				supportInvalidateOptionsMenu();
				getSupportActionBar().show();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			MenuItemBase mi = catalog.getMenuItemById(PreferenceManager
					.getInstance().getDefaultMenuItemId());
			int id = 0;
			if (getIntent() != null) {
				id = getIntent().getIntExtra(PARAM_SCREEN_ID, 0);
				if (id != 0) {
					mi = catalog.getMenuItemById(id);
				}
			}
			displayMenuItem(mi);

			if (Utils.isNetworkAvailable()) {
				new AppRate(this).setShowIfAppHasCrashed(false)
						.setMinDaysUntilPrompt(5).setMinLaunchesUntilPrompt(3)
						.init();
			}
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
	protected void onResume() {
		super.onResume();
		if (PrayerBookApplication.startupTimeMeasuringStartTimestamp != null) {
			long elapsedMls = System.currentTimeMillis()
					- PrayerBookApplication.startupTimeMeasuringStartTimestamp;
			PrayerBookApplication
					.getInstance()
					.getTracker()
					.send(new HitBuilders.TimingBuilder()
							.setCategory("Час запуску програми")
							.setValue(elapsedMls).build());
			PrayerBookApplication.startupTimeMeasuringStartTimestamp = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Fragment curFragment = getSupportFragmentManager().findFragmentById(
				R.id.content_frame);
		menu.findItem(R.id.mi_settings).setVisible(
				!(curFragment instanceof SettingsFragment));
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
		case R.id.mi_settings:
			displayFragment(new SettingsFragment(), 0, item.getTitle());
			sendAnalyticsOptionsMenuEvent(item.getTitle(), null);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Fragment curFragment = getSupportFragmentManager().findFragmentById(
				R.id.content_frame);
		if (curFragment != null && curFragment instanceof FragmentBase
				&& ((FragmentBase) curFragment).goBack()) {
			return;
		}
		super.onBackPressed();
	}

	public void sendAnalyticsOptionsMenuEvent(CharSequence menuItemName,
			String param) {
		Tracker t = PrayerBookApplication.getInstance().getTracker();
		HitBuilders.EventBuilder eb = new HitBuilders.EventBuilder();
		eb.setCategory(Analytics.CAT_OPTIONS_MENU).setAction(
				menuItemName.toString());
		if (!TextUtils.isEmpty(param)) {
			eb.setLabel(param);
		}
		t.send(eb.build());
	}

	public void displayMenuItem(final MenuItemBase mi) {
		FragmentBase f = null;
		if (mi instanceof MenuItemPrayer) {
			if (((MenuItemPrayer) mi).getType() == Type.HtmlInWebView) {
				f = HtmlViewFragment.getInstance((MenuItemPrayer) mi);
			} else {
				f = TextViewFragment.getInstance(((MenuItemPrayer) mi));
			}
		} else if (mi instanceof MenuItemSubMenu) {
			f = SubMenuFragment.getInstance((MenuItemSubMenu) mi);
		} else if (mi instanceof MenuItemCalendar) {
			f = CerkovnyyCalendarFragment.getInstance((MenuItemCalendar) mi);
		} else if (mi instanceof MenuItemOftenUsed) {
			f = OftenUsedFragment.getInstance((MenuItemOftenUsed) mi);
		}

		displayFragment(f, mi.getId(), mi.getName());

		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				if (!(mi instanceof MenuItemOftenUsed)) {
					PreferenceManager.getInstance().markMenuItemAsOpened(
							mi.getId());
				}
				return null;
			}
		}.execute();
	}

	public void displayFragment(Fragment fragment, int id, CharSequence title) {
		drawerLayout.closeDrawer(drawerList);
		getSupportActionBar().show();

		Fragment curFragment = getSupportFragmentManager().findFragmentById(
				R.id.content_frame);
		if (curFragment != null && curFragment instanceof FragmentBase
				&& ((FragmentBase) curFragment).isSameScreen(fragment)) {
			return;
		}

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content_frame, fragment);
		if (curFragment != null)
			transaction.addToBackStack(String.valueOf(id));
		transaction.commit();

		if (TextUtils.isEmpty(title)) {
			title = fragment.getClass().getSimpleName();
		}

		PrayerBookApplication
				.getInstance()
				.getTracker()
				.send(new HitBuilders.EventBuilder()
						.setCategory("Fragment Opened")
						.setAction(id + " " + title).build());
	}

	public void setNavigationDrawerEnabled(boolean enabled) {
		drawerToggle.setDrawerIndicatorEnabled(enabled);
		drawerLayout
				.setDrawerLockMode(enabled ? DrawerLayout.LOCK_MODE_UNLOCKED
						: DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
	}
}
