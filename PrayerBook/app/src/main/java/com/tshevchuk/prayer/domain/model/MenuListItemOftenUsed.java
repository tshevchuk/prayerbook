package com.tshevchuk.prayer.domain.model;


import org.parceler.Parcel;

@Parcel
public class MenuListItemOftenUsed extends MenuListItem {
	String parentName;

	public MenuListItemOftenUsed() {
	}

	public MenuListItemOftenUsed(MenuItemBase item) {
		super(item);
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}