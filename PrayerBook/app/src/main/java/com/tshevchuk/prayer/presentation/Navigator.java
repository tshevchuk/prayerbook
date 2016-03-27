package com.tshevchuk.prayer.presentation;

import com.tshevchuk.prayer.domain.model.MenuItemCalendar;
import com.tshevchuk.prayer.domain.model.MenuItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.domain.model.MenuItemSubMenu;
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
        activity.displayMenuItem(itemId, name);

        FragmentBase f = null;
        if (mi instanceof MenuItemPrayer) {
            if (((MenuItemPrayer) mi).getType() == MenuItemPrayer.Type.HtmlInWebView) {
                f = HtmlViewFragment.getInstance((MenuItemPrayer) mi);
            } else {
                f = TextViewFragment.getInstance(((MenuItemPrayer) mi));
            }
        } else if (mi instanceof MenuItemSubMenu) {
            f = SubMenuFragment.getInstance(mi.getId(), mi.getName());
        } else if (mi instanceof MenuItemCalendar) {
            f = CerkovnyyCalendarFragment.getInstance((MenuItemCalendar) mi);
        } else if (mi instanceof MenuItemOftenUsed) {
            f = OftenUsedFragment.getInstance((MenuItemOftenUsed) mi);
        }

        displayFragment(f, mi.getId(), mi.getName());

    }
}
