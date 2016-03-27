package com.tshevchuk.prayer.data;

import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuItemCalendar;
import com.tshevchuk.prayer.domain.model.MenuItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.domain.model.MenuItemSubMenu;
import com.tshevchuk.prayer.domain.model.MenuListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras on 26.03.16.
 */
public class TextsRepository {
    public final Catalog catalog;

    public TextsRepository(Catalog catalog) {
        this.catalog = catalog;
    }

    public List<MenuListItem> getMenuListItems(int id) {
        MenuItemSubMenu menuItem = (MenuItemSubMenu) catalog.getMenuItemById(id);
        List<MenuListItem> items = new ArrayList<>();
        for (MenuItemBase item : menuItem.getSubItems()) {
            MenuListItem menuListItem = new MenuListItem(item);
            if (item instanceof MenuItemCalendar) {
                menuListItem.setMenuItemType(MenuListItem.MenuItemType.Calendar);
            } else if (item instanceof MenuItemOftenUsed) {
                menuListItem.setMenuItemType(MenuListItem.MenuItemType.OftenUsed);
            } else if (item instanceof MenuItemSubMenu) {
                menuListItem.setMenuItemType(MenuListItem.MenuItemType.SubMenu);
            } else if (item instanceof MenuItemPrayer) {
                menuListItem.setMenuItemType(((MenuItemPrayer) item).getType() == MenuItemPrayer.Type.HtmlInWebView
                        ? MenuListItem.MenuItemType.WebView : MenuListItem.MenuItemType.TextView);
            }
            items.add(menuListItem);
        }
        return items;
    }

    public List<MenuListItem> getTopMenuListItems() {
        List<MenuListItem> items = new ArrayList<>();
        for (MenuItemBase item : catalog.getTopMenuItems()) {
            items.add(new MenuListItem(item));
        }
        return items;
    }
}
