package com.tshevchuk.prayer.domain.model;

public class MenuListItemSearch extends MenuListItem {
	private final String displayName;
	private String parentName;

	public MenuListItemSearch(MenuItemBase menuItem, String displayName) {
		super(menuItem);
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
