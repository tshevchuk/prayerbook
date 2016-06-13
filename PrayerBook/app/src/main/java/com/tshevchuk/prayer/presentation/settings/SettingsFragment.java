package com.tshevchuk.prayer.presentation.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.MenuItem;
import android.view.View;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.home.HomeActivity;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragmentCompat implements
        OnSharedPreferenceChangeListener, SettingsView {
    @Inject
    AnalyticsManager analyticsManager;
    @Inject
    SettingsPresenter presenter;

    private HomeActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (HomeActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PrayerBookApplication) getActivity().getApplication()).getViewComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.preferences);
        Preference aboutApp = findPreference(PreferenceManager.PREF_ABOUT_APP);
        aboutApp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                presenter.onOpenAboutAppClick();
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.settings__settings);
        }
        activity.setNavigationDrawerEnabled(false);

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().
                unregisterOnSharedPreferenceChangeListener(this);
        activity.restoreToolbarState();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        switch (key) {
            case PreferenceManager.PREF_NIGHT_MODE:
                presenter.onSettingsChanged(key,
                        String.valueOf(sharedPreferences.getBoolean(key, false)));
                getActivity().recreate();
                break;
            case PreferenceManager.PREF_SHOW_OFFICIAL_UGCC:
                presenter.onSettingsChanged(key,
                        String.valueOf(sharedPreferences.getBoolean(key, false)));
                getActivity().recreate();
                break;
            case PreferenceManager.PREF_TEXT_FONT_SIZE:
                presenter.onSettingsChanged(key, sharedPreferences.getString(key, ""));
                getActivity().recreate();
                break;
            case PreferenceManager.PREF_HIDE_TOOLBAR_ON_SCROLLING:
                presenter.onSettingsChanged(key,
                        String.valueOf(sharedPreferences.getBoolean(key,
                                getResources().getBoolean(R.bool.enable_toolbar_hiding_on_scroll_default))));
                getActivity().recreate();
                break;
            case PreferenceManager.PREF_DEFAULT_SCREENS:
                presenter.onSettingsChanged(key, sharedPreferences.getString(key, ""));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            activity.getFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        //empty
    }

    @Override
    public void hideProgress() {
        //empty
    }

    @Override
    public void showError(String msg) {
        View view = getView();
        if (view != null) {
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
        }
    }
}
