package com.tshevchuk.prayer.presentation.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;

import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hugo.weaving.DebugLog;

public abstract class FragmentBase extends Fragment implements BaseView {
    protected final List<Runnable> onResumeActions = new ArrayList<>();
    protected final Handler handler = new Handler(Looper.getMainLooper());
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

        for (Runnable r : onResumeActions) {
            handler.post(r);
        }
        onResumeActions.clear();
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

    public boolean goBack() {
        return false;
    }

    protected boolean isNavigationDrawerEnabled() {
        return false;
    }

    @DebugLog
    @Override
    public void showProgress() {
        if (isResumed()) {
            activity.setProgressBarIndeterminateVisibility(true);
        } else {
            onResumeActions.add(new Runnable() {
                @Override
                public void run() {
                    showProgress();
                }
            });
        }
    }

    @DebugLog
    @Override
    public void hideProgress() {
        if (isResumed()) {
            activity.setProgressBarIndeterminateVisibility(false);
        } else {
            onResumeActions.add(new Runnable() {
                @Override
                public void run() {
                    hideProgress();
                }
            });
        }
    }

    @DebugLog
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
