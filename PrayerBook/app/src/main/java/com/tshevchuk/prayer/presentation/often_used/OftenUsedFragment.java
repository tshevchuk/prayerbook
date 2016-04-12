package com.tshevchuk.prayer.presentation.often_used;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.model.CalendarDay;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
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
    private SearchView searchView;


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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_search, menu);
        MenuItem miSearch = menu.findItem(R.id.mi_search);
        searchView = (SearchView) MenuItemCompat.getActionView(miSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onSearchSubmit(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.onSearchQueryTextChange(newText);
                return true;
            }
        });
        searchView.setQueryHint("Введіть текст для пошуку");
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void showSearchSuggestions(final ArrayList<MenuListItemSearch> items) {
        String[] columnNames = {"_id", "text"};
        MatrixCursor cursor = new MatrixCursor(columnNames);
        CharSequence[] temp = new CharSequence[2];
        for (MenuListItemSearch item : items) {
            temp[0] = Integer.toString(item.getId());
            temp[1] = Html.fromHtml(item.getDisplayName());
            cursor.addRow(temp);
        }
        String[] from = {"text"};
        int[] to = {R.id.tvName};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.f_search_item,
                cursor, from, to, 0);
        searchView.setSuggestionsAdapter(adapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                presenter.onSearchSuggestionClick(items.get(position));
                return true;
            }
        });
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
