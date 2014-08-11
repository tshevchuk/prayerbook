package com.tshevchuk.prayer.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.SparseArray;

import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.catholic.R;

public class Catalog {
	public static final int ID_CALENDAR = 1;
	public static final int ID_RECENT_SCREENS = 2;

	private static final int NEXT_ID_TO_ADD = 58;

	private List<MenuItemBase> topMenu = new ArrayList<MenuItemBase>();
	private SparseArray<MenuItemBase> menuItemsByIds = new SparseArray<MenuItemBase>();

	{
		MenuItemSubMenu menu = new MenuItemSubMenu(0, "top");
		menu.addSubItem(new MenuItemOftenUsed(ID_RECENT_SCREENS));
		menu.addSubItem(new MenuItemCalendar(ID_CALENDAR));

		try {
			readFromXml(menu);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		topMenu = menu.getSubItems();

		verifyUniqueId();
	}

	private void readFromXml(MenuItemSubMenu topMenu)
			throws XmlPullParserException, IOException {
		XmlResourceParser xrp = PrayerBookApplication.getInstance()
				.getResources().getXml(R.xml.catalog);
		xrp.next();
		int eventType = xrp.getEventType();
		int menuLevel = 0;
		ArrayList<MenuItemSubMenu> subItems = new ArrayList<MenuItemSubMenu>();
		subItems.add(topMenu);
		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.START_DOCUMENT) {
			} else if (eventType == XmlPullParser.START_TAG) {
				String tag = xrp.getName();
				int id = xrp.getAttributeIntValue(null, "id", 1);
				String name = xrp.getAttributeValue(null, "name");
				String file = "prayers/" + xrp.getAttributeValue(null, "file");
				if (tag.equals("submenu")) {
					MenuItemSubMenu mi = subItems.get(menuLevel).subMenu(id,
							name);
					if (subItems.size() > menuLevel + 1) {
						subItems.set(menuLevel + 1, mi);
					} else {
						subItems.add(mi);
					}
					menuLevel++;
				} else if (tag.equals("html")) {
					subItems.get(menuLevel).web(id, name, file, null);
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				String tag = xrp.getName();
				if (tag.equals("submenu")) {
					menuLevel--;
				}
			} else if (eventType == XmlPullParser.TEXT) {
			}
			eventType = xrp.next();
		}
	}

	public List<MenuItemBase> getTopMenuItems() {
		return topMenu;
	}

	private void verifyUniqueId() {
		if (Utils.isDebuggable()) {
			Set<Integer> usedIds = new TreeSet<Integer>();
			for (MenuItemBase mi : topMenu) {
				verifyUniqueId(usedIds, mi);
			}
		}
	}

	private void verifyUniqueId(Set<Integer> usedIds, MenuItemBase mi) {
		if (mi instanceof MenuItemSubMenu) {
			for (MenuItemBase item : ((MenuItemSubMenu) mi).getSubItems()) {
				verifyUniqueId(usedIds, item);
			}
		}
		if (usedIds.contains(mi.getId())) {
			throw new RuntimeException("Catalog already contains item with id "
					+ mi.getId());
		}
		usedIds.add(mi.getId());
		if (NEXT_ID_TO_ADD <= mi.getId()) {
			throw new RuntimeException(
					"Catalog: please increment NEXT_ID_TO_ADD to at least "
							+ (mi.getId() + 1));
		}
	}

	private MenuItemBase getMenuItemById(int id, List<MenuItemBase> items) {
		for (MenuItemBase mi : items) {
			if (mi.getId() == id) {
				return mi;
			}
			if (mi instanceof MenuItemSubMenu) {
				MenuItemBase item = getMenuItemById(id,
						((MenuItemSubMenu) mi).getSubItems());
				if (item != null) {
					return item;
				}
			}
		}
		return null;
	}

	public MenuItemBase getMenuItemById(int id) {
		MenuItemBase mi = menuItemsByIds.get(id);
		if (mi == null) {
			mi = getMenuItemById(id, topMenu);
			menuItemsByIds.put(id, mi);
		}
		return mi;
	}

	public Set<String> getAllSources() {
		Set<String> sources = new HashSet<String>();
		for (MenuItemBase mi : topMenu)
			addSources(sources, mi);
		return sources;
	}

	private void addSources(Set<String> sources, MenuItemBase mi) {
		if (mi instanceof MenuItemPrayer) {
			String src = ((MenuItemPrayer) mi).getSource();
			if (!TextUtils.isEmpty(src)) {
				sources.add(src);
			}
		} else if (mi instanceof MenuItemSubMenu) {
			for (MenuItemBase sm : ((MenuItemSubMenu) mi).getSubItems())
				addSources(sources, sm);
		}
	}

	public List<SearchItem> filter(String searchPhrase) {
		Catalog cat = PrayerBookApplication.getInstance().getCatalog();

		List<SearchItem> filtered = new ArrayList<SearchItem>();

		if (TextUtils.isEmpty(searchPhrase)) {
			return filtered;
		}

		for (MenuItemBase mi : cat.getTopMenuItems()) {
			filter(searchPhrase, filtered, mi);
		}
		return filtered;
	}

	private List<SearchItem> filter(String searchPhrase,
			List<SearchItem> filtered, MenuItemBase mi) {
		String name = mi.getName().toLowerCase(Utils.getUkrainianLocale())
				.replace('’', '\'');
		int searchPhraseStartPos = name.indexOf(searchPhrase);
		if (searchPhraseStartPos != -1) {
			StringBuilder sb = new StringBuilder(mi.getName());
			sb.insert(searchPhraseStartPos + searchPhrase.length(), "</b>");
			sb.insert(searchPhraseStartPos, "<b>");
			filtered.add(new SearchItem(mi, sb.toString()));
		}

		if (mi instanceof MenuItemSubMenu) {
			for (MenuItemBase si : ((MenuItemSubMenu) mi).getSubItems()) {
				filter(searchPhrase, filtered, si);
			}
		}
		return filtered;
	}
}
