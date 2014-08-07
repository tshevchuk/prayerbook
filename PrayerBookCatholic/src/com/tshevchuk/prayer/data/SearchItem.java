package com.tshevchuk.prayer.data;

public class SearchItem {
	private MenuItemBase menuItem;
	private String name;

	public SearchItem(MenuItemBase menuItem, String name) {
		this.menuItem = menuItem;
		this.name = name;
	}
	
	public MenuItemBase getMenuItem(){
		return menuItem;
	}
	
	public String getName(){
		return name;
	}
}
