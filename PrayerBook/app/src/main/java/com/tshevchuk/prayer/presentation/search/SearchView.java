package com.tshevchuk.prayer.presentation.search;

import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.presentation.common.BaseView;

import java.util.ArrayList;

/**
 * Created by taras on 30.03.16.
 */
interface SearchView extends BaseView {
    void setSearchResults(String searchPhrase, ArrayList<MenuListItemSearch> items);
}
