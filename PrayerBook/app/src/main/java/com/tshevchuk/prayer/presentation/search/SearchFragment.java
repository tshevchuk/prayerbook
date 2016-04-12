package com.tshevchuk.prayer.presentation.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.base.FragmentBase;

import java.util.ArrayList;

import javax.inject.Inject;

public class SearchFragment extends FragmentBase implements SearchView {
    @Inject
    SearchPresenter presenter;
    private ListView lvItems;
    private TextView tvHeader;

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
        String searchPhrase = getArguments().getString("searchPhrase");
        presenter.setSearchPhrase(searchPhrase);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //todo: implement
        //activity.getSearchView().setIconified(false);
    }

    public void onSearchPhraseChange(String searchPhrase) {
        presenter.setSearchPhrase(searchPhrase);
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
