package com.tshevchuk.prayer.presentation.about_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.FragmentBase;

import javax.inject.Inject;

public class AboutAppFragment extends FragmentBase implements AboutAppPresenter.AboutView {

    @Inject
    private AboutAppPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PrayerBookApplication) getActivity().getApplication())
                .getViewComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_about_app, container, false);
        ((TextView) v.findViewById(R.id.tv_app_name)).setText(Utils
                .getApplicationNameAndVersion(getActivity().getApplicationContext()));

        ((TextView) v.findViewById(R.id.tv_content)).setText(sb);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.about__about_prayerbook);
        }
    }
}
