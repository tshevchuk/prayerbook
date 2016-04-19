package com.tshevchuk.prayer.presentation.cerkovnyy_calendar;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.CalendarDay;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.base.FragmentBase;

import org.parceler.Parcels;

import java.util.ArrayList;

import javax.inject.Inject;

public class CerkovnyyCalendarFragment extends FragmentBase implements CerkovnyyCalendarView {
    @Inject
    CerkovnyyCalendarPresenter presenter;
    private int prevFirstVisibleItem;
    private Integer initPosition;

    private ListView lvCalendar;
    private TextView tvMonth;
    private int[] years;
    private int selectedYear;

    public static CerkovnyyCalendarFragment getInstance() {
        return new CerkovnyyCalendarFragment();
    }

    @Override
    protected String getScreenTitle() {
        return getString(R.string.cerk_calendar__cerk_calendar);
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
        lvCalendar = (ListView) v.findViewById(R.id.lvCalendar);
        tvMonth = (TextView) v.findViewById(R.id.tvMonth);

        lvCalendar.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                onVisibleDaysChanged(firstVisibleItem, false);
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("instanceState", Parcels.wrap(presenter.instanceState));
        if (initPosition == null) {
            outState.putInt("firstVisiblePosition", lvCalendar.getFirstVisiblePosition());
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
        Spinner calendarYearSpinner = (Spinner) MenuItemCompat.getActionView(miSearch);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showCalendarForYear(int year, ArrayList<CalendarDay> calendarDays,
                                    int positionOfToday, int fontSizeSp) {
        lvCalendar.setAdapter(new CerkovnyyCalendarListAdapter(activity, calendarDays,
                positionOfToday, fontSizeSp));

        if (initPosition != null) {
            lvCalendar.setSelection(initPosition);
            initPosition = null;
        } else {
            lvCalendar.setSelection(positionOfToday);
        }

        final int pos = positionOfToday;
        lvCalendar.post(new Runnable() {
            @Override
            public void run() {
                onVisibleDaysChanged(pos, true);
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

    private void onVisibleDaysChanged(int firstVisibleItem, boolean force) {
        if (prevFirstVisibleItem != firstVisibleItem || force) {
            presenter.onVisibleDaysChanged(firstVisibleItem, lvCalendar.getLastVisiblePosition());
            prevFirstVisibleItem = firstVisibleItem;
        }
    }
}
