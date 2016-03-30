package com.tshevchuk.prayer.presentation.home;

import android.content.Intent;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.domain.DataManager;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.FragmentBase;
import com.tshevchuk.prayer.presentation.often_used.OftenUsedFragment;
import com.tshevchuk.prayer.presentation.search.SearchFragment;
import com.tshevchuk.prayer.presentation.settings.SettingsFragment;
import com.tshevchuk.prayer.presentation.sub_menu.SubMenuListAdapter;

import org.codechimp.apprater.AppRater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends AppCompatActivity {
	public final static String PARAM_SCREEN_ID = "screen_id";
	private static final String TAG = HomeActivity.class.getName();
	@Inject
	Catalog catalog;
	@Inject
	PreferenceManager preferenceManager;
	@Inject
	AnalyticsManager analyticsManager;
	@Inject
	Utils utils;
	@Inject
	DataManager dataManager;

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	private SearchView searchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		((PrayerBookApplication) getApplication()).getViewComponent().inject(this);

		setTheme(preferenceManager.isNightModeEnabled() ? R.style.PrayerBook_ThemeDark
				: R.style.PrayerBook_ThemeLight);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.a_home);
		setProgressBarIndeterminateVisibility(false);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);

		drawerList.setAdapter(new SubMenuListAdapter(this, dataManager.getTopMenuListItems()));
		drawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//todo: implement
				//displayMenuItem(catalog.getTopMenuItems().get(position));
				//todo: add update of recently used
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
		drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		drawerLayout.addDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			MenuItemBase mi = catalog.getMenuItemById(preferenceManager.getDefaultMenuItemId());
			int id;
			if (getIntent() != null) {
				id = getIntent().getIntExtra(PARAM_SCREEN_ID, 0);
				if (id != 0) {
					mi = catalog.getMenuItemById(id);
				}
			}
			//todo: implement
			//displayMenuItem(mi);
			//todo: add update of recently used

			//todo: remove this line
			displayFragment(OftenUsedFragment.getInstance(), Catalog.ID_RECENT_SCREENS, "recent");
//			displayFragment(SubMenuFragment.getInstance(7, "pisni"), 7, "pisni");

			if (Utils.isNetworkAvailable(getApplicationContext())) {
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
		GoogleAnalytics.getInstance(getApplicationContext())
				.dispatchLocalHits();
		super.onDestroy();

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (PrayerBookApplication.startupTimeMeasuringStartTimestamp != null) {
			long elapsedMls = System.currentTimeMillis()
					- PrayerBookApplication.startupTimeMeasuringStartTimestamp;
			analyticsManager.sendTimingEvent("Час запуску програми", "Час запуску",
					"Час запуску", elapsedMls);
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
				analyticsManager.sendActionEvent(Analytics.CAT_SEARCH,
						"Підтверджено пошукову фразу", query);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				if (getSupportFragmentManager().findFragmentById(R.id.content_frame) instanceof SearchFragment) {
					search(newText);
					analyticsManager.sendActionEvent(Analytics.CAT_SEARCH,
							"Пошук на фрагменті пошуку", newText);
				} else {
					String[] columnNames = { "_id", "text" };
					MatrixCursor cursor = new MatrixCursor(columnNames);
					final List<MenuListItemSearch> items = catalog.filter(newText);
					CharSequence[] temp = new CharSequence[2];
					for (MenuListItemSearch item : items) {
						temp[0] = Integer.toString(item.getId());
						temp[1] = Html.fromHtml(item.getDisplayName());
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
									MenuItemBase mi = items.get(position);
									//todo: implement
									//displayMenuItem(mi);
									//todo: add update of recently used

									analyticsManager.sendActionEvent(Analytics.CAT_SEARCH,
											"Вибрано випадаючу підказку", mi.getId() + " " + mi.getName()
									);

									return true;
								}
							});
					if (!TextUtils.isEmpty(newText)) {
						analyticsManager.sendActionEvent(Analytics.CAT_SEARCH,
								"Пошук із випадаючим списком підказок", newText);
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
		analyticsManager.sendActionEvent(Analytics.CAT_OPTIONS_MENU, menuItemName.toString(),
				param);
	}

	public void displayFragment(Fragment fragment, int id, CharSequence title) {
		drawerLayout.closeDrawer(drawerList);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.show();
		}

		Fragment curFragment = getSupportFragmentManager().findFragmentById(
				R.id.content_frame);
		if (curFragment != null
				&& fragment.getClass().equals(curFragment.getClass())
				&& curFragment instanceof FragmentBase
				&& ((FragmentBase) curFragment).hasContentWithSameId(id)) {
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

		analyticsManager.sendActionEvent("Fragment Opened", id + " " + title);
	}

	private void search(String query) {
		SearchFragment sf;
		Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
		if (f instanceof SearchFragment) {
			sf = (SearchFragment) f;
			//todo: implement search update while SearchFragment is opened
			//sf.setItemsForSearchPhrase(query);
		} else {
			sf = SearchFragment.newInstance(query);
			displayFragment(sf, 0, null);
		}
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
		v1.setDrawingCacheBackgroundColor(preferenceManager
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
		sb.append("\nПрограма: ").append(utils.getApplicationNameAndVersion());
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
		sb.append("\n").append(Utils.getDeviceInfo(getApplicationContext()));

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
