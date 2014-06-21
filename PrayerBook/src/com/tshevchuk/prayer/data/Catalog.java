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
	private static final String SRC_MOLEBNI_KYRIOS = "Християнський портал Кіріос - Молебні http://kyrios.org.ua/spirituality/molebni.html";
	private static final String SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA = "МОЛИТОВНИК військовослужбовця «Молись і служи!». Департамент Патріаршої курії Української Греко-Католицької Церкви у справах душпастирства силових структур України. Київ. 2013 рік";

	public static final int ID_SCHODENNI_MOLYTVY = 1;
	public static final int ID_CALENDAR = 5;

	private static final int NEXT_ID_TO_ADD = 468;

	private List<MenuItemBase> topMenu = new ArrayList<MenuItemBase>();
	private SparseArray<MenuItemBase> menuItemsByIds = new SparseArray<MenuItemBase>();

	{
		MenuItemSubMenu menu = new MenuItemSubMenu(0, "top");
		menu.addSubItem(new MenuItemOftenUsed(400));
		menu.html(ID_SCHODENNI_MOLYTVY, "Щоденні молитви",
				"molytvy-schodenni.html", SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(2, "Ранішні молитви", "molytvy-ranishni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(3, "Вечірні молитви", "molytvy-vechirni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		menu.addSubItem(addMolytvyNaKozhenDen());

		menu.html(4, "Молитви при трапезі", "molytvy-pry-trapezi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		menu.addSubItem(addMolytvyRizniPotreby());

		menu.addSubItem(new MenuItemCalendar(ID_CALENDAR));

		MenuItemSubMenu psalmy = new MenuItemSubMenu(6, "Псалми");
		addPsaloms(psalmy);
		menu.addSubItem(psalmy);

		menu.addSubItem(addAkafisty());
		menu.addSubItem(addVervytsya());

		menu.addSubItem(addBohosluzhinnya());
		menu.addSubItem(addKatekhPravdy());
		menu.addSubItem(addProSpovidIPrychastya());

		menu.addSubItem(addPisni());

		topMenu = menu.getSubItems();

		verifyUniqueId();
	}

	private MenuItemSubMenu addMolytvyVijskovykhFormuvan() {
		MenuItemSubMenu menu = new MenuItemSubMenu(403,
				"Молитви представників військових формувань");

		menu.text(404, "Молитва військовослужбовця",
				"rizni-potreby/vijskovykh-formuvan/vijskovosluzhbovtsya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(405, "Молитва офіцера",
				"rizni-potreby/vijskovykh-formuvan/ofitsera.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(406, "Молитва військовослужбовця повітряних сил",
				"rizni-potreby/vijskovykh-formuvan/povitryanykh-syl.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(407, "Молитва військовослужбовця військово-морських сил",
				"rizni-potreby/vijskovykh-formuvan/vijskovo-morskykh-syl.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(408, "Молитва військовослужбовця сухопутних військ",
				"rizni-potreby/vijskovykh-formuvan/sukhoputnykh.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(409, "Молитва за щасливу подорож",
				"rizni-potreby/vijskovykh-formuvan/schaslyvu-podorozh.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(410,
				"Молитва за капітанів кораблів і тих, що подорожують на водах",
				"rizni-potreby/vijskovykh-formuvan/korabli-vody.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(411, "Молитва військового водія",
				"rizni-potreby/vijskovykh-formuvan/vodiya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(412, "Молитва артилериста",
				"rizni-potreby/vijskovykh-formuvan/artelerysta.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(413, "Молитва десантника",
				"rizni-potreby/vijskovykh-formuvan/desantnyka.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(414, "Молитва зв’язківця",
				"rizni-potreby/vijskovykh-formuvan/zvazkivtsya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(415, "Молитва танкіста",
				"rizni-potreby/vijskovykh-formuvan/tankista.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(416, "Молитва курсанта",
				"rizni-potreby/vijskovykh-formuvan/kursanta.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(417, "Молитва рятівника",
				"rizni-potreby/vijskovykh-formuvan/ryativnyka.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(418, "Молитва прикордонника",
				"rizni-potreby/vijskovykh-formuvan/prykordonnyka.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(
				419,
				"Молитва працівника пенітенціарної служби",
				"rizni-potreby/vijskovykh-formuvan/penitentsiarnoyi-sluzhby.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(420, "Молитва правоохоронця",
				"rizni-potreby/vijskovykh-formuvan/pravookhorontsya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(421, "Молитва за мир",
				"rizni-potreby/vijskovykh-formuvan/za-myr.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(422, "Молитва під час бойових дій",
				"rizni-potreby/vijskovykh-formuvan/bojovykh-dij.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(423, "Молитва перед боєм",
				"rizni-potreby/vijskovykh-formuvan/pered-boyem.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(424, "Коротка молитва під час бою",
				"rizni-potreby/vijskovykh-formuvan/pid-chas-boyu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(425, "Молитва полоненого",
				"rizni-potreby/vijskovykh-formuvan/polonenoho.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(426, "Молитва пораненого чи хворого воїна",
				"rizni-potreby/vijskovykh-formuvan/poranenoho-khvoroho.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(427, "Молитва за ворогів",
				"rizni-potreby/vijskovykh-formuvan/vorohiv.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(428, "Пом’яник",
				"rizni-potreby/vijskovykh-formuvan/pomyanyk.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(429, "Молитва за тих, що впали в бою",
				"rizni-potreby/vijskovykh-formuvan/vpaly-v-boyu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(430, "Молитва матері військового",
				"rizni-potreby/vijskovykh-formuvan/materi.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(431, "Молитва дружини військового",
				"rizni-potreby/vijskovykh-formuvan/druzhyny.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(432, "Молитва воїна за родину",
				"rizni-potreby/vijskovykh-formuvan/za-rodynu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);

		return menu;
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

	private MenuItemSubMenu addBohosluzhinnya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(82, "Богослужіння");

		MenuItemSubMenu sm = menu.subMenu(437, "Молебні");
		sm.html(448, "Молебень до Христа Спасителя",
				"bohosluzhinnya/molebni/khrysta-spasytelya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(458,
				"Молебень до найсолодшого Господа нашого Ісуса Христа Чоловіколюбця",
				"bohosluzhinnya/molebni/isusa-khrysta-cholovikolyubtsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(447, "Молебень до Божого милосердя",
				"bohosluzhinnya/molebni/bozhoho-myloserdya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(449, "Молебень до Святого Духа",
				"bohosluzhinnya/molebni/sv-dukha.html", SRC_MOLEBNI_KYRIOS);
		sm.html(461, "Молебень до Пресвятої Богородиці",
				"bohosluzhinnya/molebni/presv-bohorodytsi.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(444, "Молебень до Пресвятої Богородиці на честь її Покрову",
				"bohosluzhinnya/molebni/bohorodytsi-pokrovu.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(446,
				"Параклис, або Канон молебний до Пресвятої Богородиці, який співається у всякій скорботі душевній",
				"bohosluzhinnya/molebni/bohorodytsi-skorboti.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(460,
				"Молебень до Матері Божої Непорочно Зачатої.",
				"bohosluzhinnya/molebni/materi-bozhoyi-neporochno-zachatoyi.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(440,
				"Молебень до блаженних новомучеників Української Греко-Католицької Церкви",
				"bohosluzhinnya/molebni/novomuchenykiv-ugcc.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(451,
				"Молебень до блаженних новомучеників Української Греко-Католицької Церкви (1)",
				"bohosluzhinnya/molebni/novomuchenykiv-ugcc-1.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(439,
				"Молебень до святого рівноапостольного князя Володимира Великого, хрестителя Русі-України",
				"bohosluzhinnya/molebni/volodymyra-velykoho.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(445, "Молебень до св. Миколая Чудотворця",
				"bohosluzhinnya/molebni/mykolaya-chudotvortsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(441, "Молебень до преподобного Шарбеля Чудотворця",
				"bohosluzhinnya/molebni/scherbalya-chudotvortsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(443,
				"Молебень до священномученика Теодора (Ромжі), єпископа Мукачівського",
				"bohosluzhinnya/molebni/teodora-romzhi.html",
				SRC_MOLEBNI_KYRIOS);
		sm.text(459, "Молебень до св. Йосифа",
				"bohosluzhinnya/molebni/sv-josypha.html", SRC_MOLEBNI_KYRIOS);
		sm.html(454,
				"Молебень до святого великомученика і чудотворця Юрія Побідоносця",
				"bohosluzhinnya/molebni/yuriya-pobidonostsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(438,
				"Чин молебня під час війни проти супротивників",
				"bohosluzhinnya/molebni/vijny.html",
				"Християнський портал КІРІОС http://kyrios.org.ua/spirituality/molebni/12477-chin-molebnja-pid-chas-vijni-proti-suprotivnikiv.html , за матеріалами оф.сайту храму свщм. Климентія Шептицького http://klymentij.blogspot.com/ .");
		sm.html(442, "Молебень в часі посухи",
				"bohosluzhinnya/molebni/posukhy.html", SRC_MOLEBNI_KYRIOS);
		sm.html(450, "Молебень на Святу Пасху",
				"bohosluzhinnya/molebni/sv-paskhu.html", SRC_MOLEBNI_KYRIOS);
		sm.html(452,
				"Молебень за примирення святої Церкви та визволення її від напастей єретиків і віровідступників",
				"bohosluzhinnya/molebni/prymyrennya-tserkvy.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(453, "Молебень на початок Нового року",
				"bohosluzhinnya/molebni/pochatok-novoho-roku.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(455, "Молебень перед початком навчання дітей",
				"bohosluzhinnya/molebni/pered-pochatkom-navch-ditey.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(456,
				"Молебень в часі губительного мору і смертоносної недуги (епідемії)",
				"bohosluzhinnya/molebni/mor-i-neduha.html", SRC_MOLEBNI_KYRIOS);
		sm.html(457, "Подячний молебень у дні громадянських і державних свят",
				"bohosluzhinnya/molebni/hrom-i-derzh-svyata.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(462, "Молебень за недужих",
				"bohosluzhinnya/molebni/neduzhykh.html", SRC_MOLEBNI_KYRIOS);

		menu.html(
				83,
				"Божественна Літургія св. отця нашого Йоана Золотоустого",
				"bohosluzhinnya/liturhiya-ugcc.html",
				"Молитовник для української родини. Релігійне видавництво \"Ріки води живої\". Львів-2011");
		menu.html(
				84,
				"Чин священної і Божественної Літургії святого Івана Золотоустого",
				"bohosluzhinnya/liturhiya.html",
				"Християнський портал Кіріос - Чин священної і Божественної Літургії святого Івана Золотоустого http://kyrios.org.ua/spirituality/bogosluzhinnja/1198-bozhestvenna-liturgija.html");
		menu.html(467, "Чин утрені", "bohosluzhinnya/utrennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(466, "Чин вечірні", "bohosluzhinnya/vechirnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(
				463,
				"Чин малого освячення води (у скороченні)",
				"bohosluzhinnya/male-osvyach-vody.html",
				"Християнський портал Кіріос - Чин малого освячення води http://kyrios.org.ua/spirituality/bogosluzhinnja/669-chin-malogo-osvjachennja-vodi.html");
		menu.html(
				464,
				"Чин великого освячення води | Йорданське водосвяття",
				"bohosluzhinnya/velyke-osvyach-vody.html",
				"Християнський портал Кіріос - Чин великого освячення води | Йорданське водосвяття http://kyrios.org.ua/spirituality/bogosluzhinnja/1269-chin-velikogo-osvjachennja-vodi-jordanske-vodosvjattja.html");
		menu.html(
				465,
				"Хресна дорога",
				"bohosluzhinnya/khresna-doroha.html",
				"Християнський портал Кіріос - Хресна дорога http://kyrios.org.ua/spirituality/bogosluzhinnja/1521-hresna-doroga.html");
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

		menu.addSubItem(addMolytvyVijskovykhFormuvan());

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
		menu.text(434, "Молитва за померлих",
				"rizni-potreby/za-pomerlykh.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.html(183, "Молитва християнської родини",
				"rizni-potreby/khryst-rodyny.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(184, "Молитва батьків за дітей",
				"rizni-potreby/batkiv-za-ditey.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(185, "Молитва дітей за батьків",
				"rizni-potreby/ditey-za-batkiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(186, "Молитва за чоловіка (дружину)",
				"rizni-potreby/za-cholovika-druzhynu.html",
				"МОЛИТВА - Родинні молитви http://molytva.at.ua/index/rodinni_molitvi/0-151");
		menu.html(187, "Молитва учня (учениці)", "rizni-potreby/uchnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(433, "Молитва за вибір стану",
				"rizni-potreby/vybir-stanu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
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
		menu.text(436, "Молитва за український народ",
				"rizni-potreby/za-ukr-narod.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(435, "Молитва на Новий рік",
				"rizni-potreby/na-novyj-rik.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
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
