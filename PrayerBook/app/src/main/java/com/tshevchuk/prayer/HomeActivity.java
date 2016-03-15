package com.tshevchuk.prayer;

import android.content.Intent;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView.OnSuggestionListener;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.Tracker;
import com.tshevchuk.prayer.adapters.SubMenuListAdapter;
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

import org.codechimp.apprater.AppRater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
	public final static String PARAM_SCREEN_ID = "screen_id";
	private static final String TAG = HomeActivity.class.getName();
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
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);

		catalog = PrayerBookApplication.getInstance().getCatalog();

		drawerList.setAdapter(new SubMenuListAdapter(this, catalog
				.getTopMenuItems()));
		drawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				displayMenuItem(catalog.getTopMenuItems().get(position));
			}
		});

		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setHomeButtonEnabled(true);
		}

		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(R.attr.pb_navigationDrawerIconDrawable,
				typedValue, true);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				toolbar, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				// getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				// getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
				ActionBar actionBar1 = getSupportActionBar();
				if (actionBar1 != null) {
					actionBar1.show();
				}
			}
		};
		drawerLayout.addDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			MenuItemBase mi = catalog.getMenuItemById(PreferenceManager
					.getInstance().getDefaultMenuItemId());
			int id;
			if (getIntent() != null) {
				id = getIntent().getIntExtra(PARAM_SCREEN_ID, 0);
				if (id != 0) {
					mi = catalog.getMenuItemById(id);
				}
			}
			displayMenuItem(mi);

			if (Utils.isNetworkAvailable()) {
				AppRater.app_launched(this);
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

	}

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
		searchView = (SearchView) MenuItemCompat.getActionView(miSearch);
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
				if (getSupportFragmentManager().findFragmentById(R.id.content_frame) instanceof SearchFragment) {
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
		case R.id.mi_report_mistake:
			reportError();
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
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.show();
		}

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

	private void search(String query) {
		SearchFragment sf;
		Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
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

	private void reportError() {
		File dir = new File(getCacheDir(), "error_report_screenshots");
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				Log.d(TAG, "Can't create directory");
				return;
			}
		}
		File imageFile = new File(dir
				, "prayerbook_error_image_" + System.currentTimeMillis() + ".png");

		Bitmap bitmap = null;
		View v1 = getWindow().getDecorView().findViewById(android.R.id.content);

		boolean willNotCache = v1.willNotCacheDrawing();
		v1.setWillNotCacheDrawing(false);

		int color = v1.getDrawingCacheBackgroundColor();
		v1.setDrawingCacheBackgroundColor(PreferenceManager.getInstance()
				.isNightModeEnabled() ? Color.BLACK : Color.WHITE);

		if (color != 0) {
			v1.destroyDrawingCache();
		}
		v1.buildDrawingCache();
		Bitmap cacheBitmap = v1.getDrawingCache();
		if (cacheBitmap != null) {
			bitmap = Bitmap.createBitmap(cacheBitmap);
		}

		v1.destroyDrawingCache();
		v1.setWillNotCacheDrawing(willNotCache);
		v1.setDrawingCacheBackgroundColor(color);

		if (bitmap != null) {
			OutputStream fout;
			try {
				fout = new FileOutputStream(imageFile);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
				fout.flush();
				fout.close();
			} catch (IOException e) {
				bitmap = null;
				e.printStackTrace();
			}
		}

		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("message/rfc822");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				new String[]{"taras.shevchuk@gmail.com"});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"Молитовник: Повідомлення про помилку");

		StringBuilder sb = new StringBuilder();
		sb.append("Опишіть коротко помилку:\n\n\n\n");
		sb.append("----------------------------");
		sb.append("\nПрограма: ").append(Utils.getApplicationNameAndVersion());
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			sb.append("\nЗаголовок: ").append(actionBar.getTitle());
		}
		Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
		sb.append("\nФрагмент: ").append(f.getClass().getName());
		if (f instanceof FragmentBase) {
			MenuItemBase mi = ((FragmentBase) f).getMenuItem();
			if (mi != null) {
				sb.append("\nЕлемент меню: ").append(mi.getId()).append(" ")
						.append(mi.getName());
			}
		}
		sb.append("\n").append(Utils.getDeviceInfo());

		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, sb.toString());
		if (bitmap != null) {
			emailIntent.putExtra(Intent.EXTRA_STREAM,
					FileProvider.getUriForFile(this, "com.tshevchuk.prayer.fileprovider", imageFile)
			);
		}
		startActivity(Intent.createChooser(emailIntent,
				"Відправити повідомлення про помилку..."));
	}
}
