package com.tshevchuk.prayer.presentation.often_used;

import com.tshevchuk.prayer.data.church_calendar.CalendarDateInfo;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.presentation.common.BaseView;

import java.util.ArrayList;

/**
 * Created by taras on 28.03.16.
 */
public interface OftenUsedView extends BaseView {
    void setMenuItems(ArrayList<MenuListItemOftenUsed> menuListItems);

    void setCalendarDay(CalendarDateInfo day, int fontSizeSp);

    void showSearchSuggestions(ArrayList<MenuListItemSearch> items);
    void showCalendarProgressBar(boolean show);
}
