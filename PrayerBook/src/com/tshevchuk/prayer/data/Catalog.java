package com.tshevchuk.prayer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
	private static final String SRC_BIBLIA = "Біблія, переклад Івана Хоменка";
	private static final String SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA = "Молитовник «Прийдіте поклонімся»";
	private static final String SRC_DODATOK_KATEKHYZMU_2012 = "Додаток до Катехизму УГКЦ «Христос – наша Пасха» 2012 року";

	private List<MenuItemBase> topMenu = new ArrayList<MenuItemBase>();

	public Catalog() {
		topMenu.add(new Prayer("Щоденні молитви", "molytvy_schodenni.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		topMenu.add(new Prayer("Ранішні молитви", "molytvy_ranishni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(new Prayer("Вечірні молитви", "molytvy_vechirni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(new Prayer("Молитви на кожен день",
				"molytvy_na_kozhen_den.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(new Prayer("Молитви при трапезі",
				"molytvy_pry_trapezi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(new Prayer("Молитви на різні потреби",
				"molytvy_na_rizni_potreby.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(new Prayer("Псалом 90", "molytvy_psalom_90.html")
				.setSource(SRC_BIBLIA));

		SubMenu katehPravdy = new SubMenu("Катехизмові правди");
		katehPravdy.addSubItem(new Prayer("Заповіді",
				"kateh_pravdy/zapovidi.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new Prayer("Чесноти",
				"kateh_pravdy/chesnoty.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new Prayer("Пости та загальниці",
				"kateh_pravdy/posty-ta-zahalnytsi.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new Prayer("Гріхи", "kateh_pravdy/hrikhy.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new Prayer("Християнський привіт",
				"kateh_pravdy/khryst-pryvit.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		topMenu.add(katehPravdy);
	}

	public List<MenuItemBase> getTopMenuItems() {
		return topMenu;
	}

	public abstract static class MenuItemBase implements Serializable {
		private static final long serialVersionUID = 1L;

		private String name;

		public MenuItemBase(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static class Prayer extends MenuItemBase {
		private static final long serialVersionUID = 1L;

		private String fileName;
		private String source;

		public Prayer(String name, String fileName) {
			super(name);
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}

		public Prayer setSource(String source) {
			this.source = source;
			return this;
		}

		public String getSource() {
			return source;
		}
	}

	public static class SubMenu extends MenuItemBase {
		private static final long serialVersionUID = 1L;

		private List<MenuItemBase> subItems = new ArrayList<Catalog.MenuItemBase>();

		public SubMenu(String name) {
			super(name);
		}

		public List<MenuItemBase> getSubItems() {
			return subItems;
		}

		private SubMenu addSubItem(MenuItemBase item) {
			subItems.add(item);
			return this;
		}
	}
}
