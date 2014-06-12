package com.tshevchuk.prayer.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.text.TextUtils;
import android.util.SparseArray;

import com.tshevchuk.prayer.Utils;

public class Catalog {
	private static final String SRC_BIBLIA = "Біблія, переклад Івана Хоменка";
	private static final String SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA = "Молитовник «Прийдіте поклонімся»";
	private static final String SRC_DODATOK_KATEKHYZMU_2012 = "Додаток до Катехизму УГКЦ «Христос – наша Пасха» 2012 року";
	private static final String SRC_PRO_SPOVID = "Про Сповідь. о. Порфирій В. Шумило, ЧСВВ, бр. Пімен І. Коневич, ЧСВВ http://osbm-buchach.org.ua/knygy/pro-spovid.html";
	private static final String SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH = "Уроки Біблії http://bible-lessons.in.ua/molutva/dljavagitnux.html";
	private static final String SRC_AKAFISTY_MOLYTVA = "МОЛИТВА - Акафіст http://molytva.at.ua/index/akafist/0-293";
	private static final String SRC_VERVYTSYA_MOLYTVA = "МОЛИТВА - Вервиці http://molytva.at.ua/index/vervici/0-46";

	private static final int NEXT_ID_TO_ADD = 402;

	private List<MenuItemBase> topMenu = new ArrayList<MenuItemBase>();
	private SparseArray<MenuItemBase> menuItemsByIds = new SparseArray<MenuItemBase>();

	{
		MenuItemSubMenu menu = new MenuItemSubMenu(0, "top");
		menu.addSubItem(new MenuItemOftenUsed(400));
		menu.html(1, "Щоденні молитви", "molytvy-schodenni.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(2, "Ранішні молитви", "molytvy-ranishni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(3, "Вечірні молитви", "molytvy-vechirni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		menu.addSubItem(addMolytvyNaKozhenDen());

		menu.html(4, "Молитви при трапезі", "molytvy-pry-trapezi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		menu.addSubItem(addMolytvyRizniPotreby());

		menu.addSubItem(new MenuItemCalendar(5));

		MenuItemSubMenu psalmy = new MenuItemSubMenu(6, "Псалми");
		addPsaloms(psalmy);
		menu.addSubItem(psalmy);

		menu.addSubItem(addAkafisty());
		menu.addSubItem(addVervytsya());

		menu.addSubItem(addLiturhiya());
		menu.addSubItem(addKatekhPravdy());
		menu.addSubItem(addProSpovidIPrychastya());

		menu.addSubItem(addPisni());

		topMenu = menu.getSubItems();

		verifyUniqueId();
	}

	private MenuItemSubMenu addPisni() {
		MenuItemSubMenu menu = new MenuItemSubMenu(7, "Пісні");

		MenuItemSubMenu sm = menu.subMenu(8, "Молитва прослави");
		sm.text(9, "Пісня подяки св. Амвросія, єпископа Медіолянського",
				"pisni/proslavy/podyaky-amvrosiya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(10, "Молитви за нарід");
		sm.text(11, "Боже великий, єдиний",
				"pisni/za-narid/bozhe-velykyj-jedynyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(12, "Боже, вислухай благання",
				"pisni/za-narid/bozhe-vyslukhaj-blahannya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(13, "Боже великий, Творче всесвіту",
				"pisni/za-narid/bozhe-tvorche-vsesvitu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(14, "Коляди");
		sm.text(15, "Бог предвічний", "pisni/kolyady/boh-predvichynyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(16, "Бог ся рождає", "pisni/kolyady/boh-sya-rozhdaye.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(17, "В Вифлеємі днесь Марія",
				"pisni/kolyady/v-vyflejemi-dnes-mariya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(18, "В Вифлеємі новина",
				"pisni/kolyady/v-vyfleyemi-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(19, "Во Вифлеємі нині новина",
				"pisni/kolyady/vo-vyfleyemi-nyni-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(20, "Возвеселімся всі разом нині",
				"pisni/kolyady/vozveselimsya-vsi-razom-nyni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(21, "Вселенная, веселися",
				"pisni/kolyady/vselennaya-veselysya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(22, "Дивная новина", "pisni/kolyady/dyvnaya-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(23, "На небі зірка ясна засяла",
				"pisni/kolyady/na-nebi-zirka-yasna-zasyala.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(24, "Небо і земля", "pisni/kolyady/nebo-i-zemlya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(25, "Нова радість стала",
				"pisni/kolyady/nova-radist-stala.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(26, "Нова радість стала (1)",
				"pisni/kolyady/nova-radist-stala-1.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(27, "По всьому світу", "pisni/kolyady/po-vsjomu-svitu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(28, "Великопосні пісні");
		sm.text(29, "Люди мої, люди",
				"pisni/velykoposni/lyudy-moyi-lyudy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(30, "На Вавилонських ріках",
				"pisni/velykoposni/na-vavylonskykh-rikakh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(31, "Під хрест Твій стаю",
				"pisni/velykoposni/pid-khrest-tvij-stayu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(32, "Поклоняюся, мій Христе",
				"pisni/velykoposni/poklonyayusya-mij-khryste.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(33, "Страдальна Мати", "pisni/velykoposni/stradalna-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(34, "Хрест на плечі накладають",
				"pisni/velykoposni/khrest-na-plechi-nakladayut.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(35, "Воскресні пісні");
		sm.text(36, "Сей день", "pisni/voskresni/sej-den.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(37, "Согласно заспіваймо",
				"pisni/voskresni/sohlasno-zaspivajmo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(38, "Христос воскрес! Радість з неба",
				"pisni/voskresni/khrystos-voskres.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(39, "Пісні до Христа");
		sm.text(40, "Витай між нами", "pisni/khrysta/vytaj-mizh-namy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(41, "Вірую, Господи", "pisni/khrysta/viruyu-hospody.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(42, "Ісуса в святих тайнах",
				"pisni/khrysta/isusa-v-svyatykh-tajnakh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(43, "О, Ісусе, Ти з лю6ови",
				"pisni/khrysta/o-isuse-ty-z-lyubovy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(44, "Пливи світами", "pisni/khrysta/plyvy-svitamy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(45, "Тіло Христове", "pisni/khrysta/tilo-khrystove.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(46, "Пісня до Святого Духа");
		sm.text(47, "Царю небесний", "pisni/dukha/tsaryu-nebesnyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(48, "Пісні до Богородиці");
		sm.text(49, "Богородице Діво",
				"pisni/bohorodytsi/bohorodytse-divo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(50, "Вихваляйте доли, гори",
				"pisni/bohorodytsi/vykhvalyajte-doly-hory.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(51, "Ген до неба", "pisni/bohorodytsi/hen-do-neba.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(52, "Достойно єсть", "pisni/bohorodytsi/dostojno-yest.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(53, "Коли втомлений", "pisni/bohorodytsi/koly-vtomlenyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(54, "Лугами піснь", "pisni/bohorodytsi/luhamy-pisen.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(55, "Маріє, Діво благословенна",
				"pisni/bohorodytsi/mariye-divo-blahoslovenna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(56, "Маріє Діво, Царице мая",
				"pisni/bohorodytsi/mariye-divo-tsarytse-maya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(57, "Нехай звенить", "pisni/bohorodytsi/nekhaj-dzvenyt.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(58, "О, Богородице Діво, Маріє",
				"pisni/bohorodytsi/o-bohorodytse-divo-mariye.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(59, "О, всепітая Мати",
				"pisni/bohorodytsi/o-vsepitaya-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(60, "О, Маріє, Мати Божа",
				"pisni/bohorodytsi/o-mariye-maty-bozha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(61, "О, Мати Божа, о, райський цвіте",
				"pisni/bohorodytsi/o-maty-bozha-o-rajskyj-tsvite.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(62, "О, Пречиста", "pisni/bohorodytsi/o-prechysta.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(63, "Пренебесна, пречудесна",
				"pisni/bohorodytsi/prenebesna-prechudesna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(64, "Пречистая Діво, Мати",
				"pisni/bohorodytsi/prechysta-divo-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(65, "Просимо тя, Діво",
				"pisni/bohorodytsi/prosymo-tya-divo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(66, "Там, де в небі", "pisni/bohorodytsi/tam-de-v-nebi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(67, "Пісні до святих");
		sm.text(68, "Святий Великий Володимире",
				"pisni/svyatykh/svyatyj-velykyj-volodymyre.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(69, "О, свята Ольго", "pisni/svyatykh/o-svyata-olho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(70, "Пісню слави", "pisni/svyatykh/pisnyu-slavy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(71, "Владико, отче", "pisni/svyatykh/vladyko-otche.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(72, "О, хто, хто Миколая любить",
				"pisni/svyatykh/o-khto-khto-mykolaya-lyubyt.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(73, "Похоронні пісні");
		sm.text(74, "В темну могилу", "pisni/pokhoronni/v-temnu-mohylu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(75, "Кругом життя", "pisni/pokhoronni/kruhom-zhyttya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(76, "Прощай, душе", "pisni/pokhoronni/proschaj-dushe.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.text(77, "Со святими упокой",
				"pisni/pokhoronni/so-svyatymy-upokoj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		return menu;

	}

	private MenuItemSubMenu addVervytsya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(78, "Вервиця");
		menu.text(79,
				"П’ятнадцять обітниць для тих, хто відмовлятиме святу Вервицю",
				"vervytsya/obitnytsi.html", SRC_VERVYTSYA_MOLYTVA)
				.setFullName(
						"П’ятнадцять обітниць для тих, хто відмовлятиме святу Вервицю (дані бл. Аланові де Рупе)");
		menu.html(80, "Як молитися на Вервиці", "vervytsya/yak-molytysya.html",
				SRC_VERVYTSYA_MOLYTVA);
		menu.html(81, "Молитва на Вервиці",
				"vervytsya/molytva-na-vervytsi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addLiturhiya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(82, "Літургія");
		menu.html(
				83,
				"Божественна Літургія св. отця нашого Йоана Золотоустого",
				"liturhiya/liturhiya-ugcc.html",
				"Молитовник для української родини. Релігійне видавництво \"Ріки води живої\". Львів-2011");
		menu.html(
				84,
				"Чин священної і Божественної Літургії святого Івана Золотоустого",
				"liturhiya/liturhiya.html",
				"Християнський портал Кіріос http://kyrios.org.ua/spirituality/bogosluzhinnja/1198-bozhestvenna-liturgija.html");
		return menu;
	}

	private MenuItemSubMenu addMolytvyNaKozhenDen() {
		MenuItemSubMenu menu = new MenuItemSubMenu(85, "Молитви на кожен день");
		menu.html(86, "Молитва ранішнього намірення",
				"na-kozhen-den/ranizhnoho-namirennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(87, "Молитва св. Івана Золотоустого на кожну годину дня",
				"na-kozhen-den/iv-zlatoust-kozn-hod-dnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(88, "Неділя", "na-kozhen-den/nedilya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(89, "Понеділок", "na-kozhen-den/ponedilok.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(90, "Вівторок", "na-kozhen-den/vivtorok.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(91, "Середа і п’ятниця",
				"na-kozhen-den/sereda-i-pyatnytsya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(92, "Четвер", "na-kozhen-den/chetver.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(93, "Субота", "na-kozhen-den/subota.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addAkafisty() {
		MenuItemSubMenu menu = new MenuItemSubMenu(94, "Акафісти");
		menu.html(95, "Про Акафіст", "akafisty/pro-akafist.html",
				SRC_AKAFISTY_MOLYTVA);
		// Матері Божої
		menu.text(96, "Акафіст до Благовіщення Пречистої Діви Марії",
				"akafisty/blahovischennyu-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(97, "Акафiст до Пресвятої Богородицi",
				"akafisty/do-presv-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(98, "Акафіст до Матері Божої на честь Її Покрову",
				"akafisty/pokrovu-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(99, "Акафіст на честь введення в храм Пресвятої Богородиці",
				"akafisty/vvedennyu-v-khram-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(100, "Акафіст на честь Успення Богородиці",
				"akafisty/uspinnyu-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(101, "Акафіст до Матері Божої Неустанної Помочі",
				"akafisty/presv-bohorodytsi-neustannoyi-pomochi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				102,
				"Акафіст до Пресвятої Богородиці на честь Її ікони «Невипивана Чаша»",
				"akafisty/presv-bohorodytsi-nevypyvna-chasha.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				103,
				"Акафіст до Пресвятої Богородиці на честь Її ікони «Всецариця»",
				"akafisty/presv-bohorodytsi-vsecarytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				104,
				"Акафіст Пресвятій Богородиці на честь Її ікони «Годувальниця»",
				"akafisty/presv-bohorodytsi-hoduvalnytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				105,
				"Акафіст до Пресвятої Богородиці на честь її ікони «Цілителька»",
				"akafisty/presv-bohorodytsi-tsilytelnytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(106, "Акафіст до Зарваницької Матері Божої",
				"akafisty/materi-bozhoji-zarvanytskoji.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(107, "Акафіст до Зарваницької Матері Божої (нова версія)",
				"akafisty/materi-bozhoji-zarvanytskoji-nova.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(108, "Акафіст до Матері Божої Крилоської",
				"akafisty/materi-bozhoji-kryloskoji.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(109, "Акафіст до Самбірської Богородиці",
				"akafisty/do-sambirskoji-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(110, "Акафіст до Унівської Богородиці",
				"akafisty/do-univskoji-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(
				111,
				"Акафіст до Матері Божої на честь Її ікони «Прибавлення розуму»",
				"akafisty/do-bohorodytsi-na-chest-ikony-prybavlennya-rozumu.html",
				SRC_AKAFISTY_MOLYTVA);
		// Пресвятої Тройці
		menu.text(112, "Акафіст до Пресвятої Трійці",
				"akafisty/presvyatoji-trojtsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(113, "Акафіст до Святого Духа",
				"akafisty/svyatoho-dukha.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(114, "Акафіст до Ісуса Христа",
				"akafisty/isusa-khrysta.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(115, "Акафіст до Божого Милосердя",
				"akafisty/bozhoho-myloserdya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(116, "Акафіст до Христових Страстей",
				"akafisty/khrystovykh-strastej.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(117, "Акафіст до Живоносного Гробу і Воскресіння Христа",
				"akafisty/zhyvonosnoho-hrobu-i-voskresinnya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(118, "Акафіст до Воскресіння Христового",
				"akafisty/voskresinnya-khrystovoho.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(119, "Акафіст до Причастя Святих Христових Таїн",
				"akafisty/prychastya-tain.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(120, "Акафіст подяки Ісусу Христу «Слава Богові за все»",
				"akafisty/podyaky-isusu.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(121, "Акафіст «Абетка покаяння»",
				"akafisty/abetka-pokayannya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(122, "Акафіст - подяка Пресвятій Трійці",
				"akafisty/podyaka-trijtsi.html", SRC_AKAFISTY_MOLYTVA);
		// Святі і мученики
		menu.text(123, "Акафіст до Собору дванадцяти Апостолів Христових",
				"akafisty/do-soboru-apostoliv.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(124, "Акафіст до священомученика Йосафата",
				"akafisty/do-josaphata.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(125, "Акафіст до священомученика Йосафата (другий варіант)",
				"akafisty/do-josaphata-2.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(126, "Акафіст до святого київського Аскольда",
				"akafisty/kyivskoho-askolda.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(127, "Акафіст до святителя Миколая",
				"akafisty/mykolaya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(128, "Акафіст до блаженного Омеляна Ковча",
				"akafisty/omelyana-kovcha.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(129, "Акафіст до святого отця Піо",
				"akafisty/otsya-pio.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(130, "Акафіст до святого Романа Солодкоспівця",
				"akafisty/romana-solodkospivtsya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(131, "Акафіст до святого Івана Предтечі",
				"akafisty/ivana-predtechi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(132, "Акафіст до святого Василія Великого",
				"akafisty/vasyliya-velykoho.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(133, "Акафіст до святого Агапіта", "akafisty/ahapita.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(134, "Акафіст до святих Апостолів Петра і Павла",
				"akafisty/petra-i-pavla.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(135, "Акафіст до святого Пантелеймона Цілителя",
				"akafisty/panteleymona-tsilytelya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(136, "Акафіст до святої Терези від Дитятка Ісус",
				"akafisty/terezy-vid-isusa.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(137, "Акафіст до святої Філомени", "akafisty/filomeny.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(138, "Акафіст до святої Варвари", "akafisty/varvary.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(139, "Акафіст до святої Параскеви",
				"akafisty/paraskevy.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(
				140,
				"Акафіст Дрогобицьким священомученикам Якиму, Віталію та Северіяну",
				"akafisty/drohobytskomu-yakymu.html", SRC_AKAFISTY_MOLYTVA);
		// Інші
		menu.text(141, "Акафіст до Архистратига Михаїла",
				"akafisty/mykhajila.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(142, "Акафіст до Архистратига Михаїла (другий варіант)",
				"akafisty/mykhajila-2.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(143,
				"Акафіст покаянний жінок, які вбили немовлят у своїй утробі",
				"akafisty/zhinok-vbyly-nemovlyat.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(144, "Акафіст за упокій померлих (багатьох)",
				"akafisty/dushi-pomerlykh.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(145, "Акафіст за померлого (одного)",
				"akafisty/pomerloho-odnoho.html", SRC_AKAFISTY_MOLYTVA);
		return menu;
	}

	private MenuItemSubMenu addKatekhPravdy() {
		MenuItemSubMenu menu = new MenuItemSubMenu(147, "Катехизмові правди");
		menu.html(146, "Заповіді", "kateh-pravdy/zapovidi.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(148, "Чесноти", "kateh-pravdy/chesnoty.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(149, "Пости та загальниці",
				"kateh-pravdy/posty-ta-zahalnytsi.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(150, "Гріхи", "kateh-pravdy/hrikhy.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(151, "Християнський привіт",
				"kateh-pravdy/khryst-pryvit.html", SRC_DODATOK_KATEKHYZMU_2012);
		return menu;
	}

	private MenuItemSubMenu addProSpovidIPrychastya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(401, "Сповідь та причастя");
		menu.html(152, "Про щастя людини", "pro-spovid/pro-schastya.html",
				SRC_PRO_SPOVID);
		menu.html(153, "Церква як Божа установа",
				"pro-spovid/cerkva-yak-bozha-ustanova.html", SRC_PRO_SPOVID);
		menu.html(154, "Сором у визнанні гріхів",
				"pro-spovid/sorom-u-vyznanni-hrikhiv.html", SRC_PRO_SPOVID);
		menu.html(155, "Чи всі сповіді є добрими?",
				"pro-spovid/chy-vsi-spovidi-dobri.html", SRC_PRO_SPOVID);
		menu.html(156, "Що потрібно знати, щоб сповідатися?",
				"pro-spovid/scho-potribno-znaty.html", SRC_PRO_SPOVID);
		menu.html(157, "Святе Причастя", "pro-spovid/svyate-prychastya.html",
				SRC_PRO_SPOVID);
		menu.html(158, "Приготування до сповіді",
				"pro-spovid/prohotuvannya.html", SRC_PRO_SPOVID);
		menu.html(159, "Деякі додаткові зауваги",
				"pro-spovid/dodatkovi-zauvahy.html", SRC_PRO_SPOVID);
		menu.html(160, "Сповідь", "pro-spovid/spovid.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(161, "Молитви в часі сповіді",
				"pro-spovid/molytvy-v-chasi-spovidi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(162, "Молитви перед святим причастям",
				"pro-spovid/molytvy-pered-prychastyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(163, "Молитви по святім причастю",
				"pro-spovid/molytvy-pislya-prychastya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addMolytvyRizniPotreby() {
		MenuItemSubMenu menu = new MenuItemSubMenu(164,
				"Молитви на різні потреби");

		MenuItemSubMenu sm = menu.subMenu(165, "Молитви для вагітних");
		sm.text(166, "Молитва вагітної жінки",
				"rizni-potreby/dlya-vahitnykh/vahitnoi-zhinky.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.text(167,
				"Молитва – Благословення вагітних жінок",
				"rizni-potreby/dlya-vahitnykh/blahoslovennya-vahitnykh-zhinok.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(168, "Молитва матері, яка очікує дитину",
				"rizni-potreby/dlya-vahitnykh/materi-ochikuye-dytynu.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(169,
				"Молитва до Богородиці для допомоги жінкам у часі пологів",
				"rizni-potreby/dlya-vahitnykh/do-bohorodytsi-dopomohy-pry-polohakh.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(170, "Молитва вагітних жінок за успішні пологи",
				"rizni-potreby/dlya-vahitnykh/za-uspishni-polohy.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(171, "Молитва при важких пологах",
				"rizni-potreby/dlya-vahitnykh/pry-vazhkykh-polohakh.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.text(172,
				"Молитва – Благословення жінки після пологів",
				"rizni-potreby/dlya-vahitnykh/blahoslovennya-pislya-polohiv.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(173,
				"Молитва за ласку годування грудьми",
				"rizni-potreby/dlya-vahitnykh/za-lasku-hoduvannya-hruddyu.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(174,
				"Молитва – Благословення вагітних жінок після Літургії",
				"rizni-potreby/dlya-vahitnykh/blahoslovennya-pislya-liturhiyi.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);

		menu.html(175, "Молитва подяки", "rizni-potreby/podyaky.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(176, "Молитва на всяке прошення",
				"rizni-potreby/vsyake-proshennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(177, "Молитва до Пресвятої Богородиці",
				"rizni-potreby/do-presv-bohorod.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(178, "Молитва у терпінні", "rizni-potreby/u-terpinni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(179, "Молитва за недужих", "rizni-potreby/za-neduzhykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(180, "Молитва на всяку неміч",
				"rizni-potreby/na-vsyaku-nemich.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(181, "Молитва при вмираючих",
				"rizni-potreby/pry-vmyrayuchykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(182, "Молитва у годині смерти",
				"rizni-potreby/u-hodyni-smerti.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(183, "Молитва християнської родини",
				"rizni-potreby/khryst-rodyny.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(184, "Молитва батьків за дітей",
				"rizni-potreby/batkiv-za-ditey.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(185, "Молитва батьків за дітей",
				"rizni-potreby/batkiv-za-ditey.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(186, "Молитва за чоловіка (дружину)",
				"rizni-potreby/za-cholovika-druzhynu.html",
				"МОЛИТВА http://molytva.at.ua/index/rodinni_molitvi/0-151");
		menu.html(187, "Молитва учня (учениці)", "rizni-potreby/uchnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(188, "Молитва за перемогу над спокусами",
				"rizni-potreby/peremoha-nad-spokusamy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(189, "Молитва про духа покори",
				"rizni-potreby/dukha-pokory.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(190, "Молитва в день іменин",
				"rizni-potreby/v-den-imenyn.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(191, "Молитва за того, хто хоче іти в путь",
				"rizni-potreby/ity-v-put.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(192, "Молитва на благословення дому",
				"rizni-potreby/blahoslovennya-domu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(193, "Молитва на освячення всякої речі",
				"rizni-potreby/osvyach-rechi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(194, "Молитви за з’єднання всіх християн",
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
			sm.text(195 + i, name, "psalmy/" + i + ".html", SRC_BIBLIA);
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
							+ mi.getId() + 1);
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
}
