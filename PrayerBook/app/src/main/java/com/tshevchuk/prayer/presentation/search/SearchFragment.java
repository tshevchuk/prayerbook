package com.tshevchuk.prayer.presentation.search;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.common.BasePresenter;
import com.tshevchuk.prayer.presentation.common.FragmentBase;

import java.util.ArrayList;

import javax.inject.Inject;

public class SearchFragment extends FragmentBase implements SearchView {
    @Inject
    SearchPresenter presenter;
    private ListView lvItems;
    private TextView tvHeader;
    private android.widget.SearchView searchView;
    private String searchPhrase;

    public static SearchFragment newInstance(String searchPhrase) {
        SearchFragment f = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("searchPhrase", searchPhrase);
        f.setArguments(args);
        return f;
    }

    @Override
    protected String getScreenTitle() {
        return getString(R.string.search__search);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((PrayerBookApplication) getActivity().getApplication()).getViewComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_search, container, false);
        lvItems = (ListView) v.findViewById(R.id.lvItems);
        tvHeader = (TextView) inflater.inflate(R.layout.f_search_header, lvItems, false);
        lvItems.addHeaderView(tvHeader, null, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchPhrase = getArguments().getString("searchPhrase");
        presenter.setSearchPhrase(searchPhrase);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actionbar_search, menu);
        MenuItem miSearch = menu.findItem(R.id.mi_search);
        searchView = (android.widget.SearchView) MenuItemCompat.getActionView(miSearch);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.onSearchQueryTextChange(newText);
                return true;
            }
        });
        searchView.setQueryHint(getString(R.string.search__enter_search_phrase));
        searchView.setIconified(false);
        searchView.setQuery(searchPhrase, false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setSearchResults(String searchPhrase, final ArrayList<MenuListItemSearch> items) {
        lvItems.setAdapter(new SearchListAdapter(getActivity(), items));
        tvHeader.setText(getString(R.string.search__found_for_x, searchPhrase));
        lvItems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuListItemSearch mi = items.get(position - 1);
                presenter.onItemClick(mi);
            }
        });
    }
}
