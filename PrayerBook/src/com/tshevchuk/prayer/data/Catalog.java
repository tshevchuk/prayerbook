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

		SubMenu rizniPotreby = new SubMenu("Молитви на різні потреби");
		rizniPotreby.addSubItem(new Prayer("Молитва подяки",
				"rizni_potreby/podyaky.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва на всяке прошення",
				"rizni_potreby/vsyake-proshennya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва до Пресвятої Богородиці",
				"rizni_potreby/do-presv-bohorod.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва у терпінні",
				"rizni_potreby/u-terpinni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва за недужих",
				"rizni_potreby/za-neduzhykh.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва на всяку неміч",
				"rizni_potreby/na-vsyaku-nemich.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва при вмираючих",
				"rizni_potreby/pry-vmyrayuchykh.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва у годині смерти",
				"rizni_potreby/u-hodyni-smerti.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва християнської родини",
				"rizni_potreby/khryst-rodyny.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва батьків за дітей",
				"rizni_potreby/batkiv-za-ditey.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва дітей за батьків",
				"rizni_potreby/ditey-za-batkiv.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва учня (учениці)",
				"rizni_potreby/uchnya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва за перемогу над спокусами",
				"rizni_potreby/peremoha-nad-spokusamy.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва про духа покори",
				"rizni_potreby/dukha-pokory.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва в день іменин",
				"rizni_potreby/v-den-imenyn.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва за того, хто хоче іти в путь",
				"rizni_potreby/ity-v-put.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва на благословення дому",
				"rizni_potreby/blahoslovennya-domu.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва на освячення всякої речі",
				"rizni_potreby/osvyach-rechi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитви за з’єднання всіх християн",
				"rizni_potreby/zyednannya-khrystyyan.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(rizniPotreby);

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
		private String about;

		public Prayer(String name, String fileName) {
			super(name);
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}

		public Prayer setSource(String source) {
			this.about = "Джерело тексту: " + source;
			return this;
		}

		public String getAbout() {
			return about;
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
