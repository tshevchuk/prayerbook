package com.tshevchuk.prayer.presentation.sub_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.presentation.PrayerBookApplication;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.base.FragmentBase;

import java.util.List;

import javax.inject.Inject;

public class SubMenuFragment extends FragmentBase implements SubMenuView {
    @Inject
    SubMenuPresenter presenter;
    private int subMenuId;
    private String name;
    private ListView lvItems;
    private SubMenuListAdapter adapter;

    public static SubMenuFragment getInstance(int id, String name) {
        SubMenuFragment f = new SubMenuFragment();
        Bundle b = new Bundle();
        b.putInt("subMenuId", id);
        b.putString("name", name);
        f.setArguments(b);
        return f;
    }

    @Override
    protected String getScreenTitle() {
        return name;
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
        subMenuId = getArguments().getInt("subMenuId");
        name = getArguments().getString("name");
        ((PrayerBookApplication) getActivity().getApplication()).getViewComponent().inject(this);
        presenter.setId(subMenuId);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_submenu, container, false);
        lvItems = (ListView) v.findViewById(R.id.lvItems);
        lvItems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.onItemClick(adapter.getItem(position));
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.actionbar_create_shortcut, menu);
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
    public void setMenuItems(List<MenuListItem> menuListItems) {
        adapter = new SubMenuListAdapter(activity, menuListItems);
        lvItems.setAdapter(adapter);
    }

    @Override
    public String getErrorReportInfo() {
        return super.getErrorReportInfo() + "; id: " + subMenuId;
    }
}
