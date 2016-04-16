package com.tshevchuk.prayer.presentation.home;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.FragmentBase;
import com.tshevchuk.prayer.presentation.settings.SettingsFragment;

import org.codechimp.apprater.AppRater;

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
    AnalyticsManager analyticsManager;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @DebugLog
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

    @DebugLog
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
        presenter.attachView(this);
    }

    @DebugLog
    @Override
    protected void onDestroy() {
        GoogleAnalytics.getInstance(getApplicationContext()).dispatchLocalHits();
        presenter.detachView();
        super.onDestroy();

    }

    @DebugLog
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

    @DebugLog
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @DebugLog
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Fragment curFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        menu.findItem(R.id.mi_settings).setVisible(!(curFragment instanceof SettingsFragment));
        return super.onPrepareOptionsMenu(menu);
    }

    @DebugLog
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

    @DebugLog
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

    @DebugLog
    public void clearBackStack() {
        getSupportFragmentManager().popBackStackImmediate("item#0",
                android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void setNavigationDrawerEnabled(boolean enabled) {
        drawerToggle.setDrawerIndicatorEnabled(enabled);
        drawerLayout.setDrawerLockMode(enabled ? DrawerLayout.LOCK_MODE_UNLOCKED
                : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @DebugLog
    @Override
    public void showProgress() {
        // empty
    }

    @DebugLog
    @Override
    public void hideProgress() {
        // empty
    }

    @DebugLog
    @Override
    public void showError(String msg) {
        Snackbar.make(drawerLayout, msg, Snackbar.LENGTH_LONG).show();
    }

    @DebugLog
    @Override
    public boolean handleUpAction() {
        return ((FragmentBase) getSupportFragmentManager().findFragmentById(R.id.content_frame))
                .onUpButtonPress();
    }

    @DebugLog
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
}
