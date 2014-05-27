package com.tshevchuk.prayer.data;

import java.util.ArrayList;
import java.util.List;


public class Catalog {
	private static final String SRC_BIBLIA = "Біблія, переклад Івана Хоменка";
	private static final String SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA = "Молитовник «Прийдіте поклонімся»";
	private static final String SRC_DODATOK_KATEKHYZMU_2012 = "Додаток до Катехизму УГКЦ «Христос – наша Пасха» 2012 року";
	private static final String SRC_PRO_SPOVID = "Про Сповідь. о. Порфирій В. Шумило, ЧСВВ, бр. Пімен І. Коневич, ЧСВВ http://osbm-buchach.org.ua/knygy/pro-spovid.html";
	private static final String SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH = "Уроки Біблії http://bible-lessons.in.ua/molutva/dljavagitnux.html";
	private static final String SRC_PSALTYR_1990 = "Молитовний псалтир, Рим 1990";

	private List<MenuItemBase> topMenu = new ArrayList<MenuItemBase>();

	public Catalog() {
		topMenu.add(new MenuItemPrayer("Щоденні молитви", "molytvy-schodenni.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		topMenu.add(new MenuItemPrayer("Ранішні молитви", "molytvy-ranishni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(new MenuItemPrayer("Вечірні молитви", "molytvy-vechirni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));

		topMenu.add(addMolytvyNaKozhenDen());

		topMenu.add(new MenuItemPrayer("Молитви при трапезі",
				"molytvy-pry-trapezi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));

		topMenu.add(addMolytvyRizniPotreby());

		MenuItemSubMenu psalmy = new MenuItemSubMenu("Псалми");
		addPsaloms(psalmy);
		topMenu.add(psalmy);
		topMenu.add(addAkafisty());
		topMenu.add(addLiturhiya());
		topMenu.add(addKatekhPravdy());
		topMenu.add(addProSpovidIPrychastya());
		topMenu.add(new MenuItemCalendar());
	}

	private MenuItemSubMenu addLiturhiya() {
		MenuItemSubMenu liturhiya = new MenuItemSubMenu("Літургія");
		liturhiya
				.addSubItem(new MenuItemPrayer("Божественна Літургія св. отця нашого Йоана Золотоустого",
						"liturhiya/liturhiya-ugcc.html")
						.setSource("Молитовник для української родини. Релігійне видавництво \"Ріки води живої\". Львів-2011"));
		liturhiya
				.addSubItem(new MenuItemPrayer(
						"Чин священної і Божественної Літургії святого Івана Золотоустого",
						"liturhiya/liturhiya.html")
						.setSource("Християнський портал Кіріос http://kyrios.org.ua/spirituality/bogosluzhinnja/1198-bozhestvenna-liturgija.html"));
		return liturhiya;
	}

	private MenuItemSubMenu addMolytvyNaKozhenDen() {
		MenuItemSubMenu naKozhenDen = new MenuItemSubMenu("Молитви на кожен день");
		naKozhenDen.addSubItem(new MenuItemPrayer("Молитва ранішнього намірення",
				"na-kozhen-den/ranizhnoho-namirennya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new MenuItemPrayer(
				"Молитва св. Івана Золотоустого на кожну годину дня",
				"na-kozhen-den/iv-zlatoust-kozn-hod-dnya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new MenuItemPrayer("Неділя",
				"na-kozhen-den/nedilya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new MenuItemPrayer("Понеділок",
				"na-kozhen-den/ponedilok.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new MenuItemPrayer("Вівторок",
				"na-kozhen-den/vivtorok.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new MenuItemPrayer("Середа і п’ятниця",
				"na-kozhen-den/sereda-i-pyatnytsya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen.addSubItem(new MenuItemPrayer("Четвер",
				"na-kozhen-den/chetver.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		naKozhenDen
				.addSubItem(new MenuItemPrayer("Субота", "na-kozhen-den/subota.html")
						.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		return naKozhenDen;
	}

	private MenuItemSubMenu addAkafisty() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Акафісти");
		menu.addSubItem(new MenuItemPrayer("Акафіст Благовіщенню Пресвятої Богородиці",
				"akafisty/blahovischennyu-bohorodytsi.html")
				.setSource("Християнський портал Кіріос http://kyrios.org.ua/spirituality/akafisti/718-akafist-blagovischennju.html"));
		return menu;
	}

	
	private MenuItemSubMenu addKatekhPravdy() {
		MenuItemSubMenu katehPravdy = new MenuItemSubMenu("Катехизмові правди");
		katehPravdy.addSubItem(new MenuItemPrayer("Заповіді",
				"kateh-pravdy/zapovidi.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new MenuItemPrayer("Чесноти",
				"kateh-pravdy/chesnoty.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new MenuItemPrayer("Пости та загальниці",
				"kateh-pravdy/posty-ta-zahalnytsi.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new MenuItemPrayer("Гріхи", "kateh-pravdy/hrikhy.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		katehPravdy.addSubItem(new MenuItemPrayer("Християнський привіт",
				"kateh-pravdy/khryst-pryvit.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		return katehPravdy;
	}

	private MenuItemSubMenu addProSpovidIPrychastya() {
		MenuItemSubMenu proSpovid = new MenuItemSubMenu("Сповідь та причастя");
		proSpovid.addSubItem(new MenuItemPrayer("Про щастя людини",
				"pro-spovid/pro-schastya.html").setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new MenuItemPrayer("Церква як Божа установа",
				"pro-spovid/cerkva-yak-bozha-ustanova.html")
				.setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new MenuItemPrayer("Сором у визнанні гріхів",
				"pro-spovid/sorom-u-vyznanni-hrikhiv.html")
				.setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new MenuItemPrayer("Чи всі сповіді є добрими?",
				"pro-spovid/chy-vsi-spovidi-dobri.html")
				.setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new MenuItemPrayer("Що потрібно знати, щоб сповідатися?",
				"pro-spovid/scho-potribno-znaty.html")
				.setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new MenuItemPrayer("Святе Причастя",
				"pro-spovid/svyate-prychastya.html").setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new MenuItemPrayer("Приготування до сповіді",
				"pro-spovid/prohotuvannya.html").setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new MenuItemPrayer("Деякі додаткові зауваги",
				"pro-spovid/dodatkovi-zauvahy.html").setSource(SRC_PRO_SPOVID));
		proSpovid.addSubItem(new MenuItemPrayer("Сповідь", "pro-spovid/spovid.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		proSpovid.addSubItem(new MenuItemPrayer("Молитви в часі сповіді",
				"pro-spovid/molytvy-v-chasi-spovidi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		proSpovid.addSubItem(new MenuItemPrayer("Молитви перед святим причастям",
				"pro-spovid/molytvy-pered-prychastyam.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		proSpovid.addSubItem(new MenuItemPrayer("Молитви по святім причастю",
				"pro-spovid/molytvy-pislya-prychastya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		return proSpovid;
	}

	private MenuItemSubMenu addMolytvyRizniPotreby() {
		MenuItemSubMenu rizniPotreby = new MenuItemSubMenu("Молитви на різні потреби");

		MenuItemSubMenu dlyaVahitnykh = new MenuItemSubMenu(
				"Молитви для вагітних",
				new MenuItemPrayer[] {
						new MenuItemPrayer(
								"Молитва вагітної жінки",
								"rizni-potreby/dlya-vahitnykh/vahitnoi-zhinky.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new MenuItemPrayer(
								"Молитва – Благословення вагітних жінок",
								"rizni-potreby/dlya-vahitnykh/blahoslovennya-vahitnykh-zhinok.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new MenuItemPrayer(
								"Молитва матері, яка очікує дитину",
								"rizni-potreby/dlya-vahitnykh/materi-ochikuye-dytynu.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new MenuItemPrayer(
								"Молитва до Богородиці для допомоги жінкам у часі пологів",
								"rizni-potreby/dlya-vahitnykh/do-bohorodytsi-dopomohy-pry-polohakh.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new MenuItemPrayer(
								"Молитва вагітних жінок за успішні пологи",
								"rizni-potreby/dlya-vahitnykh/za-uspishni-polohy.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new MenuItemPrayer(
								"Молитва при важких пологах",
								"rizni-potreby/dlya-vahitnykh/pry-vazhkykh-polohakh.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new MenuItemPrayer(
								"Молитва – Благословення жінки після пологів",
								"rizni-potreby/dlya-vahitnykh/blahoslovennya-pislya-polohiv.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new MenuItemPrayer(
								"Молитва за ласку годування грудьми",
								"rizni-potreby/dlya-vahitnykh/za-lasku-hoduvannya-hruddyu.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH),
						new MenuItemPrayer(
								"Молитва – Благословення вагітних жінок після Літургії",
								"rizni-potreby/dlya-vahitnykh/blahoslovennya-pislya-liturhiyi.html",
								SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH) });
		rizniPotreby.addSubItem(dlyaVahitnykh);

		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва подяки",
				"rizni-potreby/podyaky.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва на всяке прошення",
				"rizni-potreby/vsyake-proshennya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва до Пресвятої Богородиці",
				"rizni-potreby/do-presv-bohorod.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва у терпінні",
				"rizni-potreby/u-terpinni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва за недужих",
				"rizni-potreby/za-neduzhykh.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва на всяку неміч",
				"rizni-potreby/na-vsyaku-nemich.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва при вмираючих",
				"rizni-potreby/pry-vmyrayuchykh.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва у годині смерти",
				"rizni-potreby/u-hodyni-smerti.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва християнської родини",
				"rizni-potreby/khryst-rodyny.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва батьків за дітей",
				"rizni-potreby/batkiv-za-ditey.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва дітей за батьків",
				"rizni-potreby/ditey-za-batkiv.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва учня (учениці)",
				"rizni-potreby/uchnya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва за перемогу над спокусами",
				"rizni-potreby/peremoha-nad-spokusamy.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва про духа покори",
				"rizni-potreby/dukha-pokory.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва в день іменин",
				"rizni-potreby/v-den-imenyn.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer(
				"Молитва за того, хто хоче іти в путь",
				"rizni-potreby/ity-v-put.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва на благословення дому",
				"rizni-potreby/blahoslovennya-domu.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer("Молитва на освячення всякої речі",
				"rizni-potreby/osvyach-rechi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		rizniPotreby.addSubItem(new MenuItemPrayer(
				"Молитви за з’єднання всіх християн",
				"rizni-potreby/zyednannya-khrystyyan.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		return rizniPotreby;
	}

	private void addPsaloms(MenuItemSubMenu sm) {
		for (int i = 1; i <= 150; i++) {
			String name = "Псалом " + i;
			if (i >= 10) {
				name += " (" + (i - 1) + ")";
			}
			sm.addSubItem(new MenuItemPrayer(name, "psalmy/" + i + ".html").setSource(
					SRC_BIBLIA).setIsHtml(false));
		}
	}

	public List<MenuItemBase> getTopMenuItems() {
		return topMenu;
	}
}
