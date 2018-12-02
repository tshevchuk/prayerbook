package com.tshevchuk.prayer.data.repositories;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.data.Catalog;
import com.tshevchuk.prayer.data.PreferenceManager;
import com.tshevchuk.prayer.data.cache.InMemoryCacheManager;
import com.tshevchuk.prayer.data.html_parser.HtmlParser;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuItemCalendar;
import com.tshevchuk.prayer.domain.model.MenuItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.domain.model.MenuItemSubMenu;
import com.tshevchuk.prayer.domain.model.MenuListItem;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.domain.model.MenuListItemSearch;
import com.tshevchuk.prayer.domain.model.MenuListItemType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras on 26.03.16.
 */
public class TextsRepository {
    private final Catalog catalog;
    private final PreferenceManager preferenceManager;
    private final Context context;
    private final InMemoryCacheManager inMemoryCacheManager;
    private final HtmlParser htmlParser;

    public TextsRepository(Catalog catalog, PreferenceManager preferenceManager, Context context,
                           InMemoryCacheManager inMemoryCacheManager, HtmlParser htmlParser) {
        this.catalog = catalog;
        this.preferenceManager = preferenceManager;
        this.context = context;
        this.inMemoryCacheManager = inMemoryCacheManager;
        this.htmlParser = htmlParser;
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

    public ArrayList<MenuListItem> getTopMenuListItems() {
        ArrayList<MenuListItem> items = new ArrayList<>();
        for (MenuItemBase item : catalog.getTopMenuItems()) {
            MenuListItem listItem = new MenuListItem(item);
            listItem.setMenuListItemType(getMenuListItemType(item));
            items.add(listItem);
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

    public MenuItemBase getMenuItem(int id) {
        return catalog.getMenuItemById(id);
    }

    public MenuListItem getMenuListItem(int id) {
        MenuItemBase menuItemBase = catalog.getMenuItemById(id);
        MenuListItem item = new MenuListItem(menuItemBase);
        item.setMenuListItemType(getMenuListItemType(menuItemBase));
        return item;
    }

    public CharSequence loadText(MenuItemPrayer item) {
        CharSequence prayer = inMemoryCacheManager.getCharSequence(item.getFileName());

        if (prayer != null) {
            return prayer;
        }

        String rawText = "";
        try {
            rawText = Utils.getAssetAsString(context.getApplicationContext(), item.getFileName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (item.getType() == MenuItemPrayer.Type.HtmlInTextView) {
            Pair<CharSequence, Integer> pair = htmlParser.parseHtml(rawText);
            inMemoryCacheManager.putCharSequence(item.getFileName(), pair.first, pair.second);
            return pair.first;
        } else {
            inMemoryCacheManager.putCharSequence(item.getFileName(), rawText);
            return rawText;
        }
    }
}
