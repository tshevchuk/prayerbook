package com.tshevchuk.prayer.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.text.TextUtils;
import android.util.SparseArray;

import com.tshevchuk.prayer.PrayerBookApplication;
import com.tshevchuk.prayer.Utils;

public class Catalog {
	public static final int ID_CALENDAR = 1;
	public static final int ID_RECENT_SCREENS = 2;

	private static final int NEXT_ID_TO_ADD = 39;

	private List<MenuItemBase> topMenu = new ArrayList<MenuItemBase>();
	private SparseArray<MenuItemBase> menuItemsByIds = new SparseArray<MenuItemBase>();

	{
		MenuItemSubMenu menu = new MenuItemSubMenu(0, "top");
		menu.addSubItem(new MenuItemOftenUsed(ID_RECENT_SCREENS));
		menu.addSubItem(new MenuItemCalendar(ID_CALENDAR));

		MenuItemSubMenu sm = menu.subMenu(3, "1. Молімося кожного дня");
		sm.web(4, "1.1. Щоденні молитви", "prayers/schodenni-molytvy.html",
				null);
		sm.web(5, "1.2. Катихізис Католицької Церкви",
				"prayers/katehyzis.html", null);
		sm.subMenu(6, "1.3. Ранкові молитви");
		sm.subMenu(7, "1.4. Молитви протягом дня");
		sm.subMenu(8, "1.5. Вечірні молитви");
		sm.subMenu(9, "1.6. Молитва сім’ї");
		sm.subMenu(10, "1.7. Молитва псалмами");
		sm.subMenu(11, "1.8. Роздуми Lectio Divina");

		sm = menu.subMenu(12, "2. Таїнства Христа і Церкви");
		sm.subMenu(13, "2.1. Таїнство Хрещення");
		sm.subMenu(14, "2.2. Таїнство миропомазання");
		sm.subMenu(15, "2.3. Таїнство Євхаристії");
		sm.subMenu(16, "2.3.1. Свята Літургія римського обряду");
		sm.subMenu(17, "2.3.2. Подяка після Святої Меси");
		sm.subMenu(18, "2.4. Таїнство Покаяння і Примирення");
		sm.subMenu(19, "2.5. Таїнство Єлеопомазання");
		sm.subMenu(20, "2.6. Таїнство Священства");
		sm.subMenu(21, "2.7. Таїнство Подружжя");

		sm = menu.subMenu(22, "3. Молитви на честь Триєдиного Бога");
		sm.subMenu(23, "3.1. До Бога Отця");
		sm.subMenu(24, "3.2. До Бога Сина");
		sm.subMenu(25, "3.3. На честь Святого Духа");
		sm.subMenu(26, "3.4. На честь Божого милосердя");

		sm = menu.subMenu(27, "4. Молитви до Ангелів");
		sm.subMenu(28, "4.1. Молитви під час боротьби з силами темряви");

		menu.subMenu(29, "5. Молитви на честь Богородиці");

		sm = menu.subMenu(30, "6. Молитви на честь святих і про їх заступництво");
		sm.subMenu(31, "6.1. На честь святого Йосифа");
		sm.subMenu(32, "6.2. Молитви до різних святих");

		menu.subMenu(33, "7. Молитви за хворих");

		menu.subMenu(34, "8. Молитви за помираючих");

		menu.subMenu(35, "9. Молитви за померлих");

		menu.subMenu(36, "10. Молитви в різних потребах");

		menu.subMenu(37, "11. Церковний рік");

		menu.subMenu(38, "12. Молитви латинською мовою");

		topMenu = menu.getSubItems();

		verifyUniqueId();
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
