package com.tshevchuk.prayer.presentation.cerkovnyy_calendar;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.presentation.common.BasePresenter;
import com.tshevchuk.prayer.presentation.common.FragmentBase;

import org.parceler.Parcels;

import java.util.ArrayList;

import javax.inject.Inject;

public class CerkovnyyCalendarFragment extends FragmentBase implements CerkovnyyCalendarView {
    @SuppressWarnings("WeakerAccess")
    @Inject
    CerkovnyyCalendarPresenter presenter;
    private int prevFirstVisibleItem;
    private Integer initPosition;

    private RecyclerView rvCalendar;
    private LinearLayoutManager layoutManager;
    private TextView tvMonth;
    private ProgressBar pbLoading;
    private int[] years;
    private int selectedYear;

    public static CerkovnyyCalendarFragment getInstance() {
        return new CerkovnyyCalendarFragment();
    }

    @Override
    protected String getScreenTitle() {
        return getString(R.string.calendar__cerk_calendar);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((PrayerBookApplication) getActivity().getApplication()).getViewComponent().inject(this);

        if (savedInstanceState != null) {
            presenter.instanceState = Parcels.unwrap(savedInstanceState.getParcelable("instanceState"));
            prevFirstVisibleItem = initPosition = savedInstanceState.getInt("firstVisiblePosition");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_cerkovnyy_calendar, container, false);
        rvCalendar = v.findViewById(R.id.rvCalendar);
        tvMonth = v.findViewById(R.id.tvMonth);
        pbLoading = v.findViewById(R.id.pbLoading);

        layoutManager = new LinearLayoutManager(getContext());
        rvCalendar.setLayoutManager(layoutManager);

        rvCalendar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onVisibleDaysChanged(false);
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("instanceState", Parcels.wrap(presenter.instanceState));
        if (initPosition == null) {
            outState.putInt("firstVisiblePosition", layoutManager.findFirstVisibleItemPosition());
        } else {
            outState.putInt("firstVisiblePosition", initPosition);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.actionbar_cerkovnyy_calendar, menu);
        inflater.inflate(R.menu.actionbar_create_shortcut, menu);

        MenuItem miSearch = menu.findItem(R.id.mi_calendar_year_spinner);
        Spinner calendarYearSpinner = (Spinner) miSearch.getActionView();

        String[] formattedYears = new String[years.length];
        int curYearPosition = 0;
        for (int i = years.length - 1; i >= 0; --i) {
            formattedYears[i] = getString(R.string.calendar__x_year, years[i]);
            if (years[i] == selectedYear) {
                curYearPosition = i;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(calendarYearSpinner.getContext(),
                android.R.layout.simple_spinner_dropdown_item, formattedYears);
        calendarYearSpinner.setAdapter(adapter);
        calendarYearSpinner.setSelection(curYearPosition);
        calendarYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onYearSelected(years[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_create_shortcut:
                presenter.onCreateShortcutClick();
                return true;
            case R.id.mi_posty_zahalnytsi:
                presenter.onPostyZahalnytsiClick();
                return true;
            case R.id.mi_about_calendar:
                presenter.onAboutCalendarClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showCalendarForYear(int year, ArrayList<CalendarDateInfo> calendarDays,
                                    int positionOfToday, int fontSizeSp) {
        rvCalendar.setAdapter(new CerkovnyyCalendarRecyclerViewAdapter(activity, calendarDays,
                positionOfToday, fontSizeSp));

        if (initPosition != null) {
            layoutManager.scrollToPosition(initPosition);
            initPosition = null;
        } else {
            layoutManager.scrollToPosition(positionOfToday == -1 ? 0 : positionOfToday);
        }

        rvCalendar.post(new Runnable() {
            @Override
            public void run() {
                onVisibleDaysChanged(true);
            }
        });
    }

    @Override
    public void setCurrentMonths(int monthFrom, int monthTo, int year) {
        StringBuilder sb = new StringBuilder();
        final String[] months = getResources().getStringArray(R.array.calendar__monthes);
        sb.append(months[monthFrom]);
        if (monthFrom != monthTo) {
            sb.append('-').append(months[monthTo]);
        }
        tvMonth.setText(getString(R.string.calendar__month_of_x_year, sb, year));
    }

    @Override
    public void setYears(int[] years, int currentYear) {
        this.years = years;
        this.selectedYear = currentYear;
    }

    @Override
    public void showCalendarNotVerifiedWarning(int year) {
        Snackbar.make(rvCalendar, getString(R.string.calendar__calendar_not_verified_x_year, year),
                Snackbar.LENGTH_LONG).show();
    }

    private void onVisibleDaysChanged(boolean force) {
        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
        if (prevFirstVisibleItem != firstVisibleItem || force) {
            presenter.onVisibleDaysChanged(firstVisibleItem, layoutManager.findLastVisibleItemPosition());
            prevFirstVisibleItem = firstVisibleItem;
        }
    }

    @Override
    public boolean onUpButtonPress() {
        return presenter.onUpButtonPress();
    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
        rvCalendar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
        rvCalendar.setVisibility(View.VISIBLE);
    }
}
