package com.tshevchuk.prayer.presentation.home;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.FragmentBase;
import com.tshevchuk.prayer.presentation.settings.SettingsFragment;

import org.codechimp.apprater.AppRater;

import java.util.ArrayList;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

public class HomeActivity extends AppCompatActivity implements HomeView {
    public final static String PARAM_SCREEN_ID = "screen_id";
    private static final String TAG = HomeActivity.class.getName();
    @Inject
    Utils utils;
    @Inject
    HomePresenter presenter;
    @Inject
    private AnalyticsManager analyticsManager;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        ((PrayerBookApplication) getApplication()).getViewComponent().inject(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_home);
        setProgressBarIndeterminateVisibility(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
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
                presenter.onBackPressed();
            }
        });
        drawerLayout.addDrawerListener(drawerToggle);

        presenter.setRestoringInstanceState(savedInstanceState != null);

        if (getIntent() != null) {
            int screenId = getIntent().getIntExtra(PARAM_SCREEN_ID, 0);
            if (screenId != 0) {
                presenter.setParamScreenId(screenId);
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        GoogleAnalytics.getInstance(getApplicationContext()).dispatchLocalHits();
        presenter.detachView();
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
                presenter.onSearchSubmit(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.onSearchQueryTextChange(newText);
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
                presenter.onSearchSubmit(searchView.getQuery().toString());
            }
        }
        return super.onSearchRequested();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        menu.findItem(R.id.mi_settings).setVisible(!(curFragment instanceof SettingsFragment));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.mi_settings:
                presenter.onSettingsClick();
                return true;
            case R.id.mi_report_mistake:
                presenter.onReportMistakeClick();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (curFragment != null && curFragment instanceof FragmentBase
                && ((FragmentBase) curFragment).goBack()) {
            return;
        }
        super.onBackPressed();
    }

    @DebugLog
    public void displayFragment(Fragment fragment) {
        drawerLayout.closeDrawer(Gravity.LEFT);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment curFragment = fragmentManager.findFragmentById(R.id.content_frame);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        if (curFragment != null) {
            transaction.addToBackStack("item#" + fragmentManager.getBackStackEntryCount());
        }
        transaction.commit();
    }

    public void clearBackStack() {
        getSupportFragmentManager().popBackStackImmediate("item#0",
                android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public FragmentBase getCurrentContentFragment() {
        return (FragmentBase) getSupportFragmentManager().findFragmentById(R.id.content_frame);
    }

    public SearchView getSearchView() {
        return searchView;
    }

    public void setNavigationDrawerEnabled(boolean enabled) {
        drawerToggle.setDrawerIndicatorEnabled(enabled);
        drawerLayout.setDrawerLockMode(enabled ? DrawerLayout.LOCK_MODE_UNLOCKED
                : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void showProgress() {
        // empty
    }

    @Override
    public void hideProgress() {
        // empty
    }

    @Override
    public void showError(String msg) {
        Snackbar.make(drawerLayout, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean handleUpAction() {
        return ((FragmentBase) getSupportFragmentManager().findFragmentById(R.id.content_frame))
                .onUpButtonPress();
    }

    @Override
    public boolean handleBackAction() {
        return ((FragmentBase) getSupportFragmentManager().findFragmentById(R.id.content_frame))
                .onBackButtonPress();
    }

    @Override
    public void updateAppRater() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            AppRater.app_launched(this);
        }
    }

    @Override
    public void showSearchSuggestions(final ArrayList<MenuListItemSearch> items) {
        String[] columnNames = {"_id", "text"};
        MatrixCursor cursor = new MatrixCursor(columnNames);
        CharSequence[] temp = new CharSequence[2];
        for (MenuListItemSearch item : items) {
            temp[0] = Integer.toString(item.getId());
            temp[1] = Html.fromHtml(item.getDisplayName());
            cursor.addRow(temp);
        }
        String[] from = {"text"};
        int[] to = {R.id.tvName};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.f_search_item, cursor,
                from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        searchView.setSuggestionsAdapter(adapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                presenter.onSearchSuggestionClick(items.get(position));
                return true;
            }
        });

    }
}
