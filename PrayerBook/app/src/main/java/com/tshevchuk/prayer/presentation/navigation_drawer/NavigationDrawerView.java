package com.tshevchuk.prayer.presentation.navigation_drawer;

import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.presentation.common.BaseView;

import java.util.ArrayList;

/**
 * Created by taras on 09.04.16.
 */
public interface NavigationDrawerView extends BaseView {
    void setListItems(ArrayList<MenuListItem> items);
}
