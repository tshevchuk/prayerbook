package com.tshevchuk.prayer.presentation;

import com.tshevchuk.prayer.R;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.domain.model.MenuListItemType;
import com.tshevchuk.prayer.presentation.about_app.AboutAppFragment;
import com.tshevchuk.prayer.presentation.about_prayer.AboutPrayerFragment;
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
        FragmentBase f = null;

        if (item.getMenuListItemType() == MenuListItemType.WebView) {
            f = HtmlViewFragment.getInstance();
        } else if (item.getMenuListItemType() == MenuListItemType.TextView) {
            f = TextViewFragment.getInstance(item.getId());
        } else if (item.getMenuListItemType() == MenuListItemType.SubMenu) {
            f = SubMenuFragment.getInstance(item.getId(), item.getName());
        } else if (item.getMenuListItemType() == MenuListItemType.Calendar) {
            f = CerkovnyyCalendarFragment.getInstance();
        } else if (item.getMenuListItemType() == MenuListItemType.OftenUsed) {
            f = OftenUsedFragment.getInstance();
        }

        getHomeActivity(presenter).displayFragment(f, item.getId(), item.getName());
    }

    public void showCalendar(BasePresenter<? extends BaseView> presenter) {
        HomeActivity activity = getHomeActivity(presenter);
        FragmentBase f = CerkovnyyCalendarFragment.getInstance();
        activity.displayFragment(f, Catalog.ID_CALENDAR,
                activity.getString(R.string.cerk_calendar__cerk_calendar));
    }

    private HomeActivity getHomeActivity(BasePresenter<? extends BaseView> presenter) {
        FragmentBase fragmentBase = (FragmentBase) presenter.getMvpView();
        return (HomeActivity) fragmentBase.getActivity();
    }

    public void openAboutApp(BasePresenter<? extends BaseView> presenter) {
        getHomeActivity(presenter).displayFragment(new AboutAppFragment(), 0, null);
    }

    public void showAboutPrayer(BasePresenter<? extends BaseView> presenter, MenuItemPrayer prayer) {
        getHomeActivity(presenter).displayFragment(AboutPrayerFragment.getInstance(prayer), 0, null);
    }

    public void close(BasePresenter<? extends BaseView> presenter) {
        getHomeActivity(presenter).getFragmentManager().popBackStackImmediate();
    }

    public void showSubMenu(BasePresenter<? extends BaseView> presenter, int id, String name) {
        getHomeActivity(presenter).displayFragment(SubMenuFragment.getInstance(id, name), id, name);
    }
}
