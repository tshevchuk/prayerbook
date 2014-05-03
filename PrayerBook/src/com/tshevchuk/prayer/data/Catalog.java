package com.tshevchuk.prayer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

public class Catalog {
	private static final String SRC_BIBLIA = "Біблія, переклад Івана Хоменка";
	private static final String SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA = "Молитовник «Прийдіте поклонімся»";
	private static final String SRC_DODATOK_KATEKHYZMU_2012 = "Додаток до Катехизму УГКЦ «Христос – наша Пасха» 2012 року";

	private List<MenuItemBase> topMenu = new ArrayList<MenuItemBase>();

	public Catalog() {
		topMenu.add(new Prayer("Щоденні молитви", "molytvy-schodenni.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		topMenu.add(new Prayer("Ранішні молитви", "molytvy-ranishni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(new Prayer("Вечірні молитви", "molytvy-vechirni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));

		SubMenu naKozhenDen = new SubMenu("Молитви на кожен день");
		naKozhenDen.addSubItem(new Prayer("Молитва ранішнього намірення",
				"na-kozhen-den/ranizhnoho-namirennya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new Prayer(
				"Молитва св. Івана Золотоустого на кожну годину дня",
				"na-kozhen-den/iv-zlatoust-kozn-hod-dnya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new Prayer("Неділя",
				"na-kozhen-den/nedilya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new Prayer("Понеділок",
				"na-kozhen-den/ponedilok.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new Prayer("Вівторок",
				"na-kozhen-den/vivtorok.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new Prayer("Середа і п’ятниця",
				"na-kozhen-den/sereda-i-pyatnytsya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new Prayer("Четвер",
				"na-kozhen-den/chetver.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen
				.addSubItem(new Prayer("Субота", "na-kozhen-den/subota.html")
						.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(naKozhenDen);

		topMenu.add(new Prayer("Молитви при трапезі",
				"molytvy-pry-trapezi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));

		SubMenu rizniPotreby = new SubMenu("Молитви на різні потреби");
		rizniPotreby.addSubItem(new Prayer("Молитва подяки",
				"rizni-potreby/podyaky.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва на всяке прошення",
				"rizni-potreby/vsyake-proshennya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва до Пресвятої Богородиці",
				"rizni-potreby/do-presv-bohorod.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва у терпінні",
				"rizni-potreby/u-terpinni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва за недужих",
				"rizni-potreby/za-neduzhykh.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва на всяку неміч",
				"rizni-potreby/na-vsyaku-nemich.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва при вмираючих",
				"rizni-potreby/pry-vmyrayuchykh.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва у годині смерти",
				"rizni-potreby/u-hodyni-smerti.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва християнської родини",
				"rizni-potreby/khryst-rodyny.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва батьків за дітей",
				"rizni-potreby/batkiv-za-ditey.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва дітей за батьків",
				"rizni-potreby/ditey-za-batkiv.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва учня (учениці)",
				"rizni-potreby/uchnya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва за перемогу над спокусами",
				"rizni-potreby/peremoha-nad-spokusamy.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва про духа покори",
				"rizni-potreby/dukha-pokory.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва в день іменин",
				"rizni-potreby/v-den-imenyn.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer(
				"Молитва за того, хто хоче іти в путь",
				"rizni-potreby/ity-v-put.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва на благословення дому",
				"rizni-potreby/blahoslovennya-domu.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer("Молитва на освячення всякої речі",
				"rizni-potreby/osvyach-rechi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new Prayer(
				"Молитви за з’єднання всіх християн",
				"rizni-potreby/zyednannya-khrystyyan.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(rizniPotreby);

		topMenu.add(new Prayer("Псалом 90", "molytvy-psalom-90.html")
				.setSource(SRC_BIBLIA));

		topMenu.add(new Prayer(
				"Літургія",
				"liturhiya.html").setFullName("Чин священної і Божественної Літургії святого Івана Золотоустого")
				.setSource("Християнський портал Кіріос http://kyrios.org.ua/spirituality/bogosluzhinnja/1198-bozhestvenna-liturgija.html"));

		SubMenu katehPravdy = new SubMenu("Катехизмові правди");
		katehPravdy.addSubItem(new Prayer("Заповіді",
				"kateh-pravdy/zapovidi.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new Prayer("Чесноти",
				"kateh-pravdy/chesnoty.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new Prayer("Пости та загальниці",
				"kateh-pravdy/posty-ta-zahalnytsi.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new Prayer("Гріхи", "kateh-pravdy/hrikhy.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new Prayer("Християнський привіт",
				"kateh-pravdy/khryst-pryvit.html")
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
		private String fullName;

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

		public Prayer setFullName(String fullName) {
			this.fullName = fullName;
			return this;
		}

		public String getFullName() {
			if (TextUtils.isEmpty(fullName)) {
				return getName();
			}
			return fullName;
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
