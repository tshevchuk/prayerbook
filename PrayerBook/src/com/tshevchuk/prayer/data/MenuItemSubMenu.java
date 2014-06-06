package com.tshevchuk.prayer.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MenuItemSubMenu extends MenuItemBase {
	private static final long serialVersionUID = 1L;

	private List<MenuItemBase> subItems = new ArrayList<MenuItemBase>();

	public MenuItemSubMenu(int id, String name) {
		super(id, name);
	}

	public MenuItemSubMenu(int id, String name, MenuItemBase... subItems) {
		this(id, name);
		this.subItems = Arrays.asList(subItems);
	}

	public List<MenuItemBase> getSubItems() {
		return subItems;
	}

	MenuItemSubMenu addSubItem(MenuItemBase item) {
		subItems.add(item);
		return this;
	}
	
	public MenuItemPrayer html(int id, String name,
			String fileName, String source) {
		MenuItemPrayer mi = new MenuItemPrayer(id, name, fileName);
		mi.setSource(source);
		addSubItem(mi);
		return mi;
	}

	public MenuItemPrayer text(int id, String name,
			String fileName, String source) {
		return html(id, name, fileName, source).setIsHtml(false);
	}
	
	public MenuItemSubMenu subMenu(int id, String name){
		MenuItemSubMenu sm = new MenuItemSubMenu(id, name);
		addSubItem(sm);
		return sm;
	}
}