package com.tshevchuk.prayer.data;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
	private static final String SRC_BIBLIA = "Біблія, переклад Івана Хоменка";
	private static final String SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA = "Молитовник «Прийдіте поклонімся»";
	private static final String SRC_DODATOK_KATEKHYZMU_2012 = "Додаток до Катехизму УГКЦ «Христос – наша Пасха» 2012 року";
	private static final String SRC_PRO_SPOVID = "Про Сповідь. о. Порфирій В. Шумило, ЧСВВ, бр. Пімен І. Коневич, ЧСВВ http://osbm-buchach.org.ua/knygy/pro-spovid.html";
	private static final String SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH = "Уроки Біблії http://bible-lessons.in.ua/molutva/dljavagitnux.html";
	private static final String SRC_AKAFISTY_MOLYTVA = "МОЛИТВА - Акафіст http://molytva.at.ua/index/akafist/0-293";
	private static final String SRC_VERVYTSYA_MOLYTVA = "МОЛИТВА - Вервиці http://molytva.at.ua/index/vervici/0-46";

	private List<MenuItemBase> topMenu = new ArrayList<MenuItemBase>();

	public Catalog() {
		topMenu.add(new MenuItemPrayer("Щоденні молитви",
				"molytvy-schodenni.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		topMenu.add(new MenuItemPrayer("Ранішні молитви",
				"molytvy-ranishni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		topMenu.add(new MenuItemPrayer("Вечірні молитви",
				"molytvy-vechirni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));

		topMenu.add(addMolytvyNaKozhenDen());

		topMenu.add(new MenuItemPrayer("Молитви при трапезі",
				"molytvy-pry-trapezi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));

		topMenu.add(addMolytvyRizniPotreby());

		topMenu.add(new MenuItemCalendar());

		MenuItemSubMenu psalmy = new MenuItemSubMenu("Псалми");
		addPsaloms(psalmy);
		topMenu.add(psalmy);

		topMenu.add(addAkafisty());
		topMenu.add(addVervytsya());

		topMenu.add(addLiturhiya());
		topMenu.add(addKatekhPravdy());
		topMenu.add(addProSpovidIPrychastya());

		topMenu.add(addPisni());
	}

	private MenuItemSubMenu addPisni() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Пісні");

		MenuItemSubMenu proslavy = new MenuItemSubMenu("Молитва прослави");
		proslavy.text(0, "Пісня подяки св. Амвросія, єпископа Медіолянського",
				"pisni/proslavy/podyaky-amvrosiya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.addSubItem(proslavy);

		MenuItemSubMenu zaNarid = new MenuItemSubMenu("Молитви за нарід");
		proslavy.text(0, "Боже великий, єдиний",
				"pisni/za-narid/bozhe-velykyj-jedynyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		proslavy.text(0, "Боже, вислухай благання",
				"pisni/za-narid/bozhe-vyslukhaj-blahannya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		proslavy.text(0, "Боже великий, Творче всесвіту",
				"pisni/za-narid/bozhe-tvorche-vsesvitu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.addSubItem(zaNarid);

		MenuItemSubMenu kolyady = new MenuItemSubMenu("Коляди");
		kolyady.text(0, "Бог предвічний",
				"pisni/kolyady/boh-predvichynyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "Бог ся рождає", "pisni/kolyady/boh-sya-rozhdaye.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "В Вифлеємі днесь Марія",
				"pisni/kolyady/v-vyflejemi-dnes-mariya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "В Вифлеємі новина",
				"pisni/kolyady/v-vyfleyemi-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "Во Вифлеємі нині новина",
				"pisni/kolyady/vo-vyfleyemi-nyni-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "Возвеселімся всі разом нині",
				"pisni/kolyady/vozveselimsya-vsi-razom-nyni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "Вселенная, веселися",
				"pisni/kolyady/vselennaya-veselysya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "Дивная новина", "pisni/kolyady/dyvnaya-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "На небі зірка ясна засяла",
				"pisni/kolyady/na-nebi-zirka-yasna-zasyala.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "Небо і земля", "pisni/kolyady/nebo-i-zemlya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "Нова радість стала",
				"pisni/kolyady/nova-radist-stala.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "Нова радість стала (1)",
				"pisni/kolyady/nova-radist-stala-1.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		kolyady.text(0, "По всьому світу",
				"pisni/kolyady/po-vsjomu-svitu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.addSubItem(kolyady);

		MenuItemSubMenu velykoposni = new MenuItemSubMenu("Великопосні пісні");
		velykoposni.text(0, "Люди мої, люди",
				"pisni/velykoposni/lyudy-moyi-lyudy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		velykoposni.text(0, "На Вавилонських ріках",
				"pisni/velykoposni/na-vavylonskykh-rikakh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		velykoposni.text(0, "Під хрест Твій стаю",
				"pisni/velykoposni/pid-khrest-tvij-stayu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		velykoposni.text(0, "Поклоняюся, мій Христе",
				"pisni/velykoposni/poklonyayusya-mij-khryste.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		velykoposni.text(0, "Страдальна Мати",
				"pisni/velykoposni/stradalna-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		velykoposni.text(0, "Хрест на плечі накладають",
				"pisni/velykoposni/khrest-na-plechi-nakladayut.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.addSubItem(velykoposni);

		MenuItemSubMenu voskresni = new MenuItemSubMenu("Воскресні пісні");
		voskresni.text(0, "Сей день", "pisni/voskresni/sej-den.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		voskresni.text(0, "Согласно заспіваймо",
				"pisni/voskresni/sohlasno-zaspivajmo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		voskresni.text(0, "Христос воскрес! Радість з неба",
				"pisni/voskresni/khrystos-voskres.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.addSubItem(voskresni);

		MenuItemSubMenu doKhrysta = new MenuItemSubMenu("Пісні до Христа");
		doKhrysta.text(0, "Витай між нами",
				"pisni/khrysta/vytaj-mizh-namy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		doKhrysta.text(0, "Вірую, Господи",
				"pisni/khrysta/viruyu-hospody.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		doKhrysta.text(0, "Ісуса в святих тайнах",
				"pisni/khrysta/isusa-v-svyatykh-tajnakh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		doKhrysta.text(0, "О, Ісусе, Ти з лю6ови",
				"pisni/khrysta/o-isuse-ty-z-lyubovy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		doKhrysta.text(0, "Пливи світами", "pisni/khrysta/plyvy-svitamy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		doKhrysta.text(0, "Тіло Христове", "pisni/khrysta/tilo-khrystove.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.addSubItem(doKhrysta);

		MenuItemSubMenu svyatohoDukha = new MenuItemSubMenu(
				"Пісня до Святого Духа");
		svyatohoDukha.text(0, "Царю небесний",
				"pisni/dukha/tsaryu-nebesnyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.addSubItem(svyatohoDukha);

		MenuItemSubMenu bohorodytsi = new MenuItemSubMenu("Пісні до Богородиці");
		bohorodytsi.text(0, "Богородице Діво",
				"pisni/bohorodytsi/bohorodytse-divo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Вихваляйте доли, гори",
				"pisni/bohorodytsi/vykhvalyajte-doly-hory.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Ген до неба",
				"pisni/bohorodytsi/hen-do-neba.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Достойно єсть",
				"pisni/bohorodytsi/dostojno-yest.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Коли втомлений",
				"pisni/bohorodytsi/koly-vtomlenyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Лугами піснь",
				"pisni/bohorodytsi/luhamy-pisen.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Маріє, Діво благословенна",
				"pisni/bohorodytsi/mariye-divo-blahoslovenna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Маріє Діво, Царице мая",
				"pisni/bohorodytsi/mariye-divo-tsarytse-maya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Нехай звенить",
				"pisni/bohorodytsi/nekhaj-dzvenyt.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "О, Богородице Діво, Маріє",
				"pisni/bohorodytsi/o-bohorodytse-divo-mariye.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "О, всепітая Мати",
				"pisni/bohorodytsi/o-vsepitaya-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "О, Маріє, Мати Божа",
				"pisni/bohorodytsi/o-mariye-maty-bozha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "О, Мати Божа, о, райський цвіте",
				"pisni/bohorodytsi/o-maty-bozha-o-rajskyj-tsvite.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "О, Пречиста",
				"pisni/bohorodytsi/o-prechysta.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Пренебесна, пречудесна",
				"pisni/bohorodytsi/prenebesna-prechudesna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Пречистая Діво, Мати",
				"pisni/bohorodytsi/prechysta-divo-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Просимо тя, Діво",
				"pisni/bohorodytsi/prosymo-tya-divo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		bohorodytsi.text(0, "Там, де в небі",
				"pisni/bohorodytsi/tam-de-v-nebi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.addSubItem(bohorodytsi);

		MenuItemSubMenu svyatykh = new MenuItemSubMenu("Пісні до святих");
		svyatykh.text(0, "Святий Великий Володимире",
				"pisni/svyatykh/svyatyj-velykyj-volodymyre.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		svyatykh.text(0, "О, свята Ольго", "pisni/svyatykh/o-svyata-olho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		svyatykh.text(0, "Пісню слави", "pisni/svyatykh/pisnyu-slavy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		svyatykh.text(0, "Владико, отче", "pisni/svyatykh/vladyko-otche.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		svyatykh.text(0, "О, хто, хто Миколая любить",
				"pisni/svyatykh/o-khto-khto-mykolaya-lyubyt.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.addSubItem(svyatykh);

		MenuItemSubMenu pokhoronni = new MenuItemSubMenu("Похоронні пісні");
		pokhoronni.text(0, "В темну могилу",
				"pisni/pokhoronni/v-temnu-mohylu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		pokhoronni.text(0, "Кругом життя",
				"pisni/pokhoronni/kruhom-zhyttya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		pokhoronni.text(0, "Прощай, душе",
				"pisni/pokhoronni/proschaj-dushe.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		pokhoronni.text(0, "Со святими упокой",
				"pisni/pokhoronni/so-svyatymy-upokoj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.addSubItem(pokhoronni);

		return menu;

	}

	private MenuItemSubMenu addVervytsya() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Вервиця");
		menu.text(0,
				"П’ятнадцять обітниць для тих, хто відмовлятиме святу Вервицю",
				"vervytsya/obitnytsi.html", SRC_VERVYTSYA_MOLYTVA)
				.setFullName(
						"П’ятнадцять обітниць для тих, хто відмовлятиме святу Вервицю (дані бл. Аланові де Рупе)");
		menu.html(0, "Як молитися на Вервиці", "vervytsya/yak-molytysya.html",
				SRC_VERVYTSYA_MOLYTVA);
		menu.html(0, "Молитва на Вервиці",
				"vervytsya/molytva-na-vervytsi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addLiturhiya() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Літургія");
		menu.html(
				0,
				"Божественна Літургія св. отця нашого Йоана Золотоустого",
				"liturhiya/liturhiya-ugcc.html",
				"Молитовник для української родини. Релігійне видавництво \"Ріки води живої\". Львів-2011");
		menu.html(
				0,
				"Чин священної і Божественної Літургії святого Івана Золотоустого",
				"liturhiya/liturhiya.html",
				"Християнський портал Кіріос http://kyrios.org.ua/spirituality/bogosluzhinnja/1198-bozhestvenna-liturgija.html");
		return menu;
	}

	private MenuItemSubMenu addMolytvyNaKozhenDen() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Молитви на кожен день");
		menu.html(0, "Молитва ранішнього намірення",
				"na-kozhen-den/ranizhnoho-namirennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва св. Івана Золотоустого на кожну годину дня",
				"na-kozhen-den/iv-zlatoust-kozn-hod-dnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Неділя", "na-kozhen-den/nedilya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Понеділок", "na-kozhen-den/ponedilok.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Вівторок", "na-kozhen-den/vivtorok.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Середа і п’ятниця",
				"na-kozhen-den/sereda-i-pyatnytsya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Четвер", "na-kozhen-den/chetver.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Субота", "na-kozhen-den/subota.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addAkafisty() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Акафісти");
		menu.html(0, "Про Акафіст", "akafisty/pro-akafist.html",
				SRC_AKAFISTY_MOLYTVA);
		// Матері Божої
		menu.text(0, "Акафіст до Благовіщення Пречистої Діви Марії",
				"akafisty/blahovischennyu-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафiст до Пресвятої Богородицi",
				"akafisty/do-presv-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Матері Божої на честь Її Покрову",
				"akafisty/pokrovu-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст на честь введення в храм Пресвятої Богородиці",
				"akafisty/vvedennyu-v-khram-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст на честь Успення Богородиці",
				"akafisty/uspinnyu-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Матері Божої Неустанної Помочі",
				"akafisty/presv-bohorodytsi-neustannoyi-pomochi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				0,
				"Акафіст до Пресвятої Богородиці на честь Її ікони «Невипивана Чаша»",
				"akafisty/presv-bohorodytsi-nevypyvna-chasha.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				0,
				"Акафіст до Пресвятої Богородиці на честь Її ікони «Всецариця»",
				"akafisty/presv-bohorodytsi-vsecarytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				0,
				"Акафіст Пресвятій Богородиці на честь Її ікони «Годувальниця»",
				"akafisty/presv-bohorodytsi-hoduvalnytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				0,
				"Акафіст до Пресвятої Богородиці на честь її ікони «Цілителька»",
				"akafisty/presv-bohorodytsi-tsilytelnytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Зарваницької Матері Божої",
				"akafisty/materi-bozhoji-zarvanytskoji.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Зарваницької Матері Божої (нова версія)",
				"akafisty/materi-bozhoji-zarvanytskoji-nova.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Матері Божої Крилоської",
				"akafisty/materi-bozhoji-kryloskoji.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Самбірської Богородиці",
				"akafisty/do-sambirskoji-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Унівської Богородиці",
				"akafisty/do-univskoji-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(
				0,
				"Акафіст до Матері Божої на честь Її ікони «Прибавлення розуму»",
				"akafisty/do-bohorodytsi-na-chest-ikony-prybavlennya-rozumu.html",
				SRC_AKAFISTY_MOLYTVA);
		// Пресвятої Тройці
		menu.text(0, "Акафіст до Пресвятої Трійці",
				"akafisty/presvyatoji-trojtsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Святого Духа", "akafisty/svyatoho-dukha.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Ісуса Христа", "akafisty/isusa-khrysta.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Божого Милосердя",
				"akafisty/bozhoho-myloserdya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Христових Страстей",
				"akafisty/khrystovykh-strastej.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Живоносного Гробу і Воскресіння Христа",
				"akafisty/zhyvonosnoho-hrobu-i-voskresinnya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Воскресіння Христового",
				"akafisty/voskresinnya-khrystovoho.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Причастя Святих Христових Таїн",
				"akafisty/prychastya-tain.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст подяки Ісусу Христу «Слава Богові за все»",
				"akafisty/podyaky-isusu.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст «Абетка покаяння»",
				"akafisty/abetka-pokayannya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст - подяка Пресвятій Трійці",
				"akafisty/podyaka-trijtsi.html", SRC_AKAFISTY_MOLYTVA);
		// Святі і мученики
		menu.text(0, "Акафіст до Собору дванадцяти Апостолів Христових",
				"akafisty/do-soboru-apostoliv.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до священомученика Йосафата",
				"akafisty/do-josaphata.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до священомученика Йосафата (другий варіант)",
				"akafisty/do-josaphata-2.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святого київського Аскольда",
				"akafisty/kyivskoho-askolda.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святителя Миколая", "akafisty/mykolaya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до блаженного Омеляна Ковча",
				"akafisty/omelyana-kovcha.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святого отця Піо", "akafisty/otsya-pio.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святого Романа Солодкоспівця",
				"akafisty/romana-solodkospivtsya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святого Івана Предтечі",
				"akafisty/ivana-predtechi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святого Василія Великого",
				"akafisty/vasyliya-velykoho.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святого Агапіта", "akafisty/ahapita.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святих Апостолів Петра і Павла",
				"akafisty/petra-i-pavla.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святого Пантелеймона Цілителя",
				"akafisty/panteleymona-tsilytelya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святої Терези від Дитятка Ісус",
				"akafisty/terezy-vid-isusa.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святої Філомени", "akafisty/filomeny.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святої Варвари", "akafisty/varvary.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до святої Параскеви", "akafisty/paraskevy.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				0,
				"Акафіст Дрогобицьким священомученикам Якиму, Віталію та Северіяну",
				"akafisty/drohobytskomu-yakymu.html", SRC_AKAFISTY_MOLYTVA);
		// Інші
		menu.text(0, "Акафіст до Архистратига Михаїла",
				"akafisty/mykhajila.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст до Архистратига Михаїла (другий варіант)",
				"akafisty/mykhajila-2.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0,
				"Акафіст покаянний жінок, які вбили немовлят у своїй утробі",
				"akafisty/zhinok-vbyly-nemovlyat.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст за упокій померлих (багатьох)",
				"akafisty/dushi-pomerlykh.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(0, "Акафіст за померлого (одного)",
				"akafisty/pomerloho-odnoho.html", SRC_AKAFISTY_MOLYTVA);
		return menu;
	}

	private MenuItemSubMenu addKatekhPravdy() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Катехизмові правди");
		menu.html(0, "Заповіді", "kateh-pravdy/zapovidi.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(0, "Чесноти", "kateh-pravdy/chesnoty.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(0, "Пости та загальниці",
				"kateh-pravdy/posty-ta-zahalnytsi.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(0, "Гріхи", "kateh-pravdy/hrikhy.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(0, "Християнський привіт", "kateh-pravdy/khryst-pryvit.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		return menu;
	}

	private MenuItemSubMenu addProSpovidIPrychastya() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Сповідь та причастя");
		menu.html(0, "Про щастя людини", "pro-spovid/pro-schastya.html",
				SRC_PRO_SPOVID);
		menu.html(0, "Церква як Божа установа",
				"pro-spovid/cerkva-yak-bozha-ustanova.html", SRC_PRO_SPOVID);
		menu.html(0, "Сором у визнанні гріхів",
				"pro-spovid/sorom-u-vyznanni-hrikhiv.html", SRC_PRO_SPOVID);
		menu.html(0, "Чи всі сповіді є добрими?",
				"pro-spovid/chy-vsi-spovidi-dobri.html", SRC_PRO_SPOVID);
		menu.html(0, "Що потрібно знати, щоб сповідатися?",
				"pro-spovid/scho-potribno-znaty.html", SRC_PRO_SPOVID);
		menu.html(0, "Святе Причастя", "pro-spovid/svyate-prychastya.html",
				SRC_PRO_SPOVID);
		menu.html(0, "Приготування до сповіді",
				"pro-spovid/prohotuvannya.html", SRC_PRO_SPOVID);
		menu.html(0, "Деякі додаткові зауваги",
				"pro-spovid/dodatkovi-zauvahy.html", SRC_PRO_SPOVID);
		menu.html(0, "Сповідь", "pro-spovid/spovid.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитви в часі сповіді",
				"pro-spovid/molytvy-v-chasi-spovidi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитви перед святим причастям",
				"pro-spovid/molytvy-pered-prychastyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитви по святім причастю",
				"pro-spovid/molytvy-pislya-prychastya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addMolytvyRizniPotreby() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Молитви на різні потреби");

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
		menu.addSubItem(dlyaVahitnykh);

		menu.html(0, "Молитва подяки", "rizni-potreby/podyaky.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва на всяке прошення",
				"rizni-potreby/vsyake-proshennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва до Пресвятої Богородиці",
				"rizni-potreby/do-presv-bohorod.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва у терпінні", "rizni-potreby/u-terpinni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва за недужих", "rizni-potreby/za-neduzhykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва на всяку неміч",
				"rizni-potreby/na-vsyaku-nemich.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва при вмираючих",
				"rizni-potreby/pry-vmyrayuchykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва у годині смерти",
				"rizni-potreby/u-hodyni-smerti.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва християнської родини",
				"rizni-potreby/khryst-rodyny.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва батьків за дітей",
				"rizni-potreby/batkiv-za-ditey.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва батьків за дітей",
				"rizni-potreby/batkiv-za-ditey.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(0, "Молитва за чоловіка (дружину)",
				"rizni-potreby/za-cholovika-druzhynu.html",
				"МОЛИТВА http://molytva.at.ua/index/rodinni_molitvi/0-151");
		menu.html(0, "Молитва учня (учениці)", "rizni-potreby/uchnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва за перемогу над спокусами",
				"rizni-potreby/peremoha-nad-spokusamy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва про духа покори",
				"rizni-potreby/dukha-pokory.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва в день іменин",
				"rizni-potreby/v-den-imenyn.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва за того, хто хоче іти в путь",
				"rizni-potreby/ity-v-put.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва на благословення дому",
				"rizni-potreby/blahoslovennya-domu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитва на освячення всякої речі",
				"rizni-potreby/osvyach-rechi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(0, "Молитви за з’єднання всіх християн",
				"rizni-potreby/zyednannya-khrystyyan.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private void addPsaloms(MenuItemSubMenu sm) {
		for (int i = 1; i <= 150; i++) {
			String name = "Псалом " + i;
			if (i >= 10) {
				name += " (" + (i - 1) + ")";
			}
			sm.text(0, name, "psalmy/" + i + ".html", SRC_BIBLIA);
		}
	}

	public List<MenuItemBase> getTopMenuItems() {
		return topMenu;
	}
}
