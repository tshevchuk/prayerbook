package com.tshevchuk.prayer.data;

import android.support.annotation.Nullable;

import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuItemCalendar;
import com.tshevchuk.prayer.domain.model.MenuItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.domain.model.MenuItemSubMenu;
import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.domain.model.MenuListItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras on 26.03.16.
 */
public class TextsRepository {
    public final Catalog catalog;
    private final PreferenceManager preferenceManager;

    public TextsRepository(Catalog catalog, PreferenceManager preferenceManager) {
        this.catalog = catalog;
        this.preferenceManager = preferenceManager;
    }

    public List<MenuListItem> getMenuListItems(int id) {
        MenuItemSubMenu menuItem = (MenuItemSubMenu) catalog.getMenuItemById(id);
        List<MenuListItem> items = new ArrayList<>();
        for (MenuItemBase item : menuItem.getSubItems()) {
            MenuListItem menuListItem = new MenuListItem(item);
            MenuListItemType type = getMenuListItemType(item);
            menuListItem.setMenuListItemType(type);
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

    public ArrayList<MenuListItemOftenUsed> getOftenUsedMenuItems() {
        int[] recentIds = preferenceManager.getRecentMenuItems();
        ArrayList<MenuListItemOftenUsed> oftenUsedItems = new ArrayList<>(recentIds.length);

        for (int id : recentIds) {
            MenuItemBase mi = catalog.getMenuItemById(id);
            if (mi != null) {
                MenuListItemOftenUsed itemOftenUsed = new MenuListItemOftenUsed(mi);
                itemOftenUsed.setMenuListItemType(getMenuListItemType(mi));
                int parentItemId = mi.getParentItemId();
                if (parentItemId != 0) {
                    MenuItemBase parent = catalog.getMenuItemById(parentItemId);
                    if (parent != null) {
                        itemOftenUsed.setParentName(parent.getName());
                    }
                }
                oftenUsedItems.add(itemOftenUsed);
            }
        }

        return oftenUsedItems;
    }

    @Nullable
    private MenuListItemType getMenuListItemType(MenuItemBase item) {
        MenuListItemType type = null;
        if (item instanceof MenuItemCalendar) {
            type = MenuListItemType.Calendar;
        } else if (item instanceof MenuItemOftenUsed) {
            type = MenuListItemType.OftenUsed;
        } else if (item instanceof MenuItemSubMenu) {
            type = MenuListItemType.SubMenu;
        } else if (item instanceof MenuItemPrayer) {
            type = ((MenuItemPrayer) item).getType() == MenuItemPrayer.Type.HtmlInWebView
                    ? MenuListItemType.WebView : MenuListItemType.TextView;
        }
        return type;
    }

    public ArrayList<MenuListItemSearch> searchMenuItems(String searchPhrase) {
        ArrayList<MenuListItemSearch> items = catalog.filter(searchPhrase);
        for (int i = items.size() - 1; i >= 0; i--) {
            MenuListItemSearch item = items.get(i);
            item.setMenuListItemType(getMenuListItemType(catalog.getMenuItemById(item.getId())));
            int parentItemId = item.getParentItemId();
            if (parentItemId != 0) {
                item.setParentName(catalog.getMenuItemById(parentItemId).getName());
            }
        }
        return items;
    }
}
