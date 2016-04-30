package com.tshevchuk.prayer.presentation.home;

import android.app.UiModeManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.FragmentBase;
import com.tshevchuk.prayer.presentation.settings.SettingsFragment;

import org.codechimp.apprater.AppRater;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import io.fabric.sdk.android.Fabric;

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
    private AppBarLayout appBarLayout;

    @DebugLog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        ((PrayerBookApplication) getApplication()).getViewComponent().inject(this);

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.a_home);
        setProgressBarIndeterminateVisibility(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

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
                presenter.onUpPressed();
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
                && ((FragmentBase) curFragment).onBackButtonPress()) {
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
    public boolean handleContentViewUpAction() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        return f instanceof FragmentBase && ((FragmentBase) f).onUpButtonPress();
    }

    @DebugLog
    @Override
    public boolean handleContentViewBackAction() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        return f instanceof FragmentBase && ((FragmentBase) f).onBackButtonPress();
    }

    @Override
    public void updateAppRater() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            AppRater.app_launched(this);
        }
    }

    @Override
    public
    @Nullable
    byte[] createScreenshotJpeg() {
        Bitmap bitmap = null;
        View v1 = getWindow().getDecorView().findViewById(android.R.id.content);

        boolean willNotCache = v1.willNotCacheDrawing();
        v1.setWillNotCacheDrawing(false);

        int color = v1.getDrawingCacheBackgroundColor();
        v1.setDrawingCacheBackgroundColor(Color.GREEN);

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

            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
                os.flush();
                os.close();
                return os.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void sendErrorReport(String email, File imageFile) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{email});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                getString(R.string.home__error_report_subject));

        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.home__error_report_please_describe_error)).append("\n\n\n\n");
        sb.append("----------------------------");
        sb.append("\nApplication: ").append(utils.getApplicationNameAndVersion())
                .append(' ').append(getPackageName());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            sb.append("\nTitle: ").append(actionBar.getTitle());
        }
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        sb.append("\nFragment: ").append(f.getClass().getName());
        if (f instanceof FragmentBase) {
            String screenInfo = ((FragmentBase) f).getErrorReportInfo();
            if (!TextUtils.isEmpty(screenInfo)) {
                sb.append("\nCurrent Screen: ").append(screenInfo);
            }
        }
        sb.append("\n").append(Utils.getDeviceInfo(getApplicationContext()));

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, sb.toString());
        if (imageFile != null) {
            emailIntent.putExtra(Intent.EXTRA_STREAM,
                    FileProvider.getUriForFile(this, "com.tshevchuk.prayer.fileprovider", imageFile)
            );
        }
        startActivity(Intent.createChooser(emailIntent, getString(R.string.home__error_report_send_error_report)));
    }

    @Override
    public void setNightMode(boolean nightMode) {
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        uiModeManager.setNightMode(nightMode ? UiModeManager.MODE_NIGHT_YES
                : UiModeManager.MODE_NIGHT_NO);
    }

    public void restoreToolbarState() {
        appBarLayout.setExpanded(true, true);
    }
}
