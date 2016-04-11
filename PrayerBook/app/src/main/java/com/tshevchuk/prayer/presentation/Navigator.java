package com.tshevchuk.prayer.presentation;

import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.domain.analytics.Analytics;
import com.tshevchuk.prayer.domain.analytics.AnalyticsManager;
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
import com.tshevchuk.prayer.presentation.search.SearchFragment;
import com.tshevchuk.prayer.presentation.settings.SettingsFragment;
import com.tshevchuk.prayer.presentation.sub_menu.SubMenuFragment;

import hugo.weaving.DebugLog;

/**
 * Created by taras on 22.03.16.
 */
public class Navigator {
    private final AnalyticsManager analyticsManager;

    public Navigator(AnalyticsManager analyticsManager) {
        this.analyticsManager = analyticsManager;
    }

    @DebugLog
    public void showMenuItem(BasePresenter<? extends BaseView> presenter, MenuListItem item) {
        FragmentBase f = null;

        if (item.getMenuListItemType() == MenuListItemType.WebView) {
            f = HtmlViewFragment.getInstance(item.getId());
        } else if (item.getMenuListItemType() == MenuListItemType.TextView) {
            f = TextViewFragment.getInstance(item.getId());
        } else if (item.getMenuListItemType() == MenuListItemType.SubMenu) {
            f = SubMenuFragment.getInstance(item.getId(), item.getName());
        } else if (item.getMenuListItemType() == MenuListItemType.Calendar) {
            f = CerkovnyyCalendarFragment.getInstance();
        } else if (item.getMenuListItemType() == MenuListItemType.OftenUsed) {
            f = OftenUsedFragment.getInstance();
        }

        getHomeActivity(presenter).displayFragment(f);
        analyticsFragmentOpened(item.getId(), item.getName());
    }

    private void analyticsFragmentOpened(int id, String title) {
        analyticsManager.sendActionEvent(Analytics.CAT_FRAGMENT_OPEN, id + " " + title);
    }

    public void showCalendar(BasePresenter<? extends BaseView> presenter) {
        HomeActivity activity = getHomeActivity(presenter);
        FragmentBase f = CerkovnyyCalendarFragment.getInstance();
        activity.displayFragment(f);
        analyticsFragmentOpened(Catalog.ID_CALENDAR, "Церковний календар");
    }

    private HomeActivity getHomeActivity(BasePresenter<? extends BaseView> presenter) {
        BaseView mvpView = presenter.getMvpView();
        if (mvpView instanceof HomeActivity) {
            return (HomeActivity) mvpView;
        } else if (mvpView instanceof FragmentBase) {
            FragmentBase fragmentBase = (FragmentBase) mvpView;
            return (HomeActivity) fragmentBase.getActivity();
        }
        throw new IllegalStateException("Wrong activity or fragment");
    }

    public void openAboutApp(BasePresenter<? extends BaseView> presenter) {
        getHomeActivity(presenter).displayFragment(new AboutAppFragment());
        analyticsFragmentOpened(0, "About app");
    }

    public void showAboutPrayer(BasePresenter<? extends BaseView> presenter, MenuItemPrayer prayer) {
        getHomeActivity(presenter).displayFragment(AboutPrayerFragment.getInstance(prayer));
        analyticsFragmentOpened(0, "About prayer");
    }

    public void close(BasePresenter<? extends BaseView> presenter) {
        getHomeActivity(presenter).getFragmentManager().popBackStackImmediate();
    }

    public void showSubMenu(BasePresenter<? extends BaseView> presenter, int id, String name) {
        getHomeActivity(presenter).displayFragment(SubMenuFragment.getInstance(id, name));
        analyticsFragmentOpened(id, name);
    }

    public void clearBackStack(BasePresenter<? extends BaseView> presenter) {
        getHomeActivity(presenter).clearBackStack();
    }

    public void showSettings(BasePresenter<? extends BaseView> presenter) {
        getHomeActivity(presenter).displayFragment(new SettingsFragment());
        analyticsFragmentOpened(0, "Settings");
    }

    public void showSearchScreen(BasePresenter<? extends BaseView> presenter, String query) {
        getHomeActivity(presenter).displayFragment(SearchFragment.newInstance(query));
        analyticsManager.sendActionEvent(Analytics.CAT_FRAGMENT_OPEN, "0 Search", query);
    }

    public boolean updateSearchPhraseOnSearchView(BasePresenter<? extends BaseView> presenter,
                                                  String query) {
        FragmentBase f = getHomeActivity(presenter).getCurrentContentFragment();
        if (f instanceof SearchFragment) {
            ((SearchFragment) f).onSearchPhraseChange(query);
            return true;
        }
        return false;
    }
}
