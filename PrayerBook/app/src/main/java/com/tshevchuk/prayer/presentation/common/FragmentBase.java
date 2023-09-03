package com.tshevchuk.prayer.presentation.common;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import android.text.TextUtils;
import android.view.View;

import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public abstract class FragmentBase extends Fragment implements BaseView {
    private final List<Runnable> onResumeActions = new ArrayList<>();
    protected HomeActivity activity;
    @SuppressWarnings("WeakerAccess")
    @Inject
    AnalyticsManager analyticsManager;

    protected abstract String getScreenTitle();

    protected abstract BasePresenter getPresenter();

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
    }

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

        for(Runnable action : onResumeActions){
            action.run();
        }
        onResumeActions.clear();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //noinspection unchecked
        getPresenter().attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().detachView();
    }

    protected boolean isNavigationDrawerEnabled() {
        return false;
    }

    @Override
    public void showProgress() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void hideProgress() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void showError(final String msg) {
        if (isResumed()) {
            View v = getView();
            if (v == null) {
                return;
            }
            Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show();
        } else {
            onResumeActions.add(new Runnable() {
                @Override
                public void run() {
                    showError(msg);
                }
            });
        }
    }

    public boolean onUpButtonPress() {
        return false;
    }

    public boolean onBackButtonPress() {
        return false;
    }

    public String getErrorReportInfo() {
        return getScreenTitle();
    }
}
