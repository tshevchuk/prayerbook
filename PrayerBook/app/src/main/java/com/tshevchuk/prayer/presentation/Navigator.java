package com.tshevchuk.prayer.presentation;

import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.presentation.base.BasePresenter;
import com.tshevchuk.prayer.presentation.base.BaseView;
import com.tshevchuk.prayer.presentation.base.FragmentBase;
import com.tshevchuk.prayer.presentation.cerkovnyy_calendar.CerkovnyyCalendarFragment;
import com.tshevchuk.prayer.presentation.home.HomeActivity;
import com.tshevchuk.prayer.presentation.often_used.OftenUsedFragment;
import com.tshevchuk.prayer.presentation.prayer.HtmlViewFragment;
import com.tshevchuk.prayer.presentation.prayer.TextViewFragment;
import com.tshevchuk.prayer.presentation.sub_menu.SubMenuFragment;

/**
 * Created by taras on 22.03.16.
 */
public class Navigator {
    public void showMenuItem(BasePresenter<? extends BaseView> presenter, MenuListItem item) {
        FragmentBase fragmentBase = (FragmentBase) presenter.getMvpView();
        HomeActivity activity = (HomeActivity) fragmentBase.getActivity();

        FragmentBase f = null;

        if (item.getMenuItemType() == MenuListItem.MenuItemType.WebView) {
            f = HtmlViewFragment.getInstance();
        } else if (item.getMenuItemType() == MenuListItem.MenuItemType.TextView) {
            f = TextViewFragment.getInstance();
        } else if (item.getMenuItemType() == MenuListItem.MenuItemType.SubMenu) {
            f = SubMenuFragment.getInstance(item.getId(), item.getName());
        } else if (item.getMenuItemType() == MenuListItem.MenuItemType.Calendar) {
            f = CerkovnyyCalendarFragment.getInstance();
        } else if (item.getMenuItemType() == MenuListItem.MenuItemType.OftenUsed) {
            f = OftenUsedFragment.getInstance();
        }

        activity.displayFragment(f, item.getId(), item.getName());

    }
}
