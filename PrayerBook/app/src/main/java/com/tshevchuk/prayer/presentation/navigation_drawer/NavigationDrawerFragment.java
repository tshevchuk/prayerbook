package com.tshevchuk.prayer.presentation.navigation_drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.common.BasePresenter;
import com.tshevchuk.prayer.presentation.common.FragmentBase;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by taras on 09.04.16.
 */
public class NavigationDrawerFragment extends FragmentBase implements NavigationDrawerView {
    @Inject
    NavigationDrawerPresenter presenter;
    private ListView drawerList;


    @Override
    protected String getScreenTitle() {
        // no title for navigation drawer
        return null;
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PrayerBookApplication) getActivity().getApplication()).getViewComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_navigation_drawer, container, false);
        drawerList = (ListView) v.findViewById(R.id.navigation_drawer_listview);
        return v;
    }

    @Override
    public void setListItems(final ArrayList<MenuListItem> items) {
        drawerList.setAdapter(new NavigationDrawerListAdapter(getContext(), items));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onListItemClick(items.get(position));
            }
        });
    }
}
