package com.tshevchuk.prayer.domain.model;


import org.parceler.Parcel;

@Parcel
public class MenuListItemOftenUsed extends MenuItemBase {
	private MenuListItemType menuListItemType;
	private String parentName;

	public MenuListItemOftenUsed(MenuItemBase item) {
		super(item);
	}

	public MenuListItemType getMenuListItemType() {
		return menuListItemType;
	}

	public void setMenuListItemType(MenuListItemType menuListItemType) {
		this.menuListItemType = menuListItemType;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}