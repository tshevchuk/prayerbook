package com.tshevchuk.prayer.data;

import com.tshevchuk.prayer.data.MenuItemPrayer.Type;

import java.util.ArrayList;
import java.util.List;

public class MenuItemSubMenu extends MenuItemBase {

	private final List<MenuItemBase> subItems = new ArrayList<>();

	public MenuItemSubMenu(int id, String name) {
		super(id, name);
	}

	public List<MenuItemBase> getSubItems() {
		return subItems;
	}

	@SuppressWarnings("UnusedReturnValue")
	MenuItemSubMenu addSubItem(MenuItemBase item) {
		item.setParentItemId(getId());
		item.setOfficialUGCCText(isOfficialUGCCText());
		subItems.add(item);
		return this;
	}

	public MenuItemPrayer html(int id, String name, String fileName,
			String source) {
		MenuItemPrayer mi = new MenuItemPrayer(id, name, fileName);
		mi.setSource(source);
		addSubItem(mi);
		return mi;
	}

	public MenuItemPrayer text(int id, String name, String fileName,
			String source) {
		return html(id, name, fileName, source).setType(Type.Text);
	}

	public MenuItemPrayer web(int id, String name, String fileName,
			String source) {
		return html(id, name, fileName, source).setType(Type.HtmlInWebView);
	}

	public MenuItemSubMenu subMenu(int id, String name) {
		MenuItemSubMenu sm = new MenuItemSubMenu(id, name);
		addSubItem(sm);
		return sm;
	}
	
	@Override
	public MenuItemSubMenu setOfficialUGCCText(boolean isOfficialUGCCText) {
		return (MenuItemSubMenu) super.setOfficialUGCCText(isOfficialUGCCText);
	}
}