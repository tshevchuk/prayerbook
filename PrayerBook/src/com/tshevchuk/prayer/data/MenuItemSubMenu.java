package com.tshevchuk.prayer.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MenuItemSubMenu extends MenuItemBase {
	private static final long serialVersionUID = 1L;

	private List<MenuItemBase> subItems = new ArrayList<MenuItemBase>();

	public MenuItemSubMenu(String name) {
		super(name);
	}

	public MenuItemSubMenu(String name, MenuItemBase... subItems) {
		this(name);
		this.subItems = Arrays.asList(subItems);
	}

	public List<MenuItemBase> getSubItems() {
		return subItems;
	}

	MenuItemSubMenu addSubItem(MenuItemBase item) {
		subItems.add(item);
		return this;
	}
}