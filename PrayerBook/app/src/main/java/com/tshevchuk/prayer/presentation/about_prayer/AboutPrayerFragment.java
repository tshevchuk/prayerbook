package com.tshevchuk.prayer.presentation.about_prayer;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.base.FragmentBase;

import org.parceler.Parcels;

import javax.inject.Inject;

public class AboutPrayerFragment extends FragmentBase implements AboutPrayerView {
    @Inject
    AboutPrayerPresenter presenter;
    private MenuItemPrayer prayer;
    private TextView tvAbout;
    private TextView tvName;

    public static AboutPrayerFragment getInstance(MenuItemPrayer prayer) {
        AboutPrayerFragment f = new AboutPrayerFragment();
        Bundle b = new Bundle();
        b.putParcelable("prayer", Parcels.wrap(prayer));
        f.setArguments(b);
        return f;
    }

    @Override
    protected String getScreenTitle() {
        return getString(R.string.about_prayer__description);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prayer = Parcels.unwrap(getArguments().getParcelable("prayer"));

        ((PrayerBookApplication) getActivity().getApplication())
                .getViewComponent().inject(this);
        presenter.setMenuItemPrayer(prayer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_about_prayer, container, false);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvAbout = (TextView) view.findViewById(R.id.tv_about);
        return view;
    }

    @Override
    public void setPrayerName(String name) {
        tvName.setText(prayer.getName());
    }

    @Override
    public void setAboutHtml(String about) {
        tvAbout.setText(Html.fromHtml(about));
    }
}
