package com.tshevchuk.prayer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.text.TextUtils;

public class Catalog {
	private static final String SRC_BIBLIA = "Біблія, переклад Івана Хоменка";
	private static final String SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA = "Молитовник «Прийдіте поклонімся»";
	private static final String SRC_DODATOK_KATEKHYZMU_2012 = "Додаток до Катехизму УГКЦ «Христос – наша Пасха» 2012 року";
	private static final String SRC_PRO_SPOVID = "Про Сповідь. о. Порфирій В. Шумило, ЧСВВ, бр. Пімен І. Коневич, ЧСВВ http://osbm-buchach.org.ua/knygy/pro-spovid.html";
	private static final String SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH = "Уроки Біблії http://bible-lessons.in.ua/molutva/dljavagitnux.html";
	private static final String SRC_PSALTYR_1990 = "Молитовний псалтир, Рим 1990";

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

		SubMenu dlyaVahitnykh = new SubMenu(
				"Молитви для вагітних",
				new Prayer[] {
						new Prayer(
								"Молитва вагітної жінки",
								"rizni-potreby/dlya-vahitnykh/vahitnoi-zhinky.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new Prayer(
								"Молитва – Благословення вагітних жінок",
								"rizni-potreby/dlya-vahitnykh/blahoslovennya-vahitnykh-zhinok.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new Prayer(
								"Молитва матері, яка очікує дитину",
								"rizni-potreby/dlya-vahitnykh/materi-ochikuye-dytynu.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new Prayer(
								"Молитва до Богородиці для допомоги жінкам у часі пологів",
								"rizni-potreby/dlya-vahitnykh/do-bohorodytsi-dopomohy-pry-polohakh.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new Prayer(
								"Молитва вагітних жінок за успішні пологи",
								"rizni-potreby/dlya-vahitnykh/za-uspishni-polohy.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new Prayer(
								"Молитва при важких пологах",
								"rizni-potreby/dlya-vahitnykh/pry-vazhkykh-polohakh.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new Prayer(
								"Молитва – Благословення жінки після пологів",
								"rizni-potreby/dlya-vahitnykh/blahoslovennya-pislya-polohiv.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new Prayer(
								"Молитва за ласку годування грудьми",
								"rizni-potreby/dlya-vahitnykh/za-lasku-hoduvannya-hruddyu.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new Prayer(
								"Молитва – Благословення вагітних жінок після Літургії",
								"rizni-potreby/dlya-vahitnykh/blahoslovennya-pislya-liturhiyi.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH) });
		rizniPotreby.addSubItem(dlyaVahitnykh);

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

		SubMenu psalmy = new SubMenu("Псалми");
		addPsaloms(psalmy);
		topMenu.add(psalmy);

		topMenu.add(new Prayer("Літургія", "liturhiya.html")
				.setFullName(
						"Чин священної і Божественної Літургії святого Івана Золотоустого")
				.setSource(
						"Християнський портал Кіріос http://kyrios.org.ua/spirituality/bogosluzhinnja/1198-bozhestvenna-liturgija.html"));

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

		SubMenu proSpovid = new SubMenu("Про сповідь");
		proSpovid.addSubItem(new Prayer("Про щастя людини",
				"pro-spovid/pro-schastya.html").setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new Prayer("Церква як Божа установа",
				"pro-spovid/cerkva-yak-bozha-ustanova.html")
				.setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new Prayer("Сором у визнанні гріхів",
				"pro-spovid/sorom-u-vyznanni-hrikhiv.html")
				.setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new Prayer("Чи всі сповіді є добрими?",
				"pro-spovid/chy-vsi-spovidi-dobri.html")
				.setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new Prayer("Що потрібно знати, щоб сповідатися?",
				"pro-spovid/scho-potribno-znaty.html")
				.setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new Prayer("Святе Причастя",
				"pro-spovid/svyate-prychastya.html").setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new Prayer("Приготування до сповіді",
				"pro-spovid/prohotuvannya.html").setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new Prayer("Деякі додаткові зауваги",
				"pro-spovid/dodatkovi-zauvahy.html").setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new Prayer("Сповідь", "pro-spovid/spovid.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		proSpovid.addSubItem(new Prayer("Молитви в часі сповіді",
				"pro-spovid/molytvy-v-chasi-spovidi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		proSpovid.addSubItem(new Prayer("Молитви перед святим причастям",
				"pro-spovid/molytvy-pered-prychastyam.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		proSpovid.addSubItem(new Prayer("Молитви по святім причастю",
				"pro-spovid/molytvy-pislya-prychastya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(proSpovid);
	}

	private void addPsaloms(SubMenu sm) {
		for (int i = 1; i <= 150; i++) {
			String name = "Псалом " + i;
			if (i >= 10) {
				name += " (" + (i - 1) + ")";
			}
			sm.addSubItem(new Prayer(name, "psalmy/" + i + ".html").setSource(
					SRC_BIBLIA).setIsHtml(false));
		}
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
		private boolean isHtml = true;

		public Prayer(String name, String fileName) {
			super(name);
			this.fileName = fileName;
		}

		public Prayer(String name, String fileName, String source) {
			this(name, fileName);
			setSource(source);
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

		public Prayer setIsHtml(boolean isHtml) {
			this.isHtml = isHtml;
			return this;
		}

		public boolean isHtml() {
			return isHtml;
		}
	}

	public static class SubMenu extends MenuItemBase {
		private static final long serialVersionUID = 1L;

		private List<MenuItemBase> subItems = new ArrayList<Catalog.MenuItemBase>();

		public SubMenu(String name) {
			super(name);
		}

		public SubMenu(String name, MenuItemBase... subItems) {
			this(name);
			this.subItems = Arrays.asList(subItems);
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
