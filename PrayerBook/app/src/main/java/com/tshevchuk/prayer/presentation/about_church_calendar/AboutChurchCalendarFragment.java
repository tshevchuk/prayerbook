package com.tshevchuk.prayer.presentation.about_church_calendar;

import android.app.UiModeManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.presentation.common.BasePresenter;
import com.tshevchuk.prayer.presentation.common.FragmentBase;

import javax.inject.Inject;

public class AboutChurchCalendarFragment extends FragmentBase implements AboutChurchCalendarView {
    @SuppressWarnings("WeakerAccess")
    @Inject
    AboutChurchCalendarPresenter presenter;
    private UiModeManager uiModeManager;


    @Override
    protected String getScreenTitle() {
        return getString(R.string.about_church_calendar__title);
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
        uiModeManager = (UiModeManager) getContext().getSystemService(Context.UI_MODE_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_about_church_calendar, container, false);
        ImageView ivPist = view.findViewById(R.id.ivIconPist);
        ivPist.setImageResource(uiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES
                ? R.drawable.ic_pist_fish_white
                : R.drawable.ic_pist_fish_black);
        return view;
    }

}
