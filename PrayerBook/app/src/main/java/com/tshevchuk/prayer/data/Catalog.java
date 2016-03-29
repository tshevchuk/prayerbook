package com.tshevchuk.prayer.data;

import android.app.Application;
import android.text.TextUtils;
import android.util.SparseArray;

import com.tshevchuk.prayer.Utils;
import com.tshevchuk.prayer.domain.model.MenuItemBase;
import com.tshevchuk.prayer.domain.model.MenuItemCalendar;
import com.tshevchuk.prayer.domain.model.MenuItemPrayer;
import com.tshevchuk.prayer.domain.model.MenuItemSubMenu;
import com.tshevchuk.prayer.domain.model.MenuListItemOftenUsed;
import com.tshevchuk.prayer.domain.model.SearchItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Catalog {
	public static final int ID_CALENDAR = 5;
	public static final int ID_RECENT_SCREENS = 400;
	private static final int ID_FOR_EVERY_DAY = 85;
	private static final int ID_FOR_EVERY_OCASION = 164;
	private static final String SRC_BIBLIA = "Біблія, переклад Івана Хоменка";
	private static final String SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA = "Молитовник «Прийдіте поклонімся»";
	private static final String SRC_MOLYTVOSLOV = "Молитвослов. Видавництво ОО. Василіян, Рим-Торонто, 1990.";
	private static final String SRC_DODATOK_KATEKHYZMU_2012 = "Додаток до Катехизму УГКЦ «Христос – наша Пасха» 2012 року";
	private static final String SRC_PRO_SPOVID = "Про Сповідь. о. Порфирій В. Шумило, ЧСВВ, бр. Пімен І. Коневич, ЧСВВ http://osbm-buchach.org.ua/knygy/pro-spovid.html";
	private static final String SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH = "Уроки Біблії http://bible-lessons.in.ua/molutva/dljavagitnux.html";
	private static final String SRC_AKAFISTY_MOLYTVA = "МОЛИТВА - Акафіст http://molytva.at.ua/index/akafist/0-293";
	private static final String SRC_AKAFISTY_KYRIOS = "Християнський портал Кіріос - Акафісти http://kyrios.org.ua/spirituality/akafisti.html";
	private static final String SRC_VERVYTSYA_MOLYTVA = "МОЛИТВА - Вервиці http://molytva.at.ua/index/vervici/0-46";
	private static final String SRC_MOLEBNI_KYRIOS = "Християнський портал Кіріос - Молебні http://kyrios.org.ua/spirituality/molebni.html";
	private static final String SRC_MOLEBNI_MOLYTVA = "МОЛИТВА - Молебні http://molytva.at.ua/index/molebni/0-274";
	private static final String SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA = "МОЛИТОВНИК військовослужбовця «Молись і служи!». Департамент Патріаршої курії Української Греко-Католицької Церкви у справах душпастирства силових структур України. Київ. 2013 рік";
	private static final String SRC_TREBNYK_LITURHIJNI_PEREKLADY = "Літургійні переклади УКГЦ - Требник http://ugcc-littexts-ukr.blogspot.com/p/blog-page_7695.html";
	private static final String SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY = "Літургійні переклади УКГЦ - Требник http://ugcc-littexts-ukr.blogspot.com/p/blog-page_7695.html на основі Требник Львів 2001";
	private static final String SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY = "Літургійні переклади УКГЦ - Требник http://ugcc-littexts-ukr.blogspot.com/p/blog-page_7695.html на основі Требник Львів 2012";
	private static final String SRC_PISNI_KYRIOS = "Християнський портал Кіріос - Слова і ноти http://kyrios.org.ua/music/words-and-notes.html";
	private static final String SRC_MOLYTVY_KYRIOS = "Християнський портал Кіріос - Молитви http://kyrios.org.ua/spirituality/prayer.html";
	private static final String SRC_MOLYTVY_KYRIOS_DO_PRESV_BOHORODYTSI_KOZHEN_DEN_TYZHNYA = "Християнський портал Кіріос - Молитви до Пресвятої Богородиці на кожен день тижня (преп. Ніла Сорського) http://kyrios.org.ua/spirituality/prayer/816-molitvi-do-presvjatoyi-bogoroditsi-na-kozhen-den-tizhnja-prep-nila-sorskogo.html";
	private static final String SRC_LITURHIYA_KYRIOS = "Християнський портал Кіріос - Чин священної і Божественної Літургії святого Івана Золотоустого http://kyrios.org.ua/spirituality/bogosluzhinnja/1198-bozhestvenna-liturgija.html";
	private static final int ID_SCHODENNI_MOLYTVY = 1;
	private static final int NEXT_ID_TO_ADD = 911;
	private final List<MenuItemBase> topMenu;
	private final SparseArray<MenuItemBase> menuItemsByIds = new SparseArray<>();
	private Application application;

	{
		MenuItemSubMenu menu = new MenuItemSubMenu(0, "top");
		menu.addSubItem(new MenuListItemOftenUsed());
		menu.html(ID_SCHODENNI_MOLYTVY, "Щоденні молитви",
				"texts/molytvy-schodenni.html", SRC_DODATOK_KATEKHYZMU_2012)
				.setOfficialUGCCText(true);
		menu.addSubItem(addMolytvyRizniPotreby());
		menu.addSubItem(addMolytvyNaKozhenDen());
		menu.addSubItem(addMolytvyRizni());

		menu.addSubItem(new MenuItemCalendar());

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
	}

	@Inject
	public Catalog(Application application) {
		this.application = application;
		verifyUniqueId();
	}

	private MenuItemSubMenu addMolytvyRizni() {
		MenuItemSubMenu menu = new MenuItemSubMenu(565, "Молитви різні");
		menu.addSubItem(addMolytvyDoSvyatykh());
		menu.addSubItem(addMolytvyNaProslavuUkrSluhBozhykh());
		menu.addSubItem(addMolytvyMytropolytaAndreya());
		menu.addSubItem(addMolytvyVijskovykhFormuvan());

		MenuItemSubMenu sm = menu
				.subMenu(702,
						"Молитви до Пресвятої Богородиці на кожен день тижня (преп. Ніла Сорського)");
		sm.text(703,
				"У понеділок",
				"texts/molytvy/do-presv-bohorodytsi-kozhen-den-tyzhnya/ponedilok.html",
				SRC_MOLYTVY_KYRIOS_DO_PRESV_BOHORODYTSI_KOZHEN_DEN_TYZHNYA);
		sm.text(704,
				"У вівторок",
				"texts/molytvy/do-presv-bohorodytsi-kozhen-den-tyzhnya/vivtorok.html",
				SRC_MOLYTVY_KYRIOS_DO_PRESV_BOHORODYTSI_KOZHEN_DEN_TYZHNYA);
		sm.text(705, "У середу",
				"texts/molytvy/do-presv-bohorodytsi-kozhen-den-tyzhnya/sereda.html",
				SRC_MOLYTVY_KYRIOS_DO_PRESV_BOHORODYTSI_KOZHEN_DEN_TYZHNYA);
		sm.text(706, "У четвер",
				"texts/molytvy/do-presv-bohorodytsi-kozhen-den-tyzhnya/chetver.html",
				SRC_MOLYTVY_KYRIOS_DO_PRESV_BOHORODYTSI_KOZHEN_DEN_TYZHNYA);
		sm.text(707,
				"У п’ятницю",
				"texts/molytvy/do-presv-bohorodytsi-kozhen-den-tyzhnya/pyatnytsya.html",
				SRC_MOLYTVY_KYRIOS_DO_PRESV_BOHORODYTSI_KOZHEN_DEN_TYZHNYA);
		sm.text(708, "У суботу",
				"texts/molytvy/do-presv-bohorodytsi-kozhen-den-tyzhnya/subota.html",
				SRC_MOLYTVY_KYRIOS_DO_PRESV_BOHORODYTSI_KOZHEN_DEN_TYZHNYA);
		sm.text(709, "У неділю",
				"texts/molytvy/do-presv-bohorodytsi-kozhen-den-tyzhnya/nedilya.html",
				SRC_MOLYTVY_KYRIOS_DO_PRESV_BOHORODYTSI_KOZHEN_DEN_TYZHNYA);

		menu.html(710, "Ісусова молитва", "texts/molytvy/isusova.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(2, "Ранішні молитви", "texts/molytvy/molytvy-ranishni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(700, "Ранішні молитви (1)",
				"texts/molytvy/molytvy-ranishni-1.html", SRC_MOLYTVY_KYRIOS);
		menu.html(3, "Вечірні молитви", "texts/molytvy/molytvy-vechirni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(701, "Вечірні молитви (1)",
				"texts/molytvy/molytvy-vechirni-1.html", SRC_MOLYTVY_KYRIOS);
		menu.text(594, "Молитва Іларіона, митрополита Київського",
				"texts/molytvy/ilariona.html", SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA)
				.setOfficialUGCCText(true);

		return menu;
	}

	private MenuItemSubMenu addMolytvyMytropolytaAndreya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(582,
				"Молитви митрополита Андрея").setOfficialUGCCText(true);
		menu.text(583, "Приречення вірної служби Ісусові Христові",
				"texts/molytvy/mytropolyta-andreya/sluzhby-isusovi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(584, "Молитва про божу мудрість",
				"texts/molytvy/mytropolyta-andreya/bozhu-mudrist.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(585, "Інша молитва про божу мудрість",
				"texts/molytvy/mytropolyta-andreya/bozhu-mudrist-insha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(586, "Молитва за український нарід",
				"texts/molytvy/mytropolyta-andreya/ukr-narid.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(587, "Молитва про кращу долю українського народу",
				"texts/molytvy/mytropolyta-andreya/dolyu-ukr-narodu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(588, "Інша молитва за український нарід",
				"texts/molytvy/mytropolyta-andreya/ukr-narid-insha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(589, "Молитва до Пресвятої Богоматері",
				"texts/molytvy/mytropolyta-andreya/presv-bohomateri.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(590, "Молитва за українську родину",
				"texts/molytvy/mytropolyta-andreya/ukr-rodynu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(591, "Молитва до св. Йосифа за родину",
				"texts/molytvy/mytropolyta-andreya/sv-josypha-za-rodynu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(592, "Молитва про добрих українських священиків",
				"texts/molytvy/mytropolyta-andreya/dobrykh-svyaschennykiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(593, "Молитва про ласки для священиків",
				"texts/molytvy/mytropolyta-andreya/lasky-dlya-svyaschennykiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addMolytvyNaProslavuUkrSluhBozhykh() {
		MenuItemSubMenu menu = new MenuItemSubMenu(576,
				"Молитви на прославу українських слуг Божих")
				.setOfficialUGCCText(true);
		menu.text(577, "Молитва за прославу митрополита Андрея Шептицького",
				"texts/molytvy/na-proslavu-ukr-sluh-bozhykh/a-sheptytskoho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(578, "Молитва за прославу єпископа Павла Ґойдича",
				"texts/molytvy/na-proslavu-ukr-sluh-bozhykh/p-gojdycha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(579, "Молитва про заступництво отця Климентія Шептицького",
				"texts/molytvy/na-proslavu-ukr-sluh-bozhykh/k-sheptytskoho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(580, "Молитва за прославу сестри Софронії Ерделі",
				"texts/molytvy/na-proslavu-ukr-sluh-bozhykh/s-erdeli.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(581, "Молитва за прославу сестри Йосафати Гордашевськоі",
				"texts/molytvy/na-proslavu-ukr-sluh-bozhykh/j-hordashevskoji.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addMolytvyDoSvyatykh() {
		MenuItemSubMenu menu = new MenuItemSubMenu(566, "Молитви до святих")
				.setOfficialUGCCText(true);
		menu.text(567, "Молитва до святого Володимира",
				"texts/molytvy-do-svyatykh/volodymyra.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(568, "Молитва до святої княгині Ольги",
				"texts/molytvy-do-svyatykh/olhy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(569, "Молитва до святих Бориса і Гліба",
				"texts/molytvy-do-svyatykh/borysa-i-hliba.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(570, "Молитва до святого Антонія Печерського",
				"texts/molytvy-do-svyatykh/antoniya-pecherskoho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(571, "Молитва до святого Теодосія Печерського",
				"texts/molytvy-do-svyatykh/teodosiya-pechersokoho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(572, "Молитва до святого Йосафата і мучеників",
				"texts/molytvy-do-svyatykh/josaphata-i-muchenykiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(573, "Молитва до святого Йосафата",
				"texts/molytvy-do-svyatykh/josaphata.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.text(574, "Молитва до всіх святих українського народу",
				"texts/molytvy-do-svyatykh/vsikh-svyatykh-ukr-narodu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		return menu;
	}

	private MenuItemSubMenu addTrebnyk() {
		MenuItemSubMenu menu = new MenuItemSubMenu(468, "Требник");

		MenuItemSubMenu book = menu.subMenu(906, "Требник Львів 2001")
				.setOfficialUGCCText(true);
		MenuItemSubMenu sm = book.subMenu(895,
				"Таїнства хрещення і миропомазання");
		sm.web(469, "Молитви у перший день, коли жінка породила дитину",
				"texts/trebnyk/persh-den-porodyla.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(471, "Молитва для знаменування дитяти у 8-ий день",
				"texts/trebnyk/dytya-8j-den.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(472, "Молитва над матір’ю у 40-й день",
				"texts/trebnyk/nad-matiryu-40j-den.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(473, "Молитва над жінкою, яка поронила дитя",
				"texts/trebnyk/zhinkoyu-poronyla-dytya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(474, "Чин оглашення перед Хрещенням",
				"texts/trebnyk/chyn-ohlashennya-pered-khreshchennyam.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(475, "Чин Хрещення", "texts/trebnyk/chyn-khreschennya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(476, "Чин Миропомазання", "texts/trebnyk/chyn-myropomazannya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(477, "Поучення як хрестити коротко в небезпеці смерти",
				"texts/trebnyk/pouchennya-khrestyty-v-nebezp-smerti.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);

		sm = book.subMenu(896, "Таїнства сповіді і причастя");
		sm.web(478, "Чин сповіді", "texts/trebnyk/chyn-spovidi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(479, "Молитва розрішення від клятви",
				"texts/trebnyk/molytva-rozrishennya-vid-klyatvy.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(480, "Чин причастя хворого",
				"texts/trebnyk/chyn-prychastya-khvoroho.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);

		sm = book.subMenu(897, "Таїнство подружжя");
		sm.web(481, "Чин вінчання", "texts/trebnyk/chyn-vinchannya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(482, "Чин введення в церкву вперше одруженої",
				"texts/trebnyk/chyn-vvedennya-v-tserkvu-vpershe-odruzhenoji.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(483, "Чин вдруге вінчаних",
				"texts/trebnyk/chyn-vdruhe-vinchanykh.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);

		sm = book.subMenu(898, "Таїнство єлеосвячення");
		sm.web(484, "Чин малого єлеосвячення",
				"texts/trebnyk/chyn-maloho-yeleoosvyachennya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);

		sm = book.subMenu(899, "Заупокійні богослужіння");
		sm.web(485, "Чин похорону мирян", "texts/trebnyk/chyn-pokhoronu-myryan.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(486, "Чин похорону усопших у світлий тиждень",
				"texts/trebnyk/chyn-pokhoronu-usopshykh.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(487, "Похорон мирських ієреїв",
				"texts/trebnyk/pokhoron-myrskykh-iyereyiv.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(488, "Похорон ієреїв у світлий тиждень",
				"texts/trebnyk/pokhoron-iyereyiv-svitlyj-tyzhden.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(489, "Чин похорону дитини",
				"texts/trebnyk/chyn-pokhoronu-dytyny.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(490, "Чин похорону дитини у світлий тиждень",
				"texts/trebnyk/chyn-pokhoronu-dytyny-svitlyj-tyzhden.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(491, "Чин великої панахиди або парастасу",
				"texts/trebnyk/chyn-velykoyi-panakhydy-abo-parastasu.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);

		sm = book.subMenu(900, "Моління за осіб");
		sm.web(492, "Молитва на всяку неміч",
				"texts/trebnyk/molytva-na-vsyaku-nemich.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(493, "Молитва тому, хто хоче йти в путь",
				"texts/trebnyk/molytva-tomu-khto-jde-v-put.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(494, "Чин благословення дітей перед початком навчання",
				"texts/trebnyk/chyn-blahoslovennya-ditey-pered-navchannyam.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(495,
				"Чин благословення подруж, що прожили разом 25 або 50 років",
				"texts/trebnyk/chyn-blahoslovennya-podruzh-25-50-rokiv.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);

		sm = book.subMenu(901, "Освячення і благословення");
		sm.web(496, "Чин великого освячення води святих Богоявлень",
				"texts/trebnyk/chyn-velykoho-osvyachennya-vody.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(497, "Чин малого освячення води",
				"texts/trebnyk/chyn-maloho-osvyachennya-vody.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(499, "Благословення свічок у празник Стрітення ГНІХ",
				"texts/trebnyk/blahoslovennya-svichok-stritenya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(500,
				"Благословення і освячення верби у Квітну неділю",
				"texts/trebnyk/blahoslovennya-i-osvyachennya-verby-kvitnu-nedily.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(501, "Молитва на благословення артоса",
				"texts/trebnyk/blahoslovennya-artosa.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(502,
				"Чин благословення пасок і іншої поживи у Святу Неділю Пасхи",
				"texts/trebnyk/blahoslovennya-pasok.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(503,
				"Чин благословення первоплодів у празник Преображення Господнього",
				"texts/trebnyk/blahoslovennya-pervoplodiv-praznyk-preobrazhennya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(504,
				"Чин благословення зілля в день Успення Пресвятої Богородиці",
				"texts/trebnyk/blahoslovennya-zillya-uspennya-bohorodytsi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(505,
				"Чин благословення книги святих євангелій та інших богослужбових книг",
				"texts/trebnyk/blahoslovennya-yevanhelij.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(506, "Чин благословення і освячення богослужбового посуду",
				"texts/trebnyk/blahoslovennya-bohosluzhbovoho-posudu.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(507, "Чин благословення покровців для покривання святих дарів",
				"texts/trebnyk/blahoslovennya-pokrovtsiv-pokryvannya-sv-dariv.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(508, "Чин благословення і освячення священичих риз",
				"texts/trebnyk/blahoslovennya-ryz.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(509, "Чин благословення і освячення новопоставленого хреста",
				"texts/trebnyk/blahoslovennya-novopostavlenoho-khresta.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(908, "Чин благословення і освячення ікон",
				"texts/trebnyk/blahoslovennya-ikon.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(510, "Чин благословення і освячення ікон - Тройці",
				"texts/trebnyk/blahoslovennya-ikon-trojtsi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(511, "Чин благословення і освячення ікон - Христа",
				"texts/trebnyk/blahoslovennya-ikon-khrysta.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(512, "Чин благословення і освячення ікон - Богородиці",
				"texts/trebnyk/blahoslovennya-ikon-bohorodytsi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(513, "Чин благословення і освячення ікон - Святих",
				"texts/trebnyk/blahoslovennya-ikon-svyatykh.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(514, "Чин благословення і освячення церковних хоругов",
				"texts/trebnyk/blahoslovennya-khoruhov.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(515, "Молитва на освячення всякої речі",
				"texts/trebnyk/molytva-osvyach-vsyakoyi-rechi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(516, "Чин благословення нового дому",
				"texts/trebnyk/blahoslovennya-novoho-domu.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);

		sm = book.subMenu(902, "Додаток");
		sm.web(517, "Чин святої П’ятдесятниці",
				"texts/trebnyk/chyn-pyatdesyatnytsi.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(518, "Чин загального молебня",
				"texts/trebnyk/chyn-zahalnoho-molebnya.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);
		sm.web(519, "Молебень на святу Пасху",
				"texts/trebnyk/moleben-na-sv-paskhu.html",
				SRC_TREBNYK_2001_LITURHIJNI_PEREKLADY);

		book = menu.subMenu(907, "Требник Львів 2012")
				.setOfficialUGCCText(true);
		sm = book.subMenu(903, "Моління за осіб");
		sm.web(520, "Скорочений чин Єлеопомазання",
				"texts/trebnyk/skorochenyj-chyn-yeleopomazannya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(521, "Чин молебня над хворим",
				"texts/trebnyk/chyn-molebnya-nad-khvorymy.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(522, "Молитва над хворим, що терпить від безсоння",
				"texts/trebnyk/molytva-khvoryj-bezsonnya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(523, "Чин благословення дітей",
				"texts/trebnyk/chyn-blahoslovennya-ditey.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(524, "Молитва над бездітним подружжям",
				"texts/trebnyk/molytva-nad-bezditnym-podruzzhyam.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(525, "Молитва за тих, що творять нам милостиню",
				"texts/trebnyk/molytva-tvoryat-mylostynyu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(526, "Чин благодарення за здійснені прохання",
				"texts/trebnyk/chyn-blahodarennya-zdijsneni-prokhannya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(527, "Молитвоний чин відвідин перед Різдвом",
				"texts/trebnyk/molytovn-chyn-vidvidyn-pered-rizdvom.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(528, "Молитва під час відвідин на Богоявлення",
				"texts/trebnyk/molytva-vidvidyn-bohoyavlennya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(529, "Молитви на початок святої і великої Чотиридесятниці",
				"texts/trebnyk/molytva-na-pochatok-sv-chotyrydesyatnytsi.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(530, "Молитви перед Пасхальним причастям",
				"texts/trebnyk/molytva-pered-sv-paskhalnym-prychastyam.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(531, "Чин введення новопоставленого пароха",
				"texts/trebnyk/chyn-vvedennya-novopostavlenoho-parokha.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(532, "Чин прийняття у церковне братство",
				"texts/trebnyk/chyn-pryjnyattya-u-tserkovne-bratstvo.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(533,
				"Чин присяги стриманости від вживання алкогольних напоїв",
				"texts/trebnyk/chyn-prysyahy-strymanosti-vid-vzhyvannya-alkoholyu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);

		sm = book.subMenu(904, "Освячення і благословення речей та місць");
		sm.web(534, "Чин малого освячення води у скороченні",
				"texts/trebnyk/chyn-maloho-osvyach-vody-skoroch-1.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(535, "Чин благословення посуду церковного",
				"texts/trebnyk/blahosl-posudu-tserkovnoho.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(536, "Чин благословення дароносиці",
				"texts/trebnyk/chyn-blahoslovennya-daronosytsi.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(537, "Чин благословення нового кивоту",
				"texts/trebnyk/chyn-blahosl-novoho-kyvotu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(538,
				"Чин благословення посудин для зберігання мощей святих",
				"texts/trebnyk/chyn-blahoslovennya-posudyn-zberihannya-moschej-svyatykh.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(539, "Благословення кадила",
				"texts/trebnyk/blahoslovennya-kadyla.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(540, "Чин благословення напрестольного хреста",
				"texts/trebnyk/chyn-blahoslovennya-naprestolnoho-khresta.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(541, "Чин благословення хреста нагрудного",
				"texts/trebnyk/chyn-blahoslovennya-khresta-nahrudnoho.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(542, "Благословення храмових світильників або свічок",
				"texts/trebnyk/blahoslovennya-khramovykh-svitylnykiv.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(543,
				"Чин благословення і положення першого каменя на оснування церкви",
				"texts/trebnyk/blahoslovennya-pershoho-kamenya-osnuvannya-tserkvy.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(544, "Чин благословення нової церкви",
				"texts/trebnyk/blahoslovennya-novoyi-tserkvy.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(545, "Чин благословення накупольного хреста",
				"texts/trebnyk/blahoslovennya-nakupolnoho-khresta.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(546, "Чин благословення і освячення іконостасу",
				"texts/trebnyk/blahoslovennya-ikonostasu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(547, "Чин благословення нової дзвениці",
				"texts/trebnyk/blahoslovennya-novoyi-dzvenytsi.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(548, "Чин благословення дзвонів",
				"texts/trebnyk/blahoslovennya-dzvoniv.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(549, "Чин благословення нового цвинтаря",
				"texts/trebnyk/blahoslovennya-novoho-tsvyntarya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(550, "Чин примирення оскверненого цвинтаря",
				"texts/trebnyk/chyn-prymyrennya-oskvernenoho-tsvyntarya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(551, "Чин благословення гробниці або надгробку",
				"texts/trebnyk/chyn-blahoslovennya-hrobnytsi-nadhrobku.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);

		sm = book.subMenu(905, "Інші молитви на різні потреби");
		sm.web(552, "Молитви під час посухи",
				"texts/trebnyk/molytva-pid-chas-posukhy.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(553, "Молитви під час сльоти",
				"texts/trebnyk/molytva-pid-chas-sljoty.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(554, "Чин благословення оснування нового дому",
				"texts/trebnyk/chyn-blahoslovennya-osnuvannya-novoho-domu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(555, "Чин благословення нового дому",
				"texts/trebnyk/chyn-blahoslovennya-novoho-domu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(556, "Чин благословення моста",
				"texts/trebnyk/chyn-blahoslovennya-mosta.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(557, "Чин благословення транспортного засобу",
				"texts/trebnyk/chyn-blahoslovennya-transportnoho-zasobu.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(558, "Чин благословення корабля",
				"texts/trebnyk/chyn-blahoslovennya-korablya.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(559, "Благословення на копання криниці",
				"texts/trebnyk/blahoslovennya-na-kopannya-krynytsi.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(560, "Чин благословення стада худоби",
				"texts/trebnyk/chyn-blahoslovennya-stada-khudoby.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);
		sm.web(561, "Молитва на освячення всякої речі",
				"texts/trebnyk/molytva-na-osvyachennya-vsyakoyi-rechi.html",
				SRC_TREBNYK_2012_LITURHIJNI_PEREKLADY);

		menu.web(
				562,
				"Йорданські душпастирські відвідини з свяченою водою",
				"texts/trebnyk/jordanski-dushpastyrski-vidvidyny-z-osvyachenoyu-vodoyu.html",
				SRC_TREBNYK_LITURHIJNI_PEREKLADY);
		menu.web(563, "Благословення транспортного засобу",
				"texts/trebnyk/blahoslovennya-transportnoho-zasobu.html",
				SRC_TREBNYK_LITURHIJNI_PEREKLADY);

		return menu;
	}

	private MenuItemSubMenu addMolytvyVijskovykhFormuvan() {
		MenuItemSubMenu menu = new MenuItemSubMenu(403,
				"Молитви представників військових формувань");

		menu.text(404, "Молитва військовослужбовця",
				"texts/rizni-potreby/vijskovykh-formuvan/vijskovosluzhbovtsya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(405, "Молитва офіцера",
				"texts/rizni-potreby/vijskovykh-formuvan/ofitsera.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(406, "Молитва військовослужбовця повітряних сил",
				"texts/rizni-potreby/vijskovykh-formuvan/povitryanykh-syl.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(407, "Молитва військовослужбовця військово-морських сил",
				"texts/rizni-potreby/vijskovykh-formuvan/vijskovo-morskykh-syl.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(408, "Молитва військовослужбовця сухопутних військ",
				"texts/rizni-potreby/vijskovykh-formuvan/sukhoputnykh.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(409, "Молитва за щасливу подорож",
				"texts/rizni-potreby/vijskovykh-formuvan/schaslyvu-podorozh.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(410,
				"Молитва за капітанів кораблів і тих, що подорожують на водах",
				"texts/rizni-potreby/vijskovykh-formuvan/korabli-vody.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(411, "Молитва військового водія",
				"texts/rizni-potreby/vijskovykh-formuvan/vodiya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(412, "Молитва артилериста",
				"texts/rizni-potreby/vijskovykh-formuvan/artelerysta.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(413, "Молитва десантника",
				"texts/rizni-potreby/vijskovykh-formuvan/desantnyka.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(414, "Молитва зв’язківця",
				"texts/rizni-potreby/vijskovykh-formuvan/zvazkivtsya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(415, "Молитва танкіста",
				"texts/rizni-potreby/vijskovykh-formuvan/tankista.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(416, "Молитва курсанта",
				"texts/rizni-potreby/vijskovykh-formuvan/kursanta.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(417, "Молитва рятівника",
				"texts/rizni-potreby/vijskovykh-formuvan/ryativnyka.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(418, "Молитва прикордонника",
				"texts/rizni-potreby/vijskovykh-formuvan/prykordonnyka.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(
				419,
				"Молитва працівника пенітенціарної служби",
				"texts/rizni-potreby/vijskovykh-formuvan/penitentsiarnoyi-sluzhby.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(420, "Молитва правоохоронця",
				"texts/rizni-potreby/vijskovykh-formuvan/pravookhorontsya.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(421, "Молитва за мир",
				"texts/rizni-potreby/vijskovykh-formuvan/za-myr.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(422, "Молитва під час бойових дій",
				"texts/rizni-potreby/vijskovykh-formuvan/bojovykh-dij.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(423, "Молитва перед боєм",
				"texts/rizni-potreby/vijskovykh-formuvan/pered-boyem.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(424, "Коротка молитва під час бою",
				"texts/rizni-potreby/vijskovykh-formuvan/pid-chas-boyu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(425, "Молитва полоненого",
				"texts/rizni-potreby/vijskovykh-formuvan/polonenoho.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(426, "Молитва пораненого чи хворого воїна",
				"texts/rizni-potreby/vijskovykh-formuvan/poranenoho-khvoroho.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(427, "Молитва за ворогів",
				"texts/rizni-potreby/vijskovykh-formuvan/vorohiv.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(428, "Пом’яник",
				"texts/rizni-potreby/vijskovykh-formuvan/pomyanyk.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(429, "Молитва за тих, що впали в бою",
				"texts/rizni-potreby/vijskovykh-formuvan/vpaly-v-boyu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(430, "Молитва матері військового",
				"texts/rizni-potreby/vijskovykh-formuvan/materi.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(431, "Молитва дружини військового",
				"texts/rizni-potreby/vijskovykh-formuvan/druzhyny.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(432, "Молитва воїна за родину",
				"texts/rizni-potreby/vijskovykh-formuvan/za-rodynu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);

		return menu;
	}

	private MenuItemSubMenu addPisni() {
		MenuItemSubMenu menu = new MenuItemSubMenu(7, "Пісні");

		MenuItemSubMenu sm = menu.subMenu(8, "Молитва прослави");
		sm.text(9, "Пісня подяки св. Амвросія, єпископа Медіолянського",
				"texts/pisni/proslavy/podyaky-amvrosiya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);

		sm = menu.subMenu(10, "Молитви за нарід");
		sm.text(11, "Боже великий, єдиний",
				"texts/pisni/za-narid/bozhe-velykyj-jedynyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(12, "Боже, вислухай благання",
				"texts/pisni/za-narid/bozhe-vyslukhaj-blahannya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(13, "Боже великий, Творче всесвіту",
				"texts/pisni/za-narid/bozhe-tvorche-vsesvitu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);

		sm = menu.subMenu(14, "Коляди");
		sm.text(15, "Бог предвічний", "texts/pisni/kolyady/boh-predvichynyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(16, "Бог ся рождає", "texts/pisni/kolyady/boh-sya-rozhdaye.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(17, "В Вифлеємі днесь Марія",
				"texts/pisni/kolyady/v-vyflejemi-dnes-mariya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(18, "В Вифлеємі новина",
				"texts/pisni/kolyady/v-vyfleyemi-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(19, "Во Вифлеємі нині новина",
				"texts/pisni/kolyady/vo-vyfleyemi-nyni-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(20, "Возвеселімся всі разом нині",
				"texts/pisni/kolyady/vozveselimsya-vsi-razom-nyni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(21, "Вселенная, веселися",
				"texts/pisni/kolyady/vselennaya-veselysya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(22, "Дивная новина", "texts/pisni/kolyady/dyvnaya-novyna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(23, "На небі зірка ясна засяла",
				"texts/pisni/kolyady/na-nebi-zirka-yasna-zasyala.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(24, "Небо і земля", "texts/pisni/kolyady/nebo-i-zemlya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(25, "Нова радість стала",
				"texts/pisni/kolyady/nova-radist-stala.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(26, "Нова радість стала (1)",
				"texts/pisni/kolyady/nova-radist-stala-1.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(27, "По всьому світу", "texts/pisni/kolyady/po-vsjomu-svitu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.html(632, "Ірод-цар за Христом ганявся",
				"texts/pisni/kolyady/irod-tsar-za-khrystom-hanyavsya.html",
				SRC_PISNI_KYRIOS);
		sm.text(633, "А у місті Русалимі",
				"texts/pisni/kolyady/a-u-misti-rusalymi.html", SRC_PISNI_KYRIOS);
		sm.text(634, "Ангел Божий із небес",
				"texts/pisni/kolyady/anhel-bozhyj-iz-nebes.html", SRC_PISNI_KYRIOS);
		sm.text(635, "Ангели, снижайтеся",
				"texts/pisni/kolyady/anhely-snyzhajtesya.html", SRC_PISNI_KYRIOS);
		sm.text(636, "В Вифлеємі тайна",
				"texts/pisni/kolyady/v-vyfleyemi-tajna.html", SRC_PISNI_KYRIOS);
		sm.text(637, "В Вифлеємі у вертепі",
				"texts/pisni/kolyady/v-vyfleyemi-u-vertepi.html", SRC_PISNI_KYRIOS);
		sm.text(638, "В галицькій землі",
				"texts/pisni/kolyady/v-halytskij-zemli.html", SRC_PISNI_KYRIOS);
		sm.text(639, "В глибокій долині",
				"texts/pisni/kolyady/v-hlybokij-dolyni.html", SRC_PISNI_KYRIOS);
		sm.text(640, "В різдвяну ніч", "texts/pisni/kolyady/v-rizdvyanu-nich.html",
				SRC_PISNI_KYRIOS);
		sm.text(641, "Вітай, Ісусе", "texts/pisni/kolyady/vitaj-isuse.html",
				SRC_PISNI_KYRIOS);
		sm.text(643, "Возвеселіться, християни",
				"texts/pisni/kolyady/vozveselitsya-khrystyyany.html",
				SRC_PISNI_KYRIOS);
		sm.text(644, "Діва Марія церкву строїла",
				"texts/pisni/kolyady/diva-mariya-tserkvu-stroyila.html",
				SRC_PISNI_KYRIOS);
		sm.text(645, "Дивная новина днесь ся являє",
				"texts/pisni/kolyady/dyvnaya-novyna-dnes-sya-yavlyaye.html",
				SRC_PISNI_KYRIOS);
		sm.text(646, "Добрий вечір тобі, пане господарю",
				"texts/pisni/kolyady/dobryj-vechir-tobi-pane-hospodaryu.html",
				SRC_PISNI_KYRIOS);
		sm.text(647, "Землю Юдейську", "texts/pisni/kolyady/zemlyu-yudejsku.html",
				SRC_PISNI_KYRIOS);
		sm.text(648, "Коли Христос родився",
				"texts/pisni/kolyady/koly-khrystos-rodyvsya.html", SRC_PISNI_KYRIOS);
		sm.text(649, "Коляда", "texts/pisni/kolyady/kolyada.html", SRC_PISNI_KYRIOS);
		sm.text(650, "Народився Бог на санях",
				"texts/pisni/kolyady/narodyvsya-boh-na-sanyakh.html",
				SRC_PISNI_KYRIOS);
		sm.text(651, "Не плач, Рахиле", "texts/pisni/kolyady/ne-plach-rakhyle.html",
				SRC_PISNI_KYRIOS);
		sm.text(652, "Нині, Адаме", "texts/pisni/kolyady/nyni-adame.html",
				SRC_PISNI_KYRIOS);
		sm.text(653, "Нова радість стала (2)",
				"texts/pisni/kolyady/nova-radist-stala-2.html", SRC_PISNI_KYRIOS);
		sm.text(654, "Ой, як же було ізпрежди віка",
				"texts/pisni/kolyady/oj-yak-zhe-bulo-izprezhdy-vika.html",
				SRC_PISNI_KYRIOS);
		sm.text(655, "Пречистая Діва сина породила",
				"texts/pisni/kolyady/prechystaya-diva-syna-porodyla.html",
				SRC_PISNI_KYRIOS);
		sm.text(656, "Прилетіли ангелята",
				"texts/pisni/kolyady/pryletily-anhelyata.html", SRC_PISNI_KYRIOS);
		sm.text(657, "Радуйся, світ", "texts/pisni/kolyady/radujsya-svit.html",
				SRC_PISNI_KYRIOS);
		sm.text(658, "Свята ніч", "texts/pisni/kolyady/svyata-nich.html",
				SRC_PISNI_KYRIOS);
		sm.text(659, "Спи, Ісусе, спи", "texts/pisni/kolyady/spy-isuse-spy.html",
				SRC_PISNI_KYRIOS);
		sm.text(660, "Сумний Святий Вечір",
				"texts/pisni/kolyady/sumnyj-svyatyj-vechir.html", SRC_PISNI_KYRIOS);
		sm.text(661, "Темненькая нічка",
				"texts/pisni/kolyady/temnenkaya-nichka.html", SRC_PISNI_KYRIOS);
		sm.text(662, "Тиха ніч, свята ніч",
				"texts/pisni/kolyady/tykha-nich-svyata-nich.html", SRC_PISNI_KYRIOS);
		sm.text(663, "Христос родився", "texts/pisni/kolyady/khrystos-rodyvsya.html",
				SRC_PISNI_KYRIOS);
		sm.text(664, "Хто там по дорозі",
				"texts/pisni/kolyady/khto-tam-po-dorozi.html", SRC_PISNI_KYRIOS);
		sm.text(665, "Чудо у Давида домі",
				"texts/pisni/kolyady/chudo-u-davyda-domi.html", SRC_PISNI_KYRIOS);
		sm.text(667, "Шедше тріє царі",
				"texts/pisni/kolyady/shedshe-triye-tsari.html", SRC_PISNI_KYRIOS);
		sm.text(668, "Що то за предиво",
				"texts/pisni/kolyady/scho-to-za-predyvo.html", SRC_PISNI_KYRIOS);
		sm.text(669, "Яка ж радість", "texts/pisni/kolyady/yaka-zh-radist.html",
				SRC_PISNI_KYRIOS);
		sm.text(670, "Ясна зоря", "texts/pisni/kolyady/yasna-zorya.html",
				SRC_PISNI_KYRIOS);

		sm = menu.subMenu(595, "Щедрівки");
		sm.text(596, "Прийшли до вас щедрівці",
				"texts/pisni/schedrivky/pryjshly-schedrivtsi.html", SRC_PISNI_KYRIOS);
		sm.text(597, "Щедрий вечір всім нам",
				"texts/pisni/schedrivky/schedryj-vechir.html", SRC_PISNI_KYRIOS);
		sm.text(598, "Ой чи є, чи нема пан господар вдома",
				"texts/pisni/schedrivky/oj-chy-ye-pan-hospodar.html",
				SRC_PISNI_KYRIOS);
		sm.text(599, "Щедрик, щедрик, щедрівочка",
				"texts/pisni/schedrivky/schedryk.html", SRC_PISNI_KYRIOS);
		sm.text(600, "Ой сивая та і зозулечка",
				"texts/pisni/schedrivky/zozulenka.html", SRC_PISNI_KYRIOS);
		sm.text(601, "Ой, господар, господарочку",
				"texts/pisni/schedrivky/oj-hospodar.html", SRC_PISNI_KYRIOS);
		sm.text(602, "Стоїть явір зелененький", "texts/pisni/schedrivky/yavir.html",
				SRC_PISNI_KYRIOS);
		sm.text(603, "Павочка ходить", "texts/pisni/schedrivky/pavochka-khodyt.html",
				SRC_PISNI_KYRIOS);
		sm.text(671,
				"Ти, Йордане, приготовися",
				"texts/pisni/schedrivky/ty-jordane.html",
				"Українські пісні - Ти, Йордане, приготовися http://www.pisni.org.ua/songs/2170624.html");

		sm = menu.subMenu(28, "Великопосні пісні");
		sm.text(29, "Люди мої, люди",
				"texts/pisni/velykoposni/lyudy-moyi-lyudy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(30, "На Вавилонських ріках",
				"texts/pisni/velykoposni/na-vavylonskykh-rikakh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(31, "Під хрест Твій стаю",
				"texts/pisni/velykoposni/pid-khrest-tvij-stayu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(32, "Поклоняюся, мій Христе",
				"texts/pisni/velykoposni/poklonyayusya-mij-khryste.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(33, "Страдальна Мати", "texts/pisni/velykoposni/stradalna-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(34, "Хрест на плечі накладають",
				"texts/pisni/velykoposni/khrest-na-plechi-nakladayut.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(620, "Ісус перед стражданням",
				"texts/pisni/velykoposni/isus-pered-strazhdannyam.html",
				SRC_PISNI_KYRIOS);
		sm.text(621, "Христос вийшов з храму",
				"texts/pisni/velykoposni/khrystos-vyjshov-z-khramu.html",
				SRC_PISNI_KYRIOS);
		sm.text(622, "Прийди до мене", "texts/pisni/velykoposni/pryjdy-do-mene.html",
				SRC_PISNI_KYRIOS);
		sm.text(623, "По святій горі",
				"texts/pisni/velykoposni/po-svyatij-hori.html", SRC_PISNI_KYRIOS);
		sm.text(624, "Настала ніч", "texts/pisni/velykoposni/nastala-nich.html",
				SRC_PISNI_KYRIOS);
		sm.text(625, "Голгофа. Хрест",
				"texts/pisni/velykoposni/holhopha-khrest.html", SRC_PISNI_KYRIOS);
		sm.text(626, "Голгофа", "texts/pisni/velykoposni/holhopha.html",
				SRC_PISNI_KYRIOS);
		sm.text(627, "Моліться батьки",
				"texts/pisni/velykoposni/molitsya-batky.html", SRC_PISNI_KYRIOS);
		sm.text(628, "Маріє гляди", "texts/pisni/velykoposni/mariye-hlyady.html",
				SRC_PISNI_KYRIOS);
		sm.text(629, "Коли на волю проситься сльоза",
				"texts/pisni/velykoposni/koly-na-volyu-prosytsya-sljoza.html",
				SRC_PISNI_KYRIOS);

		sm = menu.subMenu(35, "Воскресні пісні");
		sm.text(36, "Сей день", "texts/pisni/voskresni/sej-den.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(37, "Согласно заспіваймо",
				"texts/pisni/voskresni/sohlasno-zaspivajmo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(38, "Христос воскрес! Радість з неба",
				"texts/pisni/voskresni/khrystos-voskres.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);

		sm = menu.subMenu(39, "Пісні до Христа");
		sm.text(40, "Витай між нами", "texts/pisni/khrysta/vytaj-mizh-namy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(41, "Вірую, Господи", "texts/pisni/khrysta/viruyu-hospody.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(42, "Ісуса в святих тайнах",
				"texts/pisni/khrysta/isusa-v-svyatykh-tajnakh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(43, "О, Ісусе, Ти з лю6ови",
				"texts/pisni/khrysta/o-isuse-ty-z-lyubovy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(44, "Пливи світами", "texts/pisni/khrysta/plyvy-svitamy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(45, "Тіло Христове", "texts/pisni/khrysta/tilo-khrystove.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(604,
				"На чудовій Україні",
				"texts/pisni/khrysta/na-chudovij-ukrayini.html",
				"Українські пісні - На чудовій Україні http://www.pisni.org.ua/songs/472333.html");
		sm.text(605, "Хто Тебе приймає, Христе",
				"texts/pisni/khrysta/khro-tebe-pryjmaye-khryste.html",
				SRC_PISNI_KYRIOS);
		sm.text(606, "Будь благословенний",
				"texts/pisni/khrysta/bud-blahoslovennyj.html", SRC_PISNI_KYRIOS);
		sm.text(607, "В страсі і покорі",
				"texts/pisni/khrysta/v-strasi-i-pokori.html", SRC_PISNI_KYRIOS);
		sm.text(608, "Святе Причастя", "texts/pisni/khrysta/svyate-prychastya.html",
				SRC_PISNI_KYRIOS);
		sm.text(609, "Хочу я з Тобою жить",
				"texts/pisni/khrysta/khochu-z-toboyu-zhyt.html", SRC_PISNI_KYRIOS);
		sm.text(610, "Люблю Тебе", "texts/pisni/khrysta/lyublyu-tebe.html",
				SRC_PISNI_KYRIOS);
		sm.text(611, "Лине пісня про Ісуса",
				"texts/pisni/khrysta/lyne-pisnya-pro-isusa.html", SRC_PISNI_KYRIOS);
		sm.text(612, "Зійшла зоря", "texts/pisni/khrysta/zijshla-zorya.html",
				SRC_PISNI_KYRIOS);
		sm.text(613, "Боже – Ти Бог мій",
				"texts/pisni/khrysta/bozhe-ty-boh-mij.html", SRC_PISNI_KYRIOS);

		sm = menu.subMenu(46, "Пісня до Святого Духа");
		sm.text(47, "Царю небесний", "texts/pisni/dukha/tsaryu-nebesnyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);

		sm = menu.subMenu(48, "Пісні до Богородиці");
		sm.text(49, "Богородице Діво",
				"texts/pisni/bohorodytsi/bohorodytse-divo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(50, "Вихваляйте доли, гори",
				"texts/pisni/bohorodytsi/vykhvalyajte-doly-hory.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(51, "Ген до неба", "texts/pisni/bohorodytsi/hen-do-neba.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(52, "Достойно єсть", "texts/pisni/bohorodytsi/dostojno-yest.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(53, "Коли втомлений", "texts/pisni/bohorodytsi/koly-vtomlenyj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(54, "Лугами піснь", "texts/pisni/bohorodytsi/luhamy-pisen.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(55, "Маріє, Діво благословенна",
				"texts/pisni/bohorodytsi/mariye-divo-blahoslovenna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(56, "Маріє Діво, Царице мая",
				"texts/pisni/bohorodytsi/mariye-divo-tsarytse-maya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(57, "Нехай звенить", "texts/pisni/bohorodytsi/nekhaj-dzvenyt.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(58, "О, Богородице Діво, Маріє",
				"texts/pisni/bohorodytsi/o-bohorodytse-divo-mariye.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(59, "О, всепітая Мати",
				"texts/pisni/bohorodytsi/o-vsepitaya-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(60, "О, Маріє, Мати Божа",
				"texts/pisni/bohorodytsi/o-mariye-maty-bozha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(61, "О, Мати Божа, о, райський цвіте",
				"texts/pisni/bohorodytsi/o-maty-bozha-o-rajskyj-tsvite.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(62, "О, Пречиста", "texts/pisni/bohorodytsi/o-prechysta.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(63, "Пренебесна, пречудесна",
				"texts/pisni/bohorodytsi/prenebesna-prechudesna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(64, "Пречистая Діво, Мати",
				"texts/pisni/bohorodytsi/prechysta-divo-maty.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(65, "Просимо тя, Діво",
				"texts/pisni/bohorodytsi/prosymo-tya-divo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(66, "Там, де в небі", "texts/pisni/bohorodytsi/tam-de-v-nebi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(614, "Маріє, Діво Пресвята",
				"texts/pisni/bohorodytsi/mariye-divo-presvyata.html",
				SRC_PISNI_KYRIOS);
		sm.text(615,
				"Пісня до Матері Божої на честь Її Ліської чудотворної ікони",
				"texts/pisni/bohorodytsi/na-chest-liskoyi-ikony.html",
				SRC_PISNI_KYRIOS);
		sm.text(616, "Унівська Мати Божа",
				"texts/pisni/bohorodytsi/univska-maty-bozha.html", SRC_PISNI_KYRIOS);
		sm.text(617, "О Царице", "texts/pisni/bohorodytsi/o-tsarytse.html",
				SRC_PISNI_KYRIOS);
		sm.text(618, "О дивноє чудо з чудес",
				"texts/pisni/bohorodytsi/o-dyvo-dyvneye-z-chudes.html",
				SRC_PISNI_KYRIOS);
		sm.text(619, "Ведуть в Зарваницю",
				"texts/pisni/bohorodytsi/vedut-v-zarvanytsu.html", SRC_PISNI_KYRIOS);

		sm = menu.subMenu(67, "Пісні до святих");
		sm.text(68, "Святий Великий Володимире",
				"texts/pisni/svyatykh/svyatyj-velykyj-volodymyre.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(69, "О, свята Ольго", "texts/pisni/svyatykh/o-svyata-olho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(70, "Пісню слави", "texts/pisni/svyatykh/pisnyu-slavy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(71, "Владико, отче", "texts/pisni/svyatykh/vladyko-otche.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(72, "О, хто, хто Миколая любить",
				"texts/pisni/svyatykh/o-khto-khto-mykolaya-lyubyt.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);

		sm = menu.subMenu(73, "Похоронні пісні");
		sm.text(74, "В темну могилу", "texts/pisni/pokhoronni/v-temnu-mohylu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(75, "Кругом життя", "texts/pisni/pokhoronni/kruhom-zhyttya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(76, "Прощай, душе", "texts/pisni/pokhoronni/proschaj-dushe.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(77, "Со святими упокой",
				"texts/pisni/pokhoronni/so-svyatymy-upokoj.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(630, "Моліться всі", "texts/pisni/pokhoronni/molitsya-vsi.html",
				SRC_PISNI_KYRIOS);
		sm.text(631, "О, Боже Великий, з високого неба",
				"texts/pisni/pokhoronni/o-bozhe-velykyj-z-vysokoho-neba.html",
				SRC_PISNI_KYRIOS);

		return menu;

	}

	private MenuItemSubMenu addVervytsya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(78, "Вервиця");
		menu.html(80, "Як молитися на Вервиці", "texts/vervytsya/yak-molytysya.html",
				SRC_VERVYTSYA_MOLYTVA);
		menu.html(81, "Молитва на Вервиці",
				"texts/vervytsya/molytva-na-vervytsi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(711, "Молитва на чотках",
				"texts/vervytsya/molytva-na-chotkakh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(894, "Світлі таїнства", "texts/vervytsya/svitli-tajinstva.html",
				"Молитва - Світлі таїнства http://molytva.at.ua/index/svitli_tajinstva/0-57");
		return menu;
	}

	private MenuItemSubMenu addBohosluzhinnya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(82, "Богослужіння");

		MenuItemSubMenu sm = menu.subMenu(687, "Часослов").setOfficialUGCCText(
				true);
		sm.web(688, "Вечірня", "texts/bohosluzhinnya/chasoslov/vechirnya.html",
				SRC_MOLYTVOSLOV);
		sm.web(689, "Мале Повечір’я",
				"texts/bohosluzhinnya/chasoslov/male-povechirya.html",
				SRC_MOLYTVOSLOV);
		sm.web(690, "Велике Повечір'я",
				"texts/bohosluzhinnya/chasoslov/velyke-povechirya.html",
				SRC_MOLYTVOSLOV);
		sm.web(691, "Північна Щоденна",
				"texts/bohosluzhinnya/chasoslov/pivnichna-schodenna.html",
				SRC_MOLYTVOSLOV);
		sm.web(692, "Північна Суботня",
				"texts/bohosluzhinnya/chasoslov/pivnichna-subotnya.html",
				SRC_MOLYTVOSLOV);
		sm.web(693, "Північна Недільна",
				"texts/bohosluzhinnya/chasoslov/pivnichna-nedilna.html",
				SRC_MOLYTVOSLOV);
		sm.web(694, "Утреня", "texts/bohosluzhinnya/chasoslov/utrenya.html",
				SRC_MOLYTVOSLOV);
		sm.web(695, "Час Перший", "texts/bohosluzhinnya/chasoslov/chas-pershyj.html",
				SRC_MOLYTVOSLOV);
		sm.web(696, "Час Третій", "texts/bohosluzhinnya/chasoslov/chas-tretij.html",
				SRC_MOLYTVOSLOV);
		sm.web(697, "Час Шостий", "texts/bohosluzhinnya/chasoslov/chas-shostyj.html",
				SRC_MOLYTVOSLOV);
		sm.web(698, "Час Дев’ятий",
				"texts/bohosluzhinnya/chasoslov/chas-devyatyj.html", SRC_MOLYTVOSLOV);
		sm.web(699, "Обідниця", "texts/bohosluzhinnya/chasoslov/obidnytsya.html",
				SRC_MOLYTVOSLOV);

		sm = menu.subMenu(437, "Молебні");
		sm.html(448, "Молебень до Христа Спасителя",
				"texts/bohosluzhinnya/molebni/khrysta-spasytelya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(458,
				"Молебень до найсолодшого Господа нашого Ісуса Христа Чоловіколюбця",
				"texts/bohosluzhinnya/molebni/isusa-khrysta-cholovikolyubtsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(447, "Молебень до Божого милосердя",
				"texts/bohosluzhinnya/molebni/bozhoho-myloserdya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(461, "Молебень до Пресвятої Богородиці",
				"texts/bohosluzhinnya/molebni/presv-bohorodytsi.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(444, "Молебень до Пресвятої Богородиці на честь її Покрову",
				"texts/bohosluzhinnya/molebni/bohorodytsi-pokrovu.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(446,
				"Параклис, або Канон молебний до Пресвятої Богородиці, який співається у всякій скорботі душевній",
				"texts/bohosluzhinnya/molebni/bohorodytsi-skorboti.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(460,
				"Молебень до Матері Божої Непорочно Зачатої.",
				"texts/bohosluzhinnya/molebni/materi-bozhoyi-neporochno-zachatoyi.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(440,
				"Молебень до блаженних новомучеників Української Греко-Католицької Церкви",
				"texts/bohosluzhinnya/molebni/novomuchenykiv-ugcc.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(451,
				"Молебень до блаженних новомучеників Української Греко-Католицької Церкви (1)",
				"texts/bohosluzhinnya/molebni/novomuchenykiv-ugcc-1.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(439,
				"Молебень до святого рівноапостольного князя Володимира Великого, хрестителя Русі-України",
				"texts/bohosluzhinnya/molebni/volodymyra-velykoho.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(445, "Молебень до св. Миколая Чудотворця",
				"texts/bohosluzhinnya/molebni/mykolaya-chudotvortsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(441, "Молебень до преподобного Шарбеля Чудотворця",
				"texts/bohosluzhinnya/molebni/scherbalya-chudotvortsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(443,
				"Молебень до священномученика Теодора (Ромжі), єпископа Мукачівського",
				"texts/bohosluzhinnya/molebni/teodora-romzhi.html",
				SRC_MOLEBNI_KYRIOS);
		sm.text(459, "Молебень до св. Йосифа",
				"texts/bohosluzhinnya/molebni/sv-josypha.html", SRC_MOLEBNI_KYRIOS);
		sm.html(454,
				"Молебень до святого великомученика і чудотворця Юрія Побідоносця",
				"texts/bohosluzhinnya/molebni/yuriya-pobidonostsya.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(575, "Молебень до священомученика Йосафата Кунцевича",
				"texts/bohosluzhinnya/molebni/josaphata-kuntsevycha.html",
				SRC_MOLEBNI_MOLYTVA);
		sm.html(438,
				"Чин молебня під час війни проти супротивників",
				"texts/bohosluzhinnya/molebni/vijny.html",
				"Християнський портал КІРІОС http://kyrios.org.ua/spirituality/molebni/12477-chin-molebnja-pid-chas-vijni-proti-suprotivnikiv.html , за матеріалами оф.сайту храму свщм. Климентія Шептицького http://klymentij.blogspot.com/ .");
		sm.html(442, "Молебень в часі посухи",
				"texts/bohosluzhinnya/molebni/posukhy.html", SRC_MOLEBNI_KYRIOS);
		sm.html(450, "Молебень на Святу Пасху",
				"texts/bohosluzhinnya/molebni/sv-paskhu.html", SRC_MOLEBNI_KYRIOS);
		sm.html(452,
				"Молебень за примирення святої Церкви та визволення її від напастей єретиків і віровідступників",
				"texts/bohosluzhinnya/molebni/prymyrennya-tserkvy.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(453, "Молебень на початок Нового року",
				"texts/bohosluzhinnya/molebni/pochatok-novoho-roku.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(455, "Молебень перед початком навчання дітей",
				"texts/bohosluzhinnya/molebni/pered-pochatkom-navch-ditey.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(456,
				"Молебень в часі губительного мору і смертоносної недуги (епідемії)",
				"texts/bohosluzhinnya/molebni/mor-i-neduha.html", SRC_MOLEBNI_KYRIOS);
		sm.html(457, "Подячний молебень у дні громадянських і державних свят",
				"texts/bohosluzhinnya/molebni/hrom-i-derzh-svyata.html",
				SRC_MOLEBNI_KYRIOS);
		sm.html(462, "Молебень за недужих",
				"texts/bohosluzhinnya/molebni/neduzhykh.html", SRC_MOLEBNI_KYRIOS);
		sm.html(910, "Молебний чин з нагоди відкриття Ювілейного року Божого милосердя",
				"texts/bohosluzhinnya/molebni/yuvilejnyj-rik-bozhoho-myloserdya.html",
				"Офіційний інформаційний ресурс Української Греко-Католицької Церкви " +
						"http://news.ugcc.ua/documents/molebniy_chin_z_nagodi_v%D1%96dkrittya_yuv%D1%96leynogo_roku_bozhogo_miloserdya_75477.html")
				.setOfficialUGCCText(true);

		menu.addSubItem(addSluzhbyPryjditePoklonimsya());
		menu.addSubItem(addTrebnyk());

		sm = menu
				.subMenu(
						682,
						"Священна і Божественна Літургія во святих отця нашого Йоана Золотоустого. Рим 1968 (переклад патріарха Йосифа чи прийдіте поклонімся)")
				.setOfficialUGCCText(true);
		sm.html(686, "Молитви перед Літургією",
				"texts/bohosluzhinnya/liturhiya/molytvy-pered-liturhiyeyu.html",
				SRC_LITURHIYA_KYRIOS);
		sm.html(683, "Проскомидія",
				"texts/bohosluzhinnya/liturhiya/proskomydiya.html",
				SRC_LITURHIYA_KYRIOS);
		sm.html(684, "Літургія Слова", "texts/bohosluzhinnya/liturhiya/slova.html",
				SRC_LITURHIYA_KYRIOS);
		sm.html(685, "Літургія Жертви",
				"texts/bohosluzhinnya/liturhiya/zhertvy.html", SRC_LITURHIYA_KYRIOS);

		menu.html(
				893,
				"Священна і Божественна Літургія святого отця нашого Йоана Золотоустого, друге видання, доповнене. Львів: Місіонер 1997. (офіційний переклад)",
				"texts/bohosluzhinnya/liturhiya.html",
				"Молитовник для української родини. Релігійне видавництво \"Ріки води живої\". Львів-2011")
				.setOfficialUGCCText(true);

		return menu;
	}

	private MenuItemSubMenu addSluzhbyPryjditePoklonimsya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(714, "Служби");

		MenuItemSubMenu sm = menu.subMenu(715, "Служби на різні потреби");
		sm.web(716, "Служба за померлих",
				"texts/bohosluzhinnya/sluzhby/za-pomerlykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.web(717, "Служба на всяке прошення",
				"texts/bohosluzhinnya/sluzhby/na-vsyake-proshennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.web(718, "Служба подяки", "texts/bohosluzhinnya/sluzhby/podyaky.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.web(719, "Служба про поміч Святого Духа",
				"texts/bohosluzhinnya/sluzhby/pro-pomich-sv-dukha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.web(720, "Служба за недужих",
				"texts/bohosluzhinnya/sluzhby/za-neduzhykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.web(721, "Служба за подорожніх",
				"texts/bohosluzhinnya/sluzhby/za-podorozhnikh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.web(909,
				"Літургія про мир під час міжусобного протистояння і за примирення і зупинення заколотів та безладів міських СЛУ-ЛЬВ-1929",
				"texts/bohosluzhinnya/sluzhby/za-myr-i-prymyrennya-pid-chas-protystoyannya.html",
				"Нотатки Никодима - Літургія про мир під час міжусобного протистояння і за примирення і зупинення заколотів та безладів міських СЛУ-ЛЬВ-1929 http://nykodym.blogspot.com/2014/05/1929.html");

		sm = menu.subMenu(728, "Повсякденна служба").setOfficialUGCCText(true);
		sm.web(722, "Понеділок", "texts/bohosluzhinnya/sluzhby/ponedilok.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(723, "Вівторок", "texts/bohosluzhinnya/sluzhby/vivtorok.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(724, "Середа", "texts/bohosluzhinnya/sluzhby/sereda.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(725, "Четвер", "texts/bohosluzhinnya/sluzhby/chetver.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(726, "П’ятниця", "texts/bohosluzhinnya/sluzhby/pyatnytsya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(727, "Субота", "texts/bohosluzhinnya/sluzhby/subota.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(729, "Загальні служби святим").setOfficialUGCCText(
				true);
		sm.web(730, "Служба Пресвятій Богородиці",
				"texts/bohosluzhinnya/sluzhby/zahalni/presv-bohorodytsi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(731, "Служба ангелам",
				"texts/bohosluzhinnya/sluzhby/zahalni/anhelam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(732, "Служба пророкам",
				"texts/bohosluzhinnya/sluzhby/zahalni/prorokam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(733, "Служба апостолам",
				"texts/bohosluzhinnya/sluzhby/zahalni/apostolam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(734, "Служба святителеві",
				"texts/bohosluzhinnya/sluzhby/zahalni/svyatytelevi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(735, "Служба святителям",
				"texts/bohosluzhinnya/sluzhby/zahalni/svyatytelyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(736,
				"Служба преподобним і Христа ради юродивим",
				"texts/bohosluzhinnya/sluzhby/zahalni/prepodobnym-i-khrysta-rady-yurodyvym.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(737, "Служба мученикові",
				"texts/bohosluzhinnya/sluzhby/zahalni/muchenykovi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(738, "Служба мученикам",
				"texts/bohosluzhinnya/sluzhby/zahalni/muchenykam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(739, "Служба священномученикові",
				"texts/bohosluzhinnya/sluzhby/zahalni/svyaschenomuchenykovi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(740, "Служба священномученикам",
				"texts/bohosluzhinnya/sluzhby/zahalni/svyaschenomuchenykam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(741, "Служба преподобномученикові",
				"texts/bohosluzhinnya/sluzhby/zahalni/prepodobnomuchenykovi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(742, "Служба преподобномученикам",
				"texts/bohosluzhinnya/sluzhby/zahalni/prepodobnomuchenykam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(743, "Служба святим мученицям",
				"texts/bohosluzhinnya/sluzhby/zahalni/sv-muchenytsyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(744, "Служба преподобним жінкам",
				"texts/bohosluzhinnya/sluzhby/zahalni/prepodobnym-zhinkam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(745, "Служба ісповідникам",
				"texts/bohosluzhinnya/sluzhby/zahalni/ispovisnykam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(892,
				"Служба безсребреникам і чудотворцям",
				"texts/bohosluzhinnya/sluzhby/zahalni/bezsrebrenykam-i-chudotvortsyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(746, "Служби на рухомі свята").setOfficialUGCCText(
				true);
		sm.web(747, "Свята і велика неділя Пасхи",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-paskhy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(748,
				"Понеділок Світлого тижня",
				"texts/bohosluzhinnya/sluzhby/rukhomi/ponedilok-svitloho-tyzhnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(749,
				"Вівторок Світлого тижня",
				"texts/bohosluzhinnya/sluzhby/rukhomi/vivtorok-svitloho-tyzhnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(750, "Середа Світлого тижня",
				"texts/bohosluzhinnya/sluzhby/rukhomi/sereda-svitloho-tyzhnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(751, "Четвер Світлого тижня",
				"texts/bohosluzhinnya/sluzhby/rukhomi/chetver-svitloho-tyzhnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(752,
				"П’ятниця Світлого тижня",
				"texts/bohosluzhinnya/sluzhby/rukhomi/pyatnytsya-svitloho-tyzhnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(753, "Субота Світлого тижня",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-svitloho-tyzhnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(754, "Неділя 2, про Тому",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-2-pro-tomu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(755, "Неділя 3, про мироносиць",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-3-pro-myronosyts.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(756,
				"Неділя 4, про розслабленого",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-4-pro-rozslablenoho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(757, "Середа переполовення",
				"texts/bohosluzhinnya/sluzhby/rukhomi/sereda-perepolovennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(758, "Неділя 5, про самарянку",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-5-pro-samaryanku.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(759,
				"Неділя 6, про сліпородженого",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-6-pro-sliporodzhenoho.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(760, "Четвер Вознесення",
				"texts/bohosluzhinnya/sluzhby/rukhomi/chetver-voznesennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(761,
				"Неділя 7, святих Отців 1-го вселенського собору в Нікеї",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-7-svyatykh-ottsiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(762,
				"Субота 7, перед П’ятдесятницею",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-7-pered-pyatdesyatnytseyu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(763, "Неділя П’ятдесятниці",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-pyatdesyatnytsi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(764, "Понеділок Святого Духа",
				"texts/bohosluzhinnya/sluzhby/rukhomi/ponedilok-sv-dukha.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(765, "Неділя 1, всіх святих",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-1-vsikh-svyatykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(766,
				"Четвер по Неділі всіх святих",
				"texts/bohosluzhinnya/sluzhby/rukhomi/chetver-po-nedili-vsikh-svyatykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(767, "П’ятниця 3-го тижня",
				"texts/bohosluzhinnya/sluzhby/rukhomi/pyatnytsya-3ho-tyzhnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(768, "Субота 3-го тижня",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-3ho-tyzhnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(769, "Неділя 4, всіх святих українського народу",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-4-vsikh-svyatykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(770,
				"Неділя 33, про митаря і фарисея",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-33-pro-mytarya-i-faryseya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(771,
				"Неділя 34, про блудного сина",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-pro-bludnoho-syna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(772, "Субота 35, м’ясопусна",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-35-myasopusna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(773, "Неділя 35, м’ясопусна",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-35-myasopusna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(774, "Субота 36, сиропусна",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-36-syropusna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(775, "Неділя 36, сиропусна",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-36-syropusna.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(776, "Субота 1 посту, великомученика Теодора Тирона",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-1-postu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(777, "Неділя 1 посту, православія",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-1-postu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(778, "Субота 2 посту",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-2-postu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(779, "Неділя 2 посту",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-2-postu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(780, "Субота 3 посту",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-3-postu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(781, "Неділя 3 посту, Хрестопоклонна",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-3-postu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(782, "Субота 4 посту",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-4-postu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(783, "Неділя 4 посту, преподобного Йоана Ліствичника",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-4-postu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(784, "Субота 5 посту, акафістова",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-5-postu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(785, "Неділя 5 посту, преподобної Марії Єгипетської",
				"texts/bohosluzhinnya/sluzhby/rukhomi/nedilya-5-postu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(786, "Субота 6, Лазарева",
				"texts/bohosluzhinnya/sluzhby/rukhomi/subota-6-lazareva.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(787, "Квітна неділя",
				"texts/bohosluzhinnya/sluzhby/rukhomi/kvitna-nedilya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(788, "Святий великий четвер",
				"texts/bohosluzhinnya/sluzhby/rukhomi/svyatyj-velykyj-chetver.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(789, "Свята велика субота",
				"texts/bohosluzhinnya/sluzhby/rukhomi/svyata-velyka-subota.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		sm = menu.subMenu(790, "Служби празників і святим")
				.setOfficialUGCCText(true);
		sm.web(791,
				"1 вересня, Начало Нового церковного року, Собор Пресвятої Богородиці, Преподобного Симеона Стовпника",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/1-veresnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(792, "7 вересня, Передпразденство Різдва Пресвятої Богородиці",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/7-veresnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(793, "8 вересня, Різдво Пресвятої Богородиці",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/8-veresnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(794, "12 вересня",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/12-veresnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(795,
				"Субота перед Воздвиженням",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/subota-pered-vozdvyzhennyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(796,
				"Неділя перед Воздвиженням",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/nedilya-pered-vozdvyzhennyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(797, "14 вересня, Воздвиження чесного Хреста",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/14-veresnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(798, "Субота по Воздвиженні",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/subota-po-vozdvyzhenni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(799,
				"Неділя по Воздвиженні",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/nedilya-po-vozdvyzhenni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(800, "21 вересня",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/21-veresnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(801,
				"26 вересня, Переставлення святого апостола і євангелиста Йоана Богослова",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/26-veresnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(802,
				"28 вересня, Преподобного отця нашого і ісповідника Харитона",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/28-veresnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(803, "1 жовтня, Покров Пресвятої Богородиці",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/1-zhovtnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(804, "6 жовтня, Святого апостола Томи",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/6-zhovtnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(805, "9 жовтня, Святого апостола Якова Алфеєвого",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/9-zhovtnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(806, "11 жовтня, Святих Отців сьомого собору",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/11-zhovtnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(807, "18 жовтня, Святого євангелиста Луки",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/18-zhovtnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(808, "26 жовтня, Великомученика Димитрія Мироточця",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/26-zhovtnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(809,
				"8 листопада, Собор архистратига Михаїла і інших безплотних сил",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/8-lystopada.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(810,
				"12 листопада, Священномученика Йосафата, архиєпископа Полоцького",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/12-lystopada.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(811,
				"13 листопада, Святого Йоана Золотоустого, архиєпископа Царгородського",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/13-lystopada.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(812, "14 листопада, Святого апостола Филипа",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/14-lystopada.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(813, "16 листопада, Святого євангелиста Матея",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/16-lystopada.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(814,
				"20 листопада, Передпразденство Входу в храм Пресвятої Богородиці",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/20-lystopada.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(815, "21 листопада, Вхід у храм Пресвятої Богородиці",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/21-lystopada.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(816, "25 листопада",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/25-lystopada.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(817, "30 листопада, Апостола Андрея Первозваного",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/30-lystopada.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(818, "5 грудня, Преподобного Сави Освященного",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/5-hrudnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(819, "6 грудня, Святого Миколая Чудотворця",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/6-hrudnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(820,
				"9 грудня, Зачаття святої Анни, коли зачала Пресвяту Богородицю",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/9-hrudnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(821, "Неділя святих Праотців",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/nedilya-sv-praottsiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(822,
				"13 грудня, Мучеників Євстратія, Авксентія, Євгенія, Мардарія і Ореста",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/13-hrudnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(823, "Субота перед Різдвом Христовим",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/subota-pered-rizdvom.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(824, "Неділя перед Різдвом Христовим, святих Отців",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/nedilya-pered-rizdvom.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(825, "20-23 грудня, Передпразденство Різдва Христового",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/20-23-hrudnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(826, "24 грудня, Навечір’я Різдва Христового",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/24-hrudnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(827, "25 грудня, Різдво Господа нашого Ісуса Христа",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/25-hrudnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(828, "26 грудня, Собор Пресвятої Богородиці",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/26-hrudnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(829,
				"Субота по Різдві Христовім",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/subota-po-rizdvi-khrestovim.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(830,
				"Неділя по Різдві Христовім",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/nedilya-po-rizdvi-khrestovim.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(831, "27 грудня, Святого первомученика Стефана",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/27-hrudnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(832, "31 грудня",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/31-hrudnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(833,
				"1 січня, Найменування Господа Ісуса Христа; і святого Василія Великого, архиєпископа Кесарії Кападокійської",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/1-sichnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(834,
				"Субота перед Просвіщенням",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/subota-pered-prosvischennyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(835,
				"Неділя перед Просвіщенням",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/nedilya-pered-prosvischennyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(836,
				"2-4 січня, Передпразденство Просвіщення",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/predprazdnenstvo-prosvischennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(837, "5 січня, Навечір’я Просвіщення",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/5-sichnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(838, "6 січня, Святе Богоявлення",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/6-sichnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(839,
				"Субота по Просвіщенні",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/subota-po-prosvischenni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(840,
				"Неділя по Просвіщенні",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/nedilya-po-prosvischenni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(841,
				"11 січня, Преподобного Теодосія Великого, спільного життя начальника",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/11-sichnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(842, "І4 січня",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/14-sichnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(843, "17 січня, Преподобного Антонія Великого",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/17-sichnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(844, "20 січня, Преподобного Євтимія Великого",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/20-sichnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(845,
				"25 січня, Святого Григорія Богослова, архиєпископа Царгородського",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/25-sichnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(846, "27 січня, Перенесення мощів святого Йоана Золотоустого",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/27-sichnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(847,
				"30 січня, Трьох святителів: Василія Великого, Григорія Богослова і Йоана Золотоустого",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/30-sichnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(848, "1 лютого, Передпразденство Стрітення",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/1-lut.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(849, "2 лютого, Стрітення Господа Ісуса Христа",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/2-lut.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(850, "9 лютого", "texts/bohosluzhinnya/sluzhby/nerukhomi/9-lut.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(851,
				"14 лютого, Переставлення преподобного Кирила, учителя слов’янського",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/14-lut.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(852,
				"24 лютого, Перше і друге знайдення голови святого Йоана Хрестителя",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/24-lut.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(853,
				"9 березня, Святих сорок мучеників, замучених у Севастійськім озері",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/9-ber.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(854, "24 березня, Передпразденство Благовіщення",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/24-ber.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(855, "25 березня, Благовіщення Пресвятої Богородиці",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/25-ber.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(856,
				"6 квітня, Переставлення святого Методія, учителя слов’янського",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/6-kv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(857, "23 квітня, Великомученика Юрія Переможця",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/23-kv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(858, "25 квітня, Святого євангелиста Марка",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/25-kv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(859, "30 квітня, Святого апостола Якова, брата Йоана Богослова",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/30-kv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(860, "3 травня, Преподобного Теодосія Печерського",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/3-tr.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(861, "8 травня, Апостола і євангелиста Йоана Богослова",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/8-tr.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(862,
				"9 травня, Перенесення мощів святого Миколая з Мир до міста Барі",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/9-tr.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(863, "10 травня, Святого апостола Симона Зилота",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/10-tr.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(864,
				"11 травня, Святих рівноапостольних Кирила і Методія, учителів слов’янських",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/11-tr.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(865, "21 травня, Святих рівноапостольних Константина і Олени",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/21-tr.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(866,
				"25 травня, Третє віднайдення голови святого Йоана Хрестителя",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/25-tr.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(867, "11 червня, Апостолів Вартоломея і Варнави",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/11-cher.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(868,
				"19 червня, Святого апостола Юди, брата Господнього по плоті",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/19-cher.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(869, "24 червня, Різдво чесного Йоана Предтечі",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/24-cher.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(870, "29 червня, Верховних апостолів Петра і Павла",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/29-cher.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(871, "30 червня, Собор святих дванадцяти апостолів",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/30-cher.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(872,
				"2 липня, Положення чесної ризи Пресвятої Богородиці у Влахерні",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/2-lyp.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(873, "5 липня, Преподобного Атанасія Атонського",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/5-lyp.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(874, "10 липня, Преподобного Антонія Печерського",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/10-lyp.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(875,
				"11 липня, Переставлення блаженної Ольги, княгині Київської, у святому хрещенні названої Оленою",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/11-lyp.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(876,
				"15 липня, Святого рівноапостола, великого князя Володимира, у святому хрещенні названого Василієм",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/15-lyp.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(877, "І6 липня, Святих Отців шести вселенських соборів",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/16-lyp.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(878, "20 липня, Святого пророка Іллі",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/20-lyp.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(879,
				"24 липня, Святих мучеників Бориса і Гліба, у святому хрещенні названих Романом і Давидом",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/24-lyp.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(880,
				"25 липня, Успення святої Анни, матері Пресвятої Богородиці",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/25-lyp.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(881, "27 липня, Святого великомученика і цілителя Пантелеймона",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/27-lyp.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(882, "5 серпня, Передпразденство Переображення",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/5-ser.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(883, "6 серпня, Переображення Ісуса Христа",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/6-ser.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(884, "9 серпня, Святого апостола Матія",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/9-ser.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(885, "13 серпня",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/13-ser.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(886,
				"14 серпня, Передпразденство Успення. I перенесення мощів преподобного Теодосія, ігумена Печерського",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/14-ser.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(887, "15 серпня, Успення Пресвятої Богородиці",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/15-ser.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(888, "23 серпня",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/23-ser.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(889, "29 серпня, Усікновення голови святого Йоана Хрестителя",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/29-ser.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		sm.web(890, "31 серпня, Положення чесного пояса Пресвятої Богородиці",
				"texts/bohosluzhinnya/sluzhby/nerukhomi/31-ser.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);

		menu.web(891, "Воскресна служба восьми гласів",
				"texts/bohosluzhinnya/sluzhby/voskresna-sluzhba-8-hlasiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);

		return menu;
	}

	private MenuItemSubMenu addMolytvyNaKozhenDen() {
		MenuItemSubMenu menu = new MenuItemSubMenu(ID_FOR_EVERY_DAY, "Молитви на кожен день")
				.setOfficialUGCCText(true);
		menu.html(86, "Молитва ранішнього намірення",
				"texts/na-kozhen-den/ranizhnoho-namirennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(87, "Молитва св. Івана Золотоустого на кожну годину дня",
				"texts/na-kozhen-den/iv-zlatoust-kozn-hod-dnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(88, "Неділя", "texts/na-kozhen-den/nedilya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(89, "Понеділок", "texts/na-kozhen-den/ponedilok.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(90, "Вівторок", "texts/na-kozhen-den/vivtorok.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(91, "Середа і п’ятниця",
				"texts/na-kozhen-den/sereda-i-pyatnytsya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(92, "Четвер", "texts/na-kozhen-den/chetver.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		menu.html(93, "Субота", "texts/na-kozhen-den/subota.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA);
		return menu;
	}

	private MenuItemSubMenu addAkafisty() {
		MenuItemSubMenu menu = new MenuItemSubMenu(94, "Акафісти");
		menu.html(95, "Про Акафіст", "texts/akafisty/pro-akafist.html",
				SRC_AKAFISTY_MOLYTVA);
		// Матері Божої
		menu.text(96, "Акафіст до Благовіщення Пречистої Діви Марії",
				"texts/akafisty/blahovischennyu-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(97, "Акафiст до Пресвятої Богородицi",
				"texts/akafisty/do-presv-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(98, "Акафіст до Матері Божої на честь Її Покрову",
				"texts/akafisty/pokrovu-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(99, "Акафіст на честь введення в храм Пресвятої Богородиці",
				"texts/akafisty/vvedennyu-v-khram-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(100, "Акафіст на честь Успення Богородиці",
				"texts/akafisty/uspinnyu-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(101, "Акафіст до Матері Божої Неустанної Помочі",
				"texts/akafisty/presv-bohorodytsi-neustannoyi-pomochi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				102,
				"Акафіст до Пресвятої Богородиці на честь Її ікони «Невипивана Чаша»",
				"texts/akafisty/presv-bohorodytsi-nevypyvna-chasha.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				103,
				"Акафіст до Пресвятої Богородиці на честь Її ікони «Всецариця»",
				"texts/akafisty/presv-bohorodytsi-vsecarytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				104,
				"Акафіст Пресвятій Богородиці на честь Її ікони «Годувальниця»",
				"texts/akafisty/presv-bohorodytsi-hoduvalnytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(
				105,
				"Акафіст до Пресвятої Богородиці на честь її ікони «Цілителька»",
				"texts/akafisty/presv-bohorodytsi-tsilytelnytsya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(106, "Акафіст до Зарваницької Матері Божої",
				"texts/akafisty/materi-bozhoji-zarvanytskoji.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(107, "Акафіст до Зарваницької Матері Божої (нова версія)",
				"texts/akafisty/materi-bozhoji-zarvanytskoji-nova.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(108, "Акафіст до Матері Божої Крилоської",
				"texts/akafisty/materi-bozhoji-kryloskoji.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(109, "Акафіст до Самбірської Богородиці",
				"texts/akafisty/do-sambirskoji-bohorodytsi.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(110, "Акафіст до Унівської Богородиці",
				"texts/akafisty/do-univskoji-bohorodytsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(
				111,
				"Акафіст до Матері Божої на честь Її ікони «Прибавлення розуму»",
				"texts/akafisty/do-bohorodytsi-na-chest-ikony-prybavlennya-rozumu.html",
				SRC_AKAFISTY_MOLYTVA);
		// Пресвятої Тройці
		menu.text(112, "Акафіст до Пресвятої Трійці",
				"texts/akafisty/presvyatoji-trojtsi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(113, "Акафіст до Святого Духа",
				"texts/akafisty/svyatoho-dukha.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(114, "Акафіст до Ісуса Христа",
				"texts/akafisty/isusa-khrysta.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(116, "Акафіст до Христових Страстей",
				"texts/akafisty/khrystovykh-strastej.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(117, "Акафіст до Живоносного Гробу і Воскресіння Христа",
				"texts/akafisty/zhyvonosnoho-hrobu-i-voskresinnya.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(118, "Акафіст до Воскресіння Христового",
				"texts/akafisty/voskresinnya-khrystovoho.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(119, "Акафіст до Причастя Святих Христових Таїн",
				"texts/akafisty/prychastya-tain.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(120, "Акафіст подяки Ісусу Христу «Слава Богові за все»",
				"texts/akafisty/podyaky-isusu.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(121, "Акафіст «Абетка покаяння»",
				"texts/akafisty/abetka-pokayannya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(122, "Акафіст - подяка Пресвятій Трійці",
				"texts/akafisty/podyaka-trijtsi.html", SRC_AKAFISTY_MOLYTVA);
		// Святі і мученики
		menu.text(123, "Акафіст до Собору дванадцяти Апостолів Христових",
				"texts/akafisty/do-soboru-apostoliv.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(124, "Акафіст до священомученика Йосафата",
				"texts/akafisty/do-josaphata.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(125, "Акафіст до священомученика Йосафата (другий варіант)",
				"texts/akafisty/do-josaphata-2.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(126, "Акафіст до святого київського Аскольда",
				"texts/akafisty/kyivskoho-askolda.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(127, "Акафіст до святителя Миколая",
				"texts/akafisty/mykolaya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(564, "Акафіст до святого Йосифа Обручника",
				"texts/akafisty/josypha.html", SRC_AKAFISTY_KYRIOS);
		menu.text(128, "Акафіст до блаженного Омеляна Ковча",
				"texts/akafisty/omelyana-kovcha.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(129, "Акафіст до святого отця Піо",
				"texts/akafisty/otsya-pio.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(130, "Акафіст до святого Романа Солодкоспівця",
				"texts/akafisty/romana-solodkospivtsya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(131, "Акафіст до святого Івана Предтечі",
				"texts/akafisty/ivana-predtechi.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(132, "Акафіст до святого Василія Великого",
				"texts/akafisty/vasyliya-velykoho.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(133, "Акафіст до святого Агапіта", "texts/akafisty/ahapita.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(134, "Акафіст до святих Апостолів Петра і Павла",
				"texts/akafisty/petra-i-pavla.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(135, "Акафіст до святого Пантелеймона Цілителя",
				"texts/akafisty/panteleymona-tsilytelya.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(136, "Акафіст до святої Терези від Дитятка Ісус",
				"texts/akafisty/terezy-vid-isusa.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(137, "Акафіст до святої Філомени", "texts/akafisty/filomeny.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(138, "Акафіст до святої Варвари", "texts/akafisty/varvary.html",
				SRC_AKAFISTY_MOLYTVA);
		menu.text(139, "Акафіст до святої Параскеви",
				"texts/akafisty/paraskevy.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(
				140,
				"Акафіст Дрогобицьким священомученикам Якиму, Віталію та Северіяну",
				"texts/akafisty/drohobytskomu-yakymu.html", SRC_AKAFISTY_MOLYTVA);
		// Інші
		menu.text(141, "Акафіст до Архистратига Михаїла",
				"texts/akafisty/mykhajila.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(142, "Акафіст до Архистратига Михаїла (другий варіант)",
				"texts/akafisty/mykhajila-2.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(143,
				"Акафіст покаянний жінок, які вбили немовлят у своїй утробі",
				"texts/akafisty/zhinok-vbyly-nemovlyat.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(144, "Акафіст за упокій померлих (багатьох)",
				"texts/akafisty/dushi-pomerlykh.html", SRC_AKAFISTY_MOLYTVA);
		menu.text(145, "Акафіст за померлого (одного)",
				"texts/akafisty/pomerloho-odnoho.html", SRC_AKAFISTY_MOLYTVA);
		return menu;
	}

	private MenuItemSubMenu addKatekhPravdy() {
		MenuItemSubMenu menu = new MenuItemSubMenu(147, "Катехизмові правди");
		menu.html(146, "Заповіді", "texts/kateh-pravdy/zapovidi.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(148, "Чесноти", "texts/kateh-pravdy/chesnoty.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(149, "Пости та загальниці",
				"texts/kateh-pravdy/posty-ta-zahalnytsi.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(150, "Гріхи", "texts/kateh-pravdy/hrikhy.html",
				SRC_DODATOK_KATEKHYZMU_2012);
		menu.html(151, "Християнський привіт",
				"texts/kateh-pravdy/khryst-pryvit.html", SRC_DODATOK_KATEKHYZMU_2012);
		return menu;
	}

	private MenuItemSubMenu addProSpovidIPrychastya() {
		MenuItemSubMenu menu = new MenuItemSubMenu(401, "Сповідь та причастя");
		menu.html(152, "Про щастя людини", "texts/pro-spovid/pro-schastya.html",
				SRC_PRO_SPOVID);
		menu.html(153, "Церква як Божа установа",
				"texts/pro-spovid/cerkva-yak-bozha-ustanova.html", SRC_PRO_SPOVID);
		menu.html(154, "Сором у визнанні гріхів",
				"texts/pro-spovid/sorom-u-vyznanni-hrikhiv.html", SRC_PRO_SPOVID);
		menu.html(155, "Чи всі сповіді є добрими?",
				"texts/pro-spovid/chy-vsi-spovidi-dobri.html", SRC_PRO_SPOVID);
		menu.html(156, "Що потрібно знати, щоб сповідатися?",
				"texts/pro-spovid/scho-potribno-znaty.html", SRC_PRO_SPOVID);
		menu.html(157, "Святе Причастя", "texts/pro-spovid/svyate-prychastya.html",
				SRC_PRO_SPOVID);
		menu.html(158, "Приготування до сповіді",
				"texts/pro-spovid/prohotuvannya.html", SRC_PRO_SPOVID);
		menu.html(159, "Деякі додаткові зауваги",
				"texts/pro-spovid/dodatkovi-zauvahy.html", SRC_PRO_SPOVID);
		menu.html(160, "Сповідь", "texts/pro-spovid/spovid.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(161, "Молитви в часі сповіді",
				"texts/pro-spovid/molytvy-v-chasi-spovidi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(162, "Молитви перед святим причастям",
				"texts/pro-spovid/molytvy-pered-prychastyam.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(163, "Молитви по святім причастю",
				"texts/pro-spovid/molytvy-pislya-prychastya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(712, "Молитовне правило до Святого Причастя",
				"texts/pro-spovid/molytovne-pravylo.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		return menu;
	}

	private MenuItemSubMenu addMolytvyRizniPotreby() {
		MenuItemSubMenu menu = new MenuItemSubMenu(ID_FOR_EVERY_OCASION,
				"Молитви на різні потреби");

		MenuItemSubMenu sm = menu.subMenu(165, "Молитви для вагітних");
		sm.text(166, "Молитва вагітної жінки",
				"texts/rizni-potreby/dlya-vahitnykh/vahitnoi-zhinky.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.text(167,
				"Молитва – Благословення вагітних жінок",
				"texts/rizni-potreby/dlya-vahitnykh/blahoslovennya-vahitnykh-zhinok.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(168, "Молитва матері, яка очікує дитину",
				"texts/rizni-potreby/dlya-vahitnykh/materi-ochikuye-dytynu.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(169,
				"Молитва до Богородиці для допомоги жінкам у часі пологів",
				"texts/rizni-potreby/dlya-vahitnykh/do-bohorodytsi-dopomohy-pry-polohakh.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(170, "Молитва вагітних жінок за успішні пологи",
				"texts/rizni-potreby/dlya-vahitnykh/za-uspishni-polohy.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(171, "Молитва при важких пологах",
				"texts/rizni-potreby/dlya-vahitnykh/pry-vazhkykh-polohakh.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.text(172,
				"Молитва – Благословення жінки після пологів",
				"texts/rizni-potreby/dlya-vahitnykh/blahoslovennya-pislya-polohiv.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(173,
				"Молитва за ласку годування грудьми",
				"texts/rizni-potreby/dlya-vahitnykh/za-lasku-hoduvannya-hruddyu.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.html(174,
				"Молитва – Благословення вагітних жінок після Літургії",
				"texts/rizni-potreby/dlya-vahitnykh/blahoslovennya-pislya-liturhiyi.html",
				SRC_UROKY_BIBLIYI_DLYA_VAHITNYKH);
		sm.text(673,
				"Молитва матері, що прагне народження зачатої дитини, а батько штовхає на вбивство",
				"texts/rizni-potreby/dlya-vahitnykh/materi-prahne-narodzh-batko-shtovkhaye-na-vbyvstvo.html",
				SRC_MOLYTVY_KYRIOS);

		sm = menu.subMenu(672, "Молитви батьків і дітей");
		sm.html(184, "Молитва батьків за дітей",
				"texts/rizni-potreby/batky-dity/batkiv-za-ditey.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.html(185, "Молитва дітей за батьків",
				"texts/rizni-potreby/batky-dity/ditey-za-batkiv.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		sm.text(674, "Молитва матері за дітей",
				"texts/rizni-potreby/batky-dity/materi-za-ditej.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(675, "Молитва матері за неслухняних дітей",
				"texts/rizni-potreby/batky-dity/materi-za-neslukhnyanykh-ditej.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(676,
				"Молитва подружжя, яке хоче, але не може мати дітей",
				"texts/rizni-potreby/batky-dity/podruzhzhya-khoche-a-ne-mozhe-maty-ditej.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(677, "Молитва за дітей, яким важко дається навчання",
				"texts/rizni-potreby/batky-dity/za-ditej-vazhko-navchannya.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(678, "Молитва при порушенні сну у дітей",
				"texts/rizni-potreby/batky-dity/porushennya-snu-ditej.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(679, "Молитва матері за дітей до Пресвятої Богородиці",
				"texts/rizni-potreby/batky-dity/materi-za-ditej-do-bohorodytsi.html",
				SRC_MOLYTVY_KYRIOS);
		sm.text(680, "Молитва за дітей на початок навчального року",
				"texts/rizni-potreby/batky-dity/za-ditej-pochatok-navch-roku.html",
				SRC_MOLYTVY_KYRIOS);
		sm.html(681,
				"Молитва матері за ненароджених загублених дітей",
				"texts/rizni-potreby/batky-dity/materi-za-nenarodzh-zahublenykh-ditej.html",
				SRC_MOLYTVY_KYRIOS);

		menu.html(4, "Молитви при трапезі", "texts/molytvy-pry-trapezi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(175, "Молитва подяки", "texts/rizni-potreby/podyaky.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(176, "Молитва на всяке прошення",
				"texts/rizni-potreby/vsyake-proshennya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(177, "Молитва до Пресвятої Богородиці",
				"texts/rizni-potreby/do-presv-bohorod.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(178, "Молитва у терпінні", "texts/rizni-potreby/u-terpinni.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(179, "Молитва за недужих", "texts/rizni-potreby/za-neduzhykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(180, "Молитва на всяку неміч",
				"texts/rizni-potreby/na-vsyaku-nemich.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(181, "Молитва при вмираючих",
				"texts/rizni-potreby/pry-vmyrayuchykh.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(182, "Молитва у годині смерти",
				"texts/rizni-potreby/u-hodyni-smerti.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.text(434, "Молитва за померлих",
				"texts/rizni-potreby/za-pomerlykh.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.html(183, "Молитва християнської родини",
				"texts/rizni-potreby/khryst-rodyny.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.text(186, "Молитва за чоловіка (дружину)",
				"texts/rizni-potreby/za-cholovika-druzhynu.html",
				"МОЛИТВА - Родинні молитви http://molytva.at.ua/index/rodinni_molitvi/0-151");
		menu.html(187, "Молитва учня (учениці)", "texts/rizni-potreby/uchnya.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.text(433, "Молитва за вибір стану",
				"texts/rizni-potreby/vybir-stanu.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.html(188, "Молитва за перемогу над спокусами",
				"texts/rizni-potreby/peremoha-nad-spokusamy.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(189, "Молитва про духа покори",
				"texts/rizni-potreby/dukha-pokory.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(190, "Молитва в день іменин",
				"texts/rizni-potreby/v-den-imenyn.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(191, "Молитва за того, хто хоче іти в путь",
				"texts/rizni-potreby/ity-v-put.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(192, "Молитва на благословення дому",
				"texts/rizni-potreby/blahoslovennya-domu.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(193, "Молитва на освячення всякої речі",
				"texts/rizni-potreby/osvyach-rechi.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.html(194, "Молитви за з’єднання всіх християн",
				"texts/rizni-potreby/zyednannya-khrystyyan.html",
				SRC_MOLYTOVNYK_PRYJDITE_POKLONIMSYA).setOfficialUGCCText(true);
		menu.text(436, "Молитва за український народ",
				"texts/rizni-potreby/za-ukr-narod.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		menu.text(435, "Молитва на Новий рік",
				"texts/rizni-potreby/na-novyj-rik.html",
				SRC_MOLYTOVNYK_VIJSKOVOZLUZHBOVTSYA);
		return menu;
	}

	private void addPsaloms(MenuItemSubMenu sm) {
		for (int i = 1; i <= 150; i++) {
			String name = "Псалом " + i;
			if (i >= 10) {
				name += " (" + (i - 1) + ")";
			}
			sm.text(195 + i, name, "texts/psalmy/" + i + ".html", SRC_BIBLIA);
		}
	}

	public List<MenuItemBase> getTopMenuItems() {
		return topMenu;
	}

	private void verifyUniqueId() {
		if (Utils.isDebuggable(application)) {
			Set<Integer> usedIds = new TreeSet<>();
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
		Set<String> sources = new HashSet<>();
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
		List<SearchItem> filtered = new ArrayList<>();

		if (TextUtils.isEmpty(searchPhrase)) {
			return filtered;
		}

		for (MenuItemBase mi : getTopMenuItems()) {
			filter(searchPhrase, filtered, mi);
		}
		return filtered;
	}

	private void filter(String searchPhrase,
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
	}
}
