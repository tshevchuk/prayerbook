package com.tshevchuk.prayer.domain.model;

import org.parceler.Parcel;

/**
 * Created by taras on 26.03.16.
 */
@Parcel
public class MenuListItem extends MenuItemBase {

    private MenuListItemType menuListItemType;

    public MenuListItem() {
    }

    public MenuListItem(MenuItemBase item) {
        super(item);
    }

    public MenuListItemType getMenuListItemType() {
        return menuListItemType;
    }

    public void setMenuListItemType(MenuListItemType menuListItemType) {
        this.menuListItemType = menuListItemType;
    }
}
