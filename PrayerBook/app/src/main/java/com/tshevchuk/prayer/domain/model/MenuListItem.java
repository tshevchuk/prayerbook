package com.tshevchuk.prayer.domain.model;

import org.parceler.Parcel;

/**
 * Created by taras on 26.03.16.
 */
@Parcel
public class MenuListItem extends MenuItemBase {

    private MenuItemType menuItemType;

    public MenuListItem() {
    }

    public MenuListItem(MenuItemBase item) {
        super(item);
    }

    public MenuItemType getMenuItemType() {
        return menuItemType;
    }

    public void setMenuItemType(MenuItemType menuItemType) {
        this.menuItemType = menuItemType;
    }

    public enum MenuItemType {TextView, WebView, Calendar, SubMenu, OftenUsed}
}
