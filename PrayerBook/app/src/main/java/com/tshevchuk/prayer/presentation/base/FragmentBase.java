package com.tshevchuk.prayer.presentation.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.home.HomeActivity;

import javax.inject.Inject;

public abstract class FragmentBase extends Fragment implements BasePresenter.BaseView {
    HomeActivity activity;
    @Inject
    Catalog catalog;
    @Inject
    AnalyticsManager analyticsManager;
    @Inject
    PreferenceManager preferenceManager;

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

        ((PrayerBookApplication) getActivity().getApplication())
                .getViewComponent().inject(this);

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
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
        activity.setNavigationDrawerEnabled(isNavigationDrawerEnabled());
        analyticsManager.sendScreenEvent(getClass().getSimpleName());
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
                if (getActivity().getFragmentManager().getBackStackEntryCount() == 0) {
                    ((HomeActivity) getActivity())
                            .displayMenuItem(catalog.getMenuItemById(Catalog.ID_RECENT_SCREENS));
                } else {
                    getActivity().getFragmentManager().popBackStack();
                }
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

    public MenuItemBase getMenuItem() {
        return null;
    }

    private boolean canCreateShortcut() {
        MenuItemBase menuItem = getMenuItem();
        return menuItem != null && menuItem.getId() != 0;
    }

    private void createShortcut(MenuItemBase mi) {
        Intent shortcutIntent = new Intent(activity.getApplicationContext(),
                HomeActivity.class);
        shortcutIntent.putExtra(HomeActivity.PARAM_SCREEN_ID, mi.getId());
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, mi.getName());
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(activity.getApplicationContext(),
                        R.mipmap.ic_launcher));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        activity.sendBroadcast(addIntent);
    }

    boolean isNavigationDrawerEnabled() {
        return false;
    }
}
