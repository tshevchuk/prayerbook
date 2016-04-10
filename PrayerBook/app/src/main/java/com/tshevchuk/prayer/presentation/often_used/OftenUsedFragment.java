package com.tshevchuk.prayer.presentation.often_used;

import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.model.CalendarDay;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.base.FragmentBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.inject.Inject;

public class OftenUsedFragment extends FragmentBase implements OftenUsedView {
    @Inject
    OftenUsedPresenter presenter;
    private ListView lvItems;
    private TextView tvDay;
    private TextView tvDescription;
    private LinearLayout llToday;

    public static OftenUsedFragment getInstance() {
        return new OftenUsedFragment();
    }

    @Override
    protected String getScreenTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected boolean isNavigationDrawerEnabled() {
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PrayerBookApplication) getActivity().getApplication()).getViewComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_often_used, container, false);
        lvItems = (ListView) v.findViewById(R.id.lvItems);

        View calendarToday = inflater.inflate(R.layout.i_calendar_for_today, lvItems, false);
        llToday = (LinearLayout) calendarToday.findViewById(R.id.ll_today);
        tvDay = (TextView) calendarToday.findViewById(R.id.tvDay);
        tvDescription = (TextView) calendarToday.findViewById(R.id.tvDescription);
        llToday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCalendarClick();
            }
        });
        lvItems.addHeaderView(calendarToday);

        return v;
    }

    @Override
    public void setMenuItems(final ArrayList<MenuListItemOftenUsed> menuListItems) {
        lvItems.setAdapter(new OftenUsedListAdapter(activity, menuListItems));
        lvItems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                presenter.onItemClick(menuListItems.get(position - 1));
            }
        });
    }

    @Override
    public void setCalendarDay(CalendarDay day, int fontSizeSp) {
        llToday.setVisibility(View.VISIBLE);
        String d = new SimpleDateFormat("d EE", Utils.getUkrainianLocale())
                .format(day.getDay());
        if (day.isDateRed()) {
            d = "<font color=\"red\">" + d + "</font>";
        }
        tvDay.setText(Html.fromHtml(d));
        tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
        tvDescription.setText(Html.fromHtml(day.getDescription().toString()));
        tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSizeSp);
    }
}
