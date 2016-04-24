package com.tshevchuk.prayer.presentation.about_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.base.FragmentBase;

import javax.inject.Inject;

public class AboutAppFragment extends FragmentBase implements AboutView {
    @Inject
    AboutAppPresenter presenter;
    private TextView tvAppName;
    private TextView tvContent;


    @Override
    protected String getScreenTitle() {
        return getString(R.string.about_app__about_prayerbook);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PrayerBookApplication) getActivity().getApplication())
                .getViewComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_about_app, container, false);
        tvAppName = (TextView) view.findViewById(R.id.tv_app_name);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        return view;
    }

    @Override
    public void setTextSources(String textSources) {
        tvContent.setText(getString(R.string.about_app__info, textSources));
    }

    @Override
    public void setAppNameAndVersion(String appNameAndVersion) {
        tvAppName.setText(appNameAndVersion);
    }
}
