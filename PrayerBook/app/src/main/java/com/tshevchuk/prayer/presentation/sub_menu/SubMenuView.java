package com.tshevchuk.prayer.presentation.sub_menu;

import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.presentation.base.BaseView;

import java.util.List;

/**
 * Created by taras on 26.03.16.
 */
public interface SubMenuView extends BaseView {
    void setMenuItems(List<MenuListItem> menuListItems);
}
