package com.tshevchuk.prayer.presentation.often_used;

import com.tshevchuk.prayer.domain.model.CalendarDay;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.presentation.base.BaseView;

import java.util.ArrayList;

/**
 * Created by taras on 28.03.16.
 */
public interface OftenUsedView extends BaseView {
    void setMenuItems(ArrayList<MenuListItemOftenUsed> menuListItems);

    void setCalendarDay(CalendarDay day, int fontSizeSp);
}
