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
		topMenu.add(addPisni());
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
	}

	private MenuItemSubMenu addPisni() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Пісні");

		MenuItemSubMenu proslavy = new MenuItemSubMenu("Молитва прослави");
		proslavy.addSubItem(new MenuItemPrayer(
				"Пісня подяки св. Амвросія, єпископа Медіолянського",
				"pisni/proslavy/podyaky-amvrosiya.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(proslavy);

		MenuItemSubMenu zaNarid = new MenuItemSubMenu("Молитви за нарід");
		zaNarid.addSubItem(new MenuItemPrayer("Боже великий, єдиний",
				"pisni/za-narid/bozhe-velykyj-jedynyj.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		zaNarid.addSubItem(new MenuItemPrayer("Боже, вислухай благання",
				"pisni/za-narid/bozhe-vyslukhaj-blahannya.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		zaNarid.addSubItem(new MenuItemPrayer("Боже великий, Творче всесвіту",
				"pisni/za-narid/bozhe-tvorche-vsesvitu.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(zaNarid);

		MenuItemSubMenu kolyady = new MenuItemSubMenu("Коляди");
		kolyady.addSubItem(new MenuItemPrayer("Бог предвічний",
				"pisni/kolyady/boh-predvichynyj.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("Бог ся рождає",
				"pisni/kolyady/boh-sya-rozhdaye.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("В Вифлеємі днесь Марія",
				"pisni/kolyady/v-vyflejemi-dnes-mariya.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("В Вифлеємі новина",
				"pisni/kolyady/v-vyfleyemi-novyna.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("Во Вифлеємі нині новина",
				"pisni/kolyady/vo-vyfleyemi-nyni-novyna.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("Возвеселімся всі разом нині",
				"pisni/kolyady/vozveselimsya-vsi-razom-nyni.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("Вселенная, веселися",
				"pisni/kolyady/vselennaya-veselysya.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("Дивная новина",
				"pisni/kolyady/dyvnaya-novyna.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("На небі зірка ясна засяла",
				"pisni/kolyady/na-nebi-zirka-yasna-zasyala.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("Небо і земля",
				"pisni/kolyady/nebo-i-zemlya.html").setIsHtml(false).setSource(
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("Нова радість стала",
				"pisni/kolyady/nova-radist-stala.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("Нова радість стала (1)",
				"pisni/kolyady/nova-radist-stala-1.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		kolyady.addSubItem(new MenuItemPrayer("По всьому світу",
				"pisni/kolyady/po-vsjomu-svitu.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(kolyady);

		MenuItemSubMenu velykoposni = new MenuItemSubMenu("Великопосні пісні");
		velykoposni.addSubItem(new MenuItemPrayer("Люди мої, люди",
				"pisni/velykoposni/lyudy-moyi-lyudy.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		velykoposni.addSubItem(new MenuItemPrayer("На Вавилонських ріках",
				"pisni/velykoposni/na-vavylonskykh-rikakh.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		velykoposni.addSubItem(new MenuItemPrayer("Під хрест Твій стаю",
				"pisni/velykoposni/pid-khrest-tvij-stayu.html")
				.setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		velykoposni.addSubItem(new MenuItemPrayer("Поклоняюся, мій Христе",
				"pisni/velykoposni/poklonyayusya-mij-khryste.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		velykoposni.addSubItem(new MenuItemPrayer("Страдальна Мати",
				"pisni/velykoposni/stradalna-maty.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		velykoposni.addSubItem(new MenuItemPrayer("Хрест на плечі накладають",
				"pisni/velykoposni/khrest-na-plechi-nakladayut.html")
				.setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(velykoposni);

		MenuItemSubMenu voskresni = new MenuItemSubMenu("Воскресні пісні");
		voskresni.addSubItem(new MenuItemPrayer("Сей день",
				"pisni/voskresni/sej-den.html").setIsHtml(false).setSource(
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		voskresni.addSubItem(new MenuItemPrayer("Согласно заспіваймо",
				"pisni/voskresni/sohlasno-zaspivajmo.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		voskresni.addSubItem(new MenuItemPrayer(
				"Христос воскрес! Радість з неба",
				"pisni/voskresni/khrystos-voskres.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(voskresni);

		MenuItemSubMenu doKhrysta = new MenuItemSubMenu("Пісні до Христа");
		doKhrysta.addSubItem(new MenuItemPrayer("Витай між нами",
				"pisni/khrysta/vytaj-mizh-namy.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		doKhrysta.addSubItem(new MenuItemPrayer("Вірую, Господи",
				"pisni/khrysta/viruyu-hospody.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		doKhrysta.addSubItem(new MenuItemPrayer("Ісуса в святих тайнах",
				"pisni/khrysta/isusa-v-svyatykh-tajnakh.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		doKhrysta.addSubItem(new MenuItemPrayer("О, Ісусе, Ти з лю6ови",
				"pisni/khrysta/o-isuse-ty-z-lyubovy.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		doKhrysta.addSubItem(new MenuItemPrayer("Пливи світами",
				"pisni/khrysta/plyvy-svitamy.html").setIsHtml(false).setSource(
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		doKhrysta.addSubItem(new MenuItemPrayer("Тіло Христове",
				"pisni/khrysta/tilo-khrystove.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(doKhrysta);

		MenuItemSubMenu svyatohoDukha = new MenuItemSubMenu(
				"Пісня до Святого Духа");
		svyatohoDukha.addSubItem(new MenuItemPrayer("Царю небесний",
				"pisni/dukha/tsaryu-nebesnyj.html").setIsHtml(false).setSource(
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(svyatohoDukha);

		MenuItemSubMenu bohorodytsi = new MenuItemSubMenu("Пісні до Богородиці");
		bohorodytsi.addSubItem(new MenuItemPrayer("Богородице Діво",
				"pisni/bohorodytsi/bohorodytse-divo.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Вихваляйте доли, гори",
				"pisni/bohorodytsi/vykhvalyajte-doly-hory.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Ген до неба",
				"pisni/bohorodytsi/hen-do-neba.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Достойно єсть",
				"pisni/bohorodytsi/dostojno-yest.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Коли втомлений",
				"pisni/bohorodytsi/koly-vtomlenyj.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Лугами піснь",
				"pisni/bohorodytsi/luhamy-pisen.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Маріє, Діво благословенна",
				"pisni/bohorodytsi/mariye-divo-blahoslovenna.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Маріє Діво, Царице мая",
				"pisni/bohorodytsi/mariye-divo-tsarytse-maya.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Нехай звенить",
				"pisni/bohorodytsi/nekhaj-dzvenyt.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("О, Богородице Діво, Маріє",
				"pisni/bohorodytsi/o-bohorodytse-divo-mariye.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("О, всепітая Мати",
				"pisni/bohorodytsi/o-vsepitaya-maty.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("О, Маріє, Мати Божа",
				"pisni/bohorodytsi/o-mariye-maty-bozha.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer(
				"О, Мати Божа, о, райський цвіте",
				"pisni/bohorodytsi/o-maty-bozha-o-rajskyj-tsvite.html")
				.setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("О, Пречиста",
				"pisni/bohorodytsi/o-prechysta.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Пренебесна, пречудесна",
				"pisni/bohorodytsi/prenebesna-prechudesna.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Пречистая Діво, Мати",
				"pisni/bohorodytsi/prechysta-divo-maty.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Просимо тя, Діво",
				"pisni/bohorodytsi/prosymo-tya-divo.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		bohorodytsi.addSubItem(new MenuItemPrayer("Там, де в небі",
				"pisni/bohorodytsi/tam-de-v-nebi.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(bohorodytsi);

		MenuItemSubMenu svyatykh = new MenuItemSubMenu("Пісні до святих");
		svyatykh.addSubItem(new MenuItemPrayer("Святий Великий Володимире",
				"pisni/svyatykh/svyatyj-velykyj-volodymyre.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		svyatykh.addSubItem(new MenuItemPrayer("О, свята Ольго",
				"pisni/svyatykh/o-svyata-olho.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		svyatykh.addSubItem(new MenuItemPrayer("Пісню слави",
				"pisni/svyatykh/pisnyu-slavy.html").setIsHtml(false).setSource(
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		svyatykh.addSubItem(new MenuItemPrayer("Владико, отче",
				"pisni/svyatykh/vladyko-otche.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		svyatykh.addSubItem(new MenuItemPrayer("О, хто, хто Миколая любить",
				"pisni/svyatykh/o-khto-khto-mykolaya-lyubyt.html").setIsHtml(
				false).setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(svyatykh);

		MenuItemSubMenu pokhoronni = new MenuItemSubMenu("Похоронні пісні");
		pokhoronni.addSubItem(new MenuItemPrayer("В темну могилу",
				"pisni/pokhoronni/v-temnu-mohylu.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		pokhoronni.addSubItem(new MenuItemPrayer("Кругом життя",
				"pisni/pokhoronni/kruhom-zhyttya.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		pokhoronni.addSubItem(new MenuItemPrayer("Прощай, душе",
				"pisni/pokhoronni/proschaj-dushe.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		pokhoronni.addSubItem(new MenuItemPrayer("Со святими упокой",
				"pisni/pokhoronni/so-svyatymy-upokoj.html").setIsHtml(false)
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(pokhoronni);

		return menu;

	}

	private MenuItemSubMenu addVervytsya() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Вервиця");
		menu.addSubItem(new MenuItemPrayer(
				"П’ятнадцять обітниць для тих, хто відмовлятиме святу Вервицю",
				"vervytsya/obitnytsi.html")
				.setFullName(
						"П’ятнадцять обітниць для тих, хто відмовлятиме святу Вервицю (дані бл. Аланові де Рупе)")
				.setSource(SRC_VERVYTSYA_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Як молитися на Вервиці",
				"vervytsya/yak-molytysya.html")
				.setSource(SRC_VERVYTSYA_MOLYTVA));
		menu.addSubItem(new MenuItemPrayer("Молитва на Вервиці",
				"vervytsya/molytva-na-vervytsi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		return menu;
	}

	private MenuItemSubMenu addLiturhiya() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Літургія");
		menu.addSubItem(new MenuItemPrayer(
				"Божественна Літургія св. отця нашого Йоана Золотоустого",
				"liturhiya/liturhiya-ugcc.html")
				.setSource("Молитовник для української родини. Релігійне видавництво \"Ріки води живої\". Львів-2011"));
		menu.addSubItem(new MenuItemPrayer(
				"Чин священної і Божественної Літургії святого Івана Золотоустого",
				"liturhiya/liturhiya.html")
				.setSource("Християнський портал Кіріос http://kyrios.org.ua/spirituality/bogosluzhinnja/1198-bozhestvenna-liturgija.html"));
		return menu;
	}

	private MenuItemSubMenu addMolytvyNaKozhenDen() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Молитви на кожен день");
		menu.addSubItem(new MenuItemPrayer("Молитва ранішнього намірення",
				"na-kozhen-den/ranizhnoho-namirennya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer(
				"Молитва св. Івана Золотоустого на кожну годину дня",
				"na-kozhen-den/iv-zlatoust-kozn-hod-dnya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Неділя",
				"na-kozhen-den/nedilya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Понеділок",
				"na-kozhen-den/ponedilok.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Вівторок",
				"na-kozhen-den/vivtorok.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Середа і п’ятниця",
				"na-kozhen-den/sereda-i-pyatnytsya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Четвер",
				"na-kozhen-den/chetver.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Субота",
				"na-kozhen-den/subota.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		return menu;
	}

	private MenuItemSubMenu addAkafisty() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Акафісти");
		menu.addSubItem(new MenuItemPrayer("Про Акафіст",
				"akafisty/pro-akafist.html").setSource(SRC_AKAFISTY_MOLYTVA));
		// Матері Божої
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Благовіщення Пречистої Діви Марії",
				"akafisty/blahovischennyu-bohorodytsi.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафiст до Пресвятої Богородицi",
				"akafisty/do-presv-bohorodytsi.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Матері Божої на честь Її Покрову",
				"akafisty/pokrovu-bohorodytsi.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст на честь введення в храм Пресвятої Богородиці",
				"akafisty/vvedennyu-v-khram-bohorodytsi.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст на честь Успення Богородиці",
				"akafisty/uspinnyu-bohorodytsi.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Матері Божої Неустанної Помочі",
				"akafisty/presv-bohorodytsi-neustannoyi-pomochi.html")
				.setSource(SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Пресвятої Богородиці на честь Її ікони «Невипивана Чаша»",
				"akafisty/presv-bohorodytsi-nevypyvna-chasha.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Пресвятої Богородиці на честь Її ікони «Всецариця»",
				"akafisty/presv-bohorodytsi-vsecarytsya.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст Пресвятій Богородиці на честь Її ікони «Годувальниця»",
				"akafisty/presv-bohorodytsi-hoduvalnytsya.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Пресвятої Богородиці на честь її ікони «Цілителька»",
				"akafisty/presv-bohorodytsi-tsilytelnytsya.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Зарваницької Матері Божої",
				"akafisty/materi-bozhoji-zarvanytskoji.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Зарваницької Матері Божої (нова версія)",
				"akafisty/materi-bozhoji-zarvanytskoji-nova.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Матері Божої Крилоської",
				"akafisty/materi-bozhoji-kryloskoji.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до Самбірської Богородиці",
				"akafisty/do-sambirskoji-bohorodytsi.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до Унівської Богородиці",
				"akafisty/do-univskoji-bohorodytsi.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Матері Божої на честь Її ікони «Прибавлення розуму»",
				"akafisty/do-bohorodytsi-na-chest-ikony-prybavlennya-rozumu.html")
				.setSource(SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		// Пресвятої Тройці
		menu.addSubItem(new MenuItemPrayer("Акафіст до Пресвятої Трійці",
				"akafisty/presvyatoji-trojtsi.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до Святого Духа",
				"akafisty/svyatoho-dukha.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до Ісуса Христа",
				"akafisty/isusa-khrysta.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до Божого Милосердя",
				"akafisty/bozhoho-myloserdya.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до Христових Страстей",
				"akafisty/khrystovykh-strastej.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Живоносного Гробу і Воскресіння Христа",
				"akafisty/zhyvonosnoho-hrobu-i-voskresinnya.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до Воскресіння Христового",
				"akafisty/voskresinnya-khrystovoho.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Причастя Святих Христових Таїн",
				"akafisty/prychastya-tain.html")
				.setSource(SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст подяки Ісусу Христу «Слава Богові за все»",
				"akafisty/podyaky-isusu.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст «Абетка покаяння»",
				"akafisty/abetka-pokayannya.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст - подяка Пресвятій Трійці",
				"akafisty/podyaka-trijtsi.html")
				.setSource(SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		// Святі і мученики
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Собору дванадцяти Апостолів Христових",
				"akafisty/do-soboru-apostoliv.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до священомученика Йосафата",
				"akafisty/do-josaphata.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до священомученика Йосафата (другий варіант)",
				"akafisty/do-josaphata-2.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до святого київського Аскольда",
				"akafisty/kyivskoho-askolda.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до святителя Миколая",
				"akafisty/mykolaya.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до блаженного Омеляна Ковча",
				"akafisty/omelyana-kovcha.html")
				.setSource(SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до святого отця Піо",
				"akafisty/otsya-pio.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до святого Романа Солодкоспівця",
				"akafisty/romana-solodkospivtsya.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до святого Івана Предтечі",
				"akafisty/ivana-predtechi.html")
				.setSource(SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до святого Василія Великого",
				"akafisty/vasyliya-velykoho.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до святого Агапіта",
				"akafisty/ahapita.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до святих Апостолів Петра і Павла",
				"akafisty/petra-i-pavla.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до святого Пантелеймона Цілителя",
				"akafisty/panteleymona-tsilytelya.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до святої Терези від Дитятка Ісус",
				"akafisty/terezy-vid-isusa.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до святої Філомени",
				"akafisty/filomeny.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до святої Варвари",
				"akafisty/varvary.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст до святої Параскеви",
				"akafisty/paraskevy.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст Дрогобицьким священомученикам Якиму, Віталію та Северіяну",
				"akafisty/drohobytskomu-yakymu.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		// Інші
		menu.addSubItem(new MenuItemPrayer("Акафіст до Архистратига Михаїла",
				"akafisty/mykhajila.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст до Архистратига Михаїла (другий варіант)",
				"akafisty/mykhajila-2.html").setSource(SRC_AKAFISTY_MOLYTVA)
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст покаянний жінок, які вбили немовлят у своїй утробі",
				"akafisty/zhinok-vbyly-nemovlyat.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer(
				"Акафіст за упокій померлих (багатьох)",
				"akafisty/dushi-pomerlykh.html")
				.setSource(SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Акафіст за померлого (одного)",
				"akafisty/pomerloho-odnoho.html").setSource(
				SRC_AKAFISTY_MOLYTVA).setIsHtml(false));
		return menu;
	}

	private MenuItemSubMenu addKatekhPravdy() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Катехизмові правди");
		menu.addSubItem(new MenuItemPrayer("Заповіді",
				"kateh-pravdy/zapovidi.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		menu.addSubItem(new MenuItemPrayer("Чесноти",
				"kateh-pravdy/chesnoty.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		menu.addSubItem(new MenuItemPrayer("Пости та загальниці",
				"kateh-pravdy/posty-ta-zahalnytsi.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		menu.addSubItem(new MenuItemPrayer("Гріхи", "kateh-pravdy/hrikhy.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		menu.addSubItem(new MenuItemPrayer("Християнський привіт",
				"kateh-pravdy/khryst-pryvit.html")
				.setSource(SRC_DODATOK_KATEKHYZMU_2012));
		return menu;
	}

	private MenuItemSubMenu addProSpovidIPrychastya() {
		MenuItemSubMenu menu = new MenuItemSubMenu("Сповідь та причастя");
		menu.addSubItem(new MenuItemPrayer("Про щастя людини",
				"pro-spovid/pro-schastya.html").setSource(SRC_PRO_SPOVID));
		menu.addSubItem(new MenuItemPrayer("Церква як Божа установа",
				"pro-spovid/cerkva-yak-bozha-ustanova.html")
				.setSource(SRC_PRO_SPOVID));
		menu.addSubItem(new MenuItemPrayer("Сором у визнанні гріхів",
				"pro-spovid/sorom-u-vyznanni-hrikhiv.html")
				.setSource(SRC_PRO_SPOVID));
		menu.addSubItem(new MenuItemPrayer("Чи всі сповіді є добрими?",
				"pro-spovid/chy-vsi-spovidi-dobri.html")
				.setSource(SRC_PRO_SPOVID));
		menu.addSubItem(new MenuItemPrayer(
				"Що потрібно знати, щоб сповідатися?",
				"pro-spovid/scho-potribno-znaty.html")
				.setSource(SRC_PRO_SPOVID));
		menu.addSubItem(new MenuItemPrayer("Святе Причастя",
				"pro-spovid/svyate-prychastya.html").setSource(SRC_PRO_SPOVID));
		menu.addSubItem(new MenuItemPrayer("Приготування до сповіді",
				"pro-spovid/prohotuvannya.html").setSource(SRC_PRO_SPOVID));
		menu.addSubItem(new MenuItemPrayer("Деякі додаткові зауваги",
				"pro-spovid/dodatkovi-zauvahy.html").setSource(SRC_PRO_SPOVID));
		menu.addSubItem(new MenuItemPrayer("Сповідь", "pro-spovid/spovid.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитви в часі сповіді",
				"pro-spovid/molytvy-v-chasi-spovidi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитви перед святим причастям",
				"pro-spovid/molytvy-pered-prychastyam.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитви по святім причастю",
				"pro-spovid/molytvy-pislya-prychastya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
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

		menu.addSubItem(new MenuItemPrayer("Молитва подяки",
				"rizni-potreby/podyaky.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва на всяке прошення",
				"rizni-potreby/vsyake-proshennya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва до Пресвятої Богородиці",
				"rizni-potreby/do-presv-bohorod.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва у терпінні",
				"rizni-potreby/u-terpinni.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва за недужих",
				"rizni-potreby/za-neduzhykh.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва на всяку неміч",
				"rizni-potreby/na-vsyaku-nemich.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва при вмираючих",
				"rizni-potreby/pry-vmyrayuchykh.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва у годині смерти",
				"rizni-potreby/u-hodyni-smerti.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва християнської родини",
				"rizni-potreby/khryst-rodyny.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва батьків за дітей",
				"rizni-potreby/batkiv-za-ditey.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва батьків за дітей",
				"rizni-potreby/batkiv-za-ditey.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва за чоловіка (дружину)",
				"rizni-potreby/za-cholovika-druzhynu.html").setSource(
				"МОЛИТВА http://molytva.at.ua/index/rodinni_molitvi/0-151")
				.setIsHtml(false));
		menu.addSubItem(new MenuItemPrayer("Молитва учня (учениці)",
				"rizni-potreby/uchnya.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва за перемогу над спокусами",
				"rizni-potreby/peremoha-nad-spokusamy.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва про духа покори",
				"rizni-potreby/dukha-pokory.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва в день іменин",
				"rizni-potreby/v-den-imenyn.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer(
				"Молитва за того, хто хоче іти в путь",
				"rizni-potreby/ity-v-put.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва на благословення дому",
				"rizni-potreby/blahoslovennya-domu.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer("Молитва на освячення всякої речі",
				"rizni-potreby/osvyach-rechi.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		menu.addSubItem(new MenuItemPrayer(
				"Молитви за з’єднання всіх християн",
				"rizni-potreby/zyednannya-khrystyyan.html")
				.setSource(SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA));
		return menu;
	}

	private void addPsaloms(MenuItemSubMenu sm) {
		for (int i = 1; i <= 150; i++) {
			String name = "Псалом " + i;
			if (i >= 10) {
				name += " (" + (i - 1) + ")";
			}
			sm.addSubItem(new MenuItemPrayer(name, "psalmy/" + i + ".html")
					.setSource(SRC_BIBLIA).setIsHtml(false));
		}
	}

	public List<MenuItemBase> getTopMenuItems() {
		return topMenu;
	}
}
