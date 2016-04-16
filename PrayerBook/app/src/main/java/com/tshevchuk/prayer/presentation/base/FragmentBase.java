package com.tshevchuk.prayer.presentation.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.home.HomeActivity;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

public abstract class FragmentBase extends Fragment implements BaseView {
    protected HomeActivity activity;
    @Inject
    AnalyticsManager analyticsManager;

    protected abstract String getScreenTitle();

    public abstract BasePresenter getPresenter();

    @DebugLog
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (HomeActivity) getActivity();
    }

    @DebugLog
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @DebugLog
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

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        HomeActivity activity = (HomeActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
            String screenTitle = getScreenTitle();
            if (!TextUtils.isEmpty(screenTitle)) {
                actionBar.setTitle(getScreenTitle());
            }
        }
        activity.setNavigationDrawerEnabled(isNavigationDrawerEnabled());
        analyticsManager.sendScreenEvent(getClass().getSimpleName());
    }

    @DebugLog
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //noinspection unchecked
        getPresenter().attachView(this);
    }

    @DebugLog
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().detachView();
    }

    @DebugLog
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //todo: implement
//        MenuItemBase menuItem = getMenuItem();
//        if (menuItem != null && menuItem.getId() != 0) {
//            inflater.inflate(R.menu.actionbar_create_shortcut, menu);
//        }
    }

    @DebugLog
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_create_shortcut:
                //TODO: implement
//                MenuItemBase mi = getMenuItem();
//                createShortcut(mi);
//                ((HomeActivity) getActivity()).sendAnalyticsOptionsMenuEvent(
//                        item.getTitle(),
//                        String.format("#%d %s", mi.getId(), mi.getName()));
                return true;

            case android.R.id.home:
                if (getActivity().getFragmentManager().getBackStackEntryCount() == 0) {
                    //todo: implement
                    //((HomeActivity) getActivity()).displayMenuItem(catalog.getMenuItemById(Catalog.ID_RECENT_SCREENS));
                } else {
                    getActivity().getFragmentManager().popBackStack();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean goBack() {
        return false;
    }

    private boolean canCreateShortcut() {
        //todo: implement
//        MenuItemBase menuItem = getMenuItem();
        //       return menuItem != null && menuItem.getId() != 0;

        return false;
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

    protected boolean isNavigationDrawerEnabled() {
        return false;
    }

    @DebugLog
    @Override
    public void showProgress() {
        //todo: show progress in case this method was called before onResume
        if (isResumed()) {
            activity.setProgressBarIndeterminateVisibility(true);
        }
    }

    @DebugLog
    @Override
    public void hideProgress() {
        //todo: hide progress in onPause
        if (isResumed()) {
            activity.setProgressBarIndeterminateVisibility(false);
        }
    }

    @DebugLog
    @Override
    public void showError(String msg) {
        //todo: handle case when this method was called before onResume
        if (isResumed()) {
            View v = getView();
            if (v == null) {
                return;
            }
            Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    @DebugLog
    public boolean onUpButtonPress() {
        return false;
    }

    @DebugLog
    public boolean onBackButtonPress() {
        return false;
    }

    public String getErrorReportInfo() {
        return getScreenTitle();
    }
}
