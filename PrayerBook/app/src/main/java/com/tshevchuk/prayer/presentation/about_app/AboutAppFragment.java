package com.tshevchuk.prayer.presentation.about_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.FragmentBase;

import javax.inject.Inject;

public class AboutAppFragment extends FragmentBase implements AboutView {
    @Inject
    AboutAppPresenter presenter;
    private TextView tvAppName;
    private TextView tvContent;
    private ActionBar actionBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PrayerBookApplication) getActivity().getApplication())
                .getViewComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_about_app, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actionBar = activity.getSupportActionBar();
        tvAppName = (TextView) view.findViewById(R.id.tv_app_name);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        presenter = null;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setTextSources(String textSources) {
        tvContent.setText(getString(R.string.about_app__info, textSources));
    }

    @Override
    public void setAppNameAndVersion(String appNameAndVersion) {
        tvAppName.setText(appNameAndVersion);
    }

    @Override
    public void setScreenTitle() {
        if (actionBar != null) {
            actionBar.setTitle(R.string.about_app__about_prayerbook);
        }
    }
}
