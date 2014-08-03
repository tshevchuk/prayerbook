package com.tshevchuk.prayer;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
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
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView.OnSuggestionListener;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.Tracker;
import com.tjeannin.apprate.AppRate;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.MenuItemBase;
import com.tshevchuk.prayer.data.MenuItemCalendar;
import com.tshevchuk.prayer.data.MenuItemOftenUsed;
import com.tshevchuk.prayer.data.MenuItemPrayer;
import com.tshevchuk.prayer.data.MenuItemPrayer.Type;
import com.tshevchuk.prayer.data.MenuItemSubMenu;
import com.tshevchuk.prayer.data.SearchItem;
import com.tshevchuk.prayer.fragments.CerkovnyyCalendarFragment;
import com.tshevchuk.prayer.fragments.FragmentBase;
import com.tshevchuk.prayer.fragments.HtmlViewFragment;
import com.tshevchuk.prayer.fragments.OftenUsedFragment;
import com.tshevchuk.prayer.fragments.SearchFragment;
import com.tshevchuk.prayer.fragments.SettingsFragment;
import com.tshevchuk.prayer.fragments.SubMenuFragment;
import com.tshevchuk.prayer.fragments.TextViewFragment;

public class HomeActivity extends Activity {
	public final static String PARAM_SCREEN_ID = "screen_id";

	private Catalog catalog;

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	private SearchView searchView;

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
				getActionBar().show();
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
							.setLabel("Час запуску")
							.setCategory("Час запуску програми")
							.setVariable("Час запуску").setValue(elapsedMls)
							.build());
			PrayerBookApplication.startupTimeMeasuringStartTimestamp = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar, menu);
		MenuItem miSearch = menu.findItem(R.id.mi_search);
		searchView = (SearchView) miSearch.getActionView();
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				search(query);

				Tracker t = PrayerBookApplication.getInstance().getTracker();
				EventBuilder event = new HitBuilders.EventBuilder()
						.setCategory(Analytics.CAT_SEARCH)
						.setAction("Підтверджено пошукову фразу")
						.setLabel(query);
				t.send(event.build());
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				if (getFragmentManager().findFragmentById(R.id.content_frame) instanceof SearchFragment) {
					search(newText);
					Tracker t = PrayerBookApplication.getInstance()
							.getTracker();
					EventBuilder event = new HitBuilders.EventBuilder()
							.setCategory(Analytics.CAT_SEARCH)
							.setAction("Пошук на фрагменті пошуку")
							.setLabel(newText);
					t.send(event.build());
				} else {
					String[] columnNames = { "_id", "text" };
					MatrixCursor cursor = new MatrixCursor(columnNames);
					final List<SearchItem> items = PrayerBookApplication
							.getInstance().getCatalog().filter(newText);
					CharSequence[] temp = new CharSequence[2];
					for (SearchItem item : items) {
						temp[0] = Integer.toString(item.getMenuItem().getId());
						temp[1] = Html.fromHtml(item.getName());
						cursor.addRow(temp);
					}
					String[] from = { "text" };
					int[] to = { R.id.tvName };
					SimpleCursorAdapter adapter = new SimpleCursorAdapter(
							HomeActivity.this, R.layout.f_search_item, cursor,
							from, to,
							CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
					searchView.setSuggestionsAdapter(adapter);
					searchView
							.setOnSuggestionListener(new OnSuggestionListener() {
								@Override
								public boolean onSuggestionSelect(int position) {
									return false;
								}

								@Override
								public boolean onSuggestionClick(int position) {
									MenuItemBase mi = items.get(position)
											.getMenuItem();
									displayMenuItem(mi);

									Tracker t = PrayerBookApplication
											.getInstance().getTracker();
									EventBuilder event = new HitBuilders.EventBuilder()
											.setCategory(Analytics.CAT_SEARCH)
											.setAction(
													"Вибрано випадаючу підказку")
											.setLabel(
													mi.getId() + " "
															+ mi.getName());
									t.send(event.build());

									return true;
								}
							});
					if (!TextUtils.isEmpty(newText)) {
						Tracker t = PrayerBookApplication.getInstance()
								.getTracker();
						EventBuilder event = new HitBuilders.EventBuilder()
								.setCategory(Analytics.CAT_SEARCH)
								.setAction(
										"Пошук із випадаючим списком підказок")
								.setLabel(newText);
						t.send(event.build());
					}
				}
				return true;
			}
		});
		searchView.setQueryHint("Введіть текст для пошуку");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onSearchRequested() {
		if (searchView != null) {
			if (searchView.isIconified()) {
				searchView.setIconified(false);
			} else {
				search(searchView.getQuery().toString());
			}
		}
		return super.onSearchRequested();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Fragment curFragment = getFragmentManager().findFragmentById(
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
		Fragment curFragment = getFragmentManager().findFragmentById(
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
		getActionBar().show();

		Fragment curFragment = getFragmentManager().findFragmentById(
				R.id.content_frame);
		if (curFragment != null && curFragment instanceof FragmentBase
				&& ((FragmentBase) curFragment).isSameScreen(fragment)) {
			return;
		}

		FragmentTransaction transaction = getFragmentManager()
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

	private void search(String query) {
		SearchFragment sf = null;
		Fragment f = getFragmentManager().findFragmentById(R.id.content_frame);
		if (f instanceof SearchFragment) {
			sf = (SearchFragment) f;
		} else {
			sf = new SearchFragment();
			displayFragment(sf, 0, null);
		}
		sf.setItemsForSearchPhrase(query);
	}

	public SearchView getSearchView() {
		return searchView;
	}

	public void setNavigationDrawerEnabled(boolean enabled) {
		drawerToggle.setDrawerIndicatorEnabled(enabled);
		drawerLayout
				.setDrawerLockMode(enabled ? DrawerLayout.LOCK_MODE_UNLOCKED
						: DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
	}
}
